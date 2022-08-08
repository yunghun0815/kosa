package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class DetailInfo implements Serializable{
//	DetailInfo(String name, String id, String queueType, String tier, long leaguePoints, long wins, long losses){
//		this.name = name;
//		summoner.setId(id);
//		
//	};
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	
	@Column(nullable = false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "summoner_id")
	private Summoner summoner;
	
	@Column(nullable = false)
	private String queueType;
	
	@Column(nullable = false)
	private String tier;
	

	
	@Column(nullable = false)
	private long leaguePoints;
	
	@Column(nullable = false)
	private long wins;
	
	@Column(nullable = false)
	private long losses;
}
