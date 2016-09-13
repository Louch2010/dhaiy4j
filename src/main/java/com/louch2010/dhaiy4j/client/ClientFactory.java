package com.louch2010.dhaiy4j.client;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.louch2010.dhaiy4j.Dhaiy;
import com.louch2010.dhaiy4j.constants.DhaiyConstant;

public class ClientFactory implements PooledObjectFactory<Dhaiy> {
	
	private String host;
	private int port;
	private String password;
	private String table;
	private String[] event;
	private int connectionTimeout;
	private boolean keepAlive;

	public ClientFactory(String host, int port, String password, String table, String[] event, int connectionTimeout, boolean keepAlive) {
		this.host = host;
		this.port = port;
		this.password = password;
		this.table = table;
		this.event = event;
		this.connectionTimeout = connectionTimeout;
		this.keepAlive = keepAlive;
	}

	@Override
	public void activateObject(PooledObject<Dhaiy> pooledJedis)
			throws Exception {
		Dhaiy dhaiy = pooledJedis.getObject();
		if (dhaiy.getTable() != table) {
			dhaiy.use(table);
		}
	}

	@Override
	public void destroyObject(PooledObject<Dhaiy> pooledJedis) throws Exception {
		Dhaiy dhaiy = pooledJedis.getObject();
		if (dhaiy.isConnectAlive()) {
			dhaiy.closeConnect();
		}
	}

	@Override
	public PooledObject<Dhaiy> makeObject() throws Exception {
		Dhaiy dhaiy = null;
		try {
			dhaiy = new Dhaiy(host, port, table, password, event, connectionTimeout, keepAlive);
		} catch (Exception e) {
			if(dhaiy != null){
				dhaiy.closeConnect();
			}
			throw e;
		}
		return new DefaultPooledObject<Dhaiy>(dhaiy);
	}

	@Override
	public void passivateObject(PooledObject<Dhaiy> pooledJedis)
			throws Exception {
		// TODO maybe should select db 0? Not sure right now.
	}

	@Override
	public boolean validateObject(PooledObject<Dhaiy> pooledJedis) {
		Dhaiy jedis = pooledJedis.getObject();
		try {
			return jedis.getHost().equals(host)
					&& jedis.getPort() == port
					&& jedis.isConnectAlive() && jedis.ping().equals(DhaiyConstant.Ping.PONG);
		} catch (final Exception e) {
			return false;
		}
	}
}
