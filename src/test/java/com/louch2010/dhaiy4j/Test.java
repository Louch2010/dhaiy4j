package com.louch2010.dhaiy4j;

public class Test {
	public static void main(String[] args) throws Exception {
		Dhaiy client = new Dhaiy("127.0.0.1", 1334);
		/*System.out.println(client.exist("bbb"));
		System.out.println(client.set("aaa", "罗词航中华人民共和国"));
		//
		System.out.println(client.exist("aaa"));
		System.out.println(client.get("aaa"));
		System.out.println(client.delete("aaa"));
		System.out.println(client.exist("aaa"));
		System.out.println(client.get("aaa"));
		System.out.println(client.delete("aaa"));
		
		System.out.println(client.get("name"));*/
		
		
		
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			int time = (int) (10 * Math.random());
			if (time ==0){
				time = 10;
			}
			client.set("key_1_" + i, "value_" + i);
		}
		long end = System.currentTimeMillis();
		System.out.println("耗时："+ (end - start));
		
		client.close();
	}
}
