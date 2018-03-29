package com.ktds.actionhistory.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.ktds.actionhistory.vo.ActionHistorySearchVO;
import com.ktds.actionhistory.vo.ActionHistoryVO;

public class ActionHistoryDaoImplForMysql extends SqlSessionDaoSupport implements ActionHistoryDAO {

	@Override
	public int insertActionHistory(ActionHistoryVO actionHistoryVO) {
		return getSqlSession().insert("ActionHistoryDAO.insertActionHistory", actionHistoryVO);
	}
	
	//0328
	@Override
	public int selectAllActionHistoryCount(ActionHistorySearchVO actionHistorySearchVO) {
		return getSqlSession().selectOne("ActionHistoryDAO.selectAllActionHistoryCount", actionHistorySearchVO);
	}
	
	@Override
	public List<ActionHistoryVO> selectAllActionHistory(ActionHistorySearchVO actionHistorySearchVO) {
		return getSqlSession().selectList("ActionHistoryDAO.selectAllActionHistory",actionHistorySearchVO);
	}

	

}
