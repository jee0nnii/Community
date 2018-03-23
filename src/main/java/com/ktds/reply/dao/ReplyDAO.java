package com.ktds.reply.dao;

import java.util.List;

import com.ktds.reply.vo.ReplyVO;

public interface ReplyDAO {

	//어떤 게시글의 댓글만 가져올 거 
	public List<ReplyVO> selectAllReplies(int communityId);
	
	//0323
	public ReplyVO selectOneReply(int replyId);
	
	//
	public int insertReply(ReplyVO replyVO);
}
