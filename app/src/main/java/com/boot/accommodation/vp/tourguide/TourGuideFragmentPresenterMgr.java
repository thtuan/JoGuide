package com.boot.accommodation.vp.tourguide;

import com.boot.accommodation.dto.view.TourGuideDTO;

import java.util.List;

/**
 * Created by thtuan on 3/18/17.
 */

interface TourGuideFragmentPresenterMgr {
    List<TourGuideDTO> getData();

    List<TourGuideDTO> getListFilter(int type);
}
