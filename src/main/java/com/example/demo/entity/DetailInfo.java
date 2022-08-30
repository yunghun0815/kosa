package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "detail_info")
public class DetailInfo{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	
	@Column(nullable = false)
	private String name;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "summoner_id")
	private Summoner summoner;
	
	@Column(nullable = false)
	private String queueType;
	
	@Column(nullable = false)
	private String tier;
	
	@Column(nullable = false)
	private String tierRank;
	
	@Column(nullable = false)
	private long leaguePoints;
	
	@Column(nullable = false)
	private long wins;
	
	@Column(nullable = false)
	private long losses;

}
