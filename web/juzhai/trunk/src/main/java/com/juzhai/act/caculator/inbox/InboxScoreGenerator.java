package com.juzhai.act.caculator.inbox;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;

import com.juzhai.act.caculator.AbstractScoreGenerator;
import com.juzhai.act.caculator.IScoreCaculator;
import com.juzhai.act.caculator.annotation.ScorePoint;
import com.juzhai.act.caculator.annotation.ScorePoint.ScoreType;

@Service
public class InboxScoreGenerator extends AbstractScoreGenerator {

	private ScoreType scoreType = ScorePoint.ScoreType.INBOX;

	@PostConstruct
	public void initBean() {
		Collection<IScoreCaculator> scoreCaculators = getApplicationContext()
				.getBeansOfType(IScoreCaculator.class).values();
		for (IScoreCaculator scoreCaculator : scoreCaculators) {
			ScorePoint scorePoint = scoreCaculator.getClass().getAnnotation(
					ScorePoint.class);
			if (null != scorePoint
					&& ArrayUtils.contains(scorePoint.value(), scoreType)) {
				getScoreCaculatorList().add(scoreCaculator);
			}
		}
	}
}
