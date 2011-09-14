package com.juzhai.act.caculator;

public interface IScoreCaculator {

	/**
	 * 计算分数
	 * 
	 * @param srcUid
	 * @param destUid
	 * @param actId
	 * @return
	 */
	long calculate(long srcUid, long destUid, long actId);
}
