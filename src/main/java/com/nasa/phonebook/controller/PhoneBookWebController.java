package com.nasa.phonebook.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nasa.phonebook.dao.PhoneBookDao;
import com.nasa.phonebook.dto.PhoneBookDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller()
@RequestMapping("/phonebook")
public class PhoneBookWebController {
	PhoneBookDao phonebookDao;
	
	private final Logger logger =
			LoggerFactory.getLogger(this.getClass()); // 해당 클래스가 빌린다
	
	@Autowired
	public PhoneBookWebController(PhoneBookDao phonebookDao) {
		this.phonebookDao = phonebookDao;
	}
	
//	로그아웃
	/* 메인페이지 로그아웃 */
	   @PostMapping("logout.do")
	   public String logoutMainGET(HttpServletRequest request,Model model) throws Exception{
	      HttpSession session = request.getSession();
	      model.addAttribute("error", "로그아웃 성공");
	      session.invalidate();
	      return "alert";   
	   }
	
//	회원가입요청(버튼누름 -> 회원가입 jsp로 넘겨줌
	@GetMapping("/signup")
	public String signUp() {
		return "phonebooksignup";
	}
	
// id check
	@PostMapping("/idcheck")
	@ResponseBody
	public int idCheck(@RequestParam("loginid") String loginid) throws Exception{
		int cnt = 1;
		cnt = phonebookDao.idCheck(loginid);
		return cnt;
	}
	
//	회원가입 정보 확인
   @PostMapping("/signrequest")
   public String signrequest(Model model
		 , @RequestParam("loginid") String loginid
         , @RequestParam("loginpw") String loginpw
         , @RequestParam("loginph") String loginph) {
      PhoneBookDto phonebookDto = new PhoneBookDto();
      phonebookDto.setLoginid(loginid);
      phonebookDto.setLoginpw(loginpw);
      phonebookDto.setLoginph(loginph);
      try {
         phonebookDao.addloginList(phonebookDto);
         model.addAttribute("login", phonebookDto);
      } catch (Exception e) {
         e.printStackTrace();
         logger.warn(loginid+loginpw+loginph);
         model.addAttribute("error",loginid+loginpw+loginph);
         return "redirect:/phonebook/signup";
      }
      return "redirect:/phonebook/login";
   }
	
//   로그인 요청
	@GetMapping("/login")
	public String logIn() {
		return "phonebooklogin";
	}
	
//	로그인 확인
	@PostMapping("/loginrequest")
	public String loginRequest(Model model,@RequestParam("loginid") String loginid
	         , @RequestParam("loginpw") String loginpw,  HttpServletRequest request) throws Exception {
		PhoneBookDto phonebookDto = new PhoneBookDto();
	    phonebookDto.setLoginid(loginid);
	    phonebookDto.setLoginpw(loginpw);
    	int resultlogin = phonebookDao.logIn(loginid, loginpw);
	         if (resultlogin == 0) {
	        	 logger.warn("아이디 및 비밀번호 오류");
	             model.addAttribute("error", "아이디 및 비밀번호를 잘못입력하셨습니다.");
	             return "alert";
	         } 
        request.getSession().setAttribute("loginid", loginid);
        request.getSession().setAttribute("loginpw", loginpw);
        return "redirect:/phonebook/list";
	}//loginIn() end
	
	@GetMapping("/list")
	public String getList(Model model, HttpServletRequest request) {
		List<PhoneBookDto> list;
		String loginid = (String)request.getSession().getAttribute("loginid");
		try {
			list = phonebookDao.getAll(loginid);
			model.addAttribute("phonebookList",list);
		} catch (Exception e) {
			e.printStackTrace(); //개발시 필요
			logger.warn("뉴스 목록 생성 과정에서 문제 발생"); // 서버쪽 콘솔에 출력
			model.addAttribute("error", "뉴스 목록이 정상적으로 처리되지 않았습니다");
		}
		return "phonebooklist";
	}
	
// ph check
	@PostMapping("/checkph")
	@ResponseBody
	public int phCheck(@RequestParam("userph") String userph) throws Exception{
		int cnt = 1;
		cnt = phonebookDao.phCheck(userph);
		logger.info(userph);
		return cnt;
	}
	
	@PostMapping("/add")
	public String addPhonebook(Model model, @RequestParam("username") String username,
			@RequestParam("userph") String userph, @RequestParam("useraddr") String useraddr, 
			@RequestParam("groupid") String groupid, HttpServletRequest request ) {
		PhoneBookDto phonebookDto = new PhoneBookDto();
		String lognid = (String)request.getSession().getAttribute("loginid");
		phonebookDto.setLoginid(lognid);
		phonebookDto.setUsername(username);
		phonebookDto.setUserph(userph);
		phonebookDto.setUseraddr(useraddr);
		phonebookDto.setGroupid(groupid);
	    try {
	    	phonebookDao.addPhonebook(phonebookDto);
	    	model.addAttribute("phonebookList", phonebookDto);
	    } catch (Exception e) {
	    	e.printStackTrace();
	         logger.warn(lognid);
	         model.addAttribute("error",lognid);
	    	return "redirect:/phonebook/list";
	    }
		return "redirect:/phonebook/list";
	}//addPhonebook() end
	
	@PostMapping("/update")
	public String updatePhonebook(Model model, @RequestParam("username") String username,
			@RequestParam("userph") String userph, @RequestParam("useraddr") String useraddr, 
			@RequestParam("groupid") String groupid, HttpServletRequest request) {
		PhoneBookDto phonebookDto = new PhoneBookDto();
		String lognid = (String)request.getSession().getAttribute("loginid");
		phonebookDto.setLoginid(lognid);
		phonebookDto.setUsername(username);
		phonebookDto.setUserph(userph);
		phonebookDto.setUseraddr(useraddr);
		phonebookDto.setGroupid(groupid);
	    try {
	    	phonebookDao.updatePhonebook(phonebookDto);
	    	model.addAttribute("phonebookList", phonebookDto);
	    } catch (Exception e) {
	    	e.printStackTrace();
	         logger.warn(lognid);
	         model.addAttribute("error",lognid);
	    	return "redirect:/phonebook/list";
	    }
		return "redirect:/phonebook/list";
	}
	
	@PostMapping("/delete")
	public String deletePhonebook(Model model, @RequestParam("userph") String userph, HttpServletRequest request) {
		PhoneBookDto phonebookDto = new PhoneBookDto();
		String lognid = (String)request.getSession().getAttribute("loginid");
		phonebookDto.setLoginid(lognid);
		phonebookDto.setUserph(userph);
		try {
	    	phonebookDao.deletePhonebook(phonebookDto);
	    } catch (Exception e) {
	    	e.printStackTrace();
	         logger.warn(userph);
	         model.addAttribute("error",userph);
	    	return "redirect:/phonebook/list";
	    }
		return "redirect:/phonebook/list";
	}
	
	
}
