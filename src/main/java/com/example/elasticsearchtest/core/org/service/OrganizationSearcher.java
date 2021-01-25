package com.example.elasticsearchtest.core.org.service;

import com.example.elasticsearchtest.core.search.AbstractElasticSearch;
import org.springframework.stereotype.Component;

/**
 * @author moubin.mo
 * @date: 2021/1/25 18:15
 */
@Component
public class OrganizationSearcher extends AbstractElasticSearch {
	@Override
	public String getIndexType() {
		if (indexExists()){
			return "organization";
		}
		throw new RuntimeException("OrganizationSearcher cannot findout any indexs..");
	}
}
