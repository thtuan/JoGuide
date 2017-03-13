package com.boot.accommodation.vp.trip;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Adapter load hinh anh cho tung tour
 *
 * @author tuanlt
 * @since: 3:28 PM 5/11/2016
 */
public class ImageTripPagerAdapter extends BaseRecyclerViewAdapter<String, ImageTripPagerAdapter.ViewHolder> {

    private Context context;
    //    private List<String> lstPhoto = new ArrayList<>();//ds hinh anh
    private TripAdapter tripAdapter;
    TripAdapter.ViewHolder viewHolder;
    private static final int TYPE_MULTI_IMAGE = 1;
    private static final int TYPE_FULL = 2;
    private boolean isFitXY = false; //Variable check image fit xy or no

    public ImageTripPagerAdapter(Context context, List<String> lstPhoto, TripAdapter tripAdapter, TripAdapter.ViewHolder viewHolder) {
        super(lstPhoto);
        this.context = context;
        this.tripAdapter = tripAdapter;
        this.viewHolder = viewHolder;
    }

    public ImageTripPagerAdapter(Context context, List<String> lstPhoto) {
        super(lstPhoto);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                if(items!= null && items.size() == 1)
                    return TYPE_FULL;
                default:
                    return TYPE_MULTI_IMAGE;

        }
    }
    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }
    @Override
    public int getItemCount() {
        if (items != null && !items.isEmpty()) {
            return items.size();
        }else {
            return 0;
        }
    }
    @Override
    public int itemLayout() {
        return R.layout.item_image_trip;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView =
            LayoutInflater.from(context).inflate(R.layout.item_image_trip, parent, false);
        itemView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                final int type = viewType;
                final ViewGroup.LayoutParams lp = itemView.getLayoutParams();
                if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams sglp =
                        (StaggeredGridLayoutManager.LayoutParams) lp;
                    switch (type) {
                        case TYPE_FULL:
                            sglp.setFullSpan(true);
                            sglp.width = itemView.getWidth() - Utils.dip2Pixel(8);
                            break;
                        case TYPE_MULTI_IMAGE:
                            sglp.setFullSpan(true);
                            sglp.width = itemView.getWidth() - Utils.dip2Pixel(20);
                            break;
                    }
                    itemView.setLayoutParams(sglp);
//                    final StaggeredGridLayoutManager lm =
//                        (StaggeredGridLayoutManager) ((RecyclerView) parent).getLayoutManager();
//                    lm.invalidateSpanAssignments();
                }
                itemView.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });

        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        holder.bindData(items.get(position), position);
    }

    @Override
    public String setDataMessage() {
        return "";
    }

    public void setFitXY(boolean fitXY) {
        isFitXY = fitXY;
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        @Bind(R.id.ivPhoto)
        ImageView ivPhoto;
        @Bind(R.id.llPhoto)
        LinearLayout llPhoto;
        @Bind(R.id.prLoading)
        ProgressBar prLoading;

        ViewHolder(View view) {
            super(view);
            if (isFitXY) {
                ivPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }

        public void bindData(String data, int position) {
            this.data = data;
            this.position = position;
            if (!StringUtil.isNullOrEmpty(data)) {
                ImageUtil.loadImage(ivPhoto, ImageUtil.getImageUrl(data), prLoading);
            } else {
                ivPhoto.setImageDrawable(Utils.getDrawable(R.drawable.img_default_error));
            }
        }

        @OnClick({R.id.ivPhoto})
        public void OnClick(View v) {
            if (ImageTripPagerAdapter.this.listener != null) {
                ImageTripPagerAdapter.this.listener.onEventControl(Constants.ActionEvent.ACTION_GO_TO_PROMOTION_DEAL_INFO, null,null,0);
            } else {
                //Using for trip, hard
                if (tripAdapter != null) {
                    tripAdapter.clickImage(viewHolder, position);
                }
            }
        }
    }
}
