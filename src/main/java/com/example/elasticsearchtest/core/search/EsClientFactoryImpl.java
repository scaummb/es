package com.example.elasticsearchtest.core.search;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author moubin.mo
 * @date: 2021/1/18 18:02
 */
@Service
public class EsClientFactoryImpl implements EsClientFactory, ApplicationListener<ApplicationPreparedEvent> {
	@Value("${elastic.nodes.hosts}")
	private String nodeHosts;

	@Value("${elastic.nodes.ports}")
	private String nodePorts;

	@Value("${cluster.name:elasticsearch}")
	private String clusterName;

	private Client client = null;

	@Override
	public Client getClient() throws Exception {
		if (client == null){
			throw new Exception("Elasticsearch initialize error.");
		}
		return client;
	}

	@Override
	public void onApplicationEvent(ApplicationPreparedEvent applicationPreparedEvent) {
		setup();
	}

	private void setup() {
		try {
			setupClient();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 *     getConnection:(获取es连接)
	 * </p>
	 */
	private void setupClient() throws UnknownHostException {
		Settings settings = Settings.builder().put("cluster.name", clusterName).build();
		TransportClient client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new TransportAddress(InetAddress.getByName(nodeHosts), 9300));
	}
}
