package com.test.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	
	@Autowired
	DashboardRepository repository;
	
	// 로그인 기능
	@RequestMapping(value="/signIn", method = RequestMethod.POST)
	public String signin(String uid, String upw, HttpServletRequest request, RedirectAttributes rttr) throws Exception {
	    tbl_user user = repository.findUserByUidAndUpw(uid, upw);
	    
	    // session에 로그인 정보 저장
	    HttpSession session = request.getSession(true);
	    session.setAttribute("user", user);

	    System.out.println("User logged in: " + user);
	    
	    // 페이지로 user 넘기기
	    rttr.addFlashAttribute("user", user);

	    return "redirect:/";
	}


	
}
