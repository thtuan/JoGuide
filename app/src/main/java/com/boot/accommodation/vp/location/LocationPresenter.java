package com.boot.accommodation.vp.location;

/**
 * Presenter cua man hinh dia diem
 *
 * @author tuanlt
 * @since: 5:32 PM 5/4/2016
 */
public class LocationPresenter implements LocationPresenterMgr {

    LocationViewMgr mapViewMgr;

    public LocationPresenter(LocationViewMgr mapViewMgr ) {
        this.mapViewMgr = mapViewMgr;
    }


}
