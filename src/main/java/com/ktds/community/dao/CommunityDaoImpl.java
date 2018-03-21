/*package com.ktds.community.dao;

import java.util.ArrayList;
import java.util.List;

import com.ktds.community.vo.CommunityVO;

public class CommunityDaoImpl implements CommunityDAO{
	//DB를 쓰지 않아서 넣어주는 것 뿐!!!!!!
	private List<CommunityVO> communityList;
	public CommunityDaoImpl() {
		communityList = new ArrayList<CommunityVO>();
	}	
	@Override
	public List<CommunityVO> selectAll() {
		return communityList;
	}	
	@Override
	public int insertCommunity(CommunityVO communityVO) {
		//이렇게 하면 삭제할 때 문제가 발생할 수 있음
		communityVO.setId(communityList.size()+1);
		communityList.add(communityVO);
		return 1;
		//한 개 넣었다고 알려주는 거 1: TRUE
	}
	@Override
	public CommunityVO selectOne(int id) {
		for(CommunityVO community : communityList) {
			if(community.getId() == id) {
				return community;
			}
		}
		return null;
	}
	
	@Override
	public int incrementViewCount(int id) {
		for (CommunityVO community : communityList) {
			if (community.getId() == id) {
				community.setViewCount(community.getViewCount()+1);
				return 1;
			}
		}		
		return 0;
	}
	
	@Override
	public int incrementRecommendCount(int id) {
		for (CommunityVO community : communityList) {
			if(community.getId() == id) {
				community.setRecommendCount(community.getRecommendCount()+1);
				return 1;
			}
		}
		return 0;
	}
	
}
*/