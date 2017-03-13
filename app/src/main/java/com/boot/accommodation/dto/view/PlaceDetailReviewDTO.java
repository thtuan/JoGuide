package com.boot.accommodation.dto.view;

import com.boot.accommodation.base.BaseResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xdung on 5/21/2016.
 */
public class PlaceDetailReviewDTO extends BaseResponse {
    public List<ReviewPlaceDetailDTO> listReview = new ArrayList<ReviewPlaceDetailDTO>();
}
