package com.github.lyokofirelyte.Divinity.Manager;

import java.util.logging.Level;

import org.bukkit.Bukkit;

import com.github.lyokofirelyte.Divinity.Divinity;
import com.github.lyokofirelyte.Divinity.Storage.DPI;
import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.ChannelCreateEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDeletedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDescriptionEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelPasswordChangedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ServerEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3Listener;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

public class TeamspeakManager {

	private Divinity main;
	
	public boolean active = false;
	private Thread tsThread = null;
	TS3Query query = null;
	
	public TeamspeakManager(Divinity i){
		main = i;
		start();
	}
	
	public void start(){
		
		final String[] cred = main.getSystem().getStr(DPI.TS3_CREDENTIALS).split(" ");
		
		if (cred.length > 1 && !active){
			
			System.out.println("Loading WAQuery!");
			
			tsThread = new Thread(new Runnable(){ public void run(){

				final TS3Config config = new TS3Config();
				config.setHost("50.23.168.200");
				config.setDebugLevel(Level.SEVERE);
				config.setLoginCredentials(cred[0], cred[1]);

				query = new TS3Query(config);
				query.connect();

				final TS3Api api = query.getApi();
				api.selectVirtualServerById(85);
				api.setNickname("WAQuery");
				api.sendChannelMessage("WAQuery is online!");
				System.out.println("WAQuery online!");

				api.registerAllEvents();
				api.addTS3Listeners(new TS3Listener() {

					public void onTextMessage(TextMessageEvent e) {
						
						if (e.getTargetMode().equals(TextMessageTargetMode.CLIENT)){
							String[] args = e.getMessage().split(" ");
							api.sendPrivateMessage(e.getInvokerId(), "Success!");
							
							switch (args.length > 1 ? args[0] : e.getMessage()){
							
								case "//stfu":
									
									for (Client client : api.getClients()){
										api.pokeClient(client.getId(), e.getInvokerName() + " requests that you STFU!");
									}
									
								break;
								
								case "//moveallhere":
									
									Client moveTo = null;
									
									for (Client client : api.getClients()){
										if (client.getId()== e.getInvokerId()){
											moveTo = client;
											break;
										}
									}
									
									for (Client client : api.getClients()){
										api.moveClient(client.getId(), moveTo.getChannelId());
									}
									
									api.sendPrivateMessage(e.getInvokerId(), "Moved " + api.getClients().size() + " clients!");
									
								break;
							}
						} else {
							Bukkit.getConsoleSender().sendMessage(main.AS("&6(TS) &7" + e.getInvokerName() + "&f: " + e.getMessage()));
						}
					}

					public void onServerEdit(ServerEditedEvent e) {
						
					}

					public void onClientMoved(ClientMovedEvent e) {
						
					}

					public void onClientLeave(ClientLeaveEvent e) {
						// ...

					}

					public void onClientJoin(ClientJoinEvent e) {
						api.sendPrivateMessage(e.getClientId(), "Query Console Active! See //help for a list of commands!");
					}

					public void onChannelEdit(ChannelEditedEvent e) {
						// ...

					}

					public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e) {
						// ...
					}

					public void onChannelCreate(ChannelCreateEvent e) {
						
					}

					public void onChannelDeleted(ChannelDeletedEvent e) {
						
					}

					public void onChannelMoved(ChannelMovedEvent e) {
						
					}

					public void onChannelPasswordChanged(ChannelPasswordChangedEvent e) {
						
					}
				});
				
				active = true;
				
			}});
			
			tsThread.start();
		}
	}
	
	public void stop(){
		query.exit();
		System.out.println("WAQuery offline!");
	}
}