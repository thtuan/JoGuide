package com.boot.accommodation.vp.location;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.TraceExceptionUtils;
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
public class LocationNearbyResultAdapter extends BaseRecyclerViewAdapter<PlaceItemDTO, LocationNearbyResultAdapter.ViewHolder> {

    public LocationNearbyResultAdapter(List<PlaceItemDTO> items) {
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
        @Bind(R.id.tvDistance)
        TextView tvDistance;

        ViewHolder(View view) {
            super(view);
        }

        public void bind(PlaceItemDTO item, int position) {
            this.data = item;
            this.position = position;
            double distance = Utils.getDistance(item.getLat(), item.getLng());
            String distanceStr = "";
            try {
                if (distance >= 1000) {
                    double tempDistance = distance / 1000;
                    distanceStr = StringUtil.parseNumberWithDigit(StringUtil.parseNumberStr(String.valueOf
                            (tempDistance)),1) + Constants
                            .STR_SPACE + StringUtil.getString(R.string.text_kilomet);
                } else if (distance >= 0) {
                    distanceStr = StringUtil.parseNumberWithDigit(StringUtil.parseNumberStr(String.valueOf(distance)),1)
                            + StringUtil.getString(R.string.text_met);
                } else {
                    distanceStr = "";
                }
            } catch (Exception e) {
                Log.e(Constants.LogName.EXCEPTION, TraceExceptionUtils.getReportFromThrowable(e));
            }
            tvNearbyResult.setText(item.getName());
            tvDistance.setText(Constants.STR_BRACKET_LEFT + distanceStr + Constants.STR_BRACKET_RIGHT);
            if (item.getIsFavourite()){
                ivIconStar.setImageDrawable(Utils.getDrawable(R.drawable.ic_favourite_fill_green));
            }else {
                ivIconStar.setImageDrawable(Utils.getDrawable(R.drawable.ic_favourite_grey));
            }
        }

        @OnClick({R.id.rlNearbyResult})
        public void OnClick(View v) {
            int action = 0;
            switch (v.getId()) {
                case R.id.rlNearbyResult:
                    action = LocationSearchFragment.ACTION_VIEW_PLACE_DETAIL;
            }
            if (listener != null && data != null) {
                listener.onEventControl(action, data, view, position);
            }
        }
    }

}
