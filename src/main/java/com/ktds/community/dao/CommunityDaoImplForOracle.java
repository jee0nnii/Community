package com.ktds.community.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.ktds.community.vo.CommunitySearchVO;
import com.ktds.community.vo.CommunityVO;

/**
 * SqlSessionDaoSupport
 * : SqlSesiionTemplate Bean객체를 가지고 있음
 */
public class CommunityDaoImplForOracle extends SqlSessionDaoSupport implements CommunityDAO{
	
	@Override
	public List<CommunityVO> selectAll(CommunitySearchVO communitySearchVO) {
											// 인터페이스.메소드명
											// communityDao.xml에 가보면 그안의 네임스페이스가 인터페이스 이름이랑 똑같음
		return getSqlSession().selectList("CommunityDAO.selectAll", communitySearchVO);
	}
	@Override
	public int insertCommunity(CommunityVO communityVO) {
		return getSqlSession().insert("CommunityDAO.insertCommunity", communityVO);
//		"CommunityDAO.insertCommunity" 쿼리를 수행할 때 
//		--> 현재 파라미터 객체 전달 : communityVO
	}
	@Override
	public CommunityVO selectOne(int id) {
		return getSqlSession().selectOne("CommunityDAO.selectOne", id);
	}
	@Override
	public int incrementViewCount(int id) {
		return getSqlSession().update("CommunityDAO.incrementViewCount", id);
	}
	@Override
	public int incrementRecommendCount(int id) {
		return getSqlSession().update("CommunityDAO.incrementRecommendCount", id);
	}

	@Override
	public int deleteOne(int id) {
		return getSqlSession().delete("CommunityDAO.deleteOne", id);
	}

	@Override
	public int deleteMyCommunities(int account) {
		return getSqlSession().delete("CommunityDAO.deleteMyCommunities", account);
	}

	@Override
	public int updateCommunity(CommunityVO communityVO) {
		return getSqlSession().update("CommunityDAO.updateCommunity",communityVO);
	}
	//0319
	@Override
	public int selectMyCommunitiesCount(int account) {
		return getSqlSession().selectOne("CommunityDAO.selectMyCommunitiesCount", account);
	}
	//0320
	@Override
	public List<CommunityVO> selectMyCommunities(int account) {
		return getSqlSession().selectList("CommunityDAO.selectMyCommunities", account);
	}
	@Override
	public int deleteCommunities(List<Integer> ids, int account) {
		//파라미터를 넘기기 위한 맵을 만듦 : 두개의 값을 하나로 합쳐서 보낼려고?
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		params.put("account", account);
		return getSqlSession().delete("CommunityDAO.deleteCommunities", params);
	}
	@Override
	public int selectCountAll(CommunitySearchVO communitySearchVO) {
		return getSqlSession().selectOne("CommunityDAO.selectCountAll",communitySearchVO);
	}

	
	
	
}
