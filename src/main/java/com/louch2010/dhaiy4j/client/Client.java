package com.louch2010.dhaiy4j.client;

import com.louch2010.dhaiy4j.cmd.Commands;
import com.louch2010.dhaiy4j.cmd.CommandsResponse;
import com.louch2010.dhaiy4j.constants.DhaiyConstant;
import com.louch2010.dhaiy4j.constants.ServerErrorConstant;
import com.louch2010.dhaiy4j.exception.DhaiyException;
import com.louch2010.dhaiy4j.utils.Logger;
import com.louch2010.dhaiy4j.utils.StringUtil;

/**
 * @Description:
 * @author: luocihang
 * @date: 2016年9月8日 下午5:48:15
 * @version: V1.0
 * @see：
 */
public class Client extends SocketClient implements Commands {
	private Logger logger = new Logger();
	private String token;
	private String table;
	private String password;
	private String[] event;
	/**
	  *description : 连接服务器
	  *@param      : @param ip 地址
	  *@param      : @param port 端口 
	  *@param      : @param table 表
	  *@param      : @param pwd 密码
	  *@param      : @param listenEvents 侦听事件 
	  *@param      : @return
	  *@return     : String
	  *modified    : 1、2016年9月8日 下午6:19:50 由 luocihang 创建 	   
	  */ 
	public String connect(String ip, int port, String table, String pwd, String[] listenEvents, int timeout, boolean keepAlive) throws Exception{
		//打开连接
		super.connectServerSocket(ip, port, timeout, keepAlive);
		StringBuffer sb = new StringBuffer(DhaiyConstant.Connect.CONNECT);
		if(!StringUtil.isEmpty(table)){
			sb.append(" " + DhaiyConstant.Connect.TABLE + table);
			this.table = table;
		}
		if(!StringUtil.isEmpty(pwd)){
			sb.append(" " + DhaiyConstant.Connect.PASSWORD + pwd);
			this.password = pwd;
		}
		sb.append(" " + DhaiyConstant.Connect.PROTOCOL + DhaiyConstant.Connect.PROTOCOL_DEFAULT);
		if(listenEvents != null && listenEvents.length > 0){
			sb.append(" " + DhaiyConstant.Connect.EVENT);
			for (int i = 0; i < listenEvents.length; i++) {
				sb.append(listenEvents[i] );
				if(i != listenEvents.length - 1){
					sb.append(DhaiyConstant.Connect.EVENT_SPLIT);
				}
			}
			this.event = listenEvents;
		}
		CommandsResponse response = sendCommand(sb.toString());
		if(!DhaiyConstant.SUCCESS.equals(response.getCode())){
			throw new DhaiyException("连接失败！", response);
		}
		this.token = response.getData().toString();
		return token;
	}
	/**
	  *description : 断开连接
	  *@param      : 
	  *@return     : void
	  *modified    : 1、2016年9月8日 下午6:57:37 由 luocihang 创建 	   
	  */ 
	public void close(){
		try {
			CommandsResponse response = sendCommand(DhaiyConstant.Exit.EXIT);
			if(!DhaiyConstant.SUCCESS.equals(response.getCode())){
				throw new DhaiyException(response);
			}
			closeConnect();
		} catch (Exception e) {
			logger.error("断开连接失败！", e);
		}
	}

	/**
	 * description : 设置值
	 * 
	 * @param : @param key 键
	 * @param : @param value 值
	 * @param : @param liveTime 存活期，单位秒
	 * @param : @return
	 * @return : String modified : 1、2016年9月8日 下午5:47:18 由 luocihang 创建
	 */
	public String set(String key, String value, int liveTime) {
		String cmd = DhaiyConstant.Set.SET + " " + key + " " + value + " " + liveTime;
		try {
			CommandsResponse response = sendCommand(cmd);
			if(!DhaiyConstant.SUCCESS.equals(response.getCode())){
				throw new DhaiyException(response);
			}
			return response.getData() == null ? null : response.getData().toString();
		} catch (Exception e) {
			logger.error("设置值失败！", e);
		}
		return null;
	}

