package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.BaseDTO;
import com.boot.accommodation.dto.view.LocationDTO;

/**
 * Geometry
 *
 * @author tuanlt
 * @since 10:41 SA 10/09/2016
 */
public class GeometryItemDTO extends BaseDTO {
    private LocationDTO location;

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation( LocationDTO location ) {
        this.location = location;
    }
}
