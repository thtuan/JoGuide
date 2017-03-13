package com.boot.accommodation.vp.location;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.boot.accommodation.base.BaseFragmentMapView;
import com.boot.accommodation.dto.view.LocationTypeDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.map.GoogleFragmentMapView;
import com.boot.accommodation.map.PlaceInfoMarkerMapView;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.TraceExceptionUtils;

/**
 * Popup thong tin place
 *
 * @author tuanlt
 * @since 3:00 PM 6/10/2016
 */
public class LocationDetailInfoWindow implements GoogleMap.InfoWindowAdapter {
    private PlaceInfoMarkerMapView myContentsView;// thong tin place
    private Context context; // context
    BaseFragmentMapView helper; //helper
    public LocationDetailInfoWindow(BaseFragmentMapView helper, Context context ){
        super();
        this.context = context;
        myContentsView = new PlaceInfoMarkerMapView(context);
        this.helper = helper;
    }

    @Override
    public View getInfoWindow( final Marker marker) {
        GoogleFragmentMapView.ListenerFromView a = ((GoogleFragmentMapView)helper).hash.get(marker.getId());
        PlaceItemDTO dto = (PlaceItemDTO) a.getData();
        myContentsView.updateView(dto);
        Glide.with(context).load(LocationTypeDTO.GOOGLE.getValue() == dto.getLocationType() ? ImageUtil
                .getGoogleImageUrlThumb(dto.getPhoto()) : ImageUtil
                .getImageUrlThumb(dto.getPhoto()))
                .asBitmap().thumbnail(.1f).fitCenter().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                try {
                    myContentsView.setImageBitMap(bitmap);
                    getInfoContents(marker);
                } catch (Exception e) {
                    Log.e("", TraceExceptionUtils.getReportFromThrowable(e));
                }
            }
        });
        return myContentsView;
    }

    @Override
    public View getInfoContents(final Marker marker) {
        if(marker!= null && marker.isInfoWindowShown()){
            marker.hideInfoWindow();
            marker.showInfoWindow();
        }
        return null;
    }
}
