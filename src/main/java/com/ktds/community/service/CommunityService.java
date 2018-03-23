package com.ktds.community.service;

import java.util.List;

import com.ktds.community.vo.CommunitySearchVO;
import com.ktds.community.vo.CommunityVO;

import io.github.seccoding.web.pager.explorer.PageExplorer;

public interface CommunityService {
	public PageExplorer getAll(CommunitySearchVO communitySearchVO);

	// dao를 호출하기 위한 징검다리를 만듦 : true false로
	public boolean createCommunity(CommunityVO communityVO);

	public CommunityVO getOne(int id);

	// 0319
	public int readMyCommunitiesCount(int account);

	// 0320
	public List<CommunityVO> readMyCommunities(int account);
	
	public boolean deleteCommunities(List<Integer>ids, int account);

	public boolean recommendCommunity(int id);

	// 조회수 증가
	public boolean incrementView(int id);

	// 게시글 삭제
	public boolean deleteCommunity(int id);

	public boolean updateCommunity(CommunityVO communityVO);
}
