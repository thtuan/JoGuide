package com.boot.accommodation.vp.promotion;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.MediaDTO;
import com.boot.accommodation.dto.view.PromotionDealDTO;
import com.boot.accommodation.listener.OnEventControl;
import com.boot.accommodation.vp.trip.ImageTripPagerAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;

public class PromotionDealAdapter extends BaseRecyclerViewAdapter<PromotionDealDTO, PromotionDealAdapter.ViewHolder> {

    public PromotionDealAdapter(List<PromotionDealDTO> items) {
        super(items);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_promotion_deal;
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        holder.bindData(items.get(position), position);
    }

    class ViewHolder extends BaseRecyclerViewHolder implements OnEventControl {

        @Bind(R.id.rcImage)
        RecyclerView rcImage;
        @Bind(R.id.tvPromotionDealName)
        TextView tvPromotionDealName;
        @Bind(R.id.tvDate)
        TextView tvDate;

        ImageTripPagerAdapter imageTripPagerAdapter; //Image adapter
        ViewHolder(View view) {
            super(view);
        }

        /**
         * Bind data
         * @param data
         * @param position
         */
        public void bindData(PromotionDealDTO data, int position) {
            this.data = data;
            this.position = position;
            tvPromotionDealName.setText(data.getName());
            tvDate.setText(data.getRunningTime());
            loadImage(data.getMediaItems());
        }

        /**
         * Load hinh anh cho 1 tour
         *
         * @param mediaDTOs
         */
        private void loadImage(List<MediaDTO> mediaDTOs) {
            List<String> lstPhoto = new ArrayList<>();
            for (MediaDTO item: mediaDTOs) {
                lstPhoto.add(item.getUri());
            }
            if (lstPhoto.size() == 0) {
                lstPhoto = new ArrayList<>();
                lstPhoto.add("error");
            }
            if (imageTripPagerAdapter == null) {
                imageTripPagerAdapter = new ImageTripPagerAdapter(mContext, lstPhoto);
                imageTripPagerAdapter.setListener(this);
                imageTripPagerAdapter.setFitXY(true);
                rcImage.setAdapter(imageTripPagerAdapter);
                StaggeredGridLayoutManager lm =
                        new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL);
                rcImage.setLayoutManager(lm);
                rcImage.setHasFixedSize(true);
            } else {
                imageTripPagerAdapter.setData(lstPhoto);
            }
            imageTripPagerAdapter.notifyDataSetChanged();
        }

        @OnClick({R.id.rlBackground, R.id.rcImage})
        public void OnClick(View v) {
            if (listener != null && data != null) {
                listener.onEventControl(Constants.ActionEvent.ACTION_GO_TO_PROMOTION_DEAL_INFO, data, view, position);
            }
        }


        @Override
        public void onEventControl(int action, Object item, View View, int position) {
            listener.onEventControl(Constants.ActionEvent.ACTION_GO_TO_PROMOTION_DEAL_INFO, data, view, position);
        }

        @Override
        public void onLoadMore(int position) {

        }
    }
}
