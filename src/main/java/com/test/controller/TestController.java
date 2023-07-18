package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.login.DashboardRepository;
import com.test.login.tbl_user;

@Controller
public class TestController {
	@Autowired
	DashboardRepository repository;
	
	@RequestMapping("/test")
	public String test() {
		
		return "test";
	}
	@RequestMapping("/main")
	public String main() {
		
		return "index";
	}
	@RequestMapping("/signUp")
	public String signup(){
		
		return "sign-up";
	}
	@PostMapping("/signUp")
	public String signup(tbl_user vo){
		repository.save(vo);
		return "redirect:/";
	}
	
	
	
	@RequestMapping("/signIn")
	public String signin(){
		
		return "sign-in";
	}
	@RequestMapping(value="/signIn", method =RequestMethod.POST)
	public String signin(String uid, String upw, RedirectAttributes rttr) throws Exception{
		tbl_user user = repository.findUserByUidAndUpw(uid, upw);
		//Scanner sc = new Scanner(System.in);
		//System.out.println("DB값" + user);
		rttr.addFlashAttribute("user", user);
		return "redirect:/";
	}
	
	
	
}
