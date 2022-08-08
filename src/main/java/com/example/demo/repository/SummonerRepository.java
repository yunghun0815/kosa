package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Summoner;

public interface SummonerRepository extends JpaRepository<Summoner, String>{
	Optional<Summoner> findById(String id);
}
