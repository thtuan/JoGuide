package com.boot.accommodation.dto.response;

/**
 * Created by Tuan on 9/12/16.
 */
public class PlacePickerDetailItemPhotoDTO {
    private int height; // Height photo
    private int width; // Width photo
    private String photo_reference; // Photo reference

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }
}
