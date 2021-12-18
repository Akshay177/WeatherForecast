package com.example.weather.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Exception.ZipFormatException;
import com.example.constant.AppConstants;
import com.example.weather.model.Output;
import com.example.weather.service.WeatherService;

@RestController
public class WeatherController {

	private final WeatherService weatherService;

	@Value(value = "${data.zipformat.exception.message}")
	private String zipFormatExceptionMessage;

	@Value(value = "${data.exception.message}")
	private String exceptionMmessage;

	@Autowired
	public WeatherController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@ExceptionHandler(value = ZipFormatException.class)
	public ResponseEntity<String> zipFormatException(ZipFormatException zipFormatException) {
		return new ResponseEntity<>(zipFormatExceptionMessage, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> responseFailsException(Exception exception) {
		return new ResponseEntity<>(exceptionMmessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}

	@RequestMapping(value = "forecast/zip/{zip}")
	public Output getWeatherForecast(@PathVariable String zip) throws Exception {
		try {
			Pattern pattern = Pattern.compile(AppConstants.US_ZIP_REG_EXP);
			Matcher matcher = pattern.matcher(zip);

			if (!matcher.matches()) {
				throw new ZipFormatException();
			}
			return this.weatherService.getWeatherForecast(zip);
		} catch (ZipFormatException ze) {
			throw new ZipFormatException(zipFormatExceptionMessage);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}

	}
}
