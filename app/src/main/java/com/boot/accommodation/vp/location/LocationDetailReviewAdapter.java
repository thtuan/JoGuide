package com.boot.accommodation.vp.location;

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

/**
 * Place detail review adapter
 *
 * @author tuanlt
 * @since 10:57 AM 7/8/2016
 */
public class LocationDetailReviewAdapter extends BaseRecyclerViewAdapter<ReviewItemDTO, LocationDetailReviewAdapter.ViewHolder> {


    public LocationDetailReviewAdapter(List<ReviewItemDTO> lstReView) {
        super(lstReView);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.activity_place_detail_comment_item;
    }

//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            ViewHolder viewHolder = null;
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_place_detail_comment_item, parent, false);
//            viewHolder = new ViewHolder(view, mListener);
//            return viewHolder;
//        }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        holder.renderView(items.get(position));
    }


    public class ViewHolder extends BaseRecyclerViewHolder implements View.OnClickListener {

        CircularImageView ivPlaceDetailUserAvatar;
        //            TextView tvUserRated;
        RatingBar rbUserRated;
        TextView tvDateRated;
        ExpandableTextView tvUserComment;
        TextView tvLikeReview;
        ImageView ivImage;
        ImageView ivLike;
        LinearLayout llLikeThisRated;

        public ViewHolder(View view) {
            super(view);
            ivPlaceDetailUserAvatar = (CircularImageView) view.findViewById(R.id.ivPlaceDetailUserAvatar);
            ivPlaceDetailUserAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onEventControl(LocationDetailReviewFragment.ACTION_GO_TO_PROFILE, data, null, -1);
                    }
                }
            });
//                tvUserRated = (TextView) view.findViewById(R.id.tvUserRated);
            rbUserRated = (RatingBar) view.findViewById(R.id.rbUserRated);
            tvDateRated = (TextView) view.findViewById(R.id.tvDateRated);
            tvUserComment = (ExpandableTextView) view.findViewById(R.id.tvUserComment);
            tvLikeReview = (TextView) view.findViewById(R.id.tvLikeReview);
            ivImage = (ImageView) view.findViewById(R.id.ivImage);
            ivImage.setOnClickListener(this);
            ivLike = (ImageView) view.findViewById(R.id.ivLikeThisRated);
            llLikeThisRated = (LinearLayout) view.findViewById(R.id.llLikeThisRated);
            llLikeThisRated.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onEventControl(LocationDetailReviewFragment.ACTION_LIKE_REVIEW, data, null, -1);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onEventControl(LocationDetailReviewFragment.ACTION_FULL_IMAGE_VIEW, data, null, -1);
            }
        }

        public void renderView(ReviewItemDTO reviewItem) {
            this.data = reviewItem;
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null && data != null) {
                        listener.onEventControl(Constants.ActionEvent.ACTION_DELETE_REVIEW, data, v, position);
                    }
                    return true;
                }
            });
            ImageUtil.loadImage(ivPlaceDetailUserAvatar, ImageUtil.getImageUrlThumb(reviewItem.getUserAvatar()));
            rbUserRated.setRating((float) reviewItem.getRating());
            tvDateRated.setText(DateUtil.convertDateWithFormat(reviewItem.getDateTime(), DateUtil
                    .FORMAT_DATE_TIME_ZONE, DateUtil.FORMAT_DATE_TIME));
            tvUserComment.setText(reviewItem.getContent());
            tvLikeReview.setText(String.valueOf(reviewItem.getNumLike()));
            //action click like
            llLikeThisRated.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEventControl(LocationDetailReviewFragment.ACTION_LIKE_REVIEW, data, null, -1);
                }
            });
            if (reviewItem.isLiked() == false) {
                ivLike.setImageResource(R.drawable.ic_like_grey);
            } else {
                ivLike.setImageResource(R.drawable.ic_like_orange);
            }
            if (StringUtil.isNullOrEmpty(reviewItem.getImage())) {
                ivImage.setVisibility(View.GONE);
            } else {
                ivImage.setVisibility(View.VISIBLE);
                ImageUtil.loadImage(ivImage, ImageUtil.getImageUrl(reviewItem.getImage()));
            }
            tvUserComment.makeExpandable(Constants.MAX_LINE_COMMENT);
        }
    }
}