	/**
	 * description : 设置值（永久）
	 * 
	 * @param : @param key
	 * @param : @param value
	 * @param : @return
	 * @return : String modified : 1、2016年9月8日 下午5:47:20 由 luocihang 创建
	 */
	public String set(String key, String value) {
		return set(key, value, 0);
	}

	/**
	 * description : 取值
	 * 
	 * @param : @param key
	 * @param : @return
	 * @return : String modified : 1、2016年9月8日 下午5:47:22 由 luocihang 创建
	 */
	public String get(String key) {
		String cmd = DhaiyConstant.Get.GET + " " + key;
		try {
			CommandsResponse response = sendCommand(cmd);
			//不存在
			if(ServerErrorConstant.ITEM_NOT_EXIST.equals(response.getCode())){
				return null;
			}
			if(!DhaiyConstant.SUCCESS.equals(response.getCode())){
				throw new DhaiyException(response);
			}
			if(!DhaiyConstant.DataType.STRING.equals(response.getDataType())){
				throw new DhaiyException("数据类型错误！" + response.getDataType());
			}
			return response.getData().toString();
		} catch (Exception e) {
			logger.error("获取值失败！", e);
		}
		return null;
	}
	/**
	  *description : 删除
	  *@param      : @param key
	  *@param      : @return
	  *@return     : boolean
	  *modified    : 1、2016年9月9日 下午2:52:04 由 luocihang 创建 	   
	  */ 
	public boolean delete(String key){
		String cmd = DhaiyConstant.Delete.DELETE + " " + key;
		try {
			CommandsResponse response = sendCommand(cmd);
			//不存在
			if(ServerErrorConstant.ITEM_NOT_EXIST.equals(response.getCode())){
				return false;
			}
			if(!DhaiyConstant.SUCCESS.equals(response.getCode())){
				throw new DhaiyException(response);
			}
			return true;
		} catch (Exception e) {
			logger.error("删除值失败！", e);
		}
		return false;
	}
	/**
	  *description : 切换表
	  *@param      : @param table
	  *@param      : @return
	  *@return     : boolean
	  *modified    : 1、2016年9月13日 下午9:36:40 由 luocihang 创建 	   
	  */ 
	public boolean use(String table){
		String cmd = DhaiyConstant.Use.USE + " " + table;
		try {
			CommandsResponse response = sendCommand(cmd);
			if(!DhaiyConstant.SUCCESS.equals(response.getCode())){
				throw new DhaiyException(response);
			}
			return true;
		} catch (Exception e) {
			logger.error("删除值失败！", e);
		}
		return false;
	}
	/**
	  *description : 是否存在
	  *@param      : @param key
	  *@param      : @return
	  *@return     : boolean
	  *modified    : 1、2016年9月8日 下午6:58:36 由 luocihang 创建 	   
	  */ 
	public boolean exist(String key){
		String cmd = DhaiyConstant.Exist.EXIST + " " + key;
		try {
			CommandsResponse response = sendCommand(cmd);
			if(!DhaiyConstant.SUCCESS.equals(response.getCode())){
				throw new DhaiyException(response);
			}
			if(!DhaiyConstant.DataType.BOOL.equals(response.getDataType())){
				throw new DhaiyException("数据类型错误！" + response.getDataType());
			}
			return (Boolean) response.getData();
		} catch (Exception e) {
			logger.error("获取值失败！", e);
		}
		return false;
	}
	
	/**
	  *description : 心跳检测
	  *@param      : @return
	  *@return     : String
	  *modified    : 1、2016年9月13日 下午9:55:18 由 luocihang 创建 	   
	  */ 
	public String ping(){
		String cmd = DhaiyConstant.Ping.PING;
		try {
			CommandsResponse response = sendCommand(cmd);
			if(!DhaiyConstant.SUCCESS.equals(response.getCode())){
				throw new DhaiyException(response);
			}
			if(!DhaiyConstant.DataType.STRING.equals(response.getDataType())){
				throw new DhaiyException("数据类型错误！" + response.getDataType());
			}
			return (String) response.getData();
		} catch (Exception e) {
			logger.error("获取值失败！", e);
		}
		return null;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String[] getEvent() {
		return event;
	}
	public void setEvent(String[] event) {
		this.event = event;
	}
	
}
