package com.ktds.member.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.community.service.CommunityService;
import com.ktds.community.vo.CommunityVO;
import com.ktds.member.constants.Member;
import com.ktds.member.service.MemberService;
import com.ktds.member.vo.MemberVO;

@Controller
public class MyPageController {

	//필요한 의존 추가
	private MemberService memberService;
	private CommunityService communityService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}
	
	@RequestMapping("/mypage/communities")
	public ModelAndView viewMyCommunities(HttpSession session){
		
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		//내가 쓴 글 가져옴
		List<CommunityVO> myCommunities = communityService.readMyCommunities(member.getAccount());
				
		ModelAndView view = new ModelAndView();
		view.setViewName("member/mypage/myCommunities");
		view.addObject("myCommunities", myCommunities);
		return view;
		
	}
	@RequestMapping("/mypage/communities/delete")
	public String doDeleteMyCommunitiesAction(
			HttpSession session, @RequestParam List<Integer> delete) {
	/*	for(int index : delete) {
			System.out.println(index);
		}*/
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		communityService.deleteCommunities(delete, member.getAccount());
		
		return "redirect:/mypage/communities";
	}
	
}
