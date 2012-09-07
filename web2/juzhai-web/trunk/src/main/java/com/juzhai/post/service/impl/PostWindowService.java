package com.juzhai.post.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.cms.controller.form.PostWindowSortForm;
import com.juzhai.post.bean.VerifyType;
import com.juzhai.post.dao.IPostWindowDao;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.exception.InputPostWindowException;
import com.juzhai.post.mapper.PostMapper;
import com.juzhai.post.mapper.PostWindowMapper;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostExample;
import com.juzhai.post.model.PostWindow;
import com.juzhai.post.model.PostWindowExample;
import com.juzhai.post.service.IPostWindowService;

@Service
public class PostWindowService implements IPostWindowService {

	@Autowired
	private PostWindowMapper postWindowMapper;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private IPostWindowDao postWindowDao;

	@Override
	public List<PostWindow> listPostWindow() {
		PostWindowExample example = new PostWindowExample();
		example.setOrderByClause("sequence");
		return postWindowMapper.selectByExample(example);
	}

	@Override
	public void removePostWindow(long id) throws InputPostWindowException {
		if (id <= 0) {
			throw new InputPostWindowException(
					InputPostWindowException.ILLEGAL_OPERATION);
		}
		postWindowMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void addPostWindow(long postId) throws InputPostException {

		if (isPostExist(postId)) {
			throw new InputPostException(InputPostException.POST_ID_REPEAT_ADD);
		}
		PostExample example = new PostExample();
		example.createCriteria().andIdEqualTo(postId).andDefunctEqualTo(false)
				.andVerifyTypeEqualTo(VerifyType.QUALIFIED.getType());
		List<Post> list = postMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			throw new InputPostException(InputPostException.POST_ID_NOT_EXIST);
		}
		Post post = list.get(0);
		PostWindow postWindow = new PostWindow();
		postWindow.setContent(post.getContent());
		postWindow.setCreateTime(new Date());
		postWindow.setLastModifyTime(new Date());
		postWindow.setPurposeType(post.getPurposeType());
		postWindow.setUid(post.getCreateUid());
		postWindow.setPostId(postId);
		postWindow.setSequence(postWindowDao.getMaxSequence() + 1);
		postWindowMapper.insertSelective(postWindow);
	}

	private boolean isPostExist(long postId) {
		PostWindowExample example = new PostWindowExample();
		example.createCriteria().andPostIdEqualTo(postId);
		return postWindowMapper.countByExample(example) > 0 ? true : false;
	}

	@Override
	public void sortPostWindow(List<PostWindowSortForm> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			for (PostWindowSortForm sort : list) {
				PostWindow window = new PostWindow();
				window.setId(sort.getId());
				window.setSequence(sort.getSequence());
				window.setLastModifyTime(new Date());
				postWindowMapper.updateByPrimaryKeySelective(window);
			}
		}
	}

}
