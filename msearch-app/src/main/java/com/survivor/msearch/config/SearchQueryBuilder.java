package com.survivor.msearch.config;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import com.survivor.msearch.model.Job;

@Component
public class SearchQueryBuilder {

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	public List<Job> getAll(String text) {

		QueryBuilder query = QueryBuilders.boolQuery()
				.should(QueryBuilders.queryStringQuery(text).lenient(true).field("title").field("location")
						.field("specification").field("type").field("category").field("prerequisite"))
				.should(QueryBuilders.queryStringQuery("*" + text + "*").lenient(true).field("title").field("location")
						.field("specification").field("type").field("category").field("prerequisite"));

		NativeSearchQuery build = new NativeSearchQueryBuilder().withQuery(query).build();
		return elasticsearchTemplate.queryForList(build, Job.class);
	}
}
