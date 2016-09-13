package com.louch2010.dhaiy4j;

import com.louch2010.dhaiy4j.client.Client;

/** 
  * @Description: Dhaiy对象
  * @author: luocihang
  * @date: 2016年9月13日 下午10:02:20
  * @version: V1.0 
  * @see：
  */
public class Dhaiy extends Client{

	public Dhaiy(String host, int port)  throws Exception{
		super.connect(host, port, null, null, null, 30, true);
	}
	
	public Dhaiy(String ip, int port, String table, String pwd, String[] listenEvents, int timeout, boolean keepAlive) throws Exception{
		super.connect(ip, port, table, pwd, listenEvents, timeout, keepAlive);
	}
}
