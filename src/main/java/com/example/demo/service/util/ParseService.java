package com.example.demo.service.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.jayway.jsonpath.JsonPath;

@Service
public class ParseService {
	
	//Timestamp to MM-dd
	public String timestampToMmdd(long timestamp) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		String date = sdf.format(timestamp);
		return date;
	}
	
	//queueType to String
	public String queue(long num) {
		//큐타입 참조
		//https://static.developer.riotgames.com/docs/lol/queues.json 
		String queue = null;
		switch ((int) num) {
			case 400: case 430:
				queue = "일반게임";
				break;
			case 420:
				queue = "솔로랭크";
				break;
			case 440:
				queue = "자유랭크";
				break;
			case 450:
				queue = "칼바람나락";
				break;
			case 800: case 810: case 820: case 830: case 840: case 850:
				queue = "봇전";
				break;
			case 900:
				queue = "우르프";
				break;
			case 920:
				queue = "포로왕";
				break;
			case 1020:
				queue = "단일챔피언";
				break;
			case 2000: case 2010: case 2020:
				queue = "튜토리얼";
				break;
			default:
				queue = "이벤트";
				break;
		}
		
		return queue;
	}
	
	//duration to m분+ s초
    public String duration(long num){
        String duration;
        String min = Long.toString(num/60);
        String sec = Long.toString(num%60);
        duration = min+"분 "+sec+"초";
        return duration;
    }
	//spell key to .png
	public String spell(long key) {
		String spell = null;
		switch ((int) key) {
		case 1:
			spell = "SummonerBoost.png"; // 정화
			break;
		case 3:
			spell = "SummonerExhaust.png"; // 탈진
			break;
		case 4:
			spell = "SummonerFlash.png"; // 점멸
			break;
		case 6:
			spell = "SummonerHaste.png"; // 유체화
			break;
		case 7:
			spell = "SummonerHeal.png"; // 회복
			break;
		case 11:
			spell = "SummonerSmite.png"; // 강타
			break;
		case 12:
			spell = "SummonerTeleport.png"; // 순간이동
			break;
		case 13:
			spell = "SummonerMana.png"; // 총명
			break;
		case 14:
			spell = "SummonerDot.png"; // 점화
			break;
		case 21:
			spell = "SummonerBarrier.png"; // 방어막
			break;
		case 30:
			spell = "SummonerPoroRecall.png"; // 포로귀환
			break;
		case 31:
			spell = "SummonerPoroThrow.png"; // 포로던지기
			break;
		case 32:
			spell = "SummonerSnowball.png"; // 눈덩이
			break;
		case 39:
			spell = "SummonerSnowURFSnowball_Mark.png"; // 눈덩이
			break;
		default:
			spell="no.png";
			break;
		}
		return spell;
	}
	
	JSONArray slots = new JSONArray();
	JSONObject rune = new JSONObject();
	
	public String mainRuneImg(JSONArray array, long rune) {//$.*.slots[:4].runes.[?(@.id==8112)].icon
		String data = JsonPath.parse(array).read("$.*.slots[:4].runes.[?(@.id=="+ (int)rune +")].icon").toString()
					.replace("\"", "").replace("[", "").replace("]", "").replace("\\", "");
		return data;
	}
	public String subRuneImg(JSONArray array, long rune) { 
		String data = JsonPath.parse(array).read("$.[?(@.id=="+ (int)rune +")].icon").toString().replace("\"", "").replace("[", "").replace("]", "").replace("\\", "");
		return data;
	}
}
