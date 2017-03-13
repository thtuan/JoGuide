package com.boot.accommodation.vp.tour;


import android.text.Html;
import android.text.Layout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.dto.view.FeedbackItemDTO;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.ImageUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Mo ta class
 *
 * @author Vuong-bv
 * @since: 5/13/2016
 */
public class FeedbackAdapter extends BaseRecyclerViewAdapter<FeedbackItemDTO, FeedbackAdapter.ViewHolder> {

    public FeedbackAdapter(List<FeedbackItemDTO> items) {
        super(items);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_feedback;
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        holder.bindData(items.get(position), position);
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        @Bind(R.id.ivImage)
        CircularImageView ivImage;
        @Bind(R.id.tvIdTour)
        TextView tvIdTour;
        @Bind(R.id.tvTourAndName)
        TextView tvTourAndName;
        @Bind(R.id.tvDateTime)
        TextView tvDateTime;
        @Bind(R.id.tvContent)
        TextView tvContent;
        @Bind(R.id.llListFeedback)
        LinearLayout llListFeedback;
        @Bind(R.id.tvIdFeedback)
        TextView tvIdFeedback;
        @Bind(R.id.llViewFeedback)
        LinearLayout llViewFeedback;
        boolean checkLines = false;//chekc content line  > 2 or not

        ViewHolder(View view) {
            super(view);
        }

        public void bindData(FeedbackItemDTO data, int position) {
            this.data = data;
            this.position = position;
            //set data
            ImageUtil.loadImage(ivImage, ImageUtil.getImageUrl(data.getUserAvatar()));
            tvTourAndName.setText(Html.fromHtml(data.getUserName() + " " + "►" + " " + data.getTourName()));
            tvDateTime.setText(DateUtil.convertDateWithFormat(data.getDate(), DateUtil.FORMAT_DATE_WITHOUT_TIME_ZONE, DateUtil
                    .FORMAT_DATE));
            tvIdFeedback.setText(Long.toString(data.getFeedbackId()));
            tvContent.setText(data.getContent());

            ViewTreeObserver vto = tvContent.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Layout l = tvContent.getLayout();
                    if (l != null) {
                        int lines = l.getLineCount();
                        if (lines > 0)
                            if (l.getEllipsisCount(lines - 1) > 0) {
                                checkLines = true;
                            }
                    }
                }
            });

        }

        @OnClick({R.id.llViewFeedback})
        public void OnClick(View v) {
            int action = 0;
            switch (v.getId()) {
                case R.id.llViewFeedback:
                    if (checkLines) {
                        action = FeedbackActivity.ACTION_VIEW_POPUP;
                    }
                    break;
            }
            if (listener != null && data != null) {
                listener.onEventControl(action, data, view, position);
            }
        }
    }


}
