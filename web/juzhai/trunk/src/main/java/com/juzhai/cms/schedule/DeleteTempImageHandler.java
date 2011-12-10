package com.juzhai.cms.schedule;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.juzhai.core.schedule.AbstractScheduleHandler;
@Component
public class DeleteTempImageHandler extends AbstractScheduleHandler{
	@Value("${upload.temp.image.home}")
	private String uploadTempImageHome;
	
	@Override
	protected void doHandle() {
		Calendar   c   =   Calendar.getInstance(); 
		c.add(Calendar.DAY_OF_MONTH,-7);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
		File f=new File(uploadTempImageHome+sdf.format(c)+File.separator);
		delFile(f);
	}
	
	private  void delFile(File f) {  
        if (f.isDirectory()) {  
            File[] list = f.listFiles();  
            for (int i = 0; i < list.length; i++) {  
                if (list[i].isDirectory()) {  
                	delFile(list[i]);  
                }else{  
                    if(list[i].isFile())  
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
