package com.boot.accommodation.vp.tourguide;

import com.boot.accommodation.dto.view.TourGuideEventDTO;

import java.util.List;

/**
 * Created by thtuan on 3/20/17.
 */

interface TourGuideDetailPresenterMgr {
    List<TourGuideEventDTO> getListEvents();
}
