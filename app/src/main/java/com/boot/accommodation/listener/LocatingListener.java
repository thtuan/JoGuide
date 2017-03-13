package com.boot.accommodation.listener;

import android.location.LocationListener;

import com.boot.accommodation.vp.position.Locating;


/**
 * Listener lien quan dinh vi
 *
 * @author tuanlt
 * @since 10:13 AM 5/14/2016
 */
public interface LocatingListener extends LocationListener {
    public void onTimeout (Locating locating);
}
