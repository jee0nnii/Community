package com.ktds.reply.vo;

import com.ktds.community.vo.CommunityVO;
import com.ktds.member.vo.MemberVO;

public class ReplyVO {

	//계층구조
	private int level;
	
	private int id;
	private int account;
	private int communityId;
	private String body;
	private String registDate;
	private int parentReplyId;
	
	private MemberVO memberVO;
	private CommunityVO communityVO;

		
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	public int getCommunityId() {
		return communityId;
	}
	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	public int getParentReplyId() {
		return parentReplyId;
	}
	public void setParentReplyId(int parentReplyId) {
		this.parentReplyId = parentReplyId;
	}
	public MemberVO getMemberVO() {
		return memberVO;
	}
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	public CommunityVO getCommunityVO() {
		return communityVO;
	}
	public void setCommunityVO(CommunityVO communityVO) {
		this.communityVO = communityVO;
	}

	
	
}
