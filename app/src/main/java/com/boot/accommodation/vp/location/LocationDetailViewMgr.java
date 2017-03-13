package com.boot.accommodation.vp.location;

import com.boot.accommodation.dto.view.PlaceItemDTO;

import java.util.List;

/**
 * Mo ta class
 *
 * @author tuanlt
 * @since: 11:20 AM 5/5/2016
 */
public interface LocationDetailViewMgr {
    void renderHeader(PlaceItemDTO item);
    void initViewPager(PlaceItemDTO item);
    void setFavourite(Boolean isFavourite);
    void checkinSucess(List<PlaceItemDTO> placeItemDTOs);
    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

    /**
     * Render popup place
     * @param lstPlaceDTO
     */
    void renderPopup(List<PlaceItemDTO> lstPlaceDTO);

}
