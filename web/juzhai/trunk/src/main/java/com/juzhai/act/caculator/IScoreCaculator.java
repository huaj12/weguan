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
	int calculate(long srcUid, long destUid, long actId);
}
