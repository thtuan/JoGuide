package com.boot.accommodation.dto.response;

/**
 * Place photo response from server
 *
 * @author tuanlt
 * @since 10:59 AM 7/9/2016
 */
public class PlaceDetailPhotoResponseDTO extends BaseResponseDTO {

    private PlaceDetailPhotoViewDTO data; // data from server

    public PlaceDetailPhotoViewDTO getData() {
        return data;
    }

    public void setData( PlaceDetailPhotoViewDTO data ) {
        this.data = data;
    }
}
