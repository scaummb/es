package com.example.elasticsearchtest.core.search;

import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequestBuilder;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author moubin.mo
 * @date: 2021/1/18 17:55
 */
//抽象类，以索引作为出发点，分别拓展到企业&项目等概念
public abstract class AbstractElasticSearch {
	@Autowired
	private EsClientFactory esClientFactory;

	@Value("${elastic.index}")
	String elasticIndex;

	public String getElasticIndex() {
		return elasticIndex;
	}

	public abstract String getIndexType();

	/**
	 * <p>
	 *     获取es客户端连接
	 * </p>
	 */
	public Client getClient() {
		try {
			return esClientFactory.getClient();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean indexExists(){
		return indexExists(getElasticIndex());
	}

	public boolean indexExists(String indexName){
		IndicesAdminClient indices = getClient().admin().indices();
		return indices.exists(new IndicesExistsRequest(indexName)).actionGet().isExists();
	}

	public String getMapping(){
		return getMapping(getElasticIndex(), getIndexType());
	}

	public String getMapping(String elasticIndex, String indexType){
		GetMappingsRequestBuilder getmapping = getClient().admin().indices().prepareGetMappings();
		MappingMetaData response = getmapping.get().getMappings().get(elasticIndex).get(indexType);
		String mapping = new String(response.source().uncompressed());
		return mapping;
	}

	public void refresh(){
		refresh(getElasticIndex());
	}

	public void refresh(String indices){
		getClient().admin().indices().refresh(new RefreshRequest(indices)).actionGet();
	}

	public void feedDoc(String id, XContentBuilder b){
		IndexRequestBuilder irb = getClient().prepareIndex(getElasticIndex(), getIndexType(), id).setSource(b).setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
		irb.execute().actionGet();
	}

	public String delete(String indexName, String type, String id){
		DeleteRequestBuilder deleteRequestBuilder = getClient().prepareDelete(indexName, type, id);
		return 	deleteRequestBuilder.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE).execute().actionGet().getId();
	}

	public void deleteById(String id){
		getClient().prepareDelete(getElasticIndex(), getIndexType(), id).setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE).execute().actionGet();
	}

	public Map<String, Object> getDocById(String id){
		return getClient().prepareGet(getElasticIndex(), getIndexType(), id).setRefresh(true).execute().actionGet().getSource();
	}

	public void deleteAll(){
		getClient().prepareDelete().setIndex(getElasticIndex()).setType(getIndexType()).execute().actionGet();
		refresh();
	}

	public List<String> analyze(String analyzer){
		AnalyzeResponse analyzeTokens = getClient().admin().indices().analyze(new AnalyzeRequest(getElasticIndex()).analyzer(analyzer)).actionGet();

		List<String> tokens = new ArrayList<>();
		for (AnalyzeResponse.AnalyzeToken tok : analyzeTokens.getTokens()) {
			tokens.add(tok.getTerm());
		}
		return tokens;
	}

	protected List<String> getStringIds(SearchResponse rsp){
		List<String> results = new ArrayList();
		SearchHit[] docs = rsp.getHits().getHits();
		for (SearchHit sd : docs) {
			results.add(sd.getId());
		}
		return results;
	}

}
