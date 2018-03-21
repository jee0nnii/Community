package com.ktds.member.service;

import com.ktds.member.vo.MemberVO;

public interface MemberService {
	//0321
	public boolean readCountMemberEmail(String email);
	public boolean readCountMemberNickname(String nickname);
	public boolean createMember(MemberVO memberVO);
	public MemberVO readMember(MemberVO memberVO);
	//0319 : 탈퇴할지말지 flag
	public boolean removeMember(int account, String deleteFlag);
	
}
