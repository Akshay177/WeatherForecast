package com.example.weather.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.constant.AppConstants;
import com.example.util.DateUtil;
import com.example.weather.model.Output;

@Service
public class WeatherService extends MappingJackson2HttpMessageConverter {

	private static WeatherService ourInstance = new WeatherService();

	@Value(value = "${api-key}")
	private String API_KEY;

	public static WeatherService getInstance() {
		return ourInstance;
	}

	private WeatherService() {
		setPrettyPrint(true);
	}

	public Output getWeatherForecast(String zip) throws Exception {

		Output tomorrowWeather = new Output();
		try {

			String websiteResponse = "http://api.weatherapi.com/v1/forecast.json?key=" + API_KEY + "&q=" + zip
					+ "&days=3&aqi=no&alerts=no";

			RestTemplate restTemplate = new RestTemplate();
			String result = restTemplate.getForObject(websiteResponse, String.class);

			JSONObject root = new JSONObject(result);
			JSONObject location = (JSONObject) root.get(AppConstants.JSON_KEY_FORECAST);
			JSONArray forecastDayArray = location.getJSONArray(AppConstants.JSON_KEY_FORECAST_DAY);

			String tomorrowDate = DateUtil.getNextDate();
			JSONObject dayObj = null;
			JSONArray hourArray = null;
			String forecastDate = null;

			for (int i = 0; i < forecastDayArray.length(); i++) {
				JSONObject arrayElement = forecastDayArray.getJSONObject(i);
				if (tomorrowDate.equals(arrayElement.get(AppConstants.JSON_KEY_DATE))) {
					forecastDate = (String) arrayElement.get(AppConstants.JSON_KEY_DATE);
					dayObj = (JSONObject) arrayElement.get(AppConstants.JSON_KEY_DAY);
					hourArray = arrayElement.getJSONArray(AppConstants.JSON_KEY_HOUR);
					break;
				}
			}

			setForecastData(tomorrowWeather, dayObj, hourArray, forecastDate);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error while getting weather API response");
		}

		return tomorrowWeather;
	}

	private void setForecastData(Output tomorrowWeather, JSONObject dayObj, JSONArray hourArray, String forecastDate) {
		if (dayObj != null) {
			tomorrowWeather.setForecastDate(forecastDate);
			tomorrowWeather.setMaxTemp(dayObj.getInt(AppConstants.JSON_KEY_MAX_TEMP));
			tomorrowWeather.setMinTemp(dayObj.getInt(AppConstants.JSON_KEY_MIN_TEMP));
		}

		if (hourArray != null && dayObj != null) {
			for (int i = 0; i < hourArray.length(); i++) {
				JSONObject arrayElement = hourArray.getJSONObject(i);
				String time = (String) arrayElement.get(AppConstants.JSON_KEY_TIME);
				if (arrayElement.get(AppConstants.JSON_KEY_TEMP).equals(dayObj.get(AppConstants.JSON_KEY_MIN_TEMP))) {
					tomorrowWeather.setCoolestHour(time.substring(time.indexOf(" ") + 1, time.length()));
				}
			}
		}
	}

}
