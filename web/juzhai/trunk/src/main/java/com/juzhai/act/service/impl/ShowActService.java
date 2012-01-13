package com.juzhai.act.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.act.mapper.ShowActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.HotAct;
import com.juzhai.act.model.ShowAct;
import com.juzhai.act.model.ShowActExample;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IHotActService;
import com.juzhai.act.service.IShowActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.dao.Limit;
import com.juzhai.index.bean.ShowActOrder;

@Service
public class ShowActService implements IShowActService {

	@Autowired
	private IHotActService hotActService;
	@Autowired
	private ShowActMapper showActMapper;
	@Autowired
	private IActService actService;
	@Autowired
	private IUserActService userActService;

	@Override
	public void updateShowActs() {
		Date cDate = new Date();
		List<HotAct> hotActList = hotActService.hotActList(true, 0,
				Integer.MAX_VALUE);
		List<Long> hotActIdList = new ArrayList<Long>();
		for (HotAct hotAct : hotActList) {
			long actId = hotAct.getActId();
			Act act = actService.getActById(actId);
			if (null != act
					&& (act.getEndTime() == null || act.getEndTime().after(
							cDate))) {
				// 更新showAct
				ShowAct showAct = getShowActByActId(actId);
				boolean isInsert = false;
				if (null == showAct) {
					isInsert = true;
					showAct = new ShowAct();
					showAct.setActId(actId);
					showAct.setCreateTime(cDate);
				}
				if (StringUtils.isNotEmpty(act.getCategoryIds())) {
					String delimiter = "|";
					String categoryIds = delimiter
							+ act.getCategoryIds().replaceAll(",", delimiter)
							+ delimiter;
					showAct.setCategoryIds(categoryIds);
				}
				showAct.setRecentPopularity(userActService.countActRecentUsers(
						actId, DateUtils.addDays(cDate, -7), cDate));
				showAct.setCity(act.getCity());
				showAct.setHotCreateTime(hotAct.getCreateTime());
				showAct.setLastModifyTime(cDate);
				showAct.setActEndTime(act.getEndTime());
				if (isInsert) {
					showActMapper.insertSelective(showAct);
				} else {
					showActMapper.updateByPrimaryKeySelective(showAct);
				}
			}
			hotActIdList.add(actId);
		}
		ShowActExample example = new ShowActExample();
		example.or().andActEndTimeLessThanOrEqualTo(cDate);
		example.or().andActIdNotIn(hotActIdList);
		showActMapper.deleteByExample(example);
	}

	@Override
	public ShowAct getShowActByActId(long actId) {
		return showActMapper.selectByPrimaryKey(actId);
	}

	@Override
	public List<Act> listShowActs(long cityId, long categoryId,
			ShowActOrder order, int firstResult, int maxResults) {
		// TODO 暂时能解决的最快方法，想办法改造
		ShowActExample example = new ShowActExample();
		ShowActExample.Criteria c = example.createCriteria();
		if (cityId > 0) {
			if (categoryId > 0) {
				example.or().andCityEqualTo(cityId)
						.andCategoryIdsLike("%|" + categoryId + "|%");
				example.or().andCityEqualTo(0L)
						.andCategoryIdsLike("%|" + categoryId + "|%");
				example.or().andCityIsNull()
						.andCategoryIdsLike("%|" + categoryId + "|%");
			} else {
				example.or().andCityEqualTo(cityId);
				example.or().andCityEqualTo(0L);
				example.or().andCityIsNull();
			}

		} else if (categoryId > 0) {
			c.andCategoryIdsLike("%|" + categoryId + "|%");
		}
		example.setOrderByClause(order.getColumn() + " desc, act_id desc");
		example.setLimit(new Limit(firstResult, maxResults));
		List<ShowAct> list = showActMapper.selectByExample(example);
		List<Long> actIds = new ArrayList<Long>();
		for (ShowAct showAct : list) {
			actIds.add(showAct.getActId());
		}
		return actService.getActListByIds(actIds);
	}

	@Override
	public int countShowActs(long cityId, long categoryId) {
		// TODO 暂时能解决的最快方法，想办法改造
		ShowActExample example = new ShowActExample();
		ShowActExample.Criteria c = example.createCriteria();
		if (cityId > 0) {
			if (categoryId > 0) {
				example.or().andCityEqualTo(cityId)
						.andCategoryIdsLike("%|" + categoryId + "|%");
				example.or().andCityEqualTo(0L)
						.andCategoryIdsLike("%|" + categoryId + "|%");
				example.or().andCityIsNull()
						.andCategoryIdsLike("%|" + categoryId + "|%");
			} else {
				example.or().andCityEqualTo(cityId);
				example.or().andCityEqualTo(0L);
				example.or().andCityIsNull();
			}

		} else if (categoryId > 0) {
			c.andCategoryIdsLike("%|" + categoryId + "|%");
		}
		return showActMapper.countByExample(example);
	}

}
