package com.juzhai.post.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.post.mapper.IdeaDetailMapper;
import com.juzhai.post.model.IdeaDetail;
import com.juzhai.post.service.IIdeaDetailService;
import com.juzhai.post.service.IIdeaImageService;

@Service
public class IdeaDetailService implements IIdeaDetailService {
	@Autowired
	private IdeaDetailMapper ideaDetailMapper;
	@Autowired
	private IIdeaImageService ideaImageService;

	@Override
	public void updateIdeaDetail(long ideaId, String detail) {
		IdeaDetail ideaDetail = ideaDetailMapper.selectByPrimaryKey(ideaId);
		if (StringUtils.isNotEmpty(detail)) {
			detail = ideaImageService.intoEditorImg(ideaId, detail);
			if (ideaDetail == null) {
				ideaDetail = new IdeaDetail();
				ideaDetail.setIdeaId(ideaId);
				ideaDetail.setDetail(detail);
				ideaDetail.setCreateTime(new Date());
				ideaDetail.setLastModifyTime(ideaDetail.getCreateTime());
				ideaDetailMapper.insertSelective(ideaDetail);
			} else {
				ideaDetail.setDetail(detail);
				ideaDetail.setLastModifyTime(new Date());
				ideaDetailMapper.updateByPrimaryKeySelective(ideaDetail);
			}
		} else {
			if (ideaDetail != null) {
				ideaDetailMapper.deleteByPrimaryKey(ideaId);
			}
		}

	}

	@Override
	public IdeaDetail getIdeaDetail(long ideaId) {
		return ideaDetailMapper.selectByPrimaryKey(ideaId);
	}

}
