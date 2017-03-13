package com.boot.accommodation.vp.home;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by hoanganh on 11/15/15.
 */
public class CustomViewPager extends ViewPager {

    private boolean isSwipeable = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.isSwipeable) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.isSwipeable) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setSwipeable(boolean isSwipeable) {
        this.isSwipeable = isSwipeable;
    }
}
