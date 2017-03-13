package com.boot.accommodation.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.boot.accommodation.listener.OnEventControl;

import butterknife.ButterKnife;

public class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    protected Context mContext;
    protected View view;
    protected OnEventControl listener;
    protected T data;
    protected int position;
    protected int totalSize;

    public BaseRecyclerViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        this.view = view;
        mContext = view.getContext();
    }

    public void bindData(T data) {

    }

    public void setListener(OnEventControl listener) {
        this.listener = listener;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
