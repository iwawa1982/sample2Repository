package com.example.demo.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class HolidayService {

	// 内閣府の祝日CSVを取得
	private static final String HOLIDAY_URL =
			"https://www8.cao.go.jp/chosei/shukujitsu/syukujitsu.csv";

	// 祝日のCSVデータを取得
	public String getHoliday() {

		// HTTP通信をするためのクライアントを作成
		HttpClient client = HttpClient.newHttpClient();

		// 内閣府へ送るリクエストを作成
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(HOLIDAY_URL))
				.GET()
				.build();

		try {

			// リクエストを送信して結果を受け取る
			HttpResponse<String> response =
					client.send(
							request,
							HttpResponse.BodyHandlers.ofString(
									Charset.forName("Shift_JIS")
							)
					);

			// CSVの中身を返す
			return response.body();

		} catch (IOException | InterruptedException e) {

			e.printStackTrace();
			return null;
		}
	}
	
	//祝日csvを日付→祝日名のmapに変換
	public Map<LocalDate, String> getHolidayMap(){
		
		//祝日を入れるMapを作成
		Map<LocalDate,String> holidays =new HashMap<>();
		
		//内閣府からCSVを取得
		String csv =getHoliday();
		
		//CSVを取得できなかった場合
		if(csv == null) {
			return holidays;
			
		}
		
		//csvを１行ずつにわける
		
		String[]lines=csv.split("\\R");
		
		//CSVの日付形式
		DateTimeFormatter formatter=
				DateTimeFormatter.ofPattern("yyyy/M/d");
		
		//１行目は見出しなので、２行目から処理する
		for (int i=1; i<lines.length; i++) {
			
			//日付　祝日名に分ける
			
			String[]data =lines[i].split(",",2);
			
			//データが2つある場合
			if(data.length ==2) {
				
				//日付をLocalDateに変換
				LocalDate date =
						LocalDate.parse(data[0].trim(),formatter);
				
				//祝日名
				String name =data[1].trim();
				
				//Mapに追加
				holidays.put(date,name);
				
			}
		} return holidays;
	}
	
}