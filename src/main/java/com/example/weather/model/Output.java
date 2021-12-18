package com.example.weather.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "forecast_date", "max_temp", "min_temp", "coolest_hour" })
public class Output {

	@JsonProperty("forecast_date")
	private String forecastDate;

	@JsonProperty("coolest_hour")
	private String coolestHour;

	@JsonProperty("max_temp")
	private int maxTemp;

	@JsonProperty("min_temp")
	private int minTemp;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("forecast_date")
	public String getForecastDate() {
		return forecastDate;
	}

	@JsonProperty("forecast_date")
	public void setForecastDate(String forecastDate) {
		this.forecastDate = forecastDate;
	}

	@JsonProperty("coolest_hour")
	public String getCoolestHour() {
		return coolestHour;
	}

	@JsonProperty("coolest_hour")
	public void setCoolestHour(String coolestHour) {
		this.coolestHour = coolestHour;
	}

	@JsonProperty("max_temp")
	public int getMaxTemp() {
		return maxTemp;
	}

	@JsonProperty("max_temp")
	public void setMaxTemp(int maxTemp) {
		this.maxTemp = maxTemp;
	}

	@JsonProperty("min_temp")
	public int getMinTemp() {
		return minTemp;
	}

	@JsonProperty("min_temp")
	public void setMinTemp(int minTemp) {
		this.minTemp = minTemp;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
