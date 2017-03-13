package com.boot.accommodation.dto.view;

import com.boot.accommodation.base.BaseResponse;

import java.util.List;

/**
 * Created by xdung on 5/20/2016.
 */
public class PlaceDetailPhotoDTO extends BaseResponse {

    private PagingDTO paging; // paging
    private List<PhotoDTO> photo; // photo

    public int getIndex( PhotoDTO item ) {
        int index = -1;
        for (int i = 0; i < getPhoto().size(); i++) {
            if (getPhoto().get(i) == item) {
                index = i;
                break;
            }
        }
        return index;
    }

    public List<PhotoDTO> getPhoto() {
        return photo;
    }

    public void setPhoto( List<PhotoDTO> photo ) {
        this.photo = photo;
    }

    public PagingDTO getPaging() {
        return paging;
    }

    public void setPaging( PagingDTO paging ) {
        this.paging = paging;
    }
}
