package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.PlaceDetailPhotoDTO;

/**
 * DTO photo view place
 *
 * @author tuanlt
 * @since 10:54 AM 7/9/2016
 */
public class PlaceDetailPhotoViewDTO {
    private PlaceDetailPhotoDTO photoOwner; // photo from user attend tour
    private PlaceDetailPhotoDTO photoJoco;  // photo from Joco

    public PlaceDetailPhotoDTO getPhotoOwner() {
        return photoOwner;
    }

    public void setPhotoOwner( PlaceDetailPhotoDTO photoOwner ) {
        this.photoOwner = photoOwner;
    }

    public PlaceDetailPhotoDTO getPhotoJoco() {
        return photoJoco;
    }

    public void setPhotoJoco( PlaceDetailPhotoDTO photoJoco ) {
        this.photoJoco = photoJoco;
    }
}
