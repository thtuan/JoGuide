package com.boot.accommodation.vp.tour;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
 * Mo ta class
 *
 * @author Vuong-bv
 * @since: 5/13/2016
 */
public class LeaderRateAdapter extends BaseRecyclerViewAdapter<ReviewItemDTO, LeaderRateAdapter.ViewHolder> {
    boolean like = false;
    String idRated;


    public LeaderRateAdapter(List<ReviewItemDTO> items) {
        super(items);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_learder_rated;
    }

    @Override
    protected void bindViewHoder( ViewHolder holder, int position ) {
        holder.bindData(items.get(position), position);
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        @Bind(R.id.ivPhoto)
        ImageView ivPhoto;
        @Bind(R.id.ivAvatarTouristRated)
        CircularImageView ivAvatarTouristRated;
        @Bind(R.id.rbLeaderRated)
        RatingBar rbLeaderRated;
        @Bind(R.id.tvDateTimeRated)
        TextView tvDateTimeRated;
        @Bind(R.id.tvContent)
        ExpandableTextView tvContent;
        @Bind(R.id.ivLikeThisRated)
        ImageView ivLikeThisRated;
        @Bind(R.id.tvNumberLiked)
        TextView tvNumberLiked;
        @Bind(R.id.llLikeLeaderRated)
        LinearLayout llLikeLeaderRated;
        ViewHolder(View view) {
            super(view);
        }

        public void bindData(final ReviewItemDTO data,final int position) {
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
            if(!StringUtil.isNullOrEmpty(data.getImage())) {
                ivPhoto.setVisibility(View.VISIBLE);
                ImageUtil.loadImage(ivPhoto, ImageUtil.getImageUrl(data.getImage()));
            } else {
                ivPhoto.setVisibility(View.GONE);
            }
            ImageUtil.loadImage(ivAvatarTouristRated, ImageUtil.getImageUrl(data.getUserAvatar()));
            rbLeaderRated.setRating((float) data.getRating());
            if (data.getDateTime() != null){
                tvDateTimeRated.setText(DateUtil.convertDateWithFormat(data.getDateTime(),DateUtil.FORMAT_DATE_TIME_ZONE,
                        DateUtil.FORMAT_DATE_TIME));

            }

            tvContent.setText(data.getContent());
            tvNumberLiked.setText(String.valueOf(data.getNumLike()));
            if (data.isLiked() == false) {
                ivLikeThisRated.setImageResource(R.drawable.ic_like_grey);
            } else {
                ivLikeThisRated.setImageResource(R.drawable.ic_like_green);
            }
            tvContent.makeExpandable(Constants.MAX_LINE_COMMENT);
        }

        @OnClick({R.id.llLikeLeaderRated, R.id.ivPhoto, R.id.ivAvatarTouristRated})
        public void OnClick(View v) {
            int action = 0;
            switch (v.getId()) {
                case R.id.llLikeLeaderRated:
                    action = LeaderRateFragment.ACTION_LIKE_THIS_COMENT;
                    break;
                case R.id.ivPhoto:
                    action = LeaderRateFragment.ACTION_VIEW_PHOTO;;
                    break;
                case R.id.ivAvatarTouristRated:
                    action = LeaderRateFragment.ACTION_GO_TO_PROFILE;
                    break;
            }
            if (listener != null && data != null) {
                listener.onEventControl(action, data, view, position);
            }
        }


    }


}
