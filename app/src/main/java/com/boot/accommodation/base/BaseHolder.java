package com.boot.accommodation.base;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.boot.accommodation.R;
import com.boot.accommodation.listener.OnEventControl;

import butterknife.ButterKnife;

/**
 * Created by Admin on 23/11/2015.
 */
public class BaseHolder<T> implements Animation.AnimationListener {

    protected Context mContext;
    protected View view;
    protected Animation animFadein, animRotate;
    public OnEventControl listenerEvent;
    protected T data;
    protected int position;

    public BaseHolder(View view) {
        ButterKnife.bind(this, view);
        this.view = view;
        this.view.setTag(this);
        this.mContext = view.getContext();
        animFadein = AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_in);
        animFadein.setAnimationListener(this);
        animRotate = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate);
        animRotate.setAnimationListener(this);
    }

    public void bind(T data, int position) {
        this.data = data;
        this.position = position;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void setListenerEvent(OnEventControl listenerEvent) {
        this.listenerEvent = listenerEvent;
    }
}
