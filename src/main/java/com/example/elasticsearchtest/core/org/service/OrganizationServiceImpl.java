package com.example.elasticsearchtest.core.org.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author moubin.mo
 * @date: 2021/1/25 18:14
 */
@Component
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationSearcher organizationSearcher;

	@Override
	public String getMapping(String indexName) {
		return organizationSearcher.getMapping();
	}
}
