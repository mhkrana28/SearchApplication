package com.survivor.msearch;

import static org.junit.Assert.assertNotSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.survivor.msearch.service.JobSearchService;
/**
 * TODO Test Case is incomplete
 * 
 * @author survivor69
 * @since 2019-05-09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MSearchApplicationTest {

	private @Autowired JobSearchService service;

	@Test
	public void testGetJobsAsInput() {
		int responseSize = service.getFirstTenJobs("tokyo").size();
		System.out.println(responseSize);
		assertNotSame(0, responseSize);
	}

}
