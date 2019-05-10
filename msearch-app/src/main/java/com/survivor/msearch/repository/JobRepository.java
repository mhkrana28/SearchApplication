package com.survivor.msearch.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.survivor.msearch.model.Job;

public interface JobRepository extends ElasticsearchRepository<Job, Long> {
	List<Job> findByTitle(String title);
}
