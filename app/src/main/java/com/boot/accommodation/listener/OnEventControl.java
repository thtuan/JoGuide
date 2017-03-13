package com.boot.accommodation.listener;

import android.view.View;

/**
 * Created by Admin on 17/03/2016.
 */
public interface OnEventControl {

    void onEventControl(int action, Object item, View View, int position);
    void onLoadMore(int position);
}
