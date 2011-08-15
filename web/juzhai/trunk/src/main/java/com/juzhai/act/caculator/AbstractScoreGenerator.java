package com.juzhai.act.caculator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public abstract class AbstractScoreGenerator implements IScoreGenerator,
		ApplicationContextAware {

	private volatile ApplicationContext applicationContext;

	private List<IScoreCaculator> scoreCaculatorList = new ArrayList<IScoreCaculator>();

	@Override
	public int genScore(long srcUid, long destUid, long actId) {
		int score = 0;
		for (IScoreCaculator scoreCaculator : scoreCaculatorList) {
			score += scoreCaculator.calculate(srcUid, destUid, actId);
		}
		return score;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	protected ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	public List<IScoreCaculator> getScoreCaculatorList() {
		return this.scoreCaculatorList;
	}
}
