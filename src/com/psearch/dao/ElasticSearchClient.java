package com.psearch.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.psearch.model.SProduct;
import com.google.gson.Gson;
import com.psearch.model.ProductSearchQuery;


public class ElasticSearchClient {
	public static RestHighLevelClient client;
	public static ElasticSearchClient instance;
	
	private ElasticSearchClient() {
		client = new RestHighLevelClient(
				RestClient.builder(
						new HttpHost("es01", 9200, "http"),
						new HttpHost("es01", 9201, "http")));
	}

	public static synchronized ElasticSearchClient getInstance() {
		if(instance==null) {
			instance = new ElasticSearchClient();
		}
		
		return instance;
	}
	
	
	
	protected void finalize() {
		try {
			if(client!=null) {
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void importData(List<SProduct> products) {
		try {
			CreateIndexRequest request = new CreateIndexRequest("sproducts");
			request.settings(Settings.builder() 
					.put("index.number_of_shards", 1)
					.put("index.number_of_replicas", 1)
					);
			
			CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);

			for(SProduct s:products) {

				try {
					XContentBuilder builder = XContentFactory.jsonBuilder()
							.startObject()
							.field("id", s.id)
							.field("title", s.title)
							.field("price", s.price)
							.field("formatted_price", s.formatted_price)
							.field("formatted_msrp", s.formatted_msrp)
							.field("image_cover_url", s.image_cover_url)
							.field("category_name", s.category_name)
							.field("country_origin", s.country_origin)
							.field("premium", s.premium)
							.field("supplier_name", s.supplier_name)
							.field("shipping_exclusions", s.shipping_exclusions)
							.endObject();
							
					IndexRequest indexRequest = new IndexRequest("sproducts");
					indexRequest.source(builder);
					IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public List<SProduct> search(ProductSearchQuery sr){
		List<SProduct> results = new ArrayList<SProduct>();
		Gson gson = new Gson();
		try {
			SearchSourceBuilder builder = new SearchSourceBuilder();
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			if(sr.page>0)
				builder.from(sr.page*sr.count);
			
			builder.size(sr.count);
			
			if(sr.query !=null && !sr.query.trim().equals("")) {
				qb.must(QueryBuilders.matchQuery("title", sr.query));
			}
			
			if(sr.price_range!=null && sr.price_range.contains("to")) {
				String [] range = sr.price_range.split("to");
				if(range.length == 2) {
					qb.must(QueryBuilders.rangeQuery("price")
							.from(Integer.parseInt(range[0].trim())*100).to(Integer.parseInt(range[1].trim())*100));
				}
			}
			
			if(sr.premium == true) {
				qb.must(QueryBuilders.termQuery("premium", "true"));
			}
			
			if(sr.category!=null && sr.category!="") {
				MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("category_name", sr.category);
				qb.must(matchQueryBuilder);
			}
			
			if(sr.ships_from!=null && sr.ships_from!="") {
				MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("country_origin", sr.ships_from);
				qb.must(matchQueryBuilder);
			}
			
			if(sr.supplier_name!=null && sr.supplier_name!="") {
				MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("supplier_name", sr.supplier_name);
				qb.must(matchQueryBuilder);
			}
			
			builder.query(qb);
			
			SearchRequest searchRequest = new SearchRequest();
			searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
			searchRequest.source(builder);

			SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
			SearchHit[] searchHits = response.getHits().getHits();
			results = 
			  Arrays.stream(searchHits)
			    .map(hit -> gson.fromJson(hit.getSourceAsString(), SProduct.class))
			    .collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return results;
	}


}
