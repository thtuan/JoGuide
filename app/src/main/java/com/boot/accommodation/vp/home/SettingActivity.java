package com.boot.accommodation.vp.home;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.dto.view.ChangePasswordDTO;
import com.boot.accommodation.dto.view.SettingViewDTO;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * setting activity
 *
 * @author Vuong-bv
 * @since: 9/8/2016
 */
public class SettingActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener,
        SettingActivityMgr {

    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.llLogout)
    LinearLayout llLogout;
    @Bind(R.id.llNotify)
    LinearLayout llNotify;
    @Bind(R.id.scLocation)
    SwitchCompat scLocation;
    @Bind(R.id.llLanguages)
    LinearLayout llLanguages;
    EditText etOldPass;
    EditText etNewPass;
    EditText etRetype;

    SettingPresenterMgr settingPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        enableSlidingMenu(false);
        checkShowLocationOrNot();
        scLocation.setOnCheckedChangeListener(this);
        settingPresenter = new SettingPresenter(this);
    }

    @OnClick({R.id.ivBack, R.id.llLogout, R.id.llNotify, R.id.llLanguages, R.id.llPassword})
    public void onClick(View v) {
        int action = 0;
        switch (v.getId()) {
            case R.id.ivBack:
                super.onBackPressed();
                break;
            case R.id.llLogout:
                logout();
                break;
            case R.id.llNotify:
                goNextScreen(SettingNotificationActivity.class);
                break;
            case R.id.llLanguages:
                goNextScreen(ChangeLanguageActivity.class);
                break;
            case R.id.llPassword:
                openPopUpPassword();
                break;
        }
    }

    /**
     * Xu li logout
     */
    private void logout() {
        showAlert(StringUtil.getString(R.string.text_confirm_logout), null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                signOutApp();
            }
        }, getString(R.string.text_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }, getString(R.string.text_cancel), true);
    }

    /**
     * Check user want to show their location or not
     */
    private void checkShowLocationOrNot(){
        if (JoCoApplication.getInstance().getSettingLocation()== SettingViewDTO.SEND_LOCATION){
            scLocation.setChecked(true);
        }else {
            scLocation.setChecked(false);
        }
    }

    /**
     * check turn on or off show location with another people
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.scLocation:
                if (!isChecked) {
                    JoCoApplication.getInstance().setSettingLocation(SettingViewDTO.NOT_SEND_LOCATION);
                } else {
                    JoCoApplication.getInstance().setSettingLocation(SettingViewDTO.SEND_LOCATION);
                }
                break;
        }
    }

    /**
     * open popup to change user password
     */
    public void openPopUpPassword() {
        final Context context = this;
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.popup_change_pass_word, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        etOldPass = (EditText) promptsView.findViewById(R.id.etOldPass);
        etNewPass = (EditText) promptsView.findViewById(R.id.etNewPass);
        etRetype = (EditText) promptsView.findViewById(R.id.etRetype);

        // set dialog message
        alertDialogBuilder.setPositiveButton(Utils.getString(R.string.text_ok),null).setNegativeButton(Utils.getString(R
                .string.text_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                    if (checkValidate()) {
                        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
                        changePasswordDTO.setNewPass(etNewPass.getText().toString());
                        changePasswordDTO.setOldPass(etOldPass.getText().toString());
                        settingPresenter.setNewPassword(changePasswordDTO );
                        alertDialog.dismiss();
                    }
                    }
                });
            }
        });

        // show it
        alertDialog.show();
    }

    /**
     * check password validate
     * @return return whether the information is accepted or not
     */
    private boolean checkValidate() {
        if (etOldPass.getText().toString().equals("")) {
            showMessage(Utils.getString(R.string.text_please_fill_your_current_password));
            etOldPass.requestFocus();
            return false;
        }else if (etNewPass.getText().toString().equals("")) {
            showMessage(Utils.getString(R.string.text_please_fill_in_new_password));
            etNewPass.requestFocus();
            return false;
        }else if (!etNewPass.getText().toString().equals(etRetype.getText().toString()) || etRetype.getText().toString().equals("")) {
            showMessage(Utils.getString(R.string.text_retype_password_is_not_match));
            etRetype.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void changePasswordSuccess( Boolean response) {
        if(response){
            showMessage(Utils.getString(R.string.text_change_password_success));
        } else {
            showMessage(Utils.getString(R.string.text_change_password_fail));
        }

    }

    @Override
    public void changePasswordError( Boolean response) {
        showMessage(Utils.getString(R.string.text_change_password_fail));
    }
}
