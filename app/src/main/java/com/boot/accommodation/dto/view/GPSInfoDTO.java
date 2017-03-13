package com.boot.accommodation.dto.view;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.constant.Constants;

import java.io.Serializable;


public class GPSInfoDTO implements Serializable{
	private static final long serialVersionUID = 4728275641384376062L;
	// lat, long
	private Double longitude = Constants.LAT_LNG_NULL;
	private Double latitude = Constants.LAT_LNG_NULL;
	// toa do lat, lng khong bi reset 10 phut
	private double lastLongitude = Constants.LAT_LNG_NULL;
	private double lastLatitude = Constants.LAT_LNG_NULL;

	public GPSInfoDTO() {
	}
	public GPSInfoDTO(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	// 10.784892,106.68302
	public Double getLongtitude() {
		if (JoCoApplication.IS_VERSION_FOR_EMULATOR){
			return 106.68302;
		}
		return longitude;
	}

	public Double getLatitude() {
		if (JoCoApplication.IS_VERSION_FOR_EMULATOR){
			return 10.784892;
		}
		return latitude;
	}

	public void setLongtitude(Double longtitude) {
		this.longitude = longtitude;
			lastLongitude = longtitude;
	}

	public void setLattitude(Double lattitude) {
		this.latitude = lattitude;
		lastLatitude = lattitude;
	}

	public Double getLastLongtitude() {
		return lastLongitude;
	}

	public Double getLastLatitude() {
		return lastLatitude;
	}
}
