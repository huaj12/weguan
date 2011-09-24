package com.juzhai.act.caculator;

import java.util.Date;

public interface IScoreCaculator {

	/**
	 * 计算分数
	 * 
	 * @param srcUid
	 * @param destUid
	 * @param actId
	 * @return
	 */
	long calculate(long srcUid, long destUid, long actId, Date time);
}
