package com.boot.accommodation.vp.location;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.dto.view.LocationFilterItemDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.dto.view.TimeToGoFilterDTO;

/**
 * Mgr Presenter cho man hinh ds dia diem
 *
 * @author tuanlt
 * @since 10:39 AM 5/25/2016
 */
public interface LocationListPresenterMgr {
    /**
     * Get places
     */
    void getPlaces(Double lat, Double lng, int typePlace, LocationFilterItemDTO
            locationFilterItemDTO, TimeToGoFilterDTO timeToGoFilterDTO, String userId);

    /**
     * Lay them cac dia diem khac
     */
    void getMorePlaces(Double lat, Double lng, BaseRecyclerViewAdapter adapter, int typePlace, LocationFilterItemDTO
            locationFilterItemDTO);
    /**
     * Di toi mot dia diem
     * @param position
     */
    void gotoPlaceDetail(int position );

    /**
     * Request favourite place
     * @param item
     */
    void requestFavouritePlace(PlaceItemDTO item);

    /**
     * Get appver
     */
    void getAppVersion();


}
