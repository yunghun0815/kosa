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
@Builder
@Getter
@Entity
@Table(name = "match_list")
public class MatchList{
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	
	@Column(nullable = false)
	private String name; //소환사 이름
	
	@JoinColumn(name = "summoner_id")
	@ManyToOne(fetch = FetchType.EAGER)
	private Summoner summoner; //소환사 id
	
	@Column(nullable = false)
	private String gameStartTimestamp; //시작시간
	
	@Column(nullable = false)
	private String matchId; //게임 id
	
	@Column(nullable = false)
	String individualPosition; //포지션
	
	@Column(nullable = false)
	String win; //승리여부
	
	@Column(nullable = false)
	String summoner1Id; //스펠1
	
	@Column(nullable = false)
	String summoner2Id; //스펠2

	@Column(nullable = false)
	String championName; //이름

	@Column(nullable = false)
	long kills; //킬

	@Column(nullable = false)
	long deaths; //데스

	@Column(nullable = false)
	long assists; //어시

	@Column(nullable = false)
	long goldEarned; //골드

	@Column(nullable = false)
	long visionWardsBoughtInGame; //핑와

	@Column(nullable = false)
	long totalDamageDealtToChampions; //딜량

	@Column(nullable = false)
	long wardsPlaced; //와드

	@Column(nullable = false)
	long wardsKilled; //와드제거

	@Column(nullable = false)
	long totalMinionsKilled; //미니언

	@Column(nullable = false)
	long neutralMinionsKilled; //정글몹

	@Column(nullable = false)
	long champLevel; //챔피언레벨

	@Column(nullable = false)
	long item0; //아이템

	@Column(nullable = false)
	long item1;

	@Column(nullable = false)
	long item2;

	@Column(nullable = false)
	long item3;

	@Column(nullable = false)
	long item4;

	@Column(nullable = false)
	long item5;

	@Column(nullable = false)
	long item6;

	@Column(nullable = false)
	String queueId; //큐타입 ex)솔랭, 자랭

	@Column(nullable = false)
	String gameDuration; //게임시간

	@Column(nullable = false)
	String mainRuneImg; //메인룬이미지

	@Column(nullable = false)
	String subRuneImg; //서브룬이미지
}
