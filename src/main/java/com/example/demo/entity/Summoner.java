package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "summoner")
public class Summoner {
	
	@Id
	@Column
	private String id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private long profileIconId;
	
	@Column(nullable = false)
	private long summonerLevel;
	
	@Column(nullable = false)
	private String puuid;
	
	@Column
	private String matchId;
	
}
