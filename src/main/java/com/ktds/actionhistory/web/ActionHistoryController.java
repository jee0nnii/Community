package com.ktds.actionhistory.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.actionhistory.service.ActionHistoryService;
import com.ktds.actionhistory.vo.ActionHistorySearchVO;
import com.ktds.util.DateUtil;

import io.github.seccoding.web.pager.explorer.PageExplorer;

@Controller
public class ActionHistoryController {

	private ActionHistoryService actionHistoryService;
	
	public void setActionHistoryService(ActionHistoryService actionHistoryService) {
		this.actionHistoryService = actionHistoryService;
	}
	
	@RequestMapping("/admin/actionhistory")
	public ModelAndView viewActionHistoryPage(ActionHistorySearchVO actionHistorySearchVO, HttpSession session) {
		ModelAndView view = new ModelAndView();
		view.setViewName("actionHistory/actionHistory");
		
		if(actionHistorySearchVO.getPageNo() == -1) {
			actionHistorySearchVO = (ActionHistorySearchVO) session.getAttribute("__AH_SEARCH__");
			if(actionHistorySearchVO == null) {
				actionHistorySearchVO = new ActionHistorySearchVO();
				actionHistorySearchVO.setPageNo(0);
				//검색 날짜 초기화
				setInitiateSearchData(view,actionHistorySearchVO);
			}				
		}
		//날짜를 하나로 모음 : 년도 월 일 각각 인것을 하나로
		actionHistorySearchVO.setStartDate(
				DateUtil.makeDate(actionHistorySearchVO.getStartDateYear()
						, actionHistorySearchVO.getStartDateMonth()
						, actionHistorySearchVO.getStartDateDate())
				+" 00:00:00"
				);
		actionHistorySearchVO.setEndDate(
				DateUtil.makeDate(actionHistorySearchVO.getEndDateYear()
						, actionHistorySearchVO.getEndDateMonth()
						, actionHistorySearchVO.getEndDateDate())
				+" 23:59:59"
				);
		
		view.addObject("search", actionHistorySearchVO);
		int startDateMaximumDate = DateUtil.getActualMaximumDate(
				Integer.parseInt(actionHistorySearchVO.getStartDateYear()),
				Integer.parseInt(actionHistorySearchVO.getStartDateMonth()));
		int endDateMaximumDate = DateUtil.getActualMaximumDate(
				Integer.parseInt(actionHistorySearchVO.getEndDateYear()),
				Integer.parseInt(actionHistorySearchVO.getEndDateMonth()));
		
		view.addObject("startDateMaximumDate", startDateMaximumDate);
		view.addObject("endDateMaximumDate", endDateMaximumDate);
		
		session.setAttribute("___AH_SEARCH__", actionHistorySearchVO);
		
		PageExplorer explorer = actionHistoryService.readAllActionHistory(actionHistorySearchVO);
		view.addObject("explorer",explorer);
		return view;
	}
	
	
	private void setInitiateSearchData(
			ModelAndView view, ActionHistorySearchVO actionHistorySearchVO) {
		//검색 초기화
		String endDate = DateUtil.getNowDate();
		String[] splitedEndDate = endDate.split("-");
		actionHistorySearchVO.setEndDateYear(splitedEndDate[0]);
		actionHistorySearchVO.setEndDateMonth(splitedEndDate[1]);
		actionHistorySearchVO.setEndDateDate(splitedEndDate[2]);
		actionHistorySearchVO.setEndDate(endDate);
		
		String startDate = DateUtil.getComputedDate(Integer.parseInt(splitedEndDate[0]),
				Integer.parseInt(splitedEndDate[1]), Integer.parseInt(splitedEndDate[2]), DateUtil.DATE, -7);
		
		String[] splitedStartDate = startDate.split("-");
		actionHistorySearchVO.setStartDateYear(splitedStartDate[0]);
		actionHistorySearchVO.setStartDateMonth(splitedStartDate[1]);
		actionHistorySearchVO.setStartDateDate(splitedStartDate[2]);
		actionHistorySearchVO.setStartDate(startDate);
		
		view.addObject("search", actionHistorySearchVO);
		
		int startDateMaximumDate = DateUtil.getActualMaximumDate(Integer.parseInt(splitedStartDate[0]), Integer.parseInt(splitedStartDate[1]));
		int endDateMaximumDate = DateUtil.getActualMaximumDate(Integer.parseInt(splitedEndDate[0]), Integer.parseInt(splitedEndDate[1]));
		
		view.addObject("startDateMaximumDate", startDateMaximumDate);
		view.addObject("endDateMaximumDate", endDateMaximumDate);
	}
	
	
	@RequestMapping("/api/date/max/{year}/{month}")
	public void getMaxDate(@PathVariable int year, @PathVariable int month, HttpServletResponse response) {
		int maxDate = DateUtil.getActualMaximumDate(year, month);
		//데이터를 json으로 보낼 때 response body!!
		PrintWriter out = null;
		try {
			out = response.getWriter();	
			out.write(maxDate + "");
			out.flush();//데이터 내보냄
		}catch(IOException e){
			throw new RuntimeException(e.getMessage(),e);
		}finally {
			if(out != null) {
				out.close();
			}
		}	
	}	
}
