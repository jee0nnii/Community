package com.ktds.member.service;

import com.ktds.member.vo.MemberVO;

public interface MemberService {
	public boolean createMember(MemberVO member);
	public MemberVO readMember(MemberVO member);
	//0319 : 탈퇴할지말지 flag
	public boolean removeMember(int account, String deleteFlag);
	
}
