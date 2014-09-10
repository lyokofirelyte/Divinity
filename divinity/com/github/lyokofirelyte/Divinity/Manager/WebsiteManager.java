package com.github.lyokofirelyte.Divinity.Manager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.github.lyokofirelyte.Divinity.Divinity;
import com.github.lyokofirelyte.Divinity.Manager.JSONManager.JSONClickType;
import com.google.common.collect.ImmutableMap;

public class WebsiteManager implements Runnable {
	
	Divinity api;
	public List<String> messages = new ArrayList<String>();
	public List<String> onlinePlayers = new ArrayList<String>();
	private boolean failed = false;
	
	public WebsiteManager(Divinity main){
		api = main;
	}
	
	@Override
	public void run(){
		
		try {
		
			onlinePlayers = new ArrayList<String>();
			
			for (Player p : Bukkit.getOnlinePlayers()){
				onlinePlayers.add(p.getName());
			}
			
			Map<String, Object> map = new HashMap<>();
			map.put("type", "minecraft_refresh");
			map.put("players", onlinePlayers);
			
			JSONObject obj = sendPost("/api/chat", map);
			List<String> message = (List<String>) obj.get("message");
			String type = (String) obj.get("type");
			
			if (type.equals("send")){
				for (String s : message){
					if (!messages.contains(s)){
						messages.add(s);
						String noTimeStamp = s.substring(s.indexOf("] ")+1);
						String user = noTimeStamp.split(":")[0];
						String send = api.AS("&6W &8\u2744&7&o" + user + "&f: " + s.substring(s.indexOf(user) + user.length() + 2));
						api.json.create("", ImmutableMap.of(
							send, ImmutableMap.of(
								JSONClickType.NONE, new String[]{
									"&6&oThis user is using our website chat!"
								}
							)
						)).sendToAllPlayers();
					}
				}
			}
			
			if (messages.size() > 100){
				messages.remove(0);
			}
			
		} catch (Exception e){
			if (!failed){
				failed = true;
				System.out.println("Error connecting to the website. There will be no further error messages.");
			}
		}
	}

	public JSONObject sendGet(final String folder){
		
		JSONObject result = null;

		try {
			
			String url = "http://worldsapart.no-ip.org:9090" + folder;
			 
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
			
		} catch (Exception e){}
		
		return result;
	}
 
	public JSONObject sendPost(final String folder, final Map<String, Object> map){
		
		JSONObject result = null;
		
		try {
			
			
			JSONObject json = new JSONObject();
			
			for (String key : map.keySet()){
				json.put(key, map.get(key));
			}
			
			 
			String url = "http://worldsapart.no-ip.org:9090" + folder;
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
			
		} catch (Exception e){}
		
		return result;
	}
}