package com.boot.accommodation.vp.location;

import com.boot.accommodation.dto.view.AppVersionDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;

import java.util.ArrayList;

/**
 * View mgr cho man hinh ds dia diem
 *
 * @author tuanlt
 * @since 10:40 AM 5/25/2016
 */
public interface LocationListViewMgr {
    /**
     * Render layout
     * @param lstPlaces
     */
    void renderLayout(ArrayList<PlaceItemDTO> lstPlaces);

    /**
     * Go to place detail
     * @param placeItemDTO
     */
    void gotoPlaceDetail( PlaceItemDTO placeItemDTO );

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

    /**
     * Check app version
     */
    void checkAppVersion(AppVersionDTO appVersionDTO);

}
