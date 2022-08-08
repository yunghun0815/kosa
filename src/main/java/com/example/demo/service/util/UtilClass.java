package com.example.demo.service.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

@Component
public class UtilClass {
	
	//API 호출 메소드
	//example 
	//String responseData = callApi("https://naver.com", "GET"); 
	public String callApi(String apiUrl, String method, String body) {
		try {
			StringBuilder urlBuilder = new StringBuilder(apiUrl);
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(method);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			
			if (responseCode == 200) { 
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { 
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			return response.toString();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	//String to JsonArray
	public JSONArray stringToJsonArray(String data) throws ParseException {
		
        JSONParser parser = new JSONParser();
		JSONArray jsonArray = (JSONArray)parser.parse(data);
		
		return jsonArray;
	}
	
	//String to JsonObject
	public JSONObject stringToJsonObject(String data) throws ParseException {
		
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(data);
		
		return jsonObject;
	}
	
}
