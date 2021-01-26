package com.example.elasticsearchtest.core.controller;

import com.example.elasticsearchtest.core.org.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *     1、@RestReturn 统一返回值封装
 *     2、统一异常处理
 * </p>
 * @author moubin.mo
 * @date: 2021/1/20 14:41
 */
@RestController
@RequestMapping("/es")
public class EsController {

	@Autowired
	private OrganizationService organizationService;

	@RequestMapping("/mapping")
	@ResponseBody
	public String getMapping(@RequestParam String indexName){
		return organizationService.getMapping(indexName);
	}
}
