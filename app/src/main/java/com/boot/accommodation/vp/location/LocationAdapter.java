package com.boot.accommodation.vp.location;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.LocationTypeDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.TraceExceptionUtils;
import com.boot.accommodation.util.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Admin on 12/10/2015.
 */
public class LocationAdapter extends BaseRecyclerViewAdapter<PlaceItemDTO, LocationAdapter.ViewHolder> {

    public LocationAdapter(List<PlaceItemDTO> items) {
        super(items);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_location;
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

        @Bind(R.id.ivPhoto)
        ImageView ivPhoto;
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.tvDistance)
        TextView tvDistance;
//        @Bind(R.id.tvShare)
//        TextView tvShare;
        @Bind(R.id.tvFavourite)
        TextView tvFavourite;
        @Bind(R.id.rlBackground)
        LinearLayout rlBackground;
        @Bind(R.id.prLoading)
        ProgressBar progressLoad;
        @Bind(R.id.tvAddress)
        TextView tvAddress;
        @Bind(R.id.tvDescription)
        TextView tvDescription;
        @Bind(R.id.tvPrice)
        TextView tvPrice;

        ViewHolder(View view) {
            super(view);
        }

        public void bind(PlaceItemDTO item, int position) {
            this.data = item;
            this.position = position;
            tvName.setText(item.getName());
            double distance = Utils.getDistance(item.getLat(), item.getLng());
            try {
                if (distance >= 1000) {
                    double tempDistance = distance / 1000;
                    tvDistance.setText(StringUtil.parseNumberWithDigit(StringUtil.parseNumberStr(String.valueOf
                            (tempDistance)), 1) +
                            Constants.STR_SPACE + StringUtil.getString(R.string.text_kilomet));
                } else if (distance >= 0) {
                    tvDistance.setText(StringUtil.parseNumberWithDigit(StringUtil.parseNumberStr(String.valueOf
                            (distance)), 1) + Constants
                            .STR_SPACE + StringUtil
                            .getString(R.string.text_met));
                } else {
                    tvDistance.setText("");
                }
            } catch (Exception e) {
                Log.e(Constants.LogName.EXCEPTION, TraceExceptionUtils.getReportFromThrowable(e));
            }

            if (item.getIsFavourite()) {
                tvFavourite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favourite_orange, 0, 0, 0);
            } else {
                tvFavourite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favourite_grey, 0, 0, 0);
            }
            if (item.getNumLike() == 0) {
                tvFavourite.setText( 0 + Constants.STR_SPACE + StringUtil.getString(R.string.text_like));
            } else {
                tvFavourite.setText(Utils.getQuantityResource(R.plurals.total_like, item.getNumLike()));
            }
            if (LocationTypeDTO.GOOGLE.getValue() == item.getLocationType()) {
                ImageUtil.loadImage(ivPhoto, ImageUtil.getGoogleImageUrl(item.getPhoto()), progressLoad);
            } else {
                ImageUtil.loadImage(ivPhoto, ImageUtil.getImageUrl(item.getPhoto()), progressLoad);
            }
            if (StringUtil.isNullOrEmpty(item.getAddress())) {
                tvAddress.setVisibility(View.GONE);
                tvAddress.setText("");
            } else {
                tvAddress.setVisibility(View.VISIBLE);
                tvAddress.setText(item.getAddress());
            }
            if (StringUtil.isNullOrEmpty(item.getOutStanding())) {
                tvDescription.setVisibility(View.GONE);
                tvDescription.setText("");
            } else {
                String outStanding = Html.fromHtml(item.getOutStanding()).toString();
                tvDescription.setVisibility(View.VISIBLE);
                tvDescription.setText(outStanding);
            }

            if (item.getPriceFrom() == 0 ) {
                tvPrice.setVisibility(View.GONE);
            } else {
                tvPrice.setVisibility(View.VISIBLE);
                if (StringUtil.isNullOrEmpty(item.getPriceUnit())) {
                    tvPrice.setText(StringUtil.parseMoneyByLocale(String.valueOf(item.getPriceFrom())) + Constants.STR_SPACE);
                } else {
                    tvPrice.setText(StringUtil.parseMoneyByLocale(String.valueOf(item.getPriceFrom())) + Constants.STR_SPACE +
                            StringUtil.getStringResourceByName(item.getPriceUnit().trim()));

                }
            }
        }

        @OnClick({R.id.rlBackground, R.id.tvFavourite})
        public void OnClick(View v) {
            int action = 0;
            switch (v.getId()) {
                case R.id.rlBackground:
                    action = LocationListFragment.ACTION_VIEW_DETAIL;
                    break;
                //                case R.id.rl_check_in:
                //                    action = LocationListFragment.ACTION_CHECK_IN_PLACE;
                //                    break;
                case R.id.tvFavourite:
                    action = LocationListFragment.ACTION_FARVORITE_PLACE;
                    break;
                //                case R.id.rl_like:
                //                    action = LocationListFragment.ACTION_LIKE_PLACE;
                //                    break;
//                case R.id.tvShare:
//                    action = LocationListFragment.ACTION_SHARE_PLACE;
//                    break;
            }
            if (listener != null && data != null) {
                listener.onEventControl(action, data, view, position);
            }
        }
    }
}
