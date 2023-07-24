package com.test.news;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class NewsController {
    private final NewsRepository newsRepository;

    public NewsController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

 // 뉴스의 전체를 가져오는 컨트롤러
    @GetMapping("/main")
    public String getNews(Model model) {
        List<News> newsList = newsRepository.findAll();
        //System.out.println(newsList + "들어오나");
        model.addAttribute("newsList", newsList);
        return "index";
    }
   
    
    
}