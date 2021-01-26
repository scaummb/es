package com.example.elasticsearchtest.core.org.service;

import com.example.elasticsearchtest.core.search.AbstractElasticSearch;
import org.springframework.stereotype.Component;

/**
 * @author moubin.mo
 * @date: 2021/1/25 18:15
 */
@Component
public class OrganizationSearcher extends AbstractElasticSearch {
	private final static String INDEX_TYPE = "enterprise";

	@Override
	public String getIndexType() {
		if (indexExists()){
			return INDEX_TYPE;
		}
		throw new RuntimeException("OrganizationSearcher cannot findout any indexs..");
	}
}
