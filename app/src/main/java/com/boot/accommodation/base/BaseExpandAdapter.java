package com.boot.accommodation.base;

import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseExpandAdapter<T> extends BaseExpandableListAdapter {

    protected List<T> items = new ArrayList<>();

    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return items.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
