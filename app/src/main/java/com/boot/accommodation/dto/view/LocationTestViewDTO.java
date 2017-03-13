package com.boot.accommodation.dto.view;

import java.util.List;

/**
 * Mo ta class
 *
 * @author tuanlt
 * @since 10:05 AM 6/8/2016
 */
public class LocationTestViewDTO {
    private List<LocationTestDTO> location; // location

    public List<LocationTestDTO> getLocation() {
        return location;
    }

    public void setLocation( List<LocationTestDTO> location ) {
        this.location = location;
    }
}
