package com.juzhai.cms.schedule;

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
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	protected void doHandle() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -6);
		for (int i = 0; i <= 7; i++) {
			c.add(Calendar.DAY_OF_MONTH, -1);
			// TODO (review) 不觉得下面调用的方式，参数很怪？
			FileUtil.forceDelete(uploadTempImageHome + sdf.format(c.getTime()),
					"");
		}

	}

}
