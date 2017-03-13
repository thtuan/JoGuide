package com.boot.accommodation.vp.location;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.dto.view.GPSInfoDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;

/**
 * Mo ta class
 *
 * @author tuanlt
 * @since: 11:20 AM 5/5/2016
 */
public interface LocationDetailPresenterMgr {

    /**
     * Init header
     *
     * @param itemId
     */
    void initDataHeader(PlaceItemDTO itemId);

    /**
     * Request favourite
     * @param placeId
     */
    void requestFavouritePlace(Long placeId);

    void requestCheckin(GPSInfoDTO locationInfo);

    void createCheckIn(PlaceItemDTO placeItemDTO);

    /**
     * Get more place
     *
     * @param adapter
     */
    void getMorePlaces(BaseRecyclerViewAdapter adapter);

    /**
     * Create places
     *
     * @param item
     */
    void getPlaceDetailGG(PlaceItemDTO item);
}
