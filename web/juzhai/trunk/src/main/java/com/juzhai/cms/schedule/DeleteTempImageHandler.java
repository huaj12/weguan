package com.juzhai.cms.schedule;

import java.io.File;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.core.util.FileUtil;

@Component
public class DeleteTempImageHandler extends AbstractScheduleHandler {
	@Value("${upload.temp.image.home}")
	private String uploadTempImageHome;
	// TODO (done) 这里变量作用域，确实是需要default？精准定义
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	protected void doHandle() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -6);
		for (int i = 0; i <= 7; i++) {
			c.add(Calendar.DAY_OF_MONTH, -1);
				// TODO (done) 我不是说了用FiltUtil里的方法吗?
			FileUtil.forceDelete(uploadTempImageHome + sdf.format(c.getTime()), "");
		}
		
	}
	
}
