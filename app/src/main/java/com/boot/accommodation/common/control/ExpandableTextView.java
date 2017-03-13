package com.boot.accommodation.common.control;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

/**
 * Ellipsize the text when the lines of text exceeds the value provided by {@link #makeExpandable} methods.
 * Appends {@link #MORE} or {@link #LESS} as needed.
 * Created by vedant on 3/10/15.
 */
public class ExpandableTextView extends TextView {
    private static final String TAG = "ExpandableTextView";
    private static final String ELLIPSIZE = "... ";
    private static final String MORE = StringUtil.getString(R.string.text_view_more);
    private static final String LESS = StringUtil.getString(R.string.text_view_less);
    private String mFullText;
    private int mMaxLines;

    public ExpandableTextView(Context context) {
        super(context);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void makeExpandable(int maxLines) {
        makeExpandable(getText().toString(), maxLines);
    }

    public void makeExpandable(String fullText, final int maxLines) {
        mFullText =fullText;
        mMaxLines = maxLines;
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);
                Layout layout = getLayout();
                if (getLineCount() <= maxLines) {
                    setText(mFullText);
                } else {
                    setMovementMethod(LinkMovementMethod.getInstance());
                    showLess(layout);
                }
            }
        });
    }

    /**
     * truncate text and append a clickable {@link #MORE}
     */
    private void showLess(final Layout layout) {
        if (layout != null) {
            int lineEndIndex = layout.getLineEnd(mMaxLines - 1);
            String newText = mFullText.substring(0, lineEndIndex - (ELLIPSIZE.length() + MORE.length() + 1))
                    + ELLIPSIZE + MORE;
            SpannableStringBuilder builder = new SpannableStringBuilder(newText);
            builder.setSpan(new NonUnderLineClickSpan() {
                @Override
                public void onClick(View widget) {
                    showMore(layout);
                }
            }, newText.length() - MORE.length(), newText.length(), 0);
            builder.setSpan(new ForegroundColorSpan(Utils.getColor(R.color.hyper_link)), newText.length() - MORE.length
                    (), newText.length(), 0);
            setText(builder, BufferType.SPANNABLE);
        }
    }

    /**
     * show full text and append a clickable {@link #LESS}
     */
    private void showMore(final Layout layout) {
        SpannableStringBuilder builder = new SpannableStringBuilder(mFullText);
//        SpannableStringBuilder builder = new SpannableStringBuilder(mFullText + Constants.STR_SPACE + LESS);
//        builder.setSpan(new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//                showLess(layout);
//            }
//        }, builder.length() - LESS.length(), builder.length(), 0);
//        builder.setSpan(new ForegroundColorSpan(Utils.getColor(R.color.hyper_link)), builder.length() - LESS.length
//                (), builder.length(), 0);
        setText(builder, BufferType.SPANNABLE);
    }
}