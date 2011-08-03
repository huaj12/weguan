/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act.dao;

import java.util.List;

import com.juzhai.act.model.Act;

public interface IActDao {

	Act insertAct(long uid, String name, List<Long> categoryIds);

	Act insertVerifiedAct(long uid, String name, List<Long> categoryIds);

	Act selectActByName(String name);

	void increasePopularity(long actId, int p);
}
