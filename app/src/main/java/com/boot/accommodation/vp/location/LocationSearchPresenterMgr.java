package com.boot.accommodation.vp.location;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.dto.view.LocationFilterItemDTO;

/**
 * TourInfomationPresenterMgr cho man hinh tour info expand
 *
 * @author vuong-bv
 * @since: 9:25 AM 5/31/2016
 */
public interface LocationSearchPresenterMgr {
    /**
     * get result search location
     *
     * @param keyWord
     * @param lat
     * @param lng
     */
    void getSearchLocation(String keyWord, Double lat, Double lng, LocationFilterItemDTO locationFilterItemDTO);

    /**
     * Get more data favourite search location
     *
     * @param adapter
     * @param keyWord
     * @param lat
     * @param lng
     */
    void getMoreFavouriteLocation(BaseRecyclerViewAdapter adapter, String keyWord, Double lat, Double lng, LocationFilterItemDTO locationFilterItemDTO);

    /**
     * Get more data nearby search location
     *
     * @param adapter
     * @param keyWord
     * @param lat
     * @param lng
     */
    void getMoreNearByLocation(BaseRecyclerViewAdapter adapter, String keyWord, Double lat, Double lng, LocationFilterItemDTO locationFilterItemDTO);



}
