package com.bridgelabz.fundoo.note.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.elasticsearch.action.delete.DeleteRequest;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

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

		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, note.getNoteId().toString()).
				source(objectMapper.convertValue(note, Map.class));

		IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
	
		return new Response(200, CommonFiles.ADD_DOCUMENT, indexResponse.getResult().name());
	}
	
	

	public Response readDocument(String id) throws IOException {
		GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		Map<String, Object> resultMap = getResponse.getSource();
		return new Response(200, CommonFiles.FETCH_DOCUMENT, objectMapper.convertValue(resultMap, Note.class));
	}
	
	

	public Response search(String value, String type) throws IOException {

		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(INDEX);
		searchRequest.types(TYPE);

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		searchSourceBuilder.query(QueryBuilders.matchQuery(type, value));
		searchRequest.source(searchSourceBuilder);

		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHit[] searchHit = searchResponse.getHits().getHits();
		List<Note> noteDocuments = new ArrayList<>();
		for (SearchHit hit : searchHit)
			noteDocuments.add(objectMapper.convertValue(hit.getSourceAsMap(), Note.class));

		List<Note> noteList = noteDocuments;
		return new Response(200, CommonFiles.SEARCH_SUCCESSS, noteList);
	}
	
	
	

	public Response deleteDocument(String id) throws IOException {

		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);

		client.delete(deleteRequest, RequestOptions.DEFAULT);

		return new Response(200, CommonFiles.DELETE_SUCCESS, true);
	}
	
	
	public Response searchByTitleDescription(String value) throws IOException {

		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(INDEX);
		searchRequest.types(TYPE);

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		searchSourceBuilder.query(QueryBuilders.boolQuery().should(QueryBuilders.queryStringQuery(value).
						field(CommonFiles.TITLE).field(CommonFiles.DESCRIPTION)));
		searchRequest.source(searchSourceBuilder);

		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHit[] searchHit = searchResponse.getHits().getHits();
		List<Note> noteDocuments = new ArrayList<>();
		for (SearchHit hit : searchHit)
			noteDocuments.add(objectMapper.convertValue(hit.getSourceAsMap(), Note.class));

		List<Note> noteList = noteDocuments;
		return new Response(200, CommonFiles.SEARCH_SUCCESSS, noteList);
	}
	
	
	

}
