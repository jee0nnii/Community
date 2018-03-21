package com.ktds.member.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ktds.member.constants.Member;
import com.ktds.member.vo.MemberVO;

@Controller
public class MCValidation1 {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	//String :: jsp 페이지를 리턴 
	public String viewLoginPage(HttpSession session) {
		/*
		 2. 세션을 그냥 가져옴!!! HttpSession session
		 세션이 있는지 검증하기
		 __USER__ 객체에 데이터가 있는지 확인하고 있으면 리스트로 가라!!
		 */
		if (session.getAttribute(Member.USER) != null) {
			return "redirect:/";
		}
		return "member/login";
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	//처리할 때는 String
	public String doLoginAction(MemberVO memberVO, HttpServletRequest req) {
		HttpSession session = req.getSession();	
		//0306 계정 비번 을 제대로 가지고 왔는지 체크
		//null : account 값 자체가 없는거         // == 0 : 있는데 안적은 거
		if(memberVO.getAccount() == null || memberVO.getAccount().length() == 0) {
			session.setAttribute("status", "emptyAccount");
			return "redirect:/login";
		}
		if(memberVO.getPassword() == null || memberVO.getPassword().length() == 0) {
			session.setAttribute("status", "emptyPassword");
			return "redirect:/login";
		}
		
		
		/*
		1. request에 있는 세션정보를 가져오기 
		:: HttpServletRequest req 파라미터 추가
		리퀘스트 객체에서 세션 꺼내와서 
		로그인했음을 알려주기 :: session.setAttribute
		로그인의 key는 고정 : __USER__ --> 상수로 변환함!! Member.USER
		*/
		// 0306 :: HttpSession session = req.getSession();		
		//ID : ADMIN PWD : 1234 --> 맞으면 리스트 틀리면 로그인으로
		if ( memberVO.getAccount().equals("admin") && 
				memberVO.getPassword().equals("1234")) {			
			memberVO.setNickname("관리자");
			session.setAttribute(Member.USER, memberVO);	
			//0306로그인 했을 때 status라는 애가 있으면 지우고 없으면 말아라
			session.removeAttribute("status");
			return "redirect:/";
		}
		//0306로그인 할 때 계정과 비밀번호 입력 잘못했을 때 뜨는 거
		session.setAttribute("status", "fail");
		return "redirect:/login";
	}	
	
	@RequestMapping("/logout")
	public String doLogoutAction(HttpSession session) {		
		//0306세션 소멸
		session.invalidate();
		return "redirect:/login";
	}
}
