package com.ktds.member.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.ktds.member.vo.MemberVO;

public class MemberDaoImplForOracle extends SqlSessionDaoSupport implements MemberDAO{
	
	@Override
	public int selectCountMemberEmail(String email) {
		return getSqlSession().selectOne("MemberDAO.selectCountMemberEmail", email);
	}
	
	@Override
	public int selectCountMemberNickname(String nickname) {
		return getSqlSession().selectOne("MemberDAO.selectCountMemberNickname", nickname);
	}
	
	@Override
	public int insertMember(MemberVO memberVO) {
											//인터페이스.메소드 , 파라미터
		return getSqlSession().insert("MemberDAO.insertMember",memberVO);
	
	}

	@Override
	public MemberVO selectMember(MemberVO memberVO) {	
		return getSqlSession().selectOne("MemberDAO.selectMember", memberVO);
	}

	@Override
	public int deleteMember(int account) {
		return getSqlSession().delete("MemberDAO.deleteMember", account);
	}

	@Override
	public String selectSalt(String email) {
		return getSqlSession().selectOne("MemberDAO.selectSalt", email);
	}
	

	
}
