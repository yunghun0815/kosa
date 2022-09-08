package com.example.demo.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.ModelAndView;

public interface RiotService {
	void search(ModelAndView mv, String name, int no, Pageable pageable) throws Exception;
	
	//기본정보 검색
	String summoner(String name) throws Exception;
	
	//상세정보 검색
	void summonerDetailInfo(String id) throws Exception;
	
	//matchId 검색
	String matchId(String puuid);
	
	//matchList 검색
	void summonerGameData(String matchId, int num) throws Exception;
}
