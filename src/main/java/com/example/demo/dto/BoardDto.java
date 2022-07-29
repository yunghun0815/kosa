package com.example.demo.dto;

import com.example.demo.entity.Board;

import lombok.Data;

@Data
public class BoardDto {
	private String title;
	private String content;
	
	public Board write(){
		Board board = Board.builder()
				.title(title)
				.content(content)
				.build();
		return board;
	}
}
