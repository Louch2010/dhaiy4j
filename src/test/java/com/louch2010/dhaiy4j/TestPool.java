package com.louch2010.dhaiy4j;

public class TestPool {
	public static void main(String[] args) {
		DhaiyPool pool = new DhaiyPool("127.0.0.1", 1334, null, null, null, 30, 3, 10, true, 3);
		/*haiy dhaiy = pool.getResource();
		dhaiy.set("name", "luocihang");
		System.out.println(dhaiy.get("name"));*/
		for (int i = 0; i < 10; i++) {
			System.out.println("====================================" + i);
			Dhaiy s = pool.getResource();
			if(i > 3 && i < 7){
				s.close();
			}
			System.out.println(s);
		}
		while(true){
			System.out.println("===================================================");
			System.out.println("borrowedCount:" + pool.getPool().getBorrowedCount());
			System.out.println("createdCount:" + pool.getPool().getCreatedCount());
			System.out.println("idel:" + pool.getPool().getNumIdle());
			System.out.println("active:" + pool.getPool().getNumActive());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
