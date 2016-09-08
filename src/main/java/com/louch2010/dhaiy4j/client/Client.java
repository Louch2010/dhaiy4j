package com.louch2010.dhaiy4j.client;

import com.louch2010.dhaiy4j.cmd.Commands;
import com.louch2010.dhaiy4j.constants.DhaiyConstant;
import com.wangyin.commons.util.Logger;
import com.wangyin.commons.util.StringUtil;

/**
 * @Description:
 * @author: luocihang
 * @date: 2016年9月8日 下午5:48:15
 * @version: V1.0
 * @see：
 */
public class Client extends SocketClient implements Commands {
	private Logger logger = new Logger();
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
	public String connect(String ip, int port, String table, String pwd, String[] listenEvents) throws Exception{
		//打开连接
		super.connectServerSocket(ip, port, true);
		StringBuffer sb = new StringBuffer(DhaiyConstant.Connect.CONNECT);
		if(!StringUtil.isEmpty(table)){
			sb.append(" " + DhaiyConstant.Connect.TABLE + table);
		}
		if(!StringUtil.isEmpty(pwd)){
			sb.append(" " + DhaiyConstant.Connect.PASSWORD + pwd);
		}
		if(listenEvents != null && listenEvents.length > 0){
			sb.append(" " + DhaiyConstant.Connect.EVENT);
			for (int i = 0; i < listenEvents.length; i++) {
				sb.append(listenEvents[i] );
				if(i != listenEvents.length - 1){
					sb.append(DhaiyConstant.Connect.EVENT_SPLIT);
				}
			}
		}
		return sendCommand(sb.toString());
	}
	/**
	  *description : 断开连接
	  *@param      : 
	  *@return     : void
	  *modified    : 1、2016年9月8日 下午6:57:37 由 luocihang 创建 	   
	  */ 
	public void close(){
		try {
			sendCommand(DhaiyConstant.Exit.EXIT);
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
			return sendCommand(cmd);
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
			return sendCommand(cmd);
		} catch (Exception e) {
			logger.error("获取值失败！", e);
		}
		return null;
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
			sendCommand(cmd);
			//TODO
		} catch (Exception e) {
			logger.error("获取值失败！", e);
		}
		return true;
	}
}
