package com.example.demo.service.impl;

import java.net.URLEncoder;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.DetailInfo;
import com.example.demo.entity.MatchList;
import com.example.demo.entity.Summoner;
import com.example.demo.repository.DetailInfoRepository;
import com.example.demo.repository.MatchListRepository;
import com.example.demo.repository.SummonerRepository;
import com.example.demo.service.RiotService;
import com.example.demo.service.util.ParseService;
import com.example.demo.service.util.RiotResource;
import com.example.demo.service.util.UtilService;
import com.jayway.jsonpath.JsonPath;


@Service
public class RiotServiceImpl implements RiotService{
	
	String id = null;
	String puuid = null;
	
	@Value("${api.key}")
	private String key;
	
	@Autowired
	UtilService util;
	@Autowired
	SummonerRepository summonerRepository;
	@Autowired
	DetailInfoRepository detailInfoRepository;
	@Autowired
	MatchListRepository matchListRepository;
	@Autowired
	ParseService parseService;
	
	//전적검색 서비스
	public void search(ModelAndView mv, String name, int no, Pageable pageable) throws Exception {
		
		id = summoner(name);
		
		if(id == null) {
			mv.addObject("error", true);
		}else {
			Optional<Summoner> summonerData = summonerRepository.findById(id); 
			puuid = summonerData.get().getPuuid();
			mv.addObject("summonerData", summonerData.get());
			
			summonerDetailInfo(id);
			summonerGameData(summonerData.get().getMatchId(), no);
			mv.addObject("detailInfo", detailInfoRepository.findBySummonerId(id));
			mv.addObject("result", matchListRepository.findBySummonerIdOrderByGameStartTimestampDesc(id, pageable));
			
			//summonerGameData(puuid, 0)
		}
		
	}
	
	//소환사 정보 조회 및 저장 서비스
	public String summoner(String name) throws Exception{
		String apiResult = util.callApi(RiotResource.summonerV4Name + URLEncoder.encode(name, "UTF-8") + "?api_key=" + key, "GET", null);
		JSONObject summonerData = util.stringToJsonObject(apiResult);
        if(summonerData.size()>1) {
        	id = JsonPath.parse(summonerData).read("$['id']");
        	
        	// DB에 해당 id 없으면 저장
        	if(summonerRepository.findById(id).isEmpty()) {
        		puuid = JsonPath.parse(summonerData).read("$['puuid']").toString();
        		
        		
	        	Summoner summoner = Summoner.builder()
	        			.id(JsonPath.parse(summonerData).read("$['id']").toString())
	        			.name(JsonPath.parse(summonerData).read("$['name']").toString())
	        			.profileIconId(Long.parseLong(JsonPath.parse(summonerData).read("$['profileIconId']").toString()))
	        			.summonerLevel(Long.parseLong(JsonPath.parse(summonerData).read("$['summonerLevel']").toString()))
	        			.puuid(JsonPath.parse(summonerData).read("$['puuid']").toString())
	        			.matchId(matchId(puuid))
	        			.build();
	        			
	            summonerRepository.save(summoner);
        	}
        	return id;
        }else {
        	return null;
        }
	}
	
	//소환사 상세정보 조회 및 저장 서비스
	public void summonerDetailInfo(String id) throws Exception{
		
		if(detailInfoRepository.findBySummonerId(id).isEmpty()) {
			String apiResult = util.callApi(RiotResource.leagueV4Etnries + id + "?api_key=" + key, "GET", null);
			
			if(apiResult != null || apiResult.equals(null)) {
				JSONArray summonerDetailInfo = util.stringToJsonArray(apiResult);
				if(summonerDetailInfo.size() > 0) {
					for(int i=0; i<summonerDetailInfo.size(); i++) {
			        	DetailInfo detailInfo = DetailInfo.builder()
			        			.summoner(Summoner.builder().id((JsonPath.parse(summonerDetailInfo.get(i)).read("$['summonerId']").toString())).build())
			        			.name(JsonPath.parse(summonerDetailInfo.get(i)).read("$['summonerName']").toString())
			        			.tierRank(JsonPath.parse(summonerDetailInfo.get(i)).read("$['rank']").toString())
			        			.queueType(JsonPath.parse(summonerDetailInfo.get(i)).read("$['queueType']").toString())
			        			.tier(JsonPath.parse(summonerDetailInfo.get(i)).read("$['tier']").toString())
			        			.leaguePoints(Long.parseLong((JsonPath.parse(summonerDetailInfo.get(i)).read("$['leaguePoints']").toString())))
			        			.wins(Long.parseLong((JsonPath.parse(summonerDetailInfo.get(i)).read("$['wins']").toString())))
			        			.losses(Long.parseLong((JsonPath.parse(summonerDetailInfo.get(i)).read("$['losses']").toString())))
			        			
			        			.build();
			        			
			        	detailInfoRepository.save(detailInfo);
					}
				}
			}
		}
	}
	// matchId 검색 서비스
	public String matchId(String puuid) {
		String apiResultMatchId = util.callApi(RiotResource.matchV5MatchId + puuid + "/ids?count=50" + "&api_key=" + key, "GET", null);
		
		return apiResultMatchId;
	}
	
