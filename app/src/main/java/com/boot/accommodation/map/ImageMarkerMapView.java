package com.boot.accommodation.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.LocationTypeDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.listener.OnEventControl;
import com.boot.accommodation.listener.OnEventMapControlListener;
import com.boot.accommodation.util.ImageUtil;


public class ImageMarkerMapView extends MarkerView {
    Context mContext;
    OnEventControl lis;
    int action;
    private OnEventMapControlListener lis1;
    private PlaceItemDTO dto;
    private ImageView ivPhoto;
    private TextView tvSTT; // stt
    private Double lat;
    private Double lng;
//    private FrameLayout frSTT; // stt
    public PlaceItemDTO getDto() {
        return dto;
    }

    public void setDto( PlaceItemDTO dto ) {
        this.dto = dto;
    }


    public ImageMarkerMapView(Context context, PlaceItemDTO dto) {
        super(context);
        this.mContext = context;
        this.dto = dto;
        initMarker();
    }

    public ImageMarkerMapView(Context context, Double lat, Double lng) {
        super(context);
        this.mContext = context;
        this.lat = lat;
        this.lng = lng;
        initDragMaker();
    }

    private void initDragMaker() {

    }

    /**
     * Khoi tao marker
     */
    private void initMarker() {
        this.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.place_map_item, this);
        ivPhoto = (ImageView) v.findViewById(R.id.ivPhoto);
        tvSTT = (TextView) v.findViewById(R.id.tvSTT);
        setPhotoUrl(LocationTypeDTO.GOOGLE.getValue() == dto.getLocationType() ? ImageUtil.getGoogleImageUrlThumb(dto
                .getPhoto(), Constants.MAX_IMAGE_MARKER_WIDTH, Constants.MAX_IMAGE_MARKER_HEIGHT) : ImageUtil.getImageUrlThumb(dto.getPhoto
                (), Constants.MAX_IMAGE_MARKER_WIDTH, Constants.MAX_IMAGE_MARKER_HEIGHT));
    }

    @Override
    public void setImageBitMap( Bitmap bitmap){
        ivPhoto.setImageBitmap(bitmap);
    }

    /**
     * Set STT
     * @param stt
     */
    public void setSTT(int stt){
        tvSTT.setVisibility(View.VISIBLE);
        tvSTT.setText(stt+"");
    }
}
