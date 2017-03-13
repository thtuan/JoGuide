package com.boot.accommodation.vp.login;

import com.boot.accommodation.dto.view.LoginDTO;

/**
 * Mo ta class
 *
 * @author Vuong-bv
 * @since: 6/24/2016
 */
public interface SignUpPresenterMgr {
    /**
     * request signup to server
     * @param loginDTO
     */
    void requestInfoSignUp(LoginDTO loginDTO);

    /**
     * get pass again
     * @param loginDTO
     */
    void requestGetPassword( LoginDTO loginDTO);
}
