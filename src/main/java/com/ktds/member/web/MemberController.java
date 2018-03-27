package com.ktds.member.web;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.actionhistory.vo.ActionHistory;
import com.ktds.actionhistory.vo.ActionHistoryVO;
import com.ktds.community.service.CommunityService;
import com.ktds.member.constants.Member;
import com.ktds.member.service.MemberService;
import com.ktds.member.vo.MemberVO;

@Controller
public class MemberController {
	
	private MemberService memberService;
	//0319 
	private CommunityService communityService;
	//0327
/*	직접호출할거 아님
 * private ActionHistoryService actionHistoryService;
	
	public void setActionHistoryService(ActionHistoryService actionHistoryService) {
		this.actionHistoryService = actionHistoryService;
	}*/
	
	//set + ctrl space 
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	//0319 
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}
	
	//0321
	@RequestMapping("/api/exists/email")
	@ResponseBody
	public Map<String, Boolean> apiIsExistsEmail(
			@RequestParam String email){
		
		boolean isExists = memberService.readCountMemberEmail(email);
		
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("response", isExists);
		
		return response;
	}
	
	@RequestMapping("/api/exists/nickname")
	@ResponseBody
	public Map<String, Boolean> apiIsExistsNickname(
			@RequestParam String nickname){
		boolean isExists = memberService.readCountMemberNickname(nickname);
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("respNick", isExists);
		return response;
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String viewLoginPage(HttpSession session) {
		if (session.getAttribute(Member.USER) != null) {
			return "redirect:/";
		}
		return "member/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLoginAction(@ModelAttribute("loginForm")
		@Valid MemberVO memberVO, Errors errors, HttpServletRequest req, @RequestAttribute ActionHistoryVO actionHistory) {
		/* 필수 입력값에 값이 안들어가 있을 경우
		if (errors.hasErrors()) {
			ModelAndView view = new ModelAndView();
			view.setViewName("member/login");
			view.addObject("memberVO", memberVO);
			return view;
		}*/
		HttpSession session = req.getSession();
		
		//0327
		actionHistory.setReqType(ActionHistory.ReqType.MEMBER);
		String log = String.format(ActionHistory.Log.LOGIN, memberVO.getEmail());
		actionHistory.setLog(log);
		
		
		// FIXME :: 디비에 계정이 존재하지 않을 경우로 변경
		MemberVO loginMember = memberService.readMember(memberVO);
		if( loginMember != null) {
			session.setAttribute(Member.USER, loginMember);
			return "redirect:/";
		}
		return "redirect:/login";		
/*if (memberVO.getEmail().equals("admin") && memberVO.getPassword().equals("1234")) {
	memberVO.setNickname("관리자");
	session.setAttribute(Member.USER, memberVO);
	session.removeAttribute("status");
	return new ModelAndView("redirect:/");
}
	session.setAttribute("status", "fail");
	return new ModelAndView("redirect:/login");*/
	}

	@RequestMapping("/logout")
	public String doLogoutAction(HttpSession session, @RequestAttribute ActionHistoryVO actionHistory) {		
		//세션에 있는 멤버 정보 받아와야됨
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		
		actionHistory.setReqType(ActionHistory.ReqType.MEMBER);
		String log = String.format(ActionHistory.Log.LOGOUT, member.getEmail());
		actionHistory.setLog(log);
		
		session.invalidate();
		
		return "redirect:/login";
	}
	
	//0319 회원탈퇴프로세스 1
	@RequestMapping("/deleteAccount/process1")
	public String viewVerifyPage() {
		return "member/delete/process1";
		
	}
	//0319 회원탈퇴프로세스 2
	@RequestMapping("/deleteAccount/process2")
	public ModelAndView viewDeleteMyCommunitiesPage(
			//파라미터 받아오는 방법
			//필수 파라미터는 아니다 라는 것을 정의하고 디폴트 벨류값을 넣음
			@RequestParam(required = false, defaultValue = "") String password, HttpSession session) {
		//사용자가 임의로 들어왔을때 에러페이지
		if(password.length()==0) {
			return new ModelAndView("error/404");
		}
		//멤버의 패스워드를 가지고와서
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		member.setPassword(password);
		//로그인을 시켜 볼꺼야ㅑㅑㅑㅑㅑ
		MemberVO verifyMember = memberService.readMember(member);
		if (verifyMember == null) {
			//비밀번호를 잘못 입력했을때 넘겨줄 페이지
			return new ModelAndView("redirect:/delete/process1");
		}
		
		ModelAndView view = new ModelAndView();
		//TODO 내가 작성한 게시글의 갯수 가져오기
		int myCommunitiesCount = communityService.readMyCommunitiesCount(verifyMember.getAccount());
		view.setViewName("member/delete/process2");
		view.addObject("myCommunitiesCount", myCommunitiesCount);
		
		//랜덤으로 난수만 전달
	    //uuid :: 시간을 베이스로 
		String uuid = UUID.randomUUID().toString();
		
		session.setAttribute("__TOKEN__", uuid);
		view.addObject("token",uuid);
		return view;
		
	}
	
	//회원탈퇴
	@RequestMapping("/deleteAccount/{deleteFlag}")
	public String deleteMember(HttpSession session, 
			@RequestParam(required=false, defaultValue="") 
	String token, @PathVariable String deleteFlag
	, @RequestAttribute ActionHistoryVO actionHistory) {
		
		String sessionToken = (String) session.getAttribute("__TOKEN__");
		//프로세스 2에서 세션을 만든 적이 없거나 || 해커가 토큰을 임의로 변경했다면
		if(sessionToken == null || !sessionToken.equals(token)) {
			return "error/404";
		}
			
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		if(member == null) {
			return "redirect:/login";
		}
		
		String log = String.format(ActionHistory.Log.EXPIRE, member.getEmail());
		actionHistory.setLog(log);
		
		int account = member.getAccount();
		if(memberService.removeMember(account, deleteFlag)) {
			session.invalidate();
		}
		return "member/delete/delete";
	}

	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public String viewRegistPage() {
		return "member/regist";
	}

	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public String doRegistAction(@ModelAttribute("registForm") 
									@Valid MemberVO memberVO, Errors errors,
									@RequestAttribute ActionHistoryVO actionHistory) {
		if (errors.hasErrors()) {
			/* ModelAndView view = new ModelAndView();
			 * view.setviewname
			 * view.addobject
			 * return view 대신에 아래로 다할 수 있움!!! ModelAttribute 써서*/
			return "member/regist";
		}	
		//0327
		actionHistory.setReqType(ActionHistory.ReqType.MEMBER);

		if ( memberService.createMember(memberVO)) {
			//0327 로그 찍기
			String log = String.format(ActionHistory.Log.REGIST, memberVO.getEmail(), memberVO.getNickname(), "true");
			actionHistory.setLog(log);
			//actionHistoryService.createActionHistory(history);
			
			return "redirect:/login";
		}		
		//0327 로그 찍기
		String log = String.format(ActionHistory.Log.REGIST, memberVO.getEmail(), memberVO.getNickname(), "false");
		actionHistory.setLog(log);
		//actionHistoryService.createActionHistory(history);
		
		return "redirect:/regist";
	}
}
