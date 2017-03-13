package com.boot.accommodation.vp.login;

/**
 * Presenter Mgr of splash
 *
 * @author tuanlt
 * @since 12:13 AM 7/18/2016
 */
public interface SplashPresenterMgr {
    /**
     * Request validate gplus
     * @param token
     */
    void requestValidateGPlus(String token);
}
