package com.boot.accommodation.vp.login;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.common.control.JoCoEditText;
import com.boot.accommodation.constant.ErrorCode;
import com.boot.accommodation.dto.view.LoginDTO;
import com.boot.accommodation.dto.view.UserItemDTO;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * activity for send email, get password again
 *
 * @author Vuong-bv
 * @since: 6/24/2016
 */
public class ForgotPasswordActivity extends BaseActivity implements SignUpViewMgr {

    @Bind(R.id.etEmail)
    JoCoEditText etEmail;
    @Bind(R.id.btSend)
    Button btSend;
    SignUpPresenterMgr signUpPresenterMgr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        enableSlidingMenu(false);
        setEventDoneForgotPass();
    }

    @OnClick({R.id.btSend, R.id.ivBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSend:
                String email = String.valueOf(etEmail.getText()).trim();
                if (!checkValidate(email)) {
                    LoginDTO dto = new LoginDTO();
                    dto.setEmail(email);
                    dto.setUserName(email);
                    sendEmail(dto);
                }
                break;
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }

    /**
     * Thuc hien dang nhap
     *
     * @param email email
     */
    private boolean checkValidate(String email) {
        boolean check = false;
        if (StringUtil.isNullOrEmpty(email)) {
            showMessage(StringUtil.getString(R.string.text_validate_email_phone));
            etEmail.setFocusableInTouchMode(true);
            etEmail.requestFocus();
            check = true;
        } else if (!email.matches(StringUtil.getString(R.string.regex_format_email))) {
            showMessage(StringUtil.getString(R.string.text_validate_fortmat_email));
            etEmail.setFocusableInTouchMode(true);
            etEmail.requestFocus();
            check = true;
        }
        return check;
    }

    /**
     * Send email when user click done at soft key
     */
    private void setEventDoneForgotPass() {
        etEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String email = String.valueOf(etEmail.getText());
                    if (!checkValidate(email)) {
                        LoginDTO dto = new LoginDTO();
                        dto.setEmail(email);
                        dto.setUserName(email);
                        sendEmail(dto);
                        Utils.hideKeyboardInput(ForgotPasswordActivity.this, etEmail);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * method request to server info for signup
     */
    private void sendEmail(LoginDTO loginDTO) {
        showProgressDialog(true);
        signUpPresenterMgr = new SignUpPresenter(this);
        signUpPresenterMgr.requestGetPassword(loginDTO);
    }

    @Override
    public void onSignUpSuccess(LoginDTO loginDTO, UserItemDTO data) {
    }

    @Override
    public void requestEmailSuccess() {
        closeProgressDialog();
        showMessage(StringUtil.getString(R.string.text_send_email_success));
//        goNextScreen(LoginActivity.class, new Bundle());
        onBackPressed();
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        closeProgressDialog();
        switch (errorCode){
            case ErrorCode.INVALID_ACCOUNT:
                showMessage(R.string.text_error_email_not_exits);
                break;
            default:
                handleError(errorCode,error);
        }
    }

}
