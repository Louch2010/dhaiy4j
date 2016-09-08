package com.louch2010.dhaiy4j;

import com.louch2010.dhaiy4j.client.Client;

public class Dhaiy extends Client{

	public Dhaiy(String host, int port)  throws Exception{
		super.connect(host, port, null, null, null);
	}
	
	public Dhaiy(String ip, int port, String table, String pwd, String[] listenEvents) throws Exception{
		super.connect(ip, port, table, pwd, listenEvents);
	}
}
