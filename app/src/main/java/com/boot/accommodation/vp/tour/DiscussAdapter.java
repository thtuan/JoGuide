package com.boot.accommodation.vp.tour;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.common.control.ExpandableTextView;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.ReviewItemDTO;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * DiscussAdapter
 *
 * @author Vuong-bv
 * @since: 5/13/2016
 */
public class DiscussAdapter extends BaseRecyclerViewAdapter<ReviewItemDTO, DiscussAdapter.ViewHolder> {


    public DiscussAdapter(List<ReviewItemDTO> items) {
        super(items);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_discuss;
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        ((ViewHolder) holder).bindData(items.get(position), position);
    }

    class ViewHolder extends BaseRecyclerViewHolder {


        @Bind(R.id.ivAvatarTouristRated)
        CircularImageView ivAvatarTouristRated;
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.tvDateTimeRatedTour)
        TextView tvDateTimeRatedTour;
        @Bind(R.id.tvContentRatedTour)
        ExpandableTextView tvContentRatedTour;
        @Bind(R.id.ivPhoto)
        ImageView ivPhoto;
        @Bind(R.id.tvNumberLikedRatedTour)
        TextView tvNumberLikedRatedTour;
        @Bind(R.id.ivLikeRatedTour)
        ImageView ivLikeRatedTour;
        @Bind(R.id.llLikeRated)
        LinearLayout llLikeRated;
        @Bind(R.id.llDiscus)
        LinearLayout llDiscus;

        ViewHolder(View view) {
            super(view);
//            tvContentRatedTour.makeExpandable(1);
        }

        public void bindData(final ReviewItemDTO data, final int position) {
            this.data = data;
            this.position = position;
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null && data != null) {
                        listener.onEventControl(Constants.ActionEvent.ACTION_DELETE_REVIEW, data, v, position);
                    }
                    return true;
                }
            });
            //set data for rated
            tvName.setText(data.getUserName());
            ImageUtil.loadImage(ivAvatarTouristRated, ImageUtil.getImageUrl(data.getUserAvatar()));
            if (!StringUtil.isNullOrEmpty(data.getImage())) {
                ivPhoto.setVisibility(View.VISIBLE);
                ImageUtil.loadImage(ivPhoto, ImageUtil.getImageUrl(data.getImage()));
            } else {
                ivPhoto.setVisibility(View.GONE);
            }
            tvDateTimeRatedTour.setText(DateUtil.convertDateWithFormat(data.getDateTime(), DateUtil.FORMAT_DATE_TIME_ZONE, DateUtil.FORMAT_DATE_TIME));
            tvContentRatedTour.setText(data.getContent());
            tvNumberLikedRatedTour.setText(String.valueOf(data.getNumLike()));
            if (data.isLiked() == false) {
                ivLikeRatedTour.setImageResource(R.drawable.ic_like_grey);
            } else {
                ivLikeRatedTour.setImageResource(R.drawable.ic_like_green);
            }
            tvContentRatedTour.makeExpandable(Constants.MAX_LINE_COMMENT);
        }

        @OnClick({R.id.llLikeRated, R.id.ivPhoto, R.id.ivAvatarTouristRated})
        public void OnClick(View v) {
            int action = 0;
            switch (v.getId()) {
                case R.id.llLikeRated:
                    action = DiscussFragment.ACTION_LIKE_THIS_COMENT;
                    break;
                case R.id.ivPhoto:
                    action = DiscussFragment.ACTION_VIEW_PHOTO;
                    break;
                case R.id.ivAvatarTouristRated:
                    action = DiscussFragment.ACTION_GO_TO_PROFILE;
                    break;
            }
            if (listener != null && data != null) {
                listener.onEventControl(action, data, view, position);
            }
        }
    }
}
