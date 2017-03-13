package com.boot.accommodation.vp.home;

import com.boot.accommodation.dto.view.ChangePasswordDTO;

/**
 * SettingPresenterMgr
 *
 * @author thtuan
 * @since 4:25 PM 06-09-2016
 */
public interface SettingPresenterMgr {
    /**
     *  set new password for user
     * changePasswordDTO
     */
    void setNewPassword(ChangePasswordDTO changePasswordDTO);
}
