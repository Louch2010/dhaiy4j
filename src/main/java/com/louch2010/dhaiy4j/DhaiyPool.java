package com.louch2010.dhaiy4j;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.louch2010.dhaiy4j.client.ClientFactory;
import com.louch2010.dhaiy4j.utils.Logger;

/** 
  * @Description: Dhaiy客户端连接池
  * @author: luocihang
  * @date: 2016年9月14日 上午12:04:05
  * @version: V1.0 
  * @see：
  */
public class DhaiyPool {

	private String host;
	private int port;
	private String password;
	private String table;
	private String[] event;
	private int connectionTimeout;
	private int connectCoreSize;
	private int connectMaxSize;
	private int maxWaitSecond;
	private boolean keepAlive;
	
	private static GenericObjectPool<Dhaiy> pool = null;
	private Logger logger = new Logger();

	/**
	  *description : 初始化连接池
	  *@param      : 
	  *@return     : void
	  *modified    : 1、2016年9月14日 上午12:03:48 由 luocihang 创建 	   
	  */ 
	public void init() {
		GenericObjectPoolConfig conf = new GenericObjectPoolConfig();
		conf.setMaxTotal(connectMaxSize);//最大连接数
		conf.setMaxIdle(connectCoreSize);//最大空闲资源数
		conf.setMaxWaitMillis(1000 * maxWaitSecond);//资源等待时间
		conf.setTestOnBorrow(true);//当取出资源时，校验其是否可用
		ClientFactory factory = new ClientFactory(host, port, password, table,
				event, connectionTimeout, keepAlive);
		pool = new GenericObjectPool<Dhaiy>(factory,conf);
	}
	
	public DhaiyPool(){
		
	}
	
	public DhaiyPool(String host, int port, String password, String table,
			String[] event, int connectionTimeout, int connectCoreSize,
			int connectMaxSize, boolean keepAlive, int maxWaitSecond) {
		super();
		this.host = host;
		this.port = port;
		this.password = password;
		this.table = table;
		this.event = event;
		this.connectionTimeout = connectionTimeout;
		this.connectCoreSize = connectCoreSize;
		this.maxWaitSecond = maxWaitSecond;
		this.connectMaxSize = connectMaxSize;
		this.keepAlive = keepAlive;
		init();
	}

	/**
	  *description : 获取连接资源
	  *@param      : @return
	  *@return     : Dhaiy
	  *modified    : 1、2016年9月14日 上午12:03:31 由 luocihang 创建 	   
	  */ 
	public Dhaiy getResource(){
		if(pool == null){
			throw new RuntimeException("连接池未初始化！");
		}
		try {
			Dhaiy dhaiy = pool.borrowObject();
			dhaiy.setDataSource(this);
			return dhaiy;
		} catch (Exception e) {
			logger.error("获取连接失败！", e);
			throw new RuntimeException("获取连接失败！" + e);
		}
	}
	
	/**
	  *description : 返还资源
	  *@param      : @param dhaiy
	  *@return     : void
	  *modified    : 1、2016年9月14日 上午12:06:12 由 luocihang 创建 	   
	  */ 
	public void returnResource(Dhaiy dhaiy){
		pool.returnObject(dhaiy);
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

	public int getMaxWaitSecond() {
		return maxWaitSecond;
	}

	public void setMaxWaitSecond(int maxWaitSecond) {
		this.maxWaitSecond = maxWaitSecond;
	}

	public int getConnectMaxSize() {
		return connectMaxSize;
	}

	public void setConnectMaxSize(int connectMaxSize) {
		this.connectMaxSize = connectMaxSize;
	}

	public GenericObjectPool<Dhaiy> getPool() {
		return pool;
	}
}
