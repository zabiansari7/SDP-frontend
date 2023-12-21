package de.srh.toolify.frontend.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.srh.toolify.frontend.data.ResponseData;
import org.springframework.http.HttpHeaders;

public class RestClient {

    public static ResponseData requestHttp(String method, String endpointUrl, Object body, Class<?> c) {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData responseObject = new ResponseData();
        String jsonBody = null;
        JsonNode node = null;
        HttpURLConnection connection = null;
        try {
            if (body != null) {
                jsonBody = objectMapper.writeValueAsString(objectMapper.convertValue(body, c));
            }
            URL url = new URL(endpointUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            if (body != null) {
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            try {
                try (InputStream inputStream = connection.getInputStream()) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                        StringBuilder responseStringBuilder = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            responseStringBuilder.append(line);
                        }
                        // Print the server response body
                        //System.out.println("Response Body: " + responseStringBuilder.toString());
                        node = objectMapper.readTree(responseStringBuilder.toString());
                    }
                }

            } catch (IOException e) {
                try (InputStream errorStream = connection.getErrorStream()) {
                    if (errorStream != null) {
                        try (BufferedReader br = new BufferedReader(new InputStreamReader(errorStream))) {
                            StringBuilder responseStringBuilder = new StringBuilder();
                            String line;
                            while ((line = br.readLine()) != null) {
                                responseStringBuilder.append(line);
                            }
                            // Print the server response body
                            //System.out.println("Error Response Body: " + responseStringBuilder);
                            // Convert the error response JSON to a JsonNode
                            node = objectMapper.readTree(responseStringBuilder.toString());
                        }
                    } else {
                        // No error stream available, handle accordingly
                        // Notification.show("No error stream available");
                        System.out.println("ERROR NO STREAM AVAILABLE");
                    }
                }
            }
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        assert connection != null;
        responseObject.setConnection(connection);
        assert node != null;
        responseObject.setNode(node);
        return responseObject;

    }

    public static ResponseData requestHttp(String method, String endpointUrl, Object body, Class<?> c, String accessToken) {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData responseObject = new ResponseData();
        String jsonBody = null;
        JsonNode node = null;
        HttpURLConnection connection = null;
        try {
            if (body != null) {
                jsonBody = objectMapper.writeValueAsString(objectMapper.convertValue(body, c));
            }
            URL url = new URL(endpointUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty(HttpHeaders.AUTHORIZATION, "Bearer " + Objects.requireNonNull(accessToken));
            connection.setDoOutput(true);
            if (body != null) {
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            try {
                try (InputStream inputStream = connection.getInputStream()) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                        StringBuilder responseStringBuilder = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            responseStringBuilder.append(line);
                        }
                        // Print the server response body
                        //System.out.println("Response Body: " + responseStringBuilder);
                        node = objectMapper.readTree(responseStringBuilder.toString());
                    }
                }

            } catch (IOException e) {
                try (InputStream errorStream = connection.getErrorStream()) {
                    if (errorStream != null) {
                        try (BufferedReader br = new BufferedReader(new InputStreamReader(errorStream))) {
                            StringBuilder responseStringBuilder = new StringBuilder();
                            String line;
                            while ((line = br.readLine()) != null) {
                                responseStringBuilder.append(line);
                            }
                            // Print the server response body
                            //System.out.println("Error Response Body: " + responseStringBuilder);
                            // Convert the error response JSON to a JsonNode
                            node = objectMapper.readTree(responseStringBuilder.toString());

                        }
                    } else {
                        // No error stream available, handle accordingly
                        // Notification.show("No error stream available");
                        System.out.println("ERROR NO STREAM AVAILABLE");
                    }
                }
            }
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        assert connection != null;
        responseObject.setConnection(connection);
        assert node != null;
        responseObject.setNode(node);
        return responseObject;

    }
}