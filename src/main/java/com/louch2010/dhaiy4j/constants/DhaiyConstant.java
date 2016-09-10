package com.louch2010.dhaiy4j.constants;

public interface DhaiyConstant {

	// 成功
	String SUCCESS = "SUCCESS";

	// 数据类型
	interface DataType {
		String STRING = "string"; // 字符
		String BOOL = "bool"; // 布尔
		String NUMBER = "number"; // 数字
		String MAP = "map"; // Map
		String SET = "set"; // 集合
		String LIST = "list"; // 列表
		String ZSET = "zset"; // 有序集合
	}

	interface Command {
		/**
		 * 发送命令时，结尾字符
		 */
		String SEND_END_CHAR = "\r\n";
		/**
		 * 接收命令响应时，结尾字符
		 */
		String RECEIVE_END_CHAR = "\r\n!--!>";
	}

	interface Set {
		String SET = "set";
	}

	interface Get {
		String GET = "get";
	}

	interface Exit {
		String EXIT = "exit";
	}

	interface Exist {
		String EXIST = "exist";
	}

	interface Delete {
		String DELETE = "delete";
	}

	interface Connect {
		String CONNECT = "connect";
		String TABLE = "-t";
		String PASSWORD = "-a";
		String EVENT = "-e";
		String EVENT_SPLIT = ",";
		String PROTOCOL = "-m";
		String PROTOCOL_DEFAULT = "JSON";
	}
}
