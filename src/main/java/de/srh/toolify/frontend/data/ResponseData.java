package de.srh.toolify.frontend.data;

import java.net.HttpURLConnection;

import com.fasterxml.jackson.databind.JsonNode;

public class ResponseData {
	
	private JsonNode node;
	private HttpURLConnection connection;
	
	public JsonNode getNode() {
		return node;
	}

	public void setNode(JsonNode node) {
		this.node = node;
	}

	public HttpURLConnection getConnection() {
		return connection;
	}
	
	public void setConnection(HttpURLConnection connection) {
		this.connection = connection;
	}
	
	

}
