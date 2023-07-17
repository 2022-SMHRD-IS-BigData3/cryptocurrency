package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
	
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
	
	@RequestMapping("/signIn")
	public String signin(){
		
		return "sign-in";
	}
	
	
	
}
