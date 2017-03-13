package com.boot.accommodation.vp.location;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.util.ImageUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * CheckinAdapter
 *
 * @author thtuan
 * @since 16:03 18/07/2016
 */

public class CheckinAdapter extends BaseRecyclerViewAdapter<PlaceItemDTO, CheckinAdapter.ViewHolder> {




    public CheckinAdapter(List<PlaceItemDTO> lstReView) {
        super(lstReView);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    public int itemLayout() {
        return R.layout.item_checkin;
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        holder.bind(items.get(position), position);
    }


    public class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.ivImage)
        ImageView ivImage;
        @Bind(R.id.tvTitle)
        TextView tvTitle;
        @Bind(R.id.tvContent)
        TextView tvContent;
        @Bind(R.id.llCheckIn)
        LinearLayout llCheckIn;
        @Bind(R.id.prLoading)
        ProgressBar progressLoad;

        public ViewHolder(View view) {
            super(view);
        }

        public void bind(PlaceItemDTO item, int position) {
            this.data = item;
            this.position = position;
            ImageUtil.loadImage(ivImage, ImageUtil.getImageUrl(item.getPhoto()), progressLoad);
            tvTitle.setText(item.getName());
            tvContent.setText(item.getAddress());
        }

        @OnClick(R.id.llCheckIn)
        public void onClick() {
            if (listener != null) {
                listener.onEventControl(Constants.ActionEvent.ACTION_CHECKIN, data, null, position);
            }
        }
    }
}
