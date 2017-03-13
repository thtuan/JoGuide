package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.BaseDTO;
import com.boot.accommodation.dto.view.WeatherItemDTO;

import java.util.List;

/**
 * Hourly
 *
 * @author tuanlt
 * @since 10:34 PM 11/2/16
 */
public class WeatherListDTO extends BaseDTO {
    private List<WeatherItemDTO> data;

    public List<WeatherItemDTO> getData() {
        return data;
    }

    public void setData(List<WeatherItemDTO> data) {
        this.data = data;
    }
}
