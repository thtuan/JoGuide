package com.boot.accommodation.dto.response;

/**
 * Place picker response
 *
 * @author tuanlt
 * @since 6:04 CH 09/09/2016
 */
public class WeatherResponseDTO extends BaseResponseDTO {
    private WeatherListDTO hourly;
    private WeatherListDTO daily;

    public WeatherListDTO getHourly() {
        return hourly;
    }

    public void setHourly(WeatherListDTO hourly) {
        this.hourly = hourly;
    }

    public WeatherListDTO getDaily() {
        return daily;
    }

    public void setDaily(WeatherListDTO daily) {
        this.daily = daily;
    }
}

