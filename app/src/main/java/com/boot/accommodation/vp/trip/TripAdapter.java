package com.boot.accommodation.vp.trip;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.TripItemDTO;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class TripAdapter extends BaseRecyclerViewAdapter<TripItemDTO, TripAdapter.ViewHolder> {

    public TripAdapter(List<TripItemDTO> items) {
        super(items);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_trip_tour;
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        holder.bindData(items.get(position), position);
    }

    /**
     * @param viewHolder
     * @param positionImage
     * @author tuanlt
     */
    public void clickImage(ViewHolder viewHolder, int positionImage) {
        viewHolder.clickImage(positionImage);
    }


    class ViewHolder extends BaseRecyclerViewHolder {

        @Bind(R.id.rcImage)
        RecyclerView rcImage; // image
        @Bind(R.id.tvTourName)
        TextView tvTourName; // ten tour
        @Bind(R.id.ivOwnerAvatar)
        CircularImageView ivOwnerAvatar; // avatar nguoi tao tour
        @Bind(R.id.tvTotalDay)
        TextView tvTotalDay; // tong ngay
        @Bind(R.id.tvTotalPlace)
        TextView tvTotalPlace; // tong so noi di
        @Bind(R.id.rlBackground)
        LinearLayout rlBackground; // back ground
        @Bind(R.id.tvFavourite)
        TextView tvFavourite;
        @Bind(R.id.tvDateStart)
        TextView tvDateStart;
        @Bind(R.id.tvMoney)
        TextView tvMoney;
        ImageTripPagerAdapter imageTripPagerAdapter; //Image adapter

        ViewHolder(View view) {
            super(view);
        }

        public void bindData(TripItemDTO data, int position) {
            this.data = data;
            this.position = position;
            tvTourName.setText(data.getName());
            if (!StringUtil.isNullOrEmpty(data.getUserOwner().getAvatar())) {
                ImageUtil.loadImage(ivOwnerAvatar, ImageUtil.getImageUrlOwnerAvatar(data.getUserOwner().getAvatar()));
            } else {
                ivOwnerAvatar.setImageDrawable(Utils.getDrawable(R.drawable.img_default_error));
            }
//            tvName.setText(data.getName());
//            if (!TextUtils.isEmpty(data.photo)) {
//                ImageUtil.loadImage(ivPhoto,data.photo);
//            } else {
//                ivPhoto.setImageDrawable(Utils.getDrawable(R.drawable.img_default_error));
//            }
            if (data.getIsFavourite()) {
                tvFavourite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favourite_fill_green, 0, 0, 0);
            } else {
                tvFavourite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favourite_grey, 0, 0, 0);
            }
            loadImage(data.getPhoto());
            tvTotalDay.setText(Utils.getQuantityResource(R.plurals.total_day, data.getNumDay()));
            tvTotalPlace.setText(Utils.getQuantityResource(R.plurals.total_place, data.getNumPlace()));
//          tvPrice.setText(StringUtil.parseAmountMoney(data.getPrice(), data.getPriceUnit()));
            if (data.getPrice() == 0) {
                tvMoney.setVisibility(View.GONE);
            } else {
                tvMoney.setVisibility(View.VISIBLE);
//                tvMoney.setText(StringUtil.parseMoneyWithTokenZero(""+data.getPrice()) + Constants.STR_SPACE + data
//                    .getPriceUnit() );
                if (StringUtil.isNullOrEmpty(data.getPriceUnit())) {
                    tvMoney.setText(StringUtil.parseMoneyByLocale(String.valueOf(data.getPrice())) + Constants.STR_SPACE);
                } else {
                    tvMoney.setText(StringUtil.parseMoneyByLocale(String.valueOf(data.getPrice())) + Constants.STR_SPACE +
                        StringUtil.getStringResourceByName(data.getPriceUnit().trim()));

                }
            }
            if (StringUtil.isNullOrEmpty(data.getStartDate())) {
                tvDateStart.setVisibility(View.GONE);
            } else {
                tvDateStart.setVisibility(View.VISIBLE);
                tvDateStart.setText(DateUtil
                        .convertDateWithFormat(data.getStartDate(), DateUtil.FORMAT_DATE_WITHOUT_TIME_ZONE, DateUtil.FORMAT_DATE));
            }
            /*ivTransparent.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:

                            // Disallow ScrollView to intercept touch events.
                            ncvView.requestDisallowInterceptTouchEvent(true);
                            // Disable touch on transparent view
                            return false;

                        case MotionEvent.ACTION_UP:
                            // Allow ScrollView to intercept touch events.
                            ncvView.requestDisallowInterceptTouchEvent(false);
                            return true;

                        case MotionEvent.ACTION_MOVE:
                            ncvView.requestDisallowInterceptTouchEvent(true);
                            return false;

                        default:
                            return true;
                    }
                }
            });*/
        }

        /**
         * Load hinh anh cho 1 tour
         *
         * @param lstPhoto
         */
        private void loadImage(List<String> lstPhoto) {
            if (lstPhoto == null) {
                lstPhoto = new ArrayList<>();
                lstPhoto.add("error");
            }
            if (imageTripPagerAdapter == null) {
                imageTripPagerAdapter = new ImageTripPagerAdapter(mContext, lstPhoto, TripAdapter
                        .this, this);
                imageTripPagerAdapter.setListener(listener);
                rcImage.setAdapter(imageTripPagerAdapter);
                StaggeredGridLayoutManager lm =
                        new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL);
                rcImage.setLayoutManager(lm);
                rcImage.setHasFixedSize(true);
            } else {
                imageTripPagerAdapter.setData(lstPhoto);
            }
            imageTripPagerAdapter.notifyDataSetChanged();
//            rcImage.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));

        }

        /**
         * Su kien click hinh anh cua tour
         *
         * @param positionImage
         */
        private void clickImage(int positionImage) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.IntentExtra.TRIP_ITEM, (TripItemDTO) data);
            bundle.putInt(Constants.IntentExtra.POSITION, positionImage);
            if (listener != null && data != null) {
                listener.onEventControl(TripFragment.ACTION_VIEW_TRIP_INFO, bundle, view, position);
            }
        }

        @OnClick({R.id.rlBackground, R.id.ivOwnerAvatar, R.id.tvFavourite, R.id.tvTotalDay, R.id.tvTotalPlace, R.id
                .rcImage, R.id.llTag})
        public void OnClick(View v) {
            int action = 0;
            switch (v.getId()) {
                case R.id.rcImage:
                    action = TripFragment.ACTION_VIEW_TRIP_INFO;
                    break;
                case R.id.llTag:
                    action = TripFragment.ACTION_VIEW_TRIP_INFO;
                    break;
                case R.id.ivOwnerAvatar:
                    action = TripFragment.ACTION_VIEW_OWNER_INFO;
                    break;
                case R.id.tvFavourite:
                    action = TripFragment.ACTION_FARVORITE_TRIP;
                    break;
//                case R.id.rl_like:
//                    action = TripFragment.ACTION_LIKE_TRIP;
//                    break;
//                case R.id.rl_share:
//                    action = TripFragment.ACTION_SHARE_TRIP;
//                    break;
            }
            if (listener != null && data != null) {
                listener.onEventControl(action, data, view, position);
            }
        }


    }
}
