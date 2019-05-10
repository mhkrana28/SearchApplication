package com.survivor.msearch.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.survivor.msearch.model.Job;

/**
 * Executes json query dsl and fetches desired job list
 * 
 * @author survivor69
 * @since  2019-05-09
 */
@Service
public class JobSearchService {

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	/**
	 * Process input using basic Native SearchQuery and returns first ten matched
	 * Jobs.
	 * 
	 * @param  String text: Input string by the user from front end.
	 * @return        List<Job>: first ten matched jobs
	 */
	public List<Job> getFirstTenJobs(String text) {
		QueryBuilder query = QueryBuilders.boolQuery()
				.should(QueryBuilders.queryStringQuery(text).lenient(true).field("title").field("location")
				.field("specification").field("type").field("category").field("prerequisite"))
				.should(QueryBuilders.queryStringQuery("*" + text + "*").lenient(true).field("title").field("location")
				.field("specification").field("type").field("category").field("prerequisite"));

		NativeSearchQuery build = new NativeSearchQueryBuilder().withQuery(query).build();
		return elasticsearchTemplate.queryForList(build, Job.class);
	}

	/**
	 * Processes input using elasticsearch scroll option to fetch all matched jobs.
	 * 
	 * @param  String : Input string by the user from front end.
	 * @return        List<job>: all matched jobs.
	 */
	public List<Job> getAllJobs(String text) {
		QueryBuilder query = QueryBuilders.boolQuery()
				.should(QueryBuilders.queryStringQuery(text).lenient(true).field("title").field("location")
				.field("specification").field("type").field("category").field("prerequisite"))
				.should(QueryBuilders.queryStringQuery("*" + text + "*").lenient(true).field("title").field("location")
				.field("specification").field("type").field("category").field("prerequisite"));
		Client client = elasticsearchTemplate.getClient();
		ObjectMapper mapper = new ObjectMapper();
		List<Job> jobList = new ArrayList<>();
		SearchResponse res = null;
		int i = 0;
		while (res == null || res.getHits().hits().length != 0) {
			res = client.prepareSearch("job").setTypes("job").setQuery(query)
					.setSize(100).setFrom((i++) * 100).execute().actionGet();
			res.getHits().forEach(it -> {
			try {
				jobList.add(mapper.readValue(it.getSourceAsString(), Job.class));
			} catch (IOException e) {
				System.out.println("\n Object mapping error" + e.getMessage());				}
			});
		}
		return jobList;
	}

}
