package com.boot.accommodation.dto.view;

@SuppressWarnings("serial")
public class UserPositionLogDTO extends BaseDTO {
    // id user
    private long id;
    private long userId;
    private double lat; // lat
    private double lng; // lng
    //createDate
    private String createAt;
    // do chinh xac khi lay vi tri
    private float accuracy = -1;
    // battery
    private double battery;
    // Wifi, GPRS, 3Gv.v.
    private String networkType;
    // Toc do: 0 - yeu, 1 - manh, 2 - rat manh
    private int networkSpeed = 0;

    public UserPositionLogDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt( String createAt ) {
        this.createAt = createAt;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy( float accuracy ) {
        this.accuracy = accuracy;
    }

    public double getBattery() {
        return battery;
    }

    public void setBattery( double battery ) {
        this.battery = battery;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType( String networkType ) {
        this.networkType = networkType;
    }

    public int getNetworkSpeed() {
        return networkSpeed;
    }

    public void setNetworkSpeed( int networkSpeed ) {
        this.networkSpeed = networkSpeed;
    }

    public double getLat() {
        return lat;
    }

    public void setLat( double lat ) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng( double lng ) {
        this.lng = lng;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId( long userId ) {
        this.userId = userId;
    }
}
