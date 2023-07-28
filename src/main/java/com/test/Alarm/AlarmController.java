package com.test.Alarm;


import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.login.tbl_user;


@Controller
public class AlarmController {

    @Autowired
    private AlarmRepository alamRepository;

    @PostMapping("/addAlarm")
    public String addAlarm(String ccd,int price, HttpSession session,  RedirectAttributes rttr) throws Exception {
		// 로그인 상태 확인
	    tbl_user user = (tbl_user) session.getAttribute("user");
    	
	    String uid = user.getUid();
	    Alarm boul = new Alarm();
	    boul.setUid(uid);
	    boul.setCcd(ccd);
	    boul.setPrice(price);
	    System.out.println("저장정보 확인"+ uid + ccd + price);
	    alamRepository.save(boul);
	    
//	    System.out.println("저장정보 확인"+ uid + ccd + price);
	    rttr.addFlashAttribute("user", user);
	    
        return "redirect:/main";
    }
    
//    @PostMapping("/alarm") //alarm 저장 메소드에서 redirect로 /alarm으로 들어오도록
//    public String getNews(HttpSession session,  RedirectAttributes rttr) throws Exception {
//      tbl_user user = (tbl_user) session.getAttribute("user");
//      if (user == null) {
//         return "index";
//      }
//      String uid = user.getUid();
//      System.out.println("들어오나"+uid);
//        ArrayList<Alarm> alarmList = alamRepository.findByUid(uid);
//        System.out.println("들어오나"+alarmList);
//        rttr.addFlashAttribute("alarmList", alarmList);
//        return "redirect:/main";
//    }
    
    @GetMapping("/alarm") //alarm 저장 메소드에서 redirect로 /alarm으로 들어오도록
    public @ResponseBody ArrayList<Alarm> getAlarms(HttpSession session) {
      tbl_user user = (tbl_user) session.getAttribute("user");
      String uid = user.getUid();
      System.out.println("들어오나"+uid);
        ArrayList<Alarm> alarmList = alamRepository.findByUid(uid);
        System.out.println("들어오나"+alarmList);
        return alarmList;
    }
}