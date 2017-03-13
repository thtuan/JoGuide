package com.boot.accommodation.dto.view;

/**
 * Mo ta class
 *
 * @author tuanlt
 * @since 10:03 AM 6/8/2016
 */
public class LocationTestDTO extends BaseDTO {
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress( String address ) {
        this.address = address;
    }
}
