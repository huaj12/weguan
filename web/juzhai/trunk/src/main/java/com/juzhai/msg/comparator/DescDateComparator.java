package com.juzhai.msg.comparator;

import java.util.Comparator;

import com.juzhai.msg.bean.MergerActMsg;

public class DescDateComparator implements Comparator<MergerActMsg>  {


	@Override
	public int compare(MergerActMsg o1, MergerActMsg o2) {
		long time1=o1.getDate().getTime();
		long time2=o2.getDate().getTime();
		if(time1>time2){
			return 1;
		}else if(time1<time2){
			return -1;
		}else{
			return 0;
		}
	}

}
