package com.boot.accommodation.common.control;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * NonUnderLineClickSpan
 *
 * @author tuanlt
 * @since 11:51 PM 10/20/16
 */
public class NonUnderLineClickSpan extends ClickableSpan {

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false); // set to false to remove underline
    }

    @Override
    public void onClick(View widget) {
    }
}
