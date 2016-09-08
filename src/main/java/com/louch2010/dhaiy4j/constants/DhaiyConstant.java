package com.louch2010.dhaiy4j.constants;

public interface DhaiyConstant {
	
	
	 interface Command{
		/**
		 * 发送命令时，结尾字符
		 */
		String SEND_END_CHAR = "\r\n";
		/**
		 * 接收命令响应时，结尾字符
		 */
		String RECEIVE_END_CHAR = "\r\n ->";
	} 
	
	interface Set{
		String SET = "set";
	}
	
	interface Get{
		String GET = "get";
	}
	
	interface Exit{
		String EXIT = "exit";
	}
	
	interface Exist{
		String EXIST = "exist";
	}
	
	interface Connect{
		String CONNECT = "connect";
		String TABLE = "-t";
		String PASSWORD = "-a";
		String EVENT = "-e";
		String EVENT_SPLIT = ",";
	}
}
