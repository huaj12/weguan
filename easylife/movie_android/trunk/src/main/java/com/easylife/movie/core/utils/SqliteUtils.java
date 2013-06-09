package com.easylife.movie.core.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SqliteUtils {
	private SQLiteHelper dbHelper = null;
	private static int DB_VERSION = 1;
	private static String DB_NAME = "movie.db";

	public void close() {
		if (connCity != null)
			connCity.close();
	}

	public Cursor query(String s) {
		if (connCity == null || !connCity.isOpen()) {
			initDB();
		}
		return connCity.rawQuery(s, null);
	}

	public void execSQL(String sql) {
		if (connCity == null || !connCity.isOpen()) {
			initDB();
		}
		connCity.execSQL(sql);
	}

	private SQLiteDatabase connCity;
	Context context;

	public SqliteUtils(Context context) {
		super();
		connCity = null;
		this.context = context;
		initDB();
	}

	private void initDB() {
		/* 初始化并创建数据库 */
		dbHelper = new SQLiteHelper(context, DB_NAME, null, DB_VERSION);
		/* 创建表 */
		connCity = dbHelper.getWritableDatabase(); // 调用SQLiteHelper.OnCreate()
	}
}
