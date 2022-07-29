package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;

//@RunWith(JUnit4.class)
@SpringBootTest
class KosaStudy1ApplicationTests {

	@Autowired
	BoardRepository boardRepository;
	
	@Test
	void contextLoads() {
		Board board = Board.builder()
				.title("제목1")
				.content("내용1")
				.build();
		boardRepository.save(board);
	}
	
}
