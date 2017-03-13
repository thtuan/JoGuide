package com.boot.accommodation.vp.profile;

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
 * Adapter cho collection fragment
 *
 * @author tuanlt
 * @since 6:28 PM 6/6/2016
 */
public class ProfileCollectionTourAdapter extends BaseRecyclerViewAdapter<TripItemDTO, ProfileCollectionTourAdapter
        .ViewHolder> {



    public ProfileCollectionTourAdapter(List<TripItemDTO> items) {
        super(items);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override

    public int itemLayout() {
        return R.layout.item_collection_profile;
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        holder.bindData(items.get(position), position);
    }


    class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.llGotoDetail)
        LinearLayout llGotoDetail;
        @Bind(R.id.ivPlace)
        CircularImageView ivPlace; // hinh place
        @Bind(R.id.tvPlace)
        TextView tvPlace; // ten place

        ViewHolder(View view) {
            super(view);
        }

        /**
         * bind du lieu
         *
         * @param data
         * @param position
         */
        public void bindData(TripItemDTO data, int position) {
            this.data = data;
            this.position = position;
            tvPlace.setText(data.getName());
            if (data.getPhoto()!= null&&data.getPhoto().size() >0){
                ImageUtil.loadImage(ivPlace, ImageUtil.getImageUrl(data.getPhoto().get(0)));
            }
        }


        @OnClick({R.id.llGotoDetail})
        public void OnClick(View v) {
            int action = 0;
            switch (v.getId()) {
                case R.id.llGotoDetail:
                    action = ProfileCollectionFragment.ACTION_VIEW_TOUR_DETAIL;
                    break;
            }
            if (listener != null && data != null) {
                listener.onEventControl(action, data, view, position);
            }
        }
    }

}
