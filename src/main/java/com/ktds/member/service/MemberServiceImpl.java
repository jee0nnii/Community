package com.ktds.member.service;

import com.ktds.community.dao.CommunityDAO;
import com.ktds.member.dao.MemberDAO;
import com.ktds.member.vo.MemberVO;
import com.ktds.util.SHA256Util;

public class MemberServiceImpl implements MemberService{
	private MemberDAO memberDao;
	private CommunityDAO communityDao;
	
	public void setCommunityDao(CommunityDAO communityDao) {
		this.communityDao = communityDao;
	}
	
	public void setMemberDao(MemberDAO memberDao) {
		this.memberDao = memberDao;
	}
	
	//0321
	@Override
	public boolean readCountMemberEmail(String email) {
		return memberDao.selectCountMemberEmail(email)>0;
	}
@Override
	public boolean readCountMemberNickname(String nickname) {
		return memberDao.selectCountMemberNickname(nickname)>0;
	}
	@Override
	public boolean createMember(MemberVO memberVO) {
		//0321 암호화
		String salt = SHA256Util.generateSalt();
		memberVO.setSalt(salt);
		
		String password = memberVO.getPassword();
		password = SHA256Util.getEncrypt(password, salt);
		memberVO.setPassword(password);
		
		return memberDao.insertMember(memberVO) > 0;
	}
	@Override
	public MemberVO readMember(MemberVO memberVO) {
		// 0321
		// 사용자의 ID로 SALT가져오기
		String salt = memberDao.selectSalt(memberVO.getEmail());
		if(salt == null) {
			salt = "";
		}
		//salt로 암호화 
		String password = memberVO.getPassword();
		password = SHA256Util.getEncrypt(password, salt);
		memberVO.setPassword(password);
	
		return memberDao.selectMember(memberVO);
	}
	@Override
	public boolean removeMember(int account, String deleteFlag) {
		if(deleteFlag.equals("y")) {
			communityDao.deleteMyCommunities(account);
		}
		return memberDao.deleteMember(account) > 0;
	}

		
}
