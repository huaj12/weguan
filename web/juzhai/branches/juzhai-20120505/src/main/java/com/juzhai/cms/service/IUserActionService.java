package com.juzhai.cms.service;

import java.util.List;

import com.juzhai.act.model.Act;
import com.juzhai.cms.model.AddActAction;
import com.juzhai.cms.model.SearchActAction;

public interface IUserActionService {

	List<SearchActAction> getSearchActAction(int firstResult, int maxResults);

	int getSearchActActionCount();

	List<AddActAction> getAddActAction(int firstResult, int maxResults);

	int getAddActActionCount();

	boolean addActActionMaximum(long uid);

	boolean searchActActionMaximum(long uid);

	AddActAction createAddActAction(String name, long uid, String userName);

	SearchActAction createSearchActAction(String name, long uid, String userName);

	List<SearchActAction> searchAutoMatch(String name);

	boolean isExistAddActAction(long uid, String name);

	boolean isExistSearchActAction(long uid, String name);

	AddActAction getAddActAction(String name, long uid);

	SearchActAction getSearchActAction(String name, long uid);

}
