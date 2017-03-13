package com.boot.accommodation.common.control;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * MyClickableSpan
 *
 * @author tuanlt
 * @since 10:10 PM 2/21/17
 */
public class MyClickableSpan extends ClickableSpan {

    final String text; //Text
    public MyClickableSpan(final String text) {
        this.text = text;
    }
    @Override
    public void onClick(final View widget) {
//        mListener.onTagClicked(text);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false); // set to false to remove underline
    }


}


