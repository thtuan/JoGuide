package com.boot.accommodation.vp.tour;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.ListTourDTO;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Adapter cho collection fragment
 *
 * @author tuanlt
 * @since 6:28 PM 6/6/2016
 */
public class ListTourAdapter extends BaseRecyclerViewAdapter<ListTourDTO, ListTourAdapter.ViewHolder> {

    public ListTourAdapter(List<ListTourDTO> items) {
        super(items);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override

    public int itemLayout() {
        return R.layout.item_list_tour;
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        holder.bindData(items.get(position), position);
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.ivPictureTour)
        ImageView ivPictureTour;
        @Bind(R.id.tvIdTour)
        TextView tvIdTour;
        @Bind(R.id.tvTourName)
        TextView tvTourName;
        @Bind(R.id.tvTourGuide)
        TextView tvTourGuide;
        @Bind(R.id.tvDateTime)
        TextView tvDateTime;
        @Bind(R.id.llListTour)
        LinearLayout llListTour;
        @Bind(R.id.rbRated)
        RatingBar rbRated;

        ViewHolder(View view) {
            super(view);
        }

        /**
         * bind du lieu
         *
         * @param data
         * @param position
         */
        public void bindData(ListTourDTO data, int position) {
            this.data = data;
            this.position = position;
            ImageUtil.loadImage(ivPictureTour, ImageUtil.getImageUrl(data.getImage()));
            tvTourName.setText(data.getTourName());
            if (data.getLeaderName()!= null){
                tvTourGuide.setText(StringUtil.getString(R.string.text_tour_guide)+ Constants.STR_COLON + Constants.STR_SPACE + data.getLeaderName());
            } else {
                tvTourGuide.setText(StringUtil.getString(R.string.text_tour_guide) +Constants.STR_COLON );
            }
            tvDateTime.setText(DateUtil.convertDateWithFormat(data.getDate(), DateUtil.FORMAT_DATE_TIME_ZONE, DateUtil.FORMAT_DATE));
            rbRated.setRating(data.getVote() > 0 ? 5 - (float) data.getVote() : 0);
        }

        @OnClick({R.id.llListTour})
        public void OnClick(View v) {
            int action = 0;
            switch (v.getId()) {
                case R.id.llListTour:
                    action = ListTourActivity.ACTION_GO_TO_DETAIL;
                    break;
            }
            if (listener != null && data != null) {
                listener.onEventControl(action, data, view, position);
            }
        }

    }

}
