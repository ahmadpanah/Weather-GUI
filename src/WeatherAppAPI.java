// Retrive Weather Data From API
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class WeatherAppAPI {
	
	public static JSONObject getWeatherData(String locationName) {
		JSONArray locationData = getLocationData(locationName);
		return null;
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
