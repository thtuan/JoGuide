package com.boot.accommodation.dto.view;

import java.util.ArrayList;
import java.util.List;

/**
 * LocationFilterViewDTO
 *
 * @author tuanlt
 * @since 2:47 PM 9/20/16
 */
public class LocationFilterViewDTO {
    private List<CategoryItemDTO> category;//list category
    private List<AreaDTO> country; // List country
    private List<AreaDTO> province; // List province
    private List<AreaDTO> famousLocation; //Toponym

    public LocationFilterViewDTO(){
        category = new ArrayList<>();
        country = new ArrayList<>();
        province = new ArrayList<>();
        famousLocation = new ArrayList<>();
    }

    public List<CategoryItemDTO> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryItemDTO> category) {
        this.category = category;
    }

    public List<AreaDTO> getCountry() {
        return country;
    }

    public void setCountry(List<AreaDTO> country) {
        this.country = country;
    }

    public List<AreaDTO> getProvince() {
        return province;
    }

    public void setProvince(List<AreaDTO> province) {
        this.province = province;
    }

    public List<AreaDTO> getFamousLocation() {
        return famousLocation;
    }

    public void setFamousLocation(List<AreaDTO> famousLocation) {
        this.famousLocation = famousLocation;
    }
}
