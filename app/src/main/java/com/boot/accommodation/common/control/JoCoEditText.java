package com.boot.accommodation.common.control;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AutoCompleteTextView;

import com.boot.accommodation.R;
import com.boot.accommodation.listener.OnEditTextControlListener;
import com.boot.accommodation.util.Utils;


/**
 * control edittext voi button clear text phia sau
 *
 * @author banghn
 */
public class JoCoEditText extends AutoCompleteTextView {
    public String defaultValue = "";
    // mac dinh editText tu xu ly handle OnTouch
    // neu khong muon editText tu xu ly thi set = false
    private boolean isHandleDefault = true;
    Drawable imgX = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_white, null);
    Drawable imgSearch = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_search_grey, null);
    ; // X image
    // image X visible ?
    private boolean isImageClearVisibile = true;
    private boolean isExistButtonX = false;
    private boolean isImageSearchVisible = false; // co hien thi search ben trai len hay ko
    private boolean isExistSearch = false;
    private OnEditTextControlListener listener; //Listener

    public JoCoEditText( Context context ) {
        super(context);
        init();
    }

    public JoCoEditText( Context context, AttributeSet attrs, int defStyle ) {
        super(context, attrs, defStyle);
        init();
    }

    public JoCoEditText( Context context, AttributeSet attrs ) {
        super(context, attrs);
        init();
    }

    /**
     * Hien thi button (X) xoa text
     *
     * @param visible
     * @author banghn
     */
    public void setImageClearVisibile( boolean visible ) {
        isImageClearVisibile = visible;
        if (!isImageClearVisibile) {
            removeClearButton();
        } else {
            manageClearButton();
        }
    }

    /**
     * Set image and visible
     * @param idResource
     * @param visible
     */
    public void setImageClearVisibile(int idResource, boolean visible ) {
        imgX = ResourcesCompat.getDrawable(getResources(),idResource, null);
        isImageClearVisibile = visible;
        init();
    }

    /**
     * Co hien thi search ben trai hay ko
     * @param visible
     */
    public void setImageSearchVisible(boolean visible){
        isImageSearchVisible = visible;
        if (!isImageSearchVisible) {
            this.setCompoundDrawables(imgSearch,
                this.getCompoundDrawables()[1], this.getCompoundDrawables()[2],
                this.getCompoundDrawables()[3]);
            isExistSearch = false;
        } else {
            if (!isExistSearch) {
                this.setCompoundDrawables(imgSearch,
                    this.getCompoundDrawables()[1], this.getCompoundDrawables()[2],
                    this.getCompoundDrawables()[3]);
                this.setCompoundDrawablePadding(Utils.dip2Pixel(4));
            }
            isExistSearch = true;
        }
    }

    /**
     * Set image search visible
     * @param resourceId
     * @param visible
     */
    public void setImageSearchVisible(int resourceId, boolean visible){
        imgSearch = ResourcesCompat.getDrawable(getResources(),resourceId, null);
        isImageSearchVisible = visible;
        if (!isImageSearchVisible) {
            this.setCompoundDrawables(imgSearch,
                    this.getCompoundDrawables()[1], this.getCompoundDrawables()[2],
                    this.getCompoundDrawables()[3]);
            isExistSearch = false;
        } else {
            if (!isExistSearch) {
                this.setCompoundDrawables(imgSearch,
                        this.getCompoundDrawables()[1], this.getCompoundDrawables()[2],
                        this.getCompoundDrawables()[3]);
                this.setCompoundDrawablePadding(Utils.dip2Pixel(4));
            }
            isExistSearch = true;
        }
//        init();
    }

    /**
     * init edittext
     */
    void init() {
        // set vung bound button
        imgX.setBounds(0, 0, imgX.getIntrinsicWidth(),
            imgX.getIntrinsicHeight());

        imgSearch.setBounds(0, 0, imgSearch.getIntrinsicWidth(),
            imgSearch.getIntrinsicHeight());

        //kiem tra viec hien thi button clear
        if (isImageClearVisibile) {
            manageClearButton();
        } else {
            removeClearButton();
        }

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged( CharSequence s, int start, int before,
                                       int count ) {

            }

            @Override
            public void afterTextChanged( Editable arg0 ) {
                if (isImageClearVisibile) {
                    JoCoEditText.this.manageClearButton();
                }
            }

            @Override
            public void beforeTextChanged( CharSequence s, int start, int count,
                                           int after ) {
            }
        });
    }

    @Override
    public boolean onTouchEvent( MotionEvent event ) {
        // touch trong vung drawable
        boolean onTouch = false;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getX() > getWidth() - getPaddingRight()
                - imgX.getIntrinsicWidth() && isImageClearVisibile) {
                setText("");
                removeClearButton();
                if(!this.isHandleDefault) {
                    onTouch = false;
                }else{
                    onTouch = true;
                }
            } else if (!this.isHandleDefault) {
                if(listener != null){
                    listener.onTouchControl();
                }
                onTouch = false;
            } else {
                if(listener != null){
                    listener.onTouchControl();
                }
                onTouch = super.onTouchEvent(event);
            }
        } else {
            onTouch = super.onTouchEvent(event);
        }
        if(onTouch) {
            setCursorVisible(true);
            setSelection(length());
        }
        return onTouch;

    }

    /**
     * hien thi button clear text?
     *
     * @author banghn
     */
    void manageClearButton() {
        if (this.getText().toString().equals("")) {
            removeClearButton();
        } else {
            addClearButton();
        }
    }


    /**
     * Add button clear text
     *
     * @author banghn
     */
    void addClearButton() {
        if (!isExistButtonX) {
            this.setCompoundDrawables(this.getCompoundDrawables()[0],
                this.getCompoundDrawables()[1], imgX,
                this.getCompoundDrawables()[3]);
        }
        isExistButtonX = true;
    }


    /**
     * remove button clear text
     *
     * @author banghn
     */
    void removeClearButton() {
        this.setCompoundDrawables(this.getCompoundDrawables()[0],
            this.getCompoundDrawables()[1], null,
            this.getCompoundDrawables()[3]);
        isExistButtonX = false;
    }

    /**
     * Remove Search
     */
    void removeSearchButton() {
        this.setCompoundDrawables(null,
            this.getCompoundDrawables()[1],  this.getCompoundDrawables()[2],
            this.getCompoundDrawables()[3]);
        isExistSearch = false;
    }

    /**
     * Add
     */
    void addSearchButton() {
        if (!isExistSearch) {
            this.setCompoundDrawables(imgSearch,
                this.getCompoundDrawables()[1], this.getCompoundDrawables()[2],
                this.getCompoundDrawables()[3]);
        }
        isExistSearch = true;
    }

    public void setIsHandleDefault(boolean isHandle) {
        this.isHandleDefault = isHandle;
    }

    public void setListener(OnEditTextControlListener listener) {
        this.listener = listener;
    }

}