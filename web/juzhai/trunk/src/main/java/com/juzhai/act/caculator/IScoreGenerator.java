package com.juzhai.act.caculator;

public interface IScoreGenerator {

	/**
	 * 计算分数
	 * 
	 * @param srcUid
	 * @param destUid
	 * @param actId
	 * @return
	 */
	int genScore(long srcUid, long destUid, long actId);
}
