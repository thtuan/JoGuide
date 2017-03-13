package com.boot.accommodation.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.android.gms.maps.model.LatLng;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

public class PlaceInfoMarkerMapView extends MarkerView implements View.OnClickListener {
    Context context;
    private PlaceItemDTO dto;
    RoundedImageView ivPhoto;
    TextView tvRatingPoint;
    RatingBar rbRatingPoint;
    TextView tvName;
    TextView tvPhoneNum;
    TextView tvDistance;

    public PlaceInfoMarkerMapView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {
        this.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.place_info_marker_map, this);
        ivPhoto = (RoundedImageView) v.findViewById(R.id.ivPhoto);
        tvRatingPoint = (TextView) v.findViewById(R.id.tvRatingPoint);
        rbRatingPoint = (RatingBar) v.findViewById(R.id.rbRatingPoint);
        tvName = (TextView) v.findViewById(R.id.tvName);
        tvPhoneNum = (TextView) v.findViewById(R.id.tvPhoneNum);
        tvPhoneNum.setOnClickListener(this);
        tvDistance = (TextView) v.findViewById(R.id.tvDistance);
    }

    /**
     * Khoi tao marker
     */
    public void updateView(PlaceItemDTO dto) {
        this.dto = dto;
//        ImageUtil.loadImage(ivPhoto, dto.getPhoto());
        tvRatingPoint.setText(String.valueOf(dto.getRatePoint()));
        rbRatingPoint.setRating((float) dto.getRatePoint());
        tvName.setText(dto.getName());
        if (StringUtil.isNullOrEmpty(dto.getPhoneNumber())){
            tvPhoneNum.setVisibility(GONE);
        } else {
            tvPhoneNum.setVisibility(VISIBLE);
            tvPhoneNum.setText(dto.getPhoneNumber());
        }
        tvPhoneNum.setTag(dto.getPhoneNumber());
        tvPhoneNum.setOnClickListener(this);

        //handle this loaction to user login location
        LatLng from = new LatLng(JoCoApplication.getInstance().getProfile()
                .getMyGPSInfo().getLatitude(), JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLongtitude());
        LatLng to = new LatLng(dto.getLat(), dto.getLng());
        double distance = Utils.getDistanceBetween(from, to);
        if (distance >= 1000) {
            double tempDistance = (double) Utils.getDistanceBetween(from, to) / 1000;
            tvDistance.setText(StringUtil.parseNumberStr(String.valueOf(tempDistance)).doubleValue() +
                    StringUtil.getString(R.string.text_kilomet));
        } else if (distance >= 0) {
            tvDistance.setText(StringUtil.parseNumberStr(String.valueOf(distance)).doubleValue()  + StringUtil
                    .getString(R.string.text_met));
        } else {
            tvDistance.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tvPhoneNum) {
            String phoneNum = (String) v.getTag();
            if(TextUtils.isEmpty(phoneNum)) {
                Utils.callPhone(context, phoneNum);
            }
        }
    }

    @Override
    public void setImageBitMap( Bitmap bitmap ) {
        ivPhoto.setImageBitmap(bitmap);
    }

    public PlaceItemDTO getDto() {
        return dto;
    }

    public void setDto( PlaceItemDTO dto ) {
        this.dto = dto;
    }
}
