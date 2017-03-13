package com.boot.accommodation.vp.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.common.control.JoCoEditText;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.constant.ErrorCode;
import com.boot.accommodation.dto.view.LanguagesDTO;
import com.boot.accommodation.dto.view.LoginDTO;
import com.boot.accommodation.dto.view.LoginTypeDTO;
import com.boot.accommodation.dto.view.UserItemDTO;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.TraceExceptionUtils;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.accommodation.AccommodationActivity;
import com.boot.accommodation.vp.tour.TourActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements OnClickListener, LoginViewMgr, AdapterView.OnItemSelectedListener {

    // action sign in Google Plus
    private static final int ACTION_SIGN_IN_GOOGLE_PLUS = 1001;
    @Bind(R.id.etEmail)
    JoCoEditText etEmail;
    @Bind(R.id.etPassword)
    JoCoEditText etPassword;
    @Bind(R.id.tvRegister)
    TextView tvRegister;
    @Bind(R.id.spLanguages)
    Spinner spLanguages;
    @Bind(R.id.tvVietNam)
    TextView tvVietNam;
    @Bind(R.id.tvEnglish)
    TextView tvEnglish;
    private AccessTokenTracker accessTokenTracker;
    LoginPresenterMgr loginPresenterMgr;
    AccessToken accessToken;
    String email, pass;
    private boolean isSelectedLanguages = false; // Have choose languagues or no

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mCallbackManagerFacebook = CallbackManager.Factory.create();
        enableSlidingMenu(false);
        setContentView(R.layout.popup_login);
        initData();
        initView();
        setEventDoneLogin();
        reStartLocating();
    }

    /**
     * Init data
     */
    private void initData(){
        Intent intent = getIntent();
        if (intent != null) {
            email = intent.getStringExtra(Constants.IntentExtra.EMAIL);
            pass = intent.getStringExtra(Constants.IntentExtra.PASSWORD);
            etEmail.setText(email);
            etPassword.setText(pass);
        }
        loginPresenterMgr = new LoginPresenter(this);
    }

    /**
     * Init view
     */
    private void initView(){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = this.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = Utils.dip2Pixel(400);
        window.setAttributes(lp);
        LanguagesDTO dtoLang = JoCoApplication.getInstance().loadLanguages();
        TextView tvLanguages = tvVietNam;
        if(StringUtil.getString(R.string.text_language_en).equals(dtoLang.getValue())){
            tvLanguages = tvEnglish;
        }
        SpannableString content = new SpannableString(tvLanguages.getText());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvLanguages.setTypeface(null, Typeface.BOLD);
        tvLanguages.setText(content);
//        ArrayList<LanguagesDTO> listLang = JoCoApplication.getInstance().getListLanguage();
//        SpinnerLanguagesAdapter adapter = new SpinnerLanguagesAdapter(this, android.R.layout.simple_spinner_item, listLang);
//        spLanguages.setAdapter(adapter);
//        spLanguages.setOnItemSelectedListener(this);
//        // Load previous languague
//        LanguagesDTO dtoLang = JoCoApplication.getInstance().loadLanguages();
//        if (dtoLang != null) {
//            for (int i = 0; i < spLanguages.getAdapter().getCount(); i++) {
//                final LanguagesDTO temp = (LanguagesDTO) spLanguages.getAdapter().getItem(i);
//                if (temp.getValue().equals(dtoLang.getValue())) {
//                    spLanguages.setSelection(i);
//                    tvLanguages.setText(dtoLang.getName());
//                    isSelectedLanguages = true;
//                    break;
//                }
//            }
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        closeProgressDialog();
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_SIGN_IN_GOOGLE_PLUS) {
            loginPresenterMgr.getProfileGooglePlus(mGoogleApiClient, data);
        } else {
            loginPresenterMgr.callFBActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        Log.d("Login", "Login start");
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    public void connectFBSuccess(LoginResult loginResult) {
        showProgressDialog(false);
        Log.e(logTag(), "Facebook connect success.");
        accessToken = loginResult.getAccessToken();
        loginPresenterMgr.requestProfileFacebook(accessToken);
    }

    @Override
    public void connectFBCancel() {
        Log.e(logTag(), "Facebook connect cancel.");
    }

    @Override
    public void connectFBError() {
        Log.e(logTag(), "Facebook connect failure.");
    }

    @Override
    public void requestProfileFBSuccess(AccessToken accessToken, JSONObject object) {
        try {
            showProgressDialog(false);
            accessToken = AccessToken.getCurrentAccessToken();
            loginPresenterMgr.getFacebookProfile(accessToken.getToken(), object);
        } catch (JSONException ex) {
            Log.w(logTag(), TraceExceptionUtils.getReportFromThrowable(ex));
        }
    }

    /**
     * Go to home
     */
    public void goToHome() {
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
        goNextScreen(AccommodationActivity.class, true);
        closeProgressDialog();
    }

    /**
     * Go to tour
     */
    public void goToTour(long tourId, long tourPlanId) {
//        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
//        Intent intent = new Intent(this, TourActivity.class);
//       intent.set
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.IntentExtra.TOUR_ID, tourId);
        bundle.putLong(Constants.IntentExtra.TOUR_PLAN_ID, tourPlanId);
        goNextScreen(TourActivity.class, bundle);
//        startActivity(intent);
//        finishAffinity();
        closeProgressDialog();
    }


    @OnClick({R.id.tvForgotPassword, R.id.btLogin, R.id.ivFacebook, R.id.ivGoogle, R.id.llLogin, R.id.tvRegister, R
        .id.tvVietNam, R.id.tvEnglish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvForgotPassword:
                goNextScreen(ForgotPasswordActivity.class, new Bundle());
                Utils.hideSoftKeyboard(this);
                break;
            case R.id.btLogin:
                checkLogin();
                Utils.hideSoftKeyboard(this);
                break;
            case R.id.ivFacebook:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","public_profile"));
                loginPresenterMgr.initFacebook(this);
                Utils.hideSoftKeyboard(this);
                break;
            case R.id.tvRegister:
                goNextScreen(SignupActivity.class, new Bundle());
                Utils.hideSoftKeyboard(this);
                break;
            case R.id.ivGoogle:
                showProgressDialog(false);
                if (mGoogleApiClient.isConnected()) {
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, ACTION_SIGN_IN_GOOGLE_PLUS);
                }
                Utils.hideSoftKeyboard(this);
                break;
            case R.id.llLogin:
                Utils.hideSoftKeyboard(this);
                break;
            case R.id.tvVietNam: {
                JoCoApplication.getInstance().saveLanguages(StringUtil.getString(R.string.text_language_vi));
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                break;
            }
            case R.id.tvEnglish: {
                JoCoApplication.getInstance().saveLanguages(StringUtil.getString(R.string.text_language_en));
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                break;
            }

        }
    }

    /**
     * Validate login
     */
    private boolean checkLogin() {
        boolean check = false;
        if (StringUtil.isNullOrEmpty(etEmail.getText().toString().trim())) {
            showMessage(StringUtil.getString(R.string.text_validate_email_phone));
            etEmail.setFocusableInTouchMode(true);
            etEmail.requestFocus();
        } else if (!etEmail.getText().toString().trim().matches(StringUtil.getString(R.string.regex_format_email)) && !etEmail
            .getText().toString().trim().matches(StringUtil.getString(R.string.regex_format_phone_number))) {
            showMessage(StringUtil.getString(R.string.text_wrong_email_or_phone));
            etEmail.setFocusableInTouchMode(true);
            etEmail.requestFocus();
        } else if (StringUtil.isNullOrEmpty(etPassword.getText().toString())) {
            showMessage(StringUtil.getString(R.string.text_validate_pass));
            etPassword.setFocusableInTouchMode(true);
            etPassword.requestFocus();
        }else {
            showProgressDialog(false);
            LoginDTO loginDTO = new LoginDTO();
            if(etEmail.getText().toString().trim().matches(StringUtil.getString(R.string.regex_format_email))) {
                loginDTO.setEmail(etEmail.getText().toString().trim());
            }else{
                loginDTO.setPhone(etEmail.getText().toString().trim());
            }
            loginDTO.setPassword(etPassword.getText().toString());
//            loginDTO.setEmail(etEmail.getText().toString());
            loginDTO.setLoginType(LoginTypeDTO.NORMAL.getValue());
            loginPresenterMgr.requestLogin(loginDTO);
            check = true;
        }
        return check;
    }

    @Override
    public void showMessageErr( int errorCode, String message ) {
        closeProgressDialog();
        switch (errorCode) {
            case ErrorCode.LOGIN_FAIL_USERNAME_PASSWORD_MISMATCH:
                showMessage(getString(R.string.text_login_fail));
                break;
            default:
                handleError(errorCode, message);
                break;
        }
    }

    @Override
    public void requestLogin( LoginDTO loginDTO ) {
        showProgressDialog(false);
        loginPresenterMgr.requestLogin(loginDTO);
    }

    /**
     * check login when user click done at soft key
     */
    private void setEventDoneLogin() {

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (checkLogin()){
                        Utils.hideKeyboardInput(LoginActivity.this, etPassword);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onItemSelected( AdapterView<?> parent, View view, int position, long id ) {
        if(!isSelectedLanguages){
            LanguagesDTO lang =(LanguagesDTO) parent.getItemAtPosition(position);
            JoCoApplication.getInstance().saveLanguages(lang.getValue());
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        } else {
            isSelectedLanguages = false;
        }
    }

    @Override
    public void onNothingSelected( AdapterView<?> parent ) {

    }

    @Override
    public void handleLoginSuccess( final LoginDTO loginDTO, final UserItemDTO userDTO ) {
//        final AppVersionDTO appVersionDTO = userDTO.getAppVersion();
//        if (appVersionDTO != null && appVersionDTO.getVersion() != null && JoCoApplication.getInstance().getAppVersion()
//            .compareTo(appVersionDTO.getVersion().trim()) < 0) {
//            if (AppVersionDTO.TYPE_FORCE == appVersionDTO.getForce()) {
//                showAlert(appVersionDTO.getDescription(), StringUtil.getString(R.string.text_title_update_version), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick( DialogInterface dialog, int which ) {
//                            closeProgressDialog();
//                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appVersionDTO.getLink()));
//                            startActivity(browserIntent);
//                        }
//                    },
//                    StringUtil.getString(R.string.text_ok), false);
//            }else{
//                showAlert(appVersionDTO.getDescription(), StringUtil.getString(R.string.text_title_update_version), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick( DialogInterface dialog, int which ) {
//                            closeProgressDialog();
//                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appVersionDTO.getLink()));
//                            startActivity(browserIntent);
//                        }
//                    },
//                    StringUtil.getString(R.string.text_ok), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick( DialogInterface dialog, int which ) {
//                            saveUserInfo(loginDTO, userDTO);
//                        }
//                    }, StringUtil.getString(R.string.text_cancel), true);
//            }
//        } else {
//            saveUserInfo(loginDTO, userDTO);
//        }
        closeProgressDialog();
        saveUserInfo(loginDTO, userDTO);
        sendBroadcast(Constants.ActionEvent.NOTIFY_REFRESH, new Bundle());
        finish();
//        checkAppVersion();
    }

    @Override
    protected void handleContinuousLogin() {
//        if (PreferenceUtils.getLong(Constants.Preference.PREFERENCE_DEEP_LINK_TOUR_ID, 0) > 0) {
//            long tourId = PreferenceUtils.getLong(Constants.Preference.PREFERENCE_DEEP_LINK_TOUR_ID, 0);
//            long tourPlanId = PreferenceUtils.getLong(Constants.Preference.PREFERENCE_DEEP_LINK_TOUR_PLAN_ID, 0);
//            PreferenceUtils.saveLong(Constants.Preference.PREFERENCE_DEEP_LINK_TOUR_ID, 0);
//            PreferenceUtils.saveLong(Constants.Preference.PREFERENCE_DEEP_LINK_TOUR_PLAN_ID, 0);
//            goToTour(tourId, tourPlanId);
//        }else {
//            goToHome();
//        }
        finish();
    }

    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action){
            case Constants.ActionEvent.ACTION_REGISTER_SUCCESS:
                finish();
                break;
            default:
                super.receiveBroadcast(action, extras);
        }
    }
}