package com.test.search;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.login.tbl_user;
import com.test.news.News;
import com.test.news.NewsRepository;

@Controller
public class SearchController {

	@Autowired
	CoinsearchRepository repository;
	@Autowired
	NewsRepository newsRepository;

	// news의 조건을 준 검색 컨트롤러
	@RequestMapping(value="/search")
	public String searchNews(@RequestParam("cname") String cname, Model model,HttpSession session) {
	    tbl_cryptocurrency cc = repository.findByCname(cname);
	    System.out.println("cc: " + cc);
	    List<News> newsList = new ArrayList<>();
	    if (cc != null) {
	        String keyword = cc.getCname();
	        newsList = newsRepository.findByKeyword(keyword);
	        System.out.println("keyword: " + keyword);
	    }
	    if (session.getAttribute("user") != null) {
	        tbl_user user = (tbl_user) session.getAttribute("user");
	        model.addAttribute("user", user);
	        
	    }
	    model.addAttribute("coinData", cc);
	    model.addAttribute("keyword", cname);
	    model.addAttribute("newsList", newsList);

	    return "index";
	}



	
}
