package com.github.lyokofirelyte.Divinity.Manager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.github.lyokofirelyte.Divinity.Divinity;

public class WebsiteManager {
	
	Divinity api;
	
	public WebsiteManager(Divinity main){
		api = main;
	}

	public JSONObject sendGet(final String folder){
		
		JSONObject result = null;

		try {
			
			String url = "http://worldsapart.no-ip.org:9090/" + folder;
			 
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			
			in.close();
	 
			result = (JSONObject) new JSONParser().parse(response.toString());
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
 
	public JSONObject sendPost(final String folder, final Map<String, String> map){
		
		JSONObject result = null;
		
		try {
			
			
			JSONObject json = new JSONObject();
			
			for (String key : map.keySet()){
				json.put(key, map.get(key));
			}
			
			 
			String url = "http://worldsapart.no-ip.org:9090/" + folder;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setDoOutput(true);
			
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(json.toJSONString());
			wr.flush();
			wr.close();
	 
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			
			in.close();
			
			result = (JSONObject) new JSONParser().parse(response.toString());
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
}