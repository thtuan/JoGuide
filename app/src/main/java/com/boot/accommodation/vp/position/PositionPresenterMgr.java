package com.boot.accommodation.vp.position;

import android.location.Location;

/**
 * Mgr update vi tri
 *
 * @author tuanlt
 * @since 2:30 PM 6/7/2016
 */
public interface PositionPresenterMgr {
    /**
     * Cap nhat vi tri len server
     * @param lng
     * @param lat
     * @param loc
     */
    void updatePosition(double lng, double lat, Location loc);

}
