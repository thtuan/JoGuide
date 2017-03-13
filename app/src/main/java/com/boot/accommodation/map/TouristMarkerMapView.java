package com.boot.accommodation.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.boot.accommodation.R;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.TouristDTO;
import com.boot.accommodation.listener.OnEventControl;
import com.boot.accommodation.listener.OnEventMapControlListener;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;


public class TouristMarkerMapView extends MarkerView {
    Context mContext;// context
    OnEventControl lis;// lister su kien
    int action;// action
    private OnEventMapControlListener lis1;
    TouristDTO dto;
    CircularImageView ivPhoto; // hinh anh
    TextView tvName;

    public TouristMarkerMapView( Context context, TouristDTO dto ) {
        super(context);
        this.dto = dto;
        initMarker();
    }

    /**
     * Khoi tao marker
     */
    private void initMarker() {
        this.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.tourist_position_item, this);
        ivPhoto = (CircularImageView) v.findViewById(R.id.ivPhoto);
        tvName = (TextView) v.findViewById(R.id.tvName);
        setPhotoUrl(ImageUtil.getImageUrlThumb(dto.getImage(), Constants.MAX_IMAGE_MARKER_WIDTH, Constants.MAX_IMAGE_MARKER_HEIGHT));
        if (!StringUtil.isNullOrEmpty(dto.getTimeLocation())){
            String date = DateUtil.convertDateWithFormat(dto.getTimeLocation(), DateUtil.FORMAT_DATE_TIME_ZONE, DateUtil
                    .FORMAT_DATE_TIME);
            tvName.setText(dto.getName() + Constants.STR_BRACKET_LEFT + date + Constants.STR_BRACKET_RIGHT);
        } else {
            tvName.setText(dto.getName() + Constants.STR_BRACKET_LEFT + "" + Constants.STR_BRACKET_RIGHT);
        }
    }

    @Override
    public void setImageBitMap( Bitmap bitmap ) {
        ivPhoto.setImageBitmap(bitmap);
    }

}
