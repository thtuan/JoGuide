package com.boot.accommodation.dto.view;

/**
 * Mo ta class
 *
 * @author Vuong-bv
 * @since: 6/1/2016
 */
public class BottomRightCoordinate extends BaseDTO{
    private  String lat;
    private String lng;

    public BottomRightCoordinate( String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
