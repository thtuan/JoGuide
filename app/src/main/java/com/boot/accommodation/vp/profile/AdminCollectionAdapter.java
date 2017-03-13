package com.boot.accommodation.vp.profile;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.TripItemDTO;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.trip.TripFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class AdminCollectionAdapter extends BaseRecyclerViewAdapter<TripItemDTO, AdminCollectionAdapter.ViewHolder> {

    public AdminCollectionAdapter( List<TripItemDTO> items ) {
        super(items);
    }

    @Override
    public ViewHolder getViewHolder( View view ) {
        return new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_trip_tour;
    }

    @Override
    protected void bindViewHoder( ViewHolder holder, int position ) {
        holder.bindData(items.get(position), position);
    }

    /**
     * @param viewHolder
     * @param positionImage
     * @author thtuan
     */
    public void clickImage( ViewHolder viewHolder, int positionImage ) {
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

        ViewHolder( View view ) {
            super(view);
        }

        public void bindData( TripItemDTO data, int position ) {
            this.data = data;
            this.position = position;
            tvTourName.setText(data.getName());
            if (!StringUtil.isNullOrEmpty(data.getUserOwner().getAvatar())) {
                ImageUtil.loadImage(ivOwnerAvatar, ImageUtil.getImageUrl(data.getUserOwner().getAvatar()));
            } else {
                ivOwnerAvatar.setImageDrawable(Utils.getDrawable(R.drawable.img_default_error));
            }
            loadImage(data.getPhoto());
            tvTotalDay.setText(Utils.getQuantityResource(R.plurals.total_day, data.getNumDay()));
            tvTotalPlace.setText(Utils.getQuantityResource(R.plurals.total_place, data.getNumPlace()));
        }

        /**
         * Load hinh anh cho 1 tour
         *
         * @param lstPhoto
         */
        private void loadImage( List<String> lstPhoto ) {
            ImageCollectionPagerAdapter imageTripPagerAdapter = new ImageCollectionPagerAdapter(mContext, lstPhoto, AdminCollectionAdapter
                    .this, this);
            rcImage.setAdapter(imageTripPagerAdapter);
            rcImage.setHasFixedSize(true);
            imageTripPagerAdapter.setListener(listener);
        }

        /**
         * Su kien click hinh anh cua tour
         *
         * @param positionImage
         */
        private void clickImage( int positionImage ) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.IntentExtra.TRIP_ITEM, (TripItemDTO) data);
            bundle.putInt(Constants.IntentExtra.POSITION, positionImage);
            if (listener != null && data != null) {
                listener.onEventControl(TripFragment.ACTION_VIEW_TRIP_INFO, bundle, view, position);
            }
        }

        @OnClick({R.id.rlBackground, R.id.ivOwnerAvatar, R.id.tvFavourite,R.id.tvTotalDay,R.id.tvTotalPlace})
        public void OnClick( View v ) {
            int action = 0;
            switch (v.getId()) {
                case R.id.rlBackground:
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
