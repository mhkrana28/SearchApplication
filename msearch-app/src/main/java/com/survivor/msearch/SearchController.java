package com.survivor.msearch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.survivor.msearch.model.Job;
import com.survivor.msearch.service.JobSearchService;

@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private JobSearchService jobSearchService;

	@GetMapping(value = "/from/job")
	public List<Job> getAll(@RequestParam("text") final String text) {
		return jobSearchService.getAllJobs(text);
	}
}
