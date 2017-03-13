package com.boot.accommodation.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.LinearLayout;

/**
 * Mo ta class
 *
 * @author tuanlt
 * @since 4:08 PM 6/10/2016
 */
public class MarkerView extends LinearLayout {
    private String photoUrl; // duong dan
    public MarkerView( Context context ) {
        super(context);
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl( String photoUrl ) {
        this.photoUrl = photoUrl;
    }

    /**
     * Set image bitmap
     * @param bitmap
     */
    public void setImageBitMap(Bitmap bitmap){

    }
}
