package com.boot.accommodation.listener;

import com.boot.accommodation.base.BaseResponse;

/**
 * Event cho model goi request API
 *
 * @author tuanlt
 * @since: 12:02 AM 5/12/2016
 */
public abstract class ModelCallBack<T extends BaseResponse> {
    private String tag = ""; // Tag

    public ModelCallBack() {
    }

    public ModelCallBack( String tag ) {
        this.tag = tag;
    }

    public abstract void onSuccess( T response );

    public abstract void onError( int errorCode, String error );

    public String getTag() {
        return tag;
    }

    public void setTag( String tag ) {
        this.tag = tag;
    }
}
