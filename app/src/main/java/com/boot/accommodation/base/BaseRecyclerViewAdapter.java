package com.boot.accommodation.base;

import android.graphics.PorterDuff;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.listener.OnEventControl;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.category.CreateLocationActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T, V extends BaseRecyclerViewHolder> extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 1; // load item cua listviews
    private final int VIEW_TYPE_PROGRESSBAR = 2; // progress bar cua listview
    private final int VIEW_NO_DATA = 3; // load item no data
    private final int VIEW_FOOTER = 4; // load footer
    protected List<T> items = new ArrayList<>();
    protected OnEventControl listener;
    private boolean isEnableLoadMore = false; // enable load more
    protected boolean isEnableFooter = false; // enable footer
    boolean isCancelLoadMore = false; // cancle load more
    private int previousSizeList = 0;
    boolean isHorizotalList = false;

    public BaseRecyclerViewAdapter() {
    }

    public BaseRecyclerViewAdapter(List<T> items) {
        if(items != null) {
            this.items.addAll(items);
        }
    }
    public abstract V getViewHolder(View view);
    //Load more
    public static class ProgressViewHolder extends BaseRecyclerViewHolder {
        public ProgressBar progressBar;
        public RelativeLayout llLoading;
        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar)v.findViewById(R.id.prLoading);
            llLoading = (RelativeLayout) v;
        }
    }
    public static class NoDataViewHolder extends BaseRecyclerViewHolder {
        public TextView tvNoData;
        public NoDataViewHolder(View v) {
            super(v);
            tvNoData = (TextView) v.findViewById(R.id.tvNoData);
        }
    }

    public static class FooterViewHolder extends BaseRecyclerViewHolder {
        public ImageView ivFooter;
        public FooterViewHolder(View v) {
            super(v);
            ivFooter = (ImageView) v.findViewById(R.id.ivFooter);
        }
    }
    @LayoutRes
    public abstract int itemLayout();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        V viewHolder = getViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout(), viewGroup, false));
//        viewHolder.setListener(listener);
//        V viewHolder;
        if (i == VIEW_FOOTER){
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.footer_item, viewGroup, false);
            FooterViewHolder noDataViewHolder = new FooterViewHolder(v);
            noDataViewHolder.ivFooter.setImageResource(R.drawable.img_create_location);
            noDataViewHolder.ivFooter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEventControl(CreateLocationActivity.ACTION_ADD_PHOTO,true,v,items.size());
                }
            });
            return noDataViewHolder;
        }
        if (i == VIEW_NO_DATA){
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.no_data_item, viewGroup, false);
            NoDataViewHolder noDataViewHolder = new NoDataViewHolder(v);
            noDataViewHolder.tvNoData.setText(setDataMessage());
            return noDataViewHolder;
        }else if(i == VIEW_TYPE_ITEM) {
            V viewHolder = getViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout(), viewGroup, false));
            viewHolder.setListener(listener);
            viewHolder.setTotalSize(items.size());
            return viewHolder;
        } else {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.progress_bar_item, viewGroup, false);
            BaseRecyclerViewHolder progressViewHolder = new ProgressViewHolder(v);
            ProgressBar prLoading = (ProgressBar) v.findViewById(R.id.prLoading);
            prLoading.getIndeterminateDrawable().setColorFilter(Utils.getColor(R.color.primary_500), PorterDuff
                    .Mode
                    .SRC_IN);
            if (isHorizotalList) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) v.getLayoutParams();
                params.height = RecyclerView.LayoutParams.MATCH_PARENT;
                params.width = RecyclerView.LayoutParams.WRAP_CONTENT;
                v.setLayoutParams(params);
                isHorizotalList = false;
            }

            return progressViewHolder;
        }

    }

    @Override
    public int getItemCount() {
        if (items != null && !items.isEmpty()) {
            return isEnableLoadMore ? items.size() + 1 : items.size();
        }else {
            return 1;
        }
    }

    public void setData(List<T> items) {
        if(this.items != null && items != null && this.items.size() > items.size()){
            previousSizeList = 0;
        }
        this.items.clear();
        this.items.addAll(items);
        setCancleLoadMore(false);
        notifyDataSetChanged();
    }

    public void clearData() {
        items.clear();
        notifyDataSetChanged();
    }

    public void addItem(T item, int position) {
        this.items.add(position, item);
        if (position == 0 || position == items.size() - 1) {
            notifyDataSetChanged();
        } else {
            notifyItemInserted(position);
        }
    }

    public void removeItem(int position) {
        this.items.remove(position);
        notifyDataSetChanged();
    }

    public void setListener(OnEventControl listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position ) {
        if (position >= getItemCount() - 1 && isEnableLoadMore && !isCancelLoadMore) {
            if (listener != null) {
                listener.onLoadMore(position);
            }
        }
        if (holder instanceof ProgressViewHolder) {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            if (isCancelLoadMore) {
                ((ProgressViewHolder) holder).progressBar.setVisibility(View.GONE);
                ((ProgressViewHolder) holder).llLoading.setVisibility(View.GONE);
            } else {
                ((ProgressViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
                ((ProgressViewHolder) holder).llLoading.setVisibility(View.VISIBLE);
            }
        } else if (holder instanceof NoDataViewHolder) {
            ((NoDataViewHolder) holder).tvNoData.setVisibility(View.VISIBLE);
        } else if (holder instanceof FooterViewHolder) {

        }else {
            bindViewHoder((V) holder, position);
        }

    }


    /**
     * Dung de render data
     * @param holder
     * @param position
     */
    protected void bindViewHoder(V holder, int position){

    }

    /**
     * Set load more co hien thi hay ko
     * @param isEnable
     */
    public void setEnableLoadMore(boolean isEnable){
        this.isEnableLoadMore = isEnable;
    }

    /**
     * Set load more horizotal list
     * @param isHorizotalList
     */
    public void setHorizotalList( boolean isHorizotalList){
        this.isHorizotalList = isHorizotalList;
    }

    /**
     * Set footer co hien thi hay ko
     * @param isEnable
     */
    public void setEnableFooter(boolean isEnable){
        this.isEnableFooter = isEnable;
    }

    /**
     * Cancle load more
     * @param isCancle
     */
    public void setCancleLoadMore( boolean isCancle){
        this.isCancelLoadMore = isCancle;
        // Cancel load more
        if(isCancelLoadMore){
            previousSizeList = 0;
        }
    }

    @Override
    public int getItemViewType( int position ) {
        if (getItemCount() == 1 && (items == null || items.isEmpty())) {
            return VIEW_NO_DATA;
        } else if (isEnableLoadMore && position >= items.size()) {
            return VIEW_TYPE_PROGRESSBAR;
        } else if (isEnableFooter && position >= items.size()){
            return VIEW_FOOTER;
        }else {
            return VIEW_TYPE_ITEM;
        }

    }

    public void setPreviousSizeList(int sizeList){
        this.previousSizeList = sizeList;
    }

    public int getPreviousSizeList(){
        return this.previousSizeList;
    }

    public String setDataMessage(){
        return Utils.getString(R.string.text_no_data);
    }
}
