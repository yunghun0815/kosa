package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.BoardDto;
import com.example.demo.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@GetMapping("/board")
	public String board() {
		return "/board/board";
	}
	
	@PostMapping("/board/write")
	public String write(BoardDto dto) {
		boardService.wrtie(dto);
		return "/board/board";
	}
}
