package com.survivor.msearch.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.survivor.msearch.model.Job;
import com.survivor.msearch.repository.JobRepository;

/**
 * Simply scrape first five pages of jobs of JPMorgan Chages and <br>
 * save it to elasticsearch repository.
 * 
 * @author survivor69
 * @since  2019-05-10
 */
@Component
public class JobLoader {

	private static final String URL = "https://jobs.jpmorganchase.com";
	private static final String LINK_URL_PATH = "//td[@class='coloriginaljobtitle']/a";
	private static final String TITLE_EL_PATH = "//div[@class='job-fields']/h1";
	private static final String LOC_EL_PATH = "//div[@class='job-subheader']/div[2]/span";
	private static final String CAT_EL_PATH = "//div[@class='job-subheader']/div[3]/span";
	private static final String SPEC_EL_PATH = "//div[@class='desc']";

	private static Long idCount = 0L;

	@Autowired
	ElasticsearchOperations operations;

	@Autowired
	JobRepository repo;

	@Scheduled(fixedRate = 4356478956L, initialDelay = 500)
	public void loadAll() {

		operations.putMapping(Job.class);
		System.out.println("\n\nJob Scraping Started...");
		scrapeJob();
		System.out.printf("\n\nScraping Completed");

	}

	@SuppressWarnings("unchecked")
	private void scrapeJob() {
		WebClient client = getClient();
		for (int page = 1; page <= 5; page++) {
			try {

				HtmlPage summaryPage = client.getPage(URL + "/ListJobs/All/Page-" + page);
				List<HtmlAnchor> linkList = (List<HtmlAnchor>) summaryPage.getByXPath(LINK_URL_PATH);
				linkList.forEach(it -> {
					Job job = getJob(it.getAttribute("href"));
					if (job != null) {
						repo.save(job);
						System.out.println("\n\nSaved Job: " + job.getId() + "-" + job.getTitle() + "\n\n");
					}
				});
			} catch (FailingHttpStatusCodeException | IOException e) {
				System.out.println("Summary page error." + e.getMessage());
			}
		}

	}

	private Job getJob(String url) {
		WebClient client = getClient();
		try {
			HtmlPage detailPage = client.getPage(URL + url);
			Job job = new Job();
			job.setId(++idCount);
			job.setTitle(((HtmlElement) detailPage.getBody().getFirstByXPath(TITLE_EL_PATH)).getTextContent());
			job.setCategory(((HtmlElement) detailPage.getBody().getFirstByXPath(CAT_EL_PATH)).getTextContent());
			job.setLocation(((HtmlElement) detailPage.getBody().getFirstByXPath(LOC_EL_PATH)).getTextContent());
			job.setSpecification(((HtmlElement) detailPage.getBody().getFirstByXPath(SPEC_EL_PATH)).getTextContent());
			return job;
		} catch (FailingHttpStatusCodeException | IOException e) {
			System.out.println("Failed to load detail page. " + url + "\n" + e.getMessage());
		}
		return null;
	}

	private WebClient getClient() {
		WebClient client = new WebClient(BrowserVersion.FIREFOX_45);
		client.setJavaScriptTimeout(10 * 1000);
		client.getOptions().setThrowExceptionOnScriptError(false);
		return client;
	}

}
