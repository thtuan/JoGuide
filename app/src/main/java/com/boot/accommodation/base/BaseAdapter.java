package com.boot.accommodation.base;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.boot.accommodation.listener.OnEventControl;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, V extends BaseHolder> extends android.widget.BaseAdapter {

    protected List<T> items = new ArrayList<>();
    protected OnEventControl listener;

    public BaseAdapter(List<T> items) {
        this.items = items;
    }

    public abstract V getViewHolder(View view);

    @LayoutRes
    public abstract int itemLayout();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        V holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), itemLayout(), null);
            holder = getViewHolder(convertView);
            holder.setListenerEvent(listener);
        } else {
            holder = (V) convertView.getTag();
        }
        bindData(holder, items.get(position), position);
        return convertView;
    }

    public void bindData(V holder, T data, int position) {
        holder.bind(data, position);
    }

    public void setData(List<T> data) {
        items.clear();
        items.addAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setListener(OnEventControl listener) {
        this.listener = listener;
    }
}
