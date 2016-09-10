package com.louch2010.dhaiy4j.exception;

import com.louch2010.dhaiy4j.cmd.CommandsResponse;

/** 
  * @Description: 自定义异常
  * @author: luocihang
  * @date: 2016年9月9日 下午3:22:47
  * @version: V1.0 
  * @see：
  */
public class DhaiyException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String systemDesc;
	private String errorCode;
	private String errorDesc;
	
	public DhaiyException(String systemDesc){
		this.systemDesc = systemDesc;
	}
	
	public DhaiyException(String systemDesc, CommandsResponse response) {
		super(response.getCode());
		this.systemDesc = systemDesc;
		this.errorCode = response.getCode();
		this.errorDesc = response.getMsg();
	}

	public DhaiyException(CommandsResponse response) {
		super(response.getCode());
		this.errorCode = response.getCode();
		this.errorDesc = response.getMsg();
	}

	@Override
	public String getMessage() {
		return systemDesc + " [errorCode: " + errorCode + ", errorDesc: " + errorDesc + "]";
	}
}
