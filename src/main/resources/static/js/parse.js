$(function(){
	var summonerName = $("#name").val();
	var btnNo = $("#nextMatch").val();
	var section = $(".gameDetail");
	
	$("#nextMatch").click(function(){
		
		$.ajax({
			type:"GET",
			url:"search",
			data:{
				name : summonerName,
				no : btnNo,	
				size : 10,
				page : btnNo
			},
			success: function(data){
				section.append(section);
				$(this).val(btnNo);
				btnoNo ++;
			}
		});
	});
	
	
	/*rune json page 비동기 호출*/
/*	var runeJson = $.ajax({
		type: "GET",
		url: "https://ddragon.leagueoflegends.com/cdn/12.15.1/data/en_US/runesReforged.json",
		success: function(data){
			runeJson = data;
		}
	});*/
	
})



/*첫 번째 룬 이미지 ex)정복자, 집중공격*/
function mainRune(main, mainFirst) {
	var slots = null;
	let mainImg = null;
	for (var i = 0; i < runeData.length; i++) {
		if (runeData[i]['id'] == main) {
			slots = runeData[i]['slots'];
		}
	}
	for (var j = 0; j < slots[0]['runes'].length; j++) {
		if (slots[0]['runes'][j]['id'] == mainFirst) {
			mainImg = slots[0]['runes'][j]['icon'];
			return mainImg;
		}
	}
}
/*두 번째 룬 이미지 ex)결의, 마법*/
function subRun(sub) {
	for (var i = 0; i < runeData.length; i++) {
		if (runeData[i]['id'] == sub) {
			return (runeData[i]['icon']);
		}
	}
}

/*스펠 png 값 매핑*/
function spell(key) {
	let spell = null;
	switch (key) {
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
			break;
	}
	return spell;
}

/*큐타입 매핑*/
function queueType(queueId) {
	var queue = null;
	switch (queueId) {
		case 400:
		case 430:
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
		case 800:
		case 810:
		case 820:
		case 830:
		case 840:
		case 850:
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
		case 2000:
		case 2010:
		case 2020:
			queue = "튜토리얼";
			break;
		default:
			queue = "이벤트";
			break;
	}
	return queue;
}

/*시간 매핑*/
function timestamp(timestamp){
	var date_not_formatted = new Date(timestamp);
	var formatted_string = "";
	if (date_not_formatted.getMonth() < 9) {
		formatted_string += "0";
	}
	formatted_string += (date_not_formatted.getMonth() + 1);
	formatted_string += "-";
	if(date_not_formatted.getDate() < 10) {
		formatted_string += "0";
	}
	formatted_string += date_not_formatted.getDate();
	return formatted_string;
}

/*kda 계산*/
function kdaaver(k, d, a){
	var result=null;
	if(d==0){
		result = "Perfect";
	}else {
		var aver = (k + a) / d;
		result = Math.round(aver*100)/100.0;
	}
	return result;
}