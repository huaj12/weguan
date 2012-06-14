package com.juzhai.antiad.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.core.dao.Limit;
import com.juzhai.post.mapper.PostCommentMapper;
import com.juzhai.post.model.PostComment;
import com.juzhai.post.model.PostCommentExample;

@Service
public class CommentFoulService extends AbstractFoulservice {
	@Autowired
	private PostCommentMapper postCommentMapper;

	@Override
	protected boolean contentIsFoul(String content, long sendUid) {
		// 判断内容是否重复
		if (StringUtils.isNotEmpty(content)) {
			PostCommentExample example = new PostCommentExample();
			example.createCriteria().andCreateUidEqualTo(sendUid);
			// 第一条是刚插入的数据。最近的第二条数据
			example.setLimit(new Limit(1, 1));
			example.setOrderByClause("create_time desc");
			List<PostComment> list = postCommentMapper.selectByExample(example);
			if (CollectionUtils.isNotEmpty(list)) {
				PostComment postComment = list.get(0);
				if (content.trim().equals(postComment.getContent().trim())) {
					return true;
				}
			}
		}
		return false;
	}
}
