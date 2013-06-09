package com.easylife.movie.core.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 实现对表的创建、更新、变更列名操作
 * 
 * @author ytm0220@163.com
 * 
 */
public class SQLiteHelper extends SQLiteOpenHelper {

	public SQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	/**
	 * 创建新表
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE tb_video_history(id varchar(35) PRIMARY KEY,title varchar(300),category_id varchar(10),play_time varchar(20),poster_img varchar(300),video_src varchar(300),create_time NUMERIC)");
		db.execSQL("CREATE TABLE tb_video_interest(id varchar(35) PRIMARY KEY,title varchar(300),category_id varchar(10),play_time varchar(20),poster_img varchar(300),video_src varchar(300),create_time NUMERIC)");
	}

	/**
	 * 当检测与前一次创建数据库版本不一样时，先删除表再创建新表
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS tb_video_history");
		db.execSQL("DROP TABLE IF EXISTS tb_video_interest");
		onCreate(db);
	}

}
