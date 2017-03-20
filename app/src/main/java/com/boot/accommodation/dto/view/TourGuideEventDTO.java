package com.boot.accommodation.dto.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * TourGuideEvent
 *
 * @author thtuan
 * @since 8:23 PM 09-11-2016
 */
public class TourGuideEventDTO implements Serializable{
    private int id;
    private Date date;
    private String name; //name of event
    private double lat; // latitude
    private double lng;// longitude
    private String address; // address of event
    private TypeSchedule status; //0: birthday , 1: book , 2 work
    private List<User> users;
    public enum TypeSchedule {
        BIRTHDAY, WORK, STUDY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public TypeSchedule getStatus() {
        return status;
    }

    public void setStatus(TypeSchedule status) {
        this.status = status;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
