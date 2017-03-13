package com.boot.accommodation.vp.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.common.control.JoCoEditText;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.constant.ErrorCode;
import com.boot.accommodation.dto.view.LoginDTO;
import com.boot.accommodation.dto.view.LoginTypeDTO;
import com.boot.accommodation.dto.view.UserItemDTO;
import com.boot.accommodation.util.PreferenceUtils;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.accommodation.AccommodationActivity;
import com.boot.accommodation.vp.tour.TourActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * activity for sign up
 *
 * @author Vuong-bv
 * @since: 6/24/2016
 */
public class SignupActivity extends BaseActivity implements SignUpViewMgr {

    @Bind(R.id.etEmail)
    JoCoEditText etEmail;
    @Bind(R.id.etFisrtName)
    JoCoEditText etFisrtName;
    @Bind(R.id.etLastName)
    JoCoEditText etLastName;
    @Bind(R.id.etPassword)
    JoCoEditText etPassword;
    @Bind(R.id.etConfirmPassword)
    JoCoEditText etConfirmPassword;
    @Bind(R.id.btSignUp)
    Button btSignUp;
    SignUpPresenterMgr signUpPresenterMgr;
    String emailOrPhone, pass, repass, firstName, lastName;// data input

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        enableSlidingMenu(false);
        signUpPresenterMgr = new SignUpPresenter(this);
        setEventDoneSignup();
    }

    @OnClick({R.id.btSignUp, R.id.ivBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSignUp:
                emailOrPhone = String.valueOf(etEmail.getText()).trim();
                pass = String.valueOf(etPassword.getText());
                repass = String.valueOf(etConfirmPassword.getText());
                firstName = String.valueOf(etFisrtName.getText()).trim();
                lastName = String.valueOf(etLastName.getText()).trim();

                if (!checkValidate(emailOrPhone, pass, repass, firstName, lastName)){
                    requestSignUp();
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
     * @param email  email
     * @param pass   password
     * @param rePass rePassword
     */
    private boolean checkValidate(String email, String pass, String rePass, String firstName, String lastName) {
        boolean check = false;
        if (StringUtil.isNullOrEmpty(firstName) ) {
            showMessage(StringUtil.getString(R.string.text_validate_firstname));
            etFisrtName.setFocusableInTouchMode(true);
            etFisrtName.requestFocus();
            check = true;
        } else if (StringUtil.isNullOrEmpty(lastName) ) {
            showMessage(StringUtil.getString(R.string.text_validate_lastname));
            etLastName.setFocusableInTouchMode(true);
            etLastName.requestFocus();
            check = true;
        } else if (StringUtil.isNullOrEmpty(email) ) {
            showMessage(StringUtil.getString(R.string.text_validate_email_phone));
            etEmail.setFocusableInTouchMode(true);
            etEmail.requestFocus();
            check = true;
        } else if (!email.matches(StringUtil.getString(R.string.regex_format_email)) && !email.matches(StringUtil
            .getString(R.string.regex_format_phone_number))){
            showMessage(StringUtil.getString(R.string.text_wrong_email_or_phone));
            etEmail.setFocusableInTouchMode(true);
            etEmail.requestFocus();
            check = true;
        } else if (StringUtil.isNullOrEmpty(pass)){
            showMessage(StringUtil.getString(R.string.text_validate_pass));
            etPassword.setFocusableInTouchMode(true);
            etPassword.requestFocus();
            check = true;
        }  else if (!pass.equals(rePass)){
            showMessage(StringUtil.getString(R.string.text_validate_confirm_password));
            etConfirmPassword.setFocusableInTouchMode(true);
            etConfirmPassword.requestFocus();
            check = true;
        }
        return  check;
    }


    /**
     * check signup when user click done at soft key
     */
    private void setEventDoneSignup() {
        etConfirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    emailOrPhone = String.valueOf(etEmail.getText()).trim();
                    pass = String.valueOf(etPassword.getText());
                    repass = String.valueOf(etConfirmPassword.getText());
                    firstName = String.valueOf(etFisrtName.getText()).trim();
                    lastName = String.valueOf(etLastName.getText()).trim();

                    if (!checkValidate(emailOrPhone, pass, repass, firstName, lastName)) {
                        requestSignUp();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Request signup
     */
    void requestSignUp(){
        showProgressDialog(false);
        LoginDTO dto = new LoginDTO();
        //dto.setUserName(email);
        if(emailOrPhone.matches(StringUtil.getString(R.string.regex_format_email))) {
            dto.setEmail(emailOrPhone);
        }else{
            dto.setPhone(emailOrPhone);
        }
        dto.setPassword(pass);
        dto.setLoginType(LoginTypeDTO.NORMAL.getValue());
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        Utils.hideKeyboardInput(SignupActivity.this, etConfirmPassword);
        signUpPresenterMgr.requestInfoSignUp(dto);
    }

    @Override
    public void onSignUpSuccess(LoginDTO loginDTO, UserItemDTO data) {
//        goNextScreen(HomeActivity.class);
        showMessage(StringUtil.getString(R.string.text_message_signup_success));
        closeProgressDialog();
        saveUserInfo(loginDTO, data);
        sendBroadcast(Constants.ActionEvent.ACTION_REGISTER_SUCCESS, new Bundle());
        finish();
//        checkAppVersion();
    }

    @Override
    public void requestEmailSuccess() {

    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        closeProgressDialog();
        switch (errorCode){
            case ErrorCode.USER_EXIST_TRY_LOGIN_SUCCESS:
            case ErrorCode.USER_EXIST_TRY_LOGIN_FAIL:
                showMessage(R.string.text_email_or_phone_existed);
                break;
            default:
                handleError(errorCode,error);
        }
    }

    /**
     * Start home screen
     */
    private void startHomeScreen() {
        goNextScreen(AccommodationActivity.class, true);
        closeProgressDialog();
    }

    /**
     * Start tour screen
     */
    private void startTourScreen() {
        Intent intent = new Intent(this, TourActivity.class);
        intent.setAction("com.joco.app.VIEW");
        intent.setData(Uri.parse(PreferenceUtils.getString(Constants.Preference.PREFERENCE_URI,"http://joco.com" +
                ".vn/tour/0/plan/0")));
        startActivity(intent);
        finishAffinity();
        closeProgressDialog();
    }

    @Override
    protected void handleContinuousLogin() {
//        if (PreferenceUtils.getString(Constants.Preference.PREFERENCE_FROM,"joco").equals("http")){
//            PreferenceUtils.saveString(Constants.Preference.PREFERENCE_FROM, "joco");
//            startTourScreen();
//        }else {
//        }
        startHomeScreen();
    }
}
