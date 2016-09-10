package com.louch2010.dhaiy4j.cmd;

/**
 * @Description: 响应体
 * @author: luocihang
 * @date: 2016年9月9日 下午2:58:59
 * @version: V1.0
 * @see：
 */
public class CommandsResponse {
	private String Code;
	private String Msg;
	private Object Data;
	private String DataType;

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getMsg() {
		return Msg;
	}

	public void setMsg(String msg) {
		Msg = msg;
	}

	public Object getData() {
		return Data;
	}

	public void setData(Object data) {
		Data = data;
	}

	public String getDataType() {
		return DataType;
	}

	public void setDataType(String dataType) {
		DataType = dataType;
	}

}
