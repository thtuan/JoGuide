package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.PhotoDTO;

import java.util.List;

/**
 * DTO place picture
 *
 * @author tuanlt
 * @since 10:35 CH 14/08/2016
 */
public interface TourPlacePictureViewMgr {
    /**
     * Render list photo
     *
     * @param photos
     */
    void renderLayout( List<PhotoDTO> photos );

    /**
     * Show error
     *
     * @param errorCode
     * @param error
     */
    void showMessageErr( int errorCode, String error );
}
