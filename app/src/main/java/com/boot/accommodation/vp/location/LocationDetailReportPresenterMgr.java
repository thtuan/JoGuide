package com.boot.accommodation.vp.location;

import com.boot.accommodation.dto.view.PlaceReportDTO;

/**
 * Place detail report
 *
 * @author tuanlt
 * @since: 11:20 AM 5/5/2016
 */
public interface LocationDetailReportPresenterMgr {

    /**
     * Request report location
     * @param placeId
     * @param placeReportDTO
     */
    void requestReportLocation(long placeId, PlaceReportDTO placeReportDTO);
}
