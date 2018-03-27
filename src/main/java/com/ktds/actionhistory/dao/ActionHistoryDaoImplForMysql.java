package com.ktds.actionhistory.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.ktds.actionhistory.vo.ActionHistoryVO;

public class ActionHistoryDaoImplForMysql extends SqlSessionDaoSupport implements ActionHistoryDAO {

	@Override
	public int insertActionHistory(ActionHistoryVO actionHistoryVO) {
		return getSqlSession().insert("ActionHistoryDAO.insertActionHistory", actionHistoryVO);
	}

}
