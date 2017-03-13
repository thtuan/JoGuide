package com.boot.accommodation.dto.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Meeting
 *
 * @author thtuan
 * @since 8:23 PM 09-11-2016
 */
public class Meeting implements Serializable{
    private String name;
    private double lat;
    private double lng;
    private String address;
    private Date date;
    private TypeSchedule status; //0: birthday , 1: book , 2 work
    private List<User> users;
    public enum TypeSchedule {
        BIRTHDAY, WORK, STUDY;
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
