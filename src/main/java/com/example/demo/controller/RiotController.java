package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.RiotService;

@Controller
public class RiotController {
	
	@Autowired
	RiotService riotService;
	
	@GetMapping("/test")
	public String testPage() {
		return "/test";
	}
	
	@GetMapping("/search") //값 없을때 14초 , 값 있을때 3초 
	public ModelAndView search(ModelAndView mv, @RequestParam(value = "name") String name, @RequestParam(value = "no") int no) throws Exception {
		
		long start = System.currentTimeMillis();
		riotService.search(mv, name, no);
		mv.setViewName("/riot/list");
		long end = System.currentTimeMillis();
		
		System.out.println("------------- 시간 ----------------");
		System.out.println((end-start)/1000.0 + "초");
		System.out.println("---------------------------------");
		
		return mv;
	}
}
