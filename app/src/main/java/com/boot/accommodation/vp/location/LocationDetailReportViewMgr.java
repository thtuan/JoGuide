package com.boot.accommodation.vp.location;

/**
 * Place detail report location view mgr
 *
 * @author tuanlt
 * @since: 11:20 AM 5/5/2016
 */
public interface LocationDetailReportViewMgr {

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

    /**
     * Request report location success
     */
    void showReportLocationSuccess();

}
