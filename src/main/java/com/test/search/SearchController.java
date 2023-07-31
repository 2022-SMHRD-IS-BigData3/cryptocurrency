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
	NewsRepository newsRepository;

	// news의 조건을 준 검색 컨트롤러
	@PostMapping(value="/search")
	public String searchNews(@RequestParam("search") String search, RedirectAttributes rttr, Model model, HttpSession session) {
		String keyword = null;
		System.out.println("이건뭐야??  :" +search);
		if ("ETH".equals(search)) {
			keyword = "이더리움";
		} else if ("BCH".equals(search)) {
			keyword = "비트코인 캐시";
		} else if ("BTC".equals(search)) {
			keyword = "비트코인";
		} else if ("BNB".equals(search)) {
			keyword = "바이낸스코인";
		} else if ("SOL".equals(search)) {
			keyword = "솔라나";
		} else {
			keyword = "라이트코인";
		}
	    List<News> newsList = new ArrayList<>();
        newsList = newsRepository.findByKeyword(keyword);
        System.out.println("keyword: " + keyword);
	    if (session.getAttribute("user") != null) {
	        tbl_user user = (tbl_user) session.getAttribute("user");
//	        rttr.addFlashAttribute("user", user);
	        model.addAttribute("user", user);
	        
	    }
//	    rttr.addFlashAttribute("newsList", newsList);
	    model.addAttribute("newsList", newsList);

	    return "index";
	}



	
}
