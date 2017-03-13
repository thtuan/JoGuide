package com.boot.accommodation.vp.location;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.dto.view.LocationTypeDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.util.ImageUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Mo ta class
 *
 * @author Vuong-bv
 * @since: 6/10/2016
 */
public class LocationFavouriteResultAdapter extends BaseRecyclerViewAdapter<PlaceItemDTO, LocationFavouriteResultAdapter.ViewHolder> {

    public LocationFavouriteResultAdapter(List<PlaceItemDTO> items) {
        super(items);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_favourite_search;
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

        @Bind(R.id.ivFavouriteResult)
        CircularImageView ivFavouriteResult;
        @Bind(R.id.tvNameFavouriteResult)
        TextView tvNameFavouriteResult;
        @Bind(R.id.llFavouriteResult)
        LinearLayout llFavouriteResult;

        ViewHolder(View view) {
            super(view);
        }

        public void bind(PlaceItemDTO item, int position) {
            this.data = item;
            this.position = position;
            if(LocationTypeDTO.GOOGLE.getValue() == item.getLocationType()) {
                ImageUtil.loadImage(ivFavouriteResult, ImageUtil.getGoogleImageUrl(item.getPhoto()));
            }else{
                ImageUtil.loadImage(ivFavouriteResult, ImageUtil.getImageUrl(item.getPhoto()));
            }
            tvNameFavouriteResult.setText(item.getName());
        }

        @OnClick({R.id.llFavouriteResult})
        public void OnClick(View v) {
            int action = 0;
            switch (v.getId()) {
                case R.id.llFavouriteResult:
                    action = LocationSearchFragment.ACTION_VIEW_PLACE_DETAIL;
                    break;
            }
            if (listener != null && data != null) {
                listener.onEventControl(action, data, view, position);
            }
        }
    }

}
