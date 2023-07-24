package com.test.favorite;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.login.tbl_user;

@Controller
public class FavoriteController {

	@Autowired
	FavoriteRepository repository;
	
	@PostMapping("/favorite")
	public String favorite(String ccd, HttpSession session,  RedirectAttributes rttr) throws Exception {
		// 로그인 상태 확인
	    tbl_user user = (tbl_user) session.getAttribute("user");
	    System.out.println("로그인 상태 확인"+user);
	    if (user == null) {
	        // 로그인되지 않은 경우 index.html로 리다이렉트
	        return "index";
	    } 
	    
	    String uid = user.getUid();
	    tbl_favorites favorite = new tbl_favorites();
	    favorite.setUid(uid);
	    favorite.setCcd(ccd);
	    repository.save(favorite);
	    
	    System.out.println("저장정보 확인"+ uid + ccd);
	    rttr.addFlashAttribute("user", user);
	    	return "redirect:/main";

	}
}
