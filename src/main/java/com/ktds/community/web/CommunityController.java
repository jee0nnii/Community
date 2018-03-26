package com.ktds.community.web;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.community.service.CommunityService;
import com.ktds.community.vo.CommunitySearchVO;
import com.ktds.community.vo.CommunityVO;
import com.ktds.member.constants.Member;
import com.ktds.member.vo.MemberVO;
import com.ktds.util.DownloadUtil;

import io.github.seccoding.web.pager.explorer.PageExplorer;

@Controller
public class CommunityController {
	// 로그 생성!!!!!
	private final Logger logger = LoggerFactory.getLogger(CommunityController.class);

	private CommunityService communityService;

	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}
	//0326
	@RequestMapping("/reset")
	public String viewInitListPage(HttpSession session) {
		session.removeAttribute("__SEARCH__");
		return "redirect:/";
	}
	//0326
	@RequestMapping("/")
	public ModelAndView viewListPage(CommunitySearchVO communitySearchVO, HttpSession session) {
		//데이터가 안넘어왔을경우
		//1. 리스트 페이지에 처음 접근 했을 떄
		//2. 글 내용을 보고 , 목록보기 링크를 클릭했을때
		if(communitySearchVO.getPageNo() < 0) {
			//세션에 저장된 CommunitySearchVO를 가져옴
			communitySearchVO = (CommunitySearchVO) session.getAttribute("__SEARCH__");
			//세션에 저장된 CommunitySearchVO가 없을 때 pageNo= 0으로 초기화
			if(communitySearchVO == null) {
				communitySearchVO = new CommunitySearchVO();
				communitySearchVO.setPageNo(0);
			}
		}
		session.setAttribute("__SEARCH__", communitySearchVO);
		
		ModelAndView view = new ModelAndView();
		// web-inf/community/list.jsp
		view.setViewName("community/list");
		
		//0326 : 현재 어떤 검색을 했는지 알려주는 거
		view.addObject("search",communitySearchVO);
		
		PageExplorer pageExplorer = communityService.getAll(communitySearchVO);
		view.addObject("pageExplorer", pageExplorer);

		return view;
	}

	/**
	 * @GetMapping("/write") ==> 이렇게도 매핑명 부여할 수 있음 public ModelAndView
	 * enrollNewContents() { ModelAndView view = new ModelAndView();
	 * view.setViewName("community/write"); return view; } 이거말고!!!! 페이지를 보여주기만 할 꺼여서
	 * 리턴타입이 스트링이래
	 */
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String viewWritePage() {
		return "community/write";
	}

	/**
	 * 파라미터 받아오는 방법이 여러가지임 1) public String doWrite(HttpHttpServletRequest req) {
	 * String title = req.getParameter("title"); 2) 커맨드 객체 방법 ( vo를 통해서 값을 받아옴 )
	 * public String doWrite(CommunityVO communityVO) {
	 * System.out.println(communityVO.getTitle());
	 * System.out.println(communityVO.getContents());
	 * System.out.println(communityVO.getNickname());
	 * System.out.println(communityVO.getAccount());
	 * System.out.println(communityVO.getWriteDate()); return ""; }
	 */
	// @PostMapping("/write")
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public ModelAndView doWrite(@ModelAttribute("writeForm") @Valid CommunityVO communityVO, Errors errors,
			HttpServletRequest request) {
		// String --> ModelAndView로 바꿈....
		// errors에는 vo에 작성한 message들이 들어있음 꼭 valid 뒤에 작성해야됨

		/*
		 * //로그인 상태 확인 --> interceptor로 처리 if (session.getAttribute(Member.USER) ==
		 * null) { return new ModelAndView("redirect:/login"); }
		 */

		// 필수 입력값에 값이 안들어가 있을 경우
		if (errors.hasErrors()) {
			ModelAndView view = new ModelAndView();
			view.setViewName("community/write");
			view.addObject("communityVO", communityVO);
			return view;
		}
		/*
		 * validation check on server area if(communityVO.getTitle() == null ||
		 * communityVO.getTitle().length() == 0) { session.setAttribute("status",
		 * "emptyTitle"); return "redirect:/write"; } if(communityVO.getContents() ==
		 * null || communityVO.getContents().length() == 0) {
		 * session.setAttribute("status", "emptyContents"); return "redirect:/write"; }
		 * if(communityVO.getWriteDate() == null || communityVO.getWriteDate().length()
		 * == 0) { session.setAttribute("status", "emptyDate"); return
		 * "redirect:/write"; }
		 */

		// 작성자의 IP를 얻어오는 코드
		String requestorIP = request.getRemoteAddr();
		communityVO.setRequestIP(requestorIP);

		communityVO.save();

		boolean isSuccess = communityService.createCommunity(communityVO);
		// 실패와 성공을 구분하는 이유는? 성공하면 리스트로 실패면 라이트로 보내려고
		// 성공이면!!
		if (isSuccess) {
			return new ModelAndView("redirect:/reset"); // 이 url로 이동해라
		} // 실패면!!
		/* session.setAttribute("status", "writeFail"); */
		return new ModelAndView("redirect:/write");
	}

	@RequestMapping("/read/{id}")
	public String viewReadPage(@PathVariable int id) {

		// 조회수 증가
		if (communityService.incrementView(id)) {
			return "redirect:/view/" + id;
		}
		return "redirect:/";

	}

	@RequestMapping("/view/{id}")
	// 데이터를 심어서 보여줌
	public ModelAndView viewViewPage(@PathVariable int id) {

		ModelAndView view = new ModelAndView();
		view.setViewName("community/view");

		CommunityVO viewOne = communityService.getOne(id);
		view.addObject("community", viewOne);
		return view;
	}

	@RequestMapping(value = "/recommend/{id}", method = RequestMethod.GET)
	// 페이지만 보여줌
	public String doRecommend(@PathVariable int id) {
		boolean isSuccess = communityService.recommendCommunity(id);
		if (isSuccess) {
			return "redirect:/view/" + id;
		}
		return null;
	}

	@RequestMapping("/get/{id}")
	public void download(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {
		CommunityVO community = communityService.getOne(id);
		String filename = community.getDisplayFilename();

		DownloadUtil download = new DownloadUtil("d:/uploadFiles/" + filename);
		try {
			download.download(request, response, filename);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	//게시글 수정하기
	@RequestMapping(value = "/modify/{id}", method=RequestMethod.GET)
	public ModelAndView viewModifyPage(@PathVariable int id, HttpSession session) {
		MemberVO member = (MemberVO)session.getAttribute(Member.USER);
		CommunityVO community = communityService.getOne(id);
		
		int account = member.getAccount();
		
		if ( account != community.getAccount()) {
			return new ModelAndView("error/404");
		}
			
		ModelAndView view = new ModelAndView();
		view.setViewName("community/write");
		view.addObject("communityVO", community);
		//FIXME
		view.addObject("mode", "modify");
		
		return view;
	}	
	
	//게시글 수정하기
	@RequestMapping(value = "/modify/{id}", method=RequestMethod.POST)
	public String doModifyAction(@PathVariable int id, HttpSession session, HttpServletRequest request
			,@ModelAttribute("writeForm") @Valid CommunityVO communityVO, Errors errors) {
		// 1. 이 글이 내 글이 맞는지
		MemberVO member = (MemberVO)session.getAttribute(Member.USER);
		CommunityVO originalVO = communityService.getOne(id); //원본 글
		if(member.getAccount() !=originalVO.getAccount()) {
			return "error/404";
		}
		// 2. 에러 체크
		if(errors.hasErrors()) {
			return "redirect:/modify/"+id;
		}
		CommunityVO newCommunity = new CommunityVO();
		newCommunity.setId(originalVO.getId()); //게시글 번호
		newCommunity.setAccount(originalVO.getAccount()); // 사용자 번호
		
		boolean isModify= false;
		
		// 3. ip 변경 확인
		String ip = request.getRemoteAddr();
		if ( !ip.equals(originalVO.getRequestIP())) {
			newCommunity.setRequestIP(ip);
			isModify = true;
		}
		// 4. 제목 변경 확인
		if ( !originalVO.getTitle().equals(communityVO.getTitle())) {
			newCommunity.setTitle(communityVO.getTitle());
			isModify = true;
		}
		//5. 내용 변경
		if ( !originalVO.getContents().equals(communityVO.getContents())) {
			newCommunity.setContents(communityVO.getContents());
			isModify = true;
		}
		// 6-1 파일변경 확인
		if(communityVO.getDisplayFilename().length()>0) {
			File file = new File("d:/uploadFiles/"+communityVO.getDisplayFilename());
			file.delete();
			//파일지우고 파일없음!!
			communityVO.setDisplayFilename("");
		}
		else {
			//등록되있는 파일 유지
			communityVO.setDisplayFilename(originalVO.getDisplayFilename());
		}
		
		// 6. 파일 
		communityVO.save();
		if ( !originalVO.getDisplayFilename().equals(communityVO.getDisplayFilename())) {
			newCommunity.setDisplayFilename(communityVO.getDisplayFilename());
			isModify = true;
		}
		// 7. 변경이 없는지 확인
		if(isModify) {
			//update하는 서비스 호출
			communityService.updateCommunity(newCommunity);
		}
		
		return "redirect:/view/"+id;
	}
	
	
	@RequestMapping(value = "/deletePage/{id}")
	public String doRemovePage(@PathVariable int id, HttpSession session) {
		//명시적 형변환!!
		//primitive type 간의 형변환 :: long --> int : (int)varrr
		//reference type 간의 형변환 :: 상속이나 구현 관계일 때만 형변환이 가능함
		MemberVO member = (MemberVO)session.getAttribute(Member.USER);
		//object랑 memberVO의 관계는 상속  :: 모든 클래스는 오브젝트를 상속 받음
		
		//삭제하려는 게시글을 하나 가져옴
		CommunityVO community = communityService.getOne(id);
		//이게 내가 쓴게 맞냐??를 묻고 지울 수 있게 해줌
		boolean isMyCommunity = member.getAccount() == community.getAccount();
		
		//short cut 평가 : 내가 쓴 거 맞는지 확인을 하고 지우는게 맞음!! 
		if(isMyCommunity && communityService.deleteCommunity(id)) {
			return "redirect:/";
		}
		return "redirect:/WEB-INF/view/error/404";
		
	}
}
