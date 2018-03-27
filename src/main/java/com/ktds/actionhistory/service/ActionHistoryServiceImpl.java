package com.ktds.actionhistory.service;

import com.ktds.actionhistory.dao.ActionHistoryDAO;
import com.ktds.actionhistory.vo.ActionHistoryVO;

public class ActionHistoryServiceImpl implements ActionHistoryService{

	private ActionHistoryDAO actionHistoryDao;
	public void setActionHistoryDao(ActionHistoryDAO actionHistoryDao) {
		this.actionHistoryDao = actionHistoryDao;
	}
	
	@Override
	public boolean createActionHistory(ActionHistoryVO actionHistoryVO) {
		return actionHistoryDao.insertActionHistory(actionHistoryVO)>0;
	}
}
