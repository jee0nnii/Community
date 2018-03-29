package com.ktds.actionhistory.service;

import java.util.List;

import com.ktds.actionhistory.dao.ActionHistoryDAO;
import com.ktds.actionhistory.vo.ActionHistorySearchVO;
import com.ktds.actionhistory.vo.ActionHistoryVO;

import io.github.seccoding.web.pager.Pager;
import io.github.seccoding.web.pager.PagerFactory;
import io.github.seccoding.web.pager.explorer.ListPageExplorer;
import io.github.seccoding.web.pager.explorer.PageExplorer;

public class ActionHistoryServiceImpl implements ActionHistoryService{

	private ActionHistoryDAO actionHistoryDao;
	public void setActionHistoryDao(ActionHistoryDAO actionHistoryDao) {
		this.actionHistoryDao = actionHistoryDao;
	}
	
	@Override
	public boolean createActionHistory(ActionHistoryVO actionHistoryVO) {
		return actionHistoryDao.insertActionHistory(actionHistoryVO)>0;
	}

	//0328 
	@Override
	public PageExplorer readAllActionHistory(ActionHistorySearchVO actionHistorySearchVO) {
		
		Pager pager = PagerFactory.getPager(Pager.OTHER, actionHistorySearchVO.getPageNo() + "", actionHistoryDao.selectAllActionHistoryCount(actionHistorySearchVO));

		PageExplorer pageExplorer = pager.makePageExplorer(ListPageExplorer.class, actionHistorySearchVO);
		List<ActionHistoryVO> list = actionHistoryDao.selectAllActionHistory(actionHistorySearchVO);
		pageExplorer.setList(list);
		return pageExplorer;
	}
}
