package com.example.elasticsearchtest.core.search;

import org.elasticsearch.client.Client;

/**
 * <p>
 *     工厂方法模式，获取es连接客户端
 * </p>
 */

public interface EsClientFactory {
	Client getClient() throws Exception;
}
