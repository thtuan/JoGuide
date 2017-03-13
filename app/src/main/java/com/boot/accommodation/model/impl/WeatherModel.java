package com.boot.accommodation.model.impl;

import com.boot.accommodation.base.BaseModel;
import com.boot.accommodation.dto.response.WeatherResponseDTO;
import com.boot.accommodation.listener.ModelCallBack;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * WeatherModel
 *
 * @author tuanlt
 * @since 10:13 PM 11/2/16
 */
public class WeatherModel extends BaseModel {

    private interface Interface {
        @GET
        Call<WeatherResponseDTO> getWeather(
                @Url String url );
    }


    public void getWeather(double lat, double lng, long time, ModelCallBack callBack) {
        StringBuilder builder = new StringBuilder();
        builder.append("https://api.darksky.net/forecast/");
        builder.append("c611f6e62ef381acbcfaed98a4aeca1f/").append(lat).append(",").append(lng).append(",").append(time);
        builder.append("?").append("units=si");
        Interface service = RETROFIT.create(Interface.class);
        call = service.getWeather(builder.toString());
        requestAPI(callBack);
    }

}
