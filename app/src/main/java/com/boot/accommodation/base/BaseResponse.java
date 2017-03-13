package com.boot.accommodation.base;

import java.io.Serializable;

/**
 * Mo ta class
 *
 * @author tuanlt
 * @since: 11:45 AM 5/11/2016
 */
public class BaseResponse<Tag extends String> implements Serializable {
    private int errorCode;
    private String error;

    public int getErrorCode() {
        return errorCode;
    }

    public String getError() {
        return error;
    }

}
