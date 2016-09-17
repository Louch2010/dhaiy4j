package com.louch2010.dhaiy4j.cmd;

import java.math.BigDecimal;

/**
 * @Description: Dhaiy缓存命令
 * @author: luocihang
 * @date: 2016年9月8日 下午5:46:49
 * @version: V1.0
 * @see：
 */
public interface Commands {
	
	/**
	  *description : 连接服务器
	  *@param      : @param ip 地址
	  *@param      : @param port 端口 
	  *@param      : @param table 表
	  *@param      : @param pwd 密码
	  *@param      : @param listenEvents 侦听事件 
	  *@param      : @param timeout 连接超时时间
	  *@param      : @param keepAlive 是否为长连接
	  *@param      : @return
	  *@return     : String
	  *modified    : 1、2016年9月8日 下午6:19:50 由 luocihang 创建 	   
	  */ 
	public String connect(String ip, int port, String table, String pwd, String[] listenEvents, int timeout, boolean keepAlive) throws Exception;
	
	/**
	  *description : 断开连接
	  *@param      : 
	  *@return     : void
	  *modified    : 1、2016年9月8日 下午6:57:37 由 luocihang 创建 	   
	  */ 
	public void close();
	/**
	  *description : 设置值
	  *@param      : @param key 键
	  *@param      : @param value 值
	  *@param      : @param liveTime 存活期，单位秒
	  *@param      : @return
	  *@return     : String
	  *modified    : 1、2016年9月8日 下午5:47:18 由 luocihang 创建 	   
	  */ 
	public String set(String key, String value, int liveTime);

	/**
	  *description : 设置值（永久）
	  *@param      : @param key
	  *@param      : @param value
	  *@param      : @return
	  *@return     : String
	  *modified    : 1、2016年9月8日 下午5:47:20 由 luocihang 创建 	   
	  */ 
	public String set(String key, String value);

	/**
	  *description : 取值
	  *@param      : @param key
	  *@param      : @return
	  *@return     : String
	  *modified    : 1、2016年9月8日 下午5:47:22 由 luocihang 创建 	   
	  */ 
	public String get(String key);
	
	/**
	  *description : 删除
	  *@param      : @param key
	  *@param      : @return
	  *@return     : boolean
	  *modified    : 1、2016年9月9日 下午2:52:04 由 luocihang 创建 	   
	  */ 
	public boolean delete(String key);
	
	/**
	  *description : 是否存在
	  *@param      : @param key
	  *@param      : @return
	  *@return     : boolean
	  *modified    : 1、2016年9月8日 下午6:58:36 由 luocihang 创建 	   
	  */ 
	public boolean exist(String key);
	
	/**
	  *description : 切换表
	  *@param      : @param table
	  *@param      : @return
	  *@return     : boolean
	  *modified    : 1、2016年9月13日 下午9:36:40 由 luocihang 创建 	   
	  */ 
	public boolean use(String table);
	
	/**
	  *description : 心跳检测
	  *@param      : @return
	  *@return     : String
	  *modified    : 1、2016年9月13日 下午9:55:18 由 luocihang 创建 	   
	  */ 
	public String ping();
	
	/**
	  *description : 设置值
	  *@param      : @param key
	  *@param      : @param value
	  *@param      : @return
	  *@return     : BigDecimal
	  *modified    : 1、2016年9月17日 下午8:03:35 由 luocihang 创建 	   
	  */ 
	public BigDecimal nset(String key, BigDecimal value);
	
	/**
	  *description : 设置带生命周期的值
	  *@param      : @param key
	  *@param      : @param value
	  *@param      : @param liveTime
	  *@param      : @return
	  *@return     : BigDecimal
	  *modified    : 1、2016年9月17日 下午8:03:38 由 luocihang 创建 	   
	  */ 
	public BigDecimal nset(String key, BigDecimal value, int liveTime);
	
	/**
	  *description : 获取数值
	  *@param      : @param key
	  *@param      : @return
	  *@return     : BigDecimal
	  *modified    : 1、2016年9月17日 下午8:03:43 由 luocihang 创建 	   
	  */ 
	public BigDecimal nget(String key);
	
	/**
	  *description : 增加1
	  *@param      : @param key
	  *@param      : @return
	  *@return     : BigDecimal
	  *modified    : 1、2016年9月17日 下午8:03:45 由 luocihang 创建 	   
	  */ 
	public BigDecimal incr(String key);
	
	/**
	  *description : 增加指定值
	  *@param      : @param key
	  *@param      : @param by
	  *@param      : @return
	  *@return     : BigDecimal
	  *modified    : 1、2016年9月17日 下午8:03:47 由 luocihang 创建 	   
	  */ 
	public BigDecimal incrBy(String key, BigDecimal by);
}
