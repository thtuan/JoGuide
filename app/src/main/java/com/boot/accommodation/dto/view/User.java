package com.boot.accommodation.dto.view;

import java.io.Serializable;

/**
 * User
 *
 * @author thtuan
 * @since 1:31 PM 06-11-2016
 */
public class User implements Serializable {
    String id;
    String name;
    double lat;
    double lng;
    Status status;
    public enum Status {
        COMING, WAITING, CHECKIN
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
