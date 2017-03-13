package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.BaseDTO;

import java.util.List;

/**
 * Place picker item
 *
 * @author tuanlt
 * @since 12:16 SA 10/09/2016
 */
public class PlacePickerItemDTO extends BaseDTO {
    private String place_id; // Place id
    private GeometryItemDTO geometry; //Geometry
    private String name; //Name
    private List<PlacePickerDetailItemPhotoDTO> photos; //List photo
    private List<String> types; // Type location
    private String vicinity; //Vicinity
    private String formatted_address; //Format address

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public GeometryItemDTO getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryItemDTO geometry) {
        this.geometry = geometry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<PlacePickerDetailItemPhotoDTO> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PlacePickerDetailItemPhotoDTO> photos) {
        this.photos = photos;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }
}
