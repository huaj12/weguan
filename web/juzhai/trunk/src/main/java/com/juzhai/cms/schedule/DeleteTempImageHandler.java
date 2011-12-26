package com.juzhai.cms.schedule;

import java.io.File;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.juzhai.core.schedule.AbstractScheduleHandler;

@Component
public class DeleteTempImageHandler extends AbstractScheduleHandler {
	@Value("${upload.temp.image.home}")
	private String uploadTempImageHome;
	// TODO (review) 这里变量作用域，确实是需要default？精准定义
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	protected void doHandle() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -6);
		for (int i = 0; i <= 7; i++) {
			c.add(Calendar.DAY_OF_MONTH, -1);
			File f = new File(uploadTempImageHome + sdf.format(c.getTime())
					+ File.separator);
			if (f.exists()) {
				// TODO (review) 我不是说了用FiltUtil里的方法吗?
				delFile(f);
			}
		}

	}

	private void delFile(File f) {
		// TODO (review) 我不是说了用FiltUtil里的方法吗？
		if (f.isDirectory()) {
			File[] list = f.listFiles();
			for (int i = 0; i < list.length; i++) {
				if (list[i].isDirectory()) {
					delFile(list[i]);
				} else {
					if (list[i].isFile())
						list[i].delete();
				}
			}
			f.delete();
		} else {
			if (f.isFile())
				f.delete();
		}
	}
}
