package com.survivor.msearch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.survivor.msearch.config.SearchQueryBuilder;
import com.survivor.msearch.model.Job;

@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private SearchQueryBuilder searchQueryBuilder;

	@GetMapping(value = "/from/job")
	public List<Job> getAll(@RequestParam("text") final String text) {
		return searchQueryBuilder.getAll(text);
	}
}
