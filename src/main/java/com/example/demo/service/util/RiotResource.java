package com.example.demo.service.util;

public class RiotResource {
	
	public static String summonerV4Name = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"; // + {summonerName}
	
	public static String leagueV4Etnries = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/"; // + {encryptedSummonerId}
	
	public static String matchV5MatchId = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"; // + {puuid}

	public static String matchV5MatchData = "https://asia.api.riotgames.com/lol/match/v5/matches/"; // + {matchId}
	
}
