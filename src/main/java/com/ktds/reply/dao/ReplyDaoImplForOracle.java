package com.ktds.reply.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.ktds.reply.vo.ReplyVO;

public class ReplyDaoImplForOracle extends SqlSessionDaoSupport implements ReplyDAO{

	@Override
	public List<ReplyVO> selectAllReplies(int communityId) {
		return getSqlSession().selectList("ReplyDAO.selectAllReplies",communityId);
	}

	@Override
	public int insertReply(ReplyVO replyVO) {
		int sequence = getSqlSession().selectOne("ReplyDAO.nextValue");
		replyVO.setId(sequence);
		return getSqlSession().insert("ReplyDAO.insertReply",replyVO);
	}


}
