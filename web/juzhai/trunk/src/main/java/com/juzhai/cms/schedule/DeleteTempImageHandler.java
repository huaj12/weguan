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

	@Override
	protected void doHandle() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -7);
		// TODO (review) sdf不用每次都new，做成员变量或者静态变量即可
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		// TODO (review) 如果哪天没有运行这个任务，现有逻辑会导致有几天的文件夹是一直都没机会删了
		File f = new File(uploadTempImageHome + sdf.format(c) + File.separator);
		delFile(f);
	}

	private void delFile(File f) {
		// TODO (review) 只删文件夹能删吗？
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
