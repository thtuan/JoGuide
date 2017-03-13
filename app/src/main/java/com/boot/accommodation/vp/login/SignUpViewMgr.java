package com.boot.accommodation.vp.login;

import com.boot.accommodation.dto.view.LoginDTO;
import com.boot.accommodation.dto.view.UserItemDTO;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 2:31 PM 03-06-2016
 */
public interface SignUpViewMgr {
    /**
     * when sign up success
     * @param loginDTO
     * @param data
     */
    void onSignUpSuccess(LoginDTO loginDTO, UserItemDTO data);

    /**
     * when send email success
     */
    void requestEmailSuccess();

    /**
     * when sign up fail
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

}
