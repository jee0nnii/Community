package com.ktds.actionhistory.dao;

import java.util.List;

import com.ktds.actionhistory.vo.ActionHistorySearchVO;
import com.ktds.actionhistory.vo.ActionHistoryVO;

public interface ActionHistoryDAO {

	public int insertActionHistory(ActionHistoryVO actionHistoryVO);
	
	//0328
	public List<ActionHistoryVO> selectAllActionHistory(ActionHistorySearchVO actionHistorySearchVO);

	public int selectAllActionHistoryCount(ActionHistorySearchVO actionHistorySearchVO);
}
