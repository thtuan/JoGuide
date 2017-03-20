package com.boot.accommodation.vp.tourguide;

import android.util.Log;
import android.view.View;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.dto.view.TourGuideDTO;
import com.boot.accommodation.util.ImageUtil;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thtuan on 3/14/17.
 */

public class TourguideAdapter extends BaseRecyclerViewAdapter<TourGuideDTO, TourguideAdapter.ViewHolder> {
    private List<TourGuideDTO> myitems = new ArrayList<>();
    private List<TourGuideDTO> itemsAll = new ArrayList<>();

    public TourguideAdapter(List<TourGuideDTO> items) {
        super(items);
        this.myitems = items;
    }
    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        holder.bindData(items.get(position), position);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_tourguide;
    }
    // loc danh sach tourguide
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                Log.d("TAG", "**** PUBLISHING RESULTS for: " + constraint);
                items = (List<TourGuideDTO>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Log.d("TAG", "**** PERFORM FILTERING for: " + constraint);
                final FilterResults oReturn = new FilterResults();
                final ArrayList<TourGuideDTO> results = new ArrayList<>();
                if (constraint != null) {
                    if (myitems != null && myitems.size() > 0) {
                        for (final TourGuideDTO g : myitems) {
                            if (g.getTown().toLowerCase()
                                    .contains(constraint.toString().toLowerCase()) || g.getName().toLowerCase()
                                    .contains(constraint.toString().toLowerCase()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }
        };
    }
    class ViewHolder extends BaseRecyclerViewHolder<TourGuideDTO> {
        CircularImageView ciAvatar;
        LinearLayout llMain;
        TextView tvName;
        TextView tvType;
        public ViewHolder(View itemView) {
            super(itemView);
            ciAvatar = (CircularImageView) itemView.findViewById(R.id.ciAvatar);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvType = (TextView) itemView.findViewById(R.id.tvtype);
            llMain = (LinearLayout) itemView.findViewById(R.id.llMain);
        }
        public void bindData (final TourGuideDTO data, final int position){
            ImageUtil.loadImage(ciAvatar, data.getImage());
            tvName.setText(data.getName());
            tvType.setText(data.getPhone());
            llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onEventControl(1, data, view, position);
                    }
                }
            });
        }
    }
}
