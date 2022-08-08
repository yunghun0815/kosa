package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.MatchList;
import com.example.demo.entity.Summoner;

public interface MatchListRepository extends JpaRepository<MatchList, Long>{

	Optional<MatchList> findByMatchId(String string);

//	List<MatchList> findBySummonerId(String id);
	
	List<MatchList> findBySummonerIdOrderByGameStartTimestampDesc(String id);

}
