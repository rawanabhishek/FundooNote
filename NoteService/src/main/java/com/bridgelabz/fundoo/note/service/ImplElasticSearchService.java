package com.bridgelabz.fundoo.note.service;

import java.util.Map;
import java.util.UUID;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.response.Response;
import com.bridgelabz.fundoo.note.utility.CommonFiles;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ImplElasticSearchService implements IElasticSearchService{
	
	private final String INDEX = "note";

	private final String TYPE = "notes";
	
	    @Autowired
	    private RestHighLevelClient client;

	    @Autowired
	    private ObjectMapper objectMapper;
	    
	    
	 
	    
	    
	

}
