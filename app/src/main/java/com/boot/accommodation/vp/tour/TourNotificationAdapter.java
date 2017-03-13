package com.boot.accommodation.vp.tour;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.TourNotificationDTO;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import java.util.List;

import butterknife.Bind;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 10:57 AM 17-05-2016
 */
public class TourNotificationAdapter extends BaseRecyclerViewAdapter<TourNotificationDTO, TourNotificationAdapter.ViewHolder> {

    ViewHolder viewHolder;
    int userPlan;


    public TourNotificationAdapter(List<TourNotificationDTO> items, int userPlan) {
        super(items);
        this.userPlan = userPlan;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return viewHolder = new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_notification;
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        holder.bindData(items.get(position), position, userPlan);
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        private final static int NOTICE = 1;
        private final static int REMIND = 2;
        private final static int ATTENTION = 3;
        private final static int USER = 1;
        private final static int PLAN = 0;
        @Bind(R.id.ivAvatarNotification)
        ImageView ivAvatarNotification;
        @Bind(R.id.tvTitleNotification)
        TextView tvTitleNotification;
        @Bind(R.id.tvDetailNotification)
        TextView tvDetailNotification;
        @Bind((R.id.tvDateTime))
        TextView tvDateTime;
        @Bind(R.id.llNotification)
        LinearLayout llNotification;

        public ViewHolder(View view) {
            super(view);
        }

        /**
         * gan data vao adapter
         *
         * @param data
         * @param position
         * @param userPlan
         */
        public void bindData(TourNotificationDTO data, int position, int userPlan) {
            this.data = data;
            this.position = position;
            SpannableStringBuilder builder = new SpannableStringBuilder();
            if (!StringUtil.isNullOrEmpty(data.getUser().getAvatar())) {
                ImageUtil.loadImage(ivAvatarNotification, ImageUtil.getImageUrl(data.getUser().getAvatar()));
            }
            tvDetailNotification.setText(data.getContent());
            tvDateTime.setText(DateUtil.convertDateWithFormat(data.getCreateAt(), DateUtil.FORMAT_DATE_TIME_ZONE, DateUtil.FORMAT_DATE));
            ImageSpan imageSpan = null;
            String typeMessage = "";
            switch (data.getNotifyType()) {
                case NOTICE: {
                    imageSpan = new ImageSpan(mContext, R.drawable.ic_speaker_green);
                    typeMessage = Constants.STR_SPACE + Constants.STR_SPACE + StringUtil.getString(R.string
                            .text_notice);
                    break;
                }
                case ATTENTION: {
                    imageSpan = new ImageSpan(mContext, R.drawable.ic_attention);
                    typeMessage = Constants.STR_SPACE + Constants.STR_SPACE + StringUtil.getString(R.string
                            .text_attention);
                    break;
                }
                case REMIND: {
                    imageSpan = new ImageSpan(mContext, R.drawable.ic_alarm_green);
                    typeMessage = Constants.STR_SPACE + Constants.STR_SPACE + StringUtil.getString(R.string
                            .text_remind) + ": " + DateUtil.convertDateWithFormat(data.getRemindTime(), DateUtil
                            .FORMAT_DATE_WITHOUT_TIME_ZONE, DateUtil.FORMAT_DATE_TIME);
                    break;
                }
            }
            builder.append(typeMessage);
            if (userPlan == USER) {
                SpannableString span1 = new SpannableString(!StringUtil.isNullOrEmpty(data.getTourName()) ? data
                        .getTourName() : Constants.STR_BLANK);
                span1.setSpan(new ForegroundColorSpan(Utils.getColor(R.color.hyper_link)), 0, span1.toString().length
                        (), 0);
                builder.append(Constants.STR_SPACE);
                builder.append(span1);
            }
            builder.append(Constants.STR_COLON + Constants.STR_SPACE);
            builder.append(data.getTitle());
            if (imageSpan != null) {
                builder.setSpan(imageSpan, 0, 1, 0);
            }
            tvTitleNotification.setText(builder, TextView.BufferType.SPANNABLE);
            if (data.getIsNewNotification()){
                llNotification.setBackgroundResource(R.color.black_trans20);
            }
        }


    }
}
