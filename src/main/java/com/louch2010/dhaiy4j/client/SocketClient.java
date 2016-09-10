package com.louch2010.dhaiy4j.client;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.Socket;
import java.nio.CharBuffer;

import com.google.gson.Gson;
import com.louch2010.dhaiy4j.cmd.CommandsResponse;
import com.louch2010.dhaiy4j.constants.DhaiyConstant;
import com.louch2010.dhaiy4j.utils.IOUtil;
import com.louch2010.dhaiy4j.utils.Logger;
import com.louch2010.dhaiy4j.utils.StringUtil;

/** 
  * @Description: Socket服务端，用于连接服务器进行读写操作
  * @author: luocihang
  * @date: 2016年9月8日 下午5:42:16
  * @version: V1.0 
  * @see：
  */
public class SocketClient {
	private String host;
	private int port;
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private boolean keepAlive;
	
	private Logger logger = new Logger();
	private Gson gson = new Gson();
	/**
	  *description : 连接服务器
	  *@param      : @param host 地址
	  *@param      : @param port 端口
	  *@param      : @throws Exception
	  *@return     : void
	  *modified    : 1、2016年9月8日 下午5:20:15 由 luocihang 创建 	   
	  */ 
	public void connectServerSocket(String host, int port) throws Exception{
		this.connectServerSocket(host, port, true);
	}
	/**
	 *description : 连接服务器
	 *@param      : @param host 地址
	 *@param      : @param port 端口
	 *@param      : @param keepAlive 是否使用长连接
	 *@param      : @throws Exception
	 *@return     : void
	 *modified    : 1、2016年9月8日 下午5:20:15 由 luocihang 创建 	   
	 */ 
	public void connectServerSocket(String host, int port, boolean keepAlive) throws Exception{
		this.host = host;
		this.port = port;
		this.keepAlive = keepAlive;
		this.connect();
	}
	/**
	  *description : 连接服务器
	  *@param      : @throws Exception
	  *@return     : void
	  *modified    : 1、2016年9月8日 下午5:34:46 由 luocihang 创建 	   
	  */ 
	private void connect() throws Exception{
		logger.debug("连接服务器，IP：", host, "，端口：", port);
		if(StringUtil.isEmpty(host) || port == 0){
			logger.error("连接服务器失败，服务器地址或端口错误！");
			return;
		}
		socket = new Socket(host, port);
		socket.setKeepAlive(keepAlive);
		os = socket.getOutputStream();
		is = socket.getInputStream();
	}
	
	/**
	  *description :向服务器写入信息 
	  *@param      : @param cmd
	  *@param      : @throws Exception
	  *@return     : void
	  *modified    : 1、2016年9月8日 下午5:30:04 由 luocihang 创建 	   
	  */ 
	public void write(String cmd) throws Exception{
		if(!isConnectAlive()){
			logger.info("连接超时或不可用，创建新的联接...");
			this.connect();
		}
		logger.debug("向服务端发送：", cmd);
		IOUtil.write(cmd, os);
	}
	
	/**
	  *description : 发送命令
	  *@param      : @param cmd
	  *@param      : @throws Exception
	  *@return     : void
	  *modified    : 1、2016年9月8日 下午5:59:23 由 luocihang 创建 	   
	  */ 
	public CommandsResponse sendCommand(String cmd)throws Exception{
		write(cmd + DhaiyConstant.Command.SEND_END_CHAR);
		String response = read(DhaiyConstant.Command.RECEIVE_END_CHAR);
		//截取响应内容体
		String body = response.substring(0, response.length() - DhaiyConstant.Command.RECEIVE_END_CHAR.length());
		//解析响应体
		CommandsResponse t = gson.fromJson(body, CommandsResponse.class);
		return t;
	}
	
	/**
	  *description : 读取服务端返回的信息
	  *@param      : @param end
	  *@param      : @return
	  *@param      : @throws Exception
	  *@return     : String
	  *modified    : 1、2016年9月8日 下午5:30:14 由 luocihang 创建 	   
	  */ 
	public String read(String end) throws Exception{
		Reader reader = new InputStreamReader(is);
		CharBuffer buf = CharBuffer.allocate(1024);
		StringBuffer sb = new StringBuffer();
		String tmp = "";
		while(reader.read(buf) != -1) {
			buf.flip();
			tmp = buf.toString();
			sb.append(tmp);
			if (tmp.endsWith(end)){
				break;
			}
		}
		String response = sb.toString();
		logger.debug("服务端返回：", response);
		//如果是短连接，则关闭连接
		if(!keepAlive){
			this.closeConnect();
		}
		return response;
	}
	
	/**
	  *description : 连接是否可用
	  *@param      : @return
	  *@return     : boolean
	  *modified    : 1、2016年9月8日 下午5:19:51 由 luocihang 创建 	   
	  */ 
	public boolean isConnectAlive(){
		if(socket == null || is == null || os == null){
			return false;
		}
		if(socket.isConnected() && !socket.isInputShutdown() && !socket.isOutputShutdown()){
			return true;
		}
		return false;
	}
	
	/**
	  *description : 关闭连接
	  *@param      : 
	  *@return     : void
	  *modified    : 1、2016年9月8日 下午5:26:13 由 luocihang 创建 	   
	  */ 
	public void closeConnect(){
		try{
			if(is != null){
				is.close();
			}
			if(os != null){
				os.close();
			}
			if(socket != null && !socket.isClosed()){
				socket.close();
			}
		} catch (Exception e) {
			logger.error("关闭连接时异常！", e);
		}
	}
}
