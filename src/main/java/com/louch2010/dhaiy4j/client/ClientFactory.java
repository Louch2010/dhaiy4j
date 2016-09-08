package com.louch2010.dhaiy4j.client;

import java.util.HashMap;
import java.util.Map;

public class ClientFactory {
	private static Map<String, Client> clientMap = new HashMap<String, Client>();
	public ClientFactory(){
		
	}
	
	public Client getClient(String ip, int port) throws Exception{
		String key = ip + port;
		Client client = clientMap.get(key);
		if(client != null && client.isConnectAlive()){
			return client;
		}
		Client c = new Client();
		c.connect(ip, port, null, null, null);
		return null;
	}
}
