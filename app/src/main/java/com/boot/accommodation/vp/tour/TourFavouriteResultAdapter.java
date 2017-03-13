package com.boot.accommodation.vp.tour;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.dto.view.TripItemDTO;
import com.boot.accommodation.util.ImageUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * adapter of tour favourite
 *
 * @author Vuong-bv
 * @since: 6/10/2016
 */
public class TourFavouriteResultAdapter extends BaseRecyclerViewAdapter<TripItemDTO, TourFavouriteResultAdapter.ViewHolder> {

    public TourFavouriteResultAdapter(List<TripItemDTO> items) {
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

        public void bind(TripItemDTO item, int position) {
            this.data = item;
            this.position = position;
            if (item.getPhoto()!= null){
                ImageUtil.loadImage(ivFavouriteResult,ImageUtil.getImageUrl(item.getPhoto().get(0)));
            }
            tvNameFavouriteResult.setText(item.getName());
        }

        @OnClick({R.id.llFavouriteResult})
        public void OnClick(View v) {
            int action = 0;
            switch (v.getId()) {
                case R.id.llFavouriteResult:
                    action = TourSearchFragment.ACTION_VIEW_TOUR_DETAIL;
                    break;
            }
            if (listener != null && data != null) {
                listener.onEventControl(action, data, view, position);
            }
        }
    }


}
