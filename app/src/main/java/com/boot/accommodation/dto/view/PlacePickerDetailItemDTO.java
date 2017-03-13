package com.boot.accommodation.dto.view;

import com.boot.accommodation.dto.response.GeometryItemDTO;
import com.boot.accommodation.dto.response.PlacePickerDetailItemPhotoDTO;

import java.util.List;

/**
 * Place picker detail response
 *
 * @author tuanlt
 * @since 10:13 SA 10/09/2016
 */
public class PlacePickerDetailItemDTO extends BaseDTO {
    private String formatted_address;// Format address
    private String formatted_phone_number; // Format number
    private String name; // Name
    private String website;// Website
    private GeometryItemDTO geometry; //Geometrys
    private List<PlacePickerDetailItemPhotoDTO> photos; //Photo
    private String place_id; //Place id
    private String rating; //Rating
    private List<String> types; // Type location
    private List<AddressGGDTO> address_components; //Address component
    private OpeningHoursDTO opening_hours; //Open hours

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address( String formatted_address ) {
        this.formatted_address = formatted_address;
    }

    public String getFormatted_phone_number() {
        return formatted_phone_number;
    }

    public void setFormatted_phone_number( String formatted_phone_number ) {
        this.formatted_phone_number = formatted_phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite( String website ) {
        this.website = website;
    }

    public GeometryItemDTO getGeometry() {
        return geometry;
    }

    public void setGeometry( GeometryItemDTO geometry ) {
        this.geometry = geometry;
    }

    public List<PlacePickerDetailItemPhotoDTO> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PlacePickerDetailItemPhotoDTO> photos) {
        this.photos = photos;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<AddressGGDTO> getAddress_components() {
        return address_components;
    }

    public void setAddress_components(List<AddressGGDTO> address_components) {
        this.address_components = address_components;
    }

    public OpeningHoursDTO getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(OpeningHoursDTO opening_hours) {
        this.opening_hours = opening_hours;
    }
}