	//게임 상세데이터 조회 및 저장 서비스
	public void summonerGameData(String matchIdStr, int num) throws Exception{
		String[] matchId = matchIdStr.replace("[", "").replace("]", "").replace("\"", "").split(","); 
		String runeData = util.callApi("https://ddragon.leagueoflegends.com/cdn/12.15.1/data/en_US/runesReforged.json", "GET", null);
		JSONArray jsonRuneData = util.stringToJsonArray(runeData);
		int maxNum = (num+1)*10 -1;
		if(matchId.length< maxNum) { 
			maxNum = matchId.length-1;
		}
		if(matchListRepository.findByMatchId(matchId[maxNum]).isEmpty()) {
			int size = num*10+10;
			if(maxNum+1 != size) {
				size = matchId.length;
			}
			for(int i=num*10; i<size; i++) {
				String apiResultMatchData = util.callApi(RiotResource.matchV5MatchData + matchId[i] + "?api_key=" + key, "GET", null);
				
				JSONObject matchesData = util.stringToJsonObject(apiResultMatchData);
				JSONObject info = (JSONObject) matchesData.get("info");
				JSONArray participants = (JSONArray)info.get("participants");
				for(int j=0; j<10; j++) {
					JSONObject participant = (JSONObject)participants.get(j);
					if(participant.get("puuid").equals(puuid)) {
						JSONObject perks = (JSONObject)participant.get("perks");
						JSONArray styles = (JSONArray)perks.get("styles");
						JSONObject mainRunePart = (JSONObject)styles.get(0);
						JSONArray selections = (JSONArray)mainRunePart.get("selections");
						JSONObject perk = (JSONObject)selections.get(0);
						JSONObject subRunePart = (JSONObject)styles.get(1);
						MatchList matchList = MatchList.builder()
								.summoner(Summoner.builder().id(participant.get("summonerId").toString()).build())
								.gameStartTimestamp(parseService.timestampToMmdd(Long.parseLong(info.get("gameStartTimestamp").toString())))
								.matchId(matchId[i])
								.name(participant.get("summonerName").toString())
								.individualPosition(participant.get("individualPosition").toString())
								.win(participant.get("win").toString())
								.summoner1Id(parseService.spell(Long.parseLong(participant.get("summoner1Id").toString())))
								.summoner2Id(parseService.spell(Long.parseLong(participant.get("summoner2Id").toString())))
								.championName(participant.get("championName").toString())
								.kills(Long.parseLong(participant.get("kills").toString()))
								.deaths(Long.parseLong(participant.get("deaths").toString()))
								.assists(Long.parseLong(participant.get("assists").toString()))
								.goldEarned(Long.parseLong(participant.get("goldEarned").toString()))
								.visionWardsBoughtInGame(Long.parseLong(participant.get("visionWardsBoughtInGame").toString()))
								.totalDamageDealtToChampions(Long.parseLong(participant.get("totalDamageDealtToChampions").toString()))
								.wardsPlaced(Long.parseLong(participant.get("wardsPlaced").toString()))
								.wardsKilled(Long.parseLong(participant.get("wardsKilled").toString()))
								.totalMinionsKilled(Long.parseLong(participant.get("totalMinionsKilled").toString()))
								.neutralMinionsKilled(Long.parseLong(participant.get("neutralMinionsKilled").toString()))
								.champLevel(Long.parseLong(participant.get("champLevel").toString()))
								.item0(Long.parseLong(participant.get("item0").toString()))
								.item1(Long.parseLong(participant.get("item1").toString()))
								.item2(Long.parseLong(participant.get("item2").toString()))
								.item3(Long.parseLong(participant.get("item3").toString()))
								.item4(Long.parseLong(participant.get("item4").toString()))
								.item5(Long.parseLong(participant.get("item5").toString()))
								.item6(Long.parseLong(participant.get("item6").toString()))
								.queueId(parseService.queue(Long.parseLong(info.get("queueId").toString())))
								.gameDuration(parseService.duration(Long.parseLong(info.get("gameDuration").toString())))
								.mainRuneImg(parseService.mainRuneImg(jsonRuneData, Long.parseLong(perk.get("perk").toString())))
								.subRuneImg(parseService.subRuneImg(jsonRuneData, Long.parseLong(subRunePart.get("style").toString())))
								.build();
						matchListRepository.save(matchList);
						break;
					}
				} 
			}
		}
	}
	
	public void test() {
		
	}
}
