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
	@RequestMapping("/")
	public String main() {
		
		return "start";
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
	@RequestMapping("/start")
	public String start() {
		
		return "start";
	}
	
	
	
	
}
