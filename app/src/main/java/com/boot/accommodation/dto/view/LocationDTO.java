package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

import com.boot.accommodation.constant.Constants;

/**
 * Mo ta class
 *
 * @author Vuong-bv
 * @since: 5/18/2016
 */
public class LocationDTO extends BaseDTO implements Parcelable {
    private Double lng = Constants.LAT_LNG_NULL;
    private Double lat = Constants.LAT_LNG_NULL;

    public LocationDTO(Double lat, Double lng) {
        this.lng = lng;
        this.lat = lat;
    }

    public LocationDTO() {
    }

    protected LocationDTO(Parcel in) {
        lng = in.readDouble();
        lat = in.readDouble();
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lng);
        dest.writeDouble(lat);
    }

    public static final Creator<LocationDTO> CREATOR = new Creator<LocationDTO>() {
        @Override
        public LocationDTO createFromParcel(Parcel in) {
            return new LocationDTO(in);
        }

        @Override
        public LocationDTO[] newArray(int size) {
            return new LocationDTO[size];
        }
    };
}
