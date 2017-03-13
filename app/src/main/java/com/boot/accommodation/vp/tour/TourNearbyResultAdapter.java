package com.boot.accommodation.vp.tour;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.dto.view.TripItemDTO;
import com.boot.accommodation.util.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Mo ta class
 *
 * @author Vuong-bv
 * @since: 6/10/2016
 */
public class TourNearbyResultAdapter extends BaseRecyclerViewAdapter<TripItemDTO, TourNearbyResultAdapter.ViewHolder> {

    public TourNearbyResultAdapter(List<TripItemDTO> items) {
        super(items);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_nearby_search;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        holder.bind(items.get(position), position);
    }

    static class ViewHolder extends BaseRecyclerViewHolder {

        @Bind(R.id.ivIconStar)
        ImageView ivIconStar;
        @Bind(R.id.tvNearbyResult)
        TextView tvNearbyResult;
        @Bind(R.id.rlNearbyResult)
        RelativeLayout rlNearbyResult;
        ViewHolder(View view) {
            super(view);
        }

        public void bind(TripItemDTO item, int position) {
            this.data = item;
            this.position = position;
            tvNearbyResult.setText(item.getName());
            if (item.getIsFavourite()){
                ivIconStar.setImageDrawable(Utils.getDrawable(R.drawable.ic_favourite_fill_green));
            }
        }

        @OnClick({R.id.rlNearbyResult})
        public void OnClick(View v) {
            int action = 0;
            switch (v.getId()) {
                case R.id.rlNearbyResult:
                    action = TourSearchFragment.ACTION_VIEW_TOUR_DETAIL;
                    break;
            }
            if (listener != null && data != null) {
                listener.onEventControl(action, data, view, position);
            }
        }
    }

}
