// Retrive Weather Data From API
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class WeatherAppAPI {
	
	public static JSONObject getWeatherData(String locationName) {
		JSONArray locationData = getLocationData(locationName);
		
		JSONObject location = (JSONObject) locationData.get(0);
		double latitude = (double) location.get("latitude");
		double longitude = (double) location.get("longitude");
		
		String urlString = "https://api.open-meteo.com/v1/forecast?"
				+ "latitude=" + latitude + "longitude=" + longitude + 			
				"&hourly=temperature_2m,relative_humidity_2m,weathercode,wind_speed_10m&timezone=auto";
		
		try {
			HttpURLConnection conn = fetchApiResponse(urlString);
			if (conn.getResponseCode() != 200) {
				System.out.println("Error! Could not connect to API!");
				return null;
			}
			StringBuilder resultJson = new StringBuilder();
			Scanner scanner = new Scanner(conn.getInputStream());
			while(scanner.hasNext()) {
				resultJson.append(scanner.nextLine());
			}
			scanner.close();
			conn.disconnect();
			
			JSONParser parser = new JSONParser();
			JSONObject resultJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));
			
			JSONObject hourly = (JSONObject) resultJsonObj.get("hourly");
			
			JSONArray time = (JSONArray) hourly.get("time");
			
			int index = findIndexOfCurrentTime(time);
			
			JSONArray temperatureData = (JSONArray) hourly.get("temperature_2m");	
			double temperature = (double) temperatureData.get(index);
			
			JSONArray weathercode = (JSONArray) hourly.get("weathercode");
			
			String weetherCondtion = convertWeatherCode ((long) weathercode.get(index));
			
			JSONArray relativeHudimidity = (JSONArray) hourly.get("relative_humidity_2m");
			long humidity = (long) relativeHudimidity.get(index);
			
			JSONArray WindSpeedData = (JSONArray) hourly.get("wind_speed_10m");
			long windspeed = (long) WindSpeedData.get(index);
			
			
			 
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String convertWeatherCode (long weathercode) {
		String weatherCondition = "";
		if (weathercode == 0L) {
			weatherCondition = "Clear";
		} else if (weathercode > 0L && weathercode <= 3L) {
			weatherCondition = "Cloudy";
		} else if ((weathercode > 51L && weathercode <= 67L) || (weathercode > 80L && weathercode <= 99L)) {
			weatherCondition = "Rain";
		} else if (weathercode > 71L && weathercode <= 77L) {
			weatherCondition = "Snow";
		}
			
		return weatherCondition;
	}

	private static int findIndexOfCurrentTime(JSONArray timeList) {
		
		String currentTime = getCurrentTime();
		
		for (int i = 0; i < timeList.size(); i++) {
			String time = (String) timeList.get(i);
			if (time.equalsIgnoreCase(currentTime)) {
				return i;
			}
		}
		
		return 0;

	}

	public static String getCurrentTime() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00");
		
		String formattedDateTime = currentDateTime.format(formatter);
		
		return formattedDateTime;
	}

	public static JSONArray getLocationData(String locationName) {

		locationName = locationName.replaceAll(" ", "+");
		
		String urlString = "https://geocoding-api.open-meteo.com/v1/search?name="
				+ locationName + "&count=10&language=en&format=json";
		
		try {
			HttpURLConnection conn = fetchApiResponse(urlString);
			
			if (conn.getResponseCode() != 200) {
				System.out.println("Error! Could not Connect To API");
				
				return null;
			} else {
				StringBuilder resultJson = new StringBuilder();
				Scanner scanner = new Scanner(conn.getInputStream());
				
				while (scanner.hasNext()) {
					resultJson.append(scanner.nextLine());
				}
				
				scanner.close();
				conn.disconnect();
				
				JSONParser parser = new JSONParser();
				JSONObject resultJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));
				JSONArray locationData = (JSONArray) resultJsonObj.get("results");
				
				return locationData;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private static HttpURLConnection fetchApiResponse(String urlString) {
		
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			
			conn.connect();
			return conn;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	
	}
	
	

}
