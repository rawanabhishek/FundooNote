package com.bridgelabz.fundoo.note.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.response.Response;
import com.bridgelabz.fundoo.note.utility.CommonFiles;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ImplElasticSearchService implements IElasticSearchService {

	private final String INDEX = "note";

	private final String TYPE = "notes";

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper objectMapper;

	public Response addDocument(Note note) throws Exception {

		System.out.println("in add service");
		Map<String, Object> documentMapper = objectMapper.convertValue(note, Map.class);
		System.out.println("1");
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, note.getNoteId().toString()).source(documentMapper);
		System.out.println("2");
		IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
		System.out.println("3");
		return new Response(200, CommonFiles.ADD_LABEL_SUCCESS, indexResponse.getResult().name());
	}

	public Response readDocument(String id) throws IOException {
		GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		Map<String, Object> resultMap = getResponse.getSource();
		return new Response(200, CommonFiles.ADD_LABEL_SUCCESS, objectMapper.convertValue(resultMap, Note.class));
	}

	public Response search(String value , String type) throws IOException {

		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(INDEX);
		searchRequest.types(TYPE);

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		//QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("title", value));
		searchSourceBuilder.query(QueryBuilders.matchQuery(type, value));
		searchRequest.source(searchSourceBuilder);

		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println(searchResponse);
		List<Note> noteList = getSearchResult(searchResponse);
		return new Response(200, CommonFiles.ADD_LABEL_SUCCESS, noteList);
	}

	private List<Note> getSearchResult(SearchResponse response) {
		SearchHit[] searchHit = response.getHits().getHits();
		List<Note> noteDocuments = new ArrayList<>();
		for (SearchHit hit : searchHit)
			noteDocuments.add(objectMapper.convertValue(hit.getSourceAsMap(), Note.class));
		return noteDocuments;
	}

}
