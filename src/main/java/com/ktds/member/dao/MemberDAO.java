package com.ktds.member.dao;

import com.ktds.member.vo.MemberVO;

public interface MemberDAO {
	//0321 전달받은 이메일 & 닉네임 존재여부 확인
	public int selectCountMemberEmail(String email);
	public int selectCountMemberNickname(String nickname);
	
	//회원가입한 사람 테이블에 넣을꺼어어어어
	public int insertMember(MemberVO memberVO);
	
	//0321 암호화
	public String selectSalt(String email);
	
	public MemberVO selectMember(MemberVO memberVO);
	
	//회원탈퇴!!!
	public int deleteMember(int account);
}
