package com.louch2010.dhaiy4j;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.louch2010.dhaiy4j.client.ClientFactory;
import com.louch2010.dhaiy4j.utils.Logger;

public class DhaiyPool {

	private String host;
	private int port;
	private String password;
	private String table;
	private String[] event;
	private int connectionTimeout;
	private int connectCoreSize;
	private int aliveTime;
	private int connectMaxSize;
	private boolean keepAlive;
	
	private static GenericObjectPool<Dhaiy> pool = null;
	private Logger logger = new Logger();

	public void init() {
		GenericObjectPoolConfig conf = new GenericObjectPoolConfig();
		conf.setMaxTotal(connectMaxSize);
		conf.setMaxIdle(connectMaxSize - connectCoreSize);
		conf.setMaxWaitMillis(1000 * aliveTime);
		ClientFactory factory = new ClientFactory(host, port, password, table,
				event, connectionTimeout, keepAlive);
		pool = new GenericObjectPool<Dhaiy>(factory,conf);
	}
	
	public Dhaiy getDhaiy(){
		if(pool == null){
			throw new RuntimeException("连接池未初始化！");
		}
		try {
			return pool.borrowObject();
		} catch (Exception e) {
			logger.error("获取连接失败！", e);
			throw new RuntimeException("连接池未初始化！" + e);
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String[] getEvent() {
		return event;
	}

	public void setEvent(String[] event) {
		this.event = event;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	public int getConnectCoreSize() {
		return connectCoreSize;
	}

	public void setConnectCoreSize(int connectCoreSize) {
		this.connectCoreSize = connectCoreSize;
	}

	public int getAliveTime() {
		return aliveTime;
	}

	public void setAliveTime(int aliveTime) {
		this.aliveTime = aliveTime;
	}

	public int getConnectMaxSize() {
		return connectMaxSize;
	}

	public void setConnectMaxSize(int connectMaxSize) {
		this.connectMaxSize = connectMaxSize;
	}
	
}
