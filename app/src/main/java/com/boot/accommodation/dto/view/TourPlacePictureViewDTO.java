package com.boot.accommodation.dto.view;

import java.util.List;

/**
 * Tour place picture view
 *
 * @author tuanlt
 * @since 10:57 CH 14/08/2016
 */
public class TourPlacePictureViewDTO {
    private PagingDTO paging;
    private List<PhotoDTO> photo;

    public PagingDTO getPaging() {
        return paging;
    }

    public void setPaging( PagingDTO paging ) {
        this.paging = paging;
    }

    public List<PhotoDTO> getPhotos() {
        return photo;
    }

    public void setPhotos( List<PhotoDTO> photos ) {
        this.photo = photos;
    }
}
