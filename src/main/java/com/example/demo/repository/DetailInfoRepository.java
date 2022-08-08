package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.DetailInfo;

public interface DetailInfoRepository extends JpaRepository<DetailInfo, Long>{

	List<DetailInfo> findBySummonerId(String id);

}
