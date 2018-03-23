package com.ktds.community.dao;

import java.util.List;

import com.ktds.community.vo.CommunitySearchVO;
import com.ktds.community.vo.CommunityVO;

public interface CommunityDAO {
	
	public int selectCountAll(CommunitySearchVO communitySearchVO);
	
	public List<CommunityVO> selectAll(CommunitySearchVO communitySearchVO);
	public int insertCommunity(CommunityVO communityVO);
	//아이디로 게시글 하나만 가져오는 거
	public CommunityVO selectOne(int id);
	
	//0319
	public int selectMyCommunitiesCount(int account);
	
	//0320 :: 내가 쓴 글의 리스트를 가져올 거
	public List<CommunityVO> selectMyCommunities(int account);
	
	//C.U.D --> INT
	public int incrementViewCount(int id);
	//추천하기
	public int incrementRecommendCount(int id);
	
	//게시글 삭제하기
	public int deleteOne(int id);
	//사용자 계정가지고 지울거
	public int deleteMyCommunities(int account);
	
	//0320
	public int deleteCommunities(List<Integer>ids, int account);
	
	public int updateCommunity(CommunityVO communityVO);
}

