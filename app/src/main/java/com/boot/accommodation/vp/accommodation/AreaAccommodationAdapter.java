package com.boot.accommodation.vp.accommodation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.dto.view.AreaDTO;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Admin on 12/10/2015.
 */
public class AreaAccommodationAdapter extends BaseRecyclerViewAdapter<AreaDTO, AreaAccommodationAdapter.ViewHolder> {

    private static final int TYPE_MULTI_IMAGE = 1;
    private static final int TYPE_FULL = 2;
    private Context context; //Context

    public AreaAccommodationAdapter(Context context, List<AreaDTO> items) {
        super(items);
        this.context = context;
    }

    @Override
    public int itemLayout() {
        return R.layout.item_area_accomodation;
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
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView =
                LayoutInflater.from(context).inflate(R.layout.item_area_accomodation, parent, false);
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
                }
                itemView.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });

        AreaAccommodationAdapter.ViewHolder holder = new AreaAccommodationAdapter.ViewHolder(itemView);
        return holder;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        holder.setListener(listener);
        holder.bind(items.get(position), position);
    }

    static class ViewHolder extends BaseRecyclerViewHolder {

        @Bind(R.id.ivPhoto)
        ImageView ivPhoto;
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.prLoading)
        ProgressBar prLoading;

        ViewHolder(View view) {
            super(view);
        }

        public void bind(AreaDTO item, int position) {
            this.data = item;
            this.position = position;
            ImageUtil.loadImage(ivPhoto, ImageUtil.getImageUrl(item.getPhoto()), prLoading);
            tvName.setText(item.getLongName());
        }

        @OnClick({R.id.rlBackground})
        public void OnClick(View v) {
            switch (v.getId()) {
                case R.id.rlBackground:
                    if (listener != null && data != null) {
                        listener.onEventControl(AccommodationActivity.ACTION_CHOOSE_AREA, data, view, position);
                    }
                    break;
            }

        }

    }
}
