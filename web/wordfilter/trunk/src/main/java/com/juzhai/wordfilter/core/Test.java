package com.juzhai.wordfilter.core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;

public class Test extends Thread {
	static class St {
		byte[] s;
		boolean b;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int threadCount = 1;

		try {
			filter = Filter.init();
			if (filter == null) {
				System.out.println("initial filter failed");
				return;
			}
			String filename = "D:\\mwu\\code\\TextFilter\\test.txt";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename), Charset.forName("GBK")));
			boolean bSpam;
			StringBuilder sb = new StringBuilder();
			boolean bDo = true;
			list = new ArrayList<St>();
			while (bDo) {
				String line = reader.readLine();
				if (line == null)
					break;
				sb.setLength(0);
				if (line.equals("SPAM")) {
					bSpam = true;
					line = reader.readLine();
					if (line == null)
						break;
					sb.append(line);
				} else {
					bSpam = false;
					sb.append(line);
				}
				while (true) {
					line = reader.readLine();
					if (line == null) {
						bDo = false;
						break;
					}
					if (line.equals("#########################################")) {
						St st = new St();
						st.b = bSpam;
						st.s = sb.toString().getBytes(Config.CharSetInstance);
						list.add(st);
						break;
					}
					sb.append('\n').append(line);
				}
			}

			Thread[] threads = new Test[threadCount];
			for (int i = 0; i < threadCount; i++) {
				threads[i] = new Test();
				threads[i].start();
			}

			for (int i = 0; i < threadCount; i++) {
				threads[i].join();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long tot = 0;
		int ret;
		Date date = new Date();
		St st;
		long l = 0;
		for (int t = 0; t < 30000; t++) {
			for (int i = 0; i < list.size(); i++) {
				l++;
				st = list.get(i);
				ret = filter.Check(st.s, (int) l, new Long(l).toString(),
						new Long(l).toString(), 1);

				tot += st.s.length;
				if (st.b == (ret != Config.RET_Pass)) {
					// System.out.println("Yes");
				} else {
					System.out.printf(
							"Error, ret=%d, id=%d  answer is %s\n%s\n", ret, i,
							st.b ? "Spam" : "Not spam", new String(st.s,
									Config.CharSetInstance));
				}
			}
		}

		System.out.printf("OVER, total byte = %d, total time = %d ms\n", tot,
				new Date().getTime() - date.getTime());
	}

	private static ArrayList<St> list;
	private static Filter filter;
}
