package com.boot.accommodation.util;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * ExpandCollapseUtil
 *
 * @author tuanlt
 * @since 4:22 PM 12/27/16
 */
public class ExpandCollapseUtil {

    private boolean isExpand = true;

    /**
     * Animation view
     *
     * @param across
     * @param viewAnimation
     */
    public void animationView(View across, View viewAnimation) {
        if (isExpand) {
            across.animate().setDuration(150L).rotation(0.0F);
            collapse(viewAnimation);
        } else {
            across.animate().setDuration(150L).rotation(90.0F);
            expand(viewAnimation);
        }
        isExpand = !isExpand;
    }

    /**
     * Expand view
     *
     * @param v
     */
    public void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();
        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration(100);
        v.startAnimation(a);
    }

    /**
     * Collapse view
     *
     * @param v
     */
    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration(100);
        v.startAnimation(a);
    }
}
