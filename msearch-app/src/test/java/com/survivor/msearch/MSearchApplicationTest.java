package com.survivor.msearch;

import static org.junit.Assert.assertNotSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.survivor.msearch.config.SearchQueryBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MSearchApplicationTest {

	private @Autowired SearchQueryBuilder builder;

	@Test
	public void testGetJobsAsInput() {
		int responseSize = builder.getAll("tokyo").size();
		System.out.println(responseSize);
		assertNotSame(0, responseSize);
	}

}
