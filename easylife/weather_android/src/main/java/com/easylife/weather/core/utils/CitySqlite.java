package com.easylife.weather.core.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.easylife.weather.R;

public class CitySqlite {

	public void close() {
		if (connCity != null)
			connCity.close();
	}

	public Cursor getChooseCity(String s) {
		initDBCity();
		if (connCity == null || !connCity.isOpen()) {
			connCity = context.openOrCreateDatabase("city.db", 0, null);
		}
		String s1 = (new StringBuilder())
				.append("select cityname,provincename from city c inner join province p on c.provinceid=p.provinceid where c.cityname like '")
				.append(s).append("%' or c.pycityname like '").append(s)
				.append("%' or c.pyshort like '").append(s)
				// .append("%' or p.provincename like '").append(s)
				.append("%'").toString();
		return connCity.rawQuery(s1, null);
	}

	public void initDBCity() {
		File file;
		file = context.getDatabasePath("city.db");
		File file1 = new File(file.toString().substring(0,
				file.toString().lastIndexOf("/")));
		if (!file1.exists())
			file1.mkdir();
		if (!file.exists())
			return;
		InputStream inputstream = context.getResources().openRawResource(
				R.raw.city);
		FileOutputStream fileoutputstream;
		try {
			fileoutputstream = new FileOutputStream(file);
			byte abyte0[] = new byte[8192];
			do {
				int i = inputstream.read(abyte0);
				if (i <= 0)
					break;
				fileoutputstream.write(abyte0, 0, i);
			} while (true);

			fileoutputstream.close();
			inputstream.close();
		} catch (Exception e) {
		}

	}

	private SQLiteDatabase connCity;
	Context context;

	public CitySqlite(Context context1) {

		super();
		connCity = null;
		context = context1;
		initDBCity();
		if (connCity == null || !connCity.isOpen()) {
			connCity = context1.openOrCreateDatabase("city.db", 0, null);
		}
	}
}
