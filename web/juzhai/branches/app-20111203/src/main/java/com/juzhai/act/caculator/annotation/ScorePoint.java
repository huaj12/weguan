package com.juzhai.act.caculator.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ScorePoint {

	public enum ScoreType {
		INBOX
	}

	ScoreType[] value() default ScoreType.INBOX;
}
