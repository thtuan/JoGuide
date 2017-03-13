package com.boot.accommodation.vp.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.common.control.JoCoEditText;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.constant.ErrorCode;
import com.boot.accommodation.dto.view.ChangePasswordDTO;
import com.boot.accommodation.dto.view.FollowItemDTO;
import com.boot.accommodation.dto.view.ProfileUserViewDTO;
import com.boot.accommodation.dto.view.StatusTypeDTO;
import com.boot.accommodation.dto.view.TouristDTO;
import com.boot.accommodation.dto.view.UserItemDTO;
import com.boot.accommodation.dto.view.VipMemberDTO;
import com.boot.accommodation.util.ExpandCollapseUtil;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.tour.ProfileActivity;
import com.boot.accommodation.vp.tour.ProfileUserPresenter;
import com.boot.accommodation.vp.tour.ProfileUserPresenterMgr;
import com.boot.accommodation.vp.tour.ProfileUserViewMgr;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Mo ta class
 *
 * @author Vuong-bv
 * @since: 6/4/2016
 */
public class ProfileUserFragment extends BaseFragment implements ProfileUserViewMgr {

    public static final int ACTION_EDIT_INFO = 1002;//  action for even click

    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvFullName)
    TextView tvFullName;
    @Bind(R.id.tvFullNameValue)
    JoCoEditText tvFullNameValue;
    @Bind(R.id.tvEmail)
    TextView tvEmail;
    @Bind(R.id.tvEmailValue)
    JoCoEditText tvEmailValue;
    @Bind(R.id.tvPhone)
    TextView tvPhone;
    @Bind(R.id.ivTextMesssage)
    ImageView ivTextMesssage;
    @Bind(R.id.ivCallPhone)
    ImageView ivCallPhone;
    @Bind(R.id.llCall)
    LinearLayout llCall;
    @Bind(R.id.tvPhoneValue)
    JoCoEditText tvPhoneValue;
    @Bind(R.id.tvDescription)
    TextView tvDescription;
    @Bind(R.id.btRegister)
    Button btRegister;
    @Bind(R.id.llVipMemberDescription)
    LinearLayout llVipMemberDescription;
    @Bind(R.id.etSpecial)
    JoCoEditText etSpecial;
    @Bind(R.id.etDescription)
    JoCoEditText etDescription;
    @Bind(R.id.ivAcrossSpecial)
    ImageView ivAcrossSpecial;
    @Bind(R.id.ivAcrossIntroduce)
    ImageView ivAcrossIntroduce;
    @Bind(R.id.rlPhone)
    RelativeLayout rlPhone;
    private EditText etOldPass;
    private EditText etNewPass;
    private EditText etRetype;
    private boolean isFriend = false;
    private String nameCurrent, emailCurrent, phoneCurrent, specialCurrent, descriptionCurrent;//info current of user
    private TouristDTO touristDTO = new TouristDTO();
    private ProfileUserPresenterMgr profileUserPresenterMgr;
    private ExpandCollapseUtil exSpecial;//Expand collapse special
    private ExpandCollapseUtil exIntroduce;//Expand collapse description

    public static ProfileUserFragment newInstance(Bundle data) {
        ProfileUserFragment fragment = new ProfileUserFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            touristDTO = args.getParcelable(Constants.IntentExtra.TOURIST_DTO);
        }
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_profile_info;
    }

    /**
     * Khoi tao data
     */
    private void initData() {
        profileUserPresenterMgr = new ProfileUserPresenter(this);
        profileUserPresenterMgr.getProfileUser(touristDTO.getId());
    }

    /**
     * Initview
     */
    private void initView() {
        enableData(false);
//        if (Long.valueOf(JoCoApplication.getInstance().getProfile().getUserData().getId()) == touristDTO.getId()) {
//            llVipMemberDescription.setVisibility(View.VISIBLE);
//        } else {
//            llVipMemberDescription.setVisibility(View.GONE);
//        }
        if (UserItemDTO.TYPE_TOURIST != touristDTO.getUserType()) {
            rlPhone.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showProgress();
        initData();
        initView();
        setEventDoneLogin();
    }

    @Override
    public void renderInnitLayout(ProfileUserViewDTO data) {
        nameCurrent = data.getUser().getFullName();
        phoneCurrent = data.getUser().getPhone();
        emailCurrent = data.getUser().getEmail();
        descriptionCurrent = data.getUser().getSelfIntroduce();
//        specialCurrent = data.getUser().getSpeciality();
        closeProgress();
        tvFullNameValue.setText(data.getUser().getFullName());
        tvEmailValue.setText(data.getUser().getEmail());
        tvPhoneValue.setText(data.getUser().getPhone());
//        if (StringUtil.isNullOrEmpty(data.getUser().getSpeciality())) {
//            etSpecial.setVisibility(View.GONE);
//        } else {
//            etSpecial.setVisibility(View.VISIBLE);
//            etSpecial.setText(data.getUser().getSpeciality());
//        }
//        if (StringUtil.isNullOrEmpty(data.getUser().getSelfIntroduce())) {
//            etDescription.setVisibility(View.GONE);
//        } else {
//            etDescription.setVisibility(View.VISIBLE);
//            etDescription.setText(data.getUser().getSelfIntroduce());
//        }
        // set full name of user at activity
        if (mActivity instanceof ProfileActivity) {
            ((ProfileActivity) mActivity).updateInfo(String.valueOf(data.getUser().getFullName()), false);
        }
        stopRefresh();
    }

    @OnClick({R.id.ivCallPhone, R.id.ivTextMesssage, R.id.btRegister, R.id.ivAcrossIntroduce, R.id.ivAcrossSpecial, R
            .id.btChangePass})
    public void OnClick(View v) {
        int option = v.getId();
        Bundle bundle = new Bundle();
        switch (option) {
            case R.id.ivCallPhone:
                if (!StringUtil.isNullOrEmpty(String.valueOf(tvPhoneValue.getText()))) {
                    callPhoneTourist(String.valueOf(tvPhoneValue.getText()));
                }
                break;
            case R.id.ivTextMesssage:
                if (!StringUtil.isNullOrEmpty(String.valueOf(tvPhoneValue.getText()))) {
                    textMessage(String.valueOf(tvPhoneValue.getText()));
                }
                break;
            case R.id.btRegister:
                mActivity.goNextScreen(VipMemberRegisterActivity.class, bundle);
                break;
            case R.id.ivAcrossIntroduce:
                if (exIntroduce == null) {
                    exIntroduce = new ExpandCollapseUtil();
                }
                exIntroduce.animationView(ivAcrossIntroduce, etDescription);
                break;
            case R.id.ivAcrossSpecial:
                if (exSpecial == null) {
                    exSpecial = new ExpandCollapseUtil();
                }
                exSpecial.animationView(ivAcrossSpecial, etSpecial);
                break;
            case R.id.btChangePass:
                openPopUpPassword();
                break;
        }
    }


    @Override
    public void showMessage(String message) {
        mActivity.showMessage(message);
        closeProgress();
    }

    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action) {
            case Constants.ActionEvent.NOTIFY_REFRESH:
                profileUserPresenterMgr.getProfileUser(touristDTO.getId());
                profileUserPresenterMgr.getInfoVipMember();
                break;
        }
    }

    @Override
    public void onEventControl(int action, Object item, final View View, final int position) {
        switch (action) {
            case ACTION_EDIT_INFO:
                enableData(true);
                break;
        }
    }

    /**
     * save when user click done in softkey
     */
    private void setEventDoneLogin() {
        tvPhoneValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (mActivity instanceof ProfileActivity) {
                        ((ProfileActivity) getActivity()).saveInfo();
                    }
                    Utils.hideKeyboardInput(mActivity, tvPhoneValue);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * call phone
     *
     * @param dialNumber
     */
    public void callPhoneTourist(String dialNumber) {
        try {
            Intent callIntent = new Intent();
            callIntent.setAction(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + dialNumber));
            startActivity(callIntent);
        } catch (Exception e) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + dialNumber));
            startActivity(callIntent);
        }
    }

    /**
     * send message
     *
     * @param dialNumber
     */
    public void textMessage(String dialNumber) {
        Uri uri = Uri.parse("smsto:" + dialNumber);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("", "");
        startActivity(it);
    }


    /**
     * Xu li edit info
     */
    public void editUserInfo() {
        enableData(true);
    }

    /**
     * method disable focus to edittext when user edit success
     */
    public void editUserInfoSuccess() {
        tvFullNameValue.setText(String.valueOf(tvFullNameValue.getText()));
        enableData(false);
        Toast.makeText(mActivity, getString(R.string.text_edit_profile_success), Toast.LENGTH_SHORT).show();
        if (mActivity instanceof ProfileActivity) {
            ((ProfileActivity) mActivity).updateInfo(String.valueOf(tvFullNameValue.getText()), true);
        }
        tvEmailValue.setText(String.valueOf(tvEmailValue.getText()));
        tvPhoneValue.setText(String.valueOf(tvPhoneValue.getText()));
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        closeProgress();
        mActivity.closeProgressDialog();
        switch (errorCode) {
            case ErrorCode.USER_EXIST_TRY_LOGIN_FAIL:
                mActivity.showMessage(R.string.text_email_or_phone_existed);
                break;
            default:
                handleError(errorCode, error);
        }
    }

    /**
     * Save user info
     */
    public void requestEditUserInfo() {
        String nameInput = String.valueOf(tvFullNameValue.getText()).trim();
        String emailInput = String.valueOf(tvEmailValue.getText()).trim();
        String phoneInput = String.valueOf(tvPhoneValue.getText()).trim();
        String description = String.valueOf(etDescription.getText()).trim();
        String special = String.valueOf(etSpecial.getText()).trim();
        if (checkIsEdit(nameInput, emailInput, phoneInput, special, description)) {
            if (validate(nameInput, phoneInput, emailInput)) {
                UserItemDTO dto = new UserItemDTO();
                dto.setId("" + touristDTO.getId());
                if (!StringUtil.isNullOrEmpty(nameInput)) {
                    String[] lstName = nameInput.split(Constants.STR_SPACE);
                    dto.setFirstName(lstName[0]);
                    dto.setLastName(nameInput.substring(dto.getFirstName().length()).trim());
                }
                dto.setEmail(emailInput);
                dto.setPhone(phoneInput);
                dto.setSelfIntroduce(description);
                dto.setSpeciality(special);
                Utils.hideKeyboardInput(mActivity, tvPhoneValue);
                profileUserPresenterMgr.requestEditProfile(dto);
            }
        } else {
            if (mActivity instanceof ProfileActivity) {
                enableData(false);
                ((ProfileActivity) getActivity()).saveNotChange();
            }
        }
    }

    /**
     * method chech user had change their info or not
     *
     * @param nameInput
     * @param mailInput
     * @param phoneInput
     * @return
     */
    public boolean checkIsEdit(String nameInput, String mailInput, String phoneInput, String special, String
            description) {
        boolean check = false;
        if (StringUtil.isNullOrEmpty(nameInput) || !nameInput.equals(nameCurrent)) {
            check = true;
        } else if (StringUtil.isNullOrEmpty(mailInput) || !mailInput.equals(emailCurrent)) {
            check = true;
        } else if (StringUtil.isNullOrEmpty(phoneInput) || !phoneInput.equals(phoneCurrent)) {
            check = true;
        } else if (StringUtil.isNullOrEmpty(special) || !special.equals(specialCurrent)) {
            check = true;
        } else if (StringUtil.isNullOrEmpty(description) || !description.equals(descriptionCurrent)) {
            check = true;
        }
        return check;
    }

    /**
     * validate data input
     *
     * @param name
     * @param phone
     * @param email
     * @return
     */
    public Boolean validate(String name, String phone, String email) {
        boolean check = false;
        if (StringUtil.isNullOrEmpty(name)) {
            Toast.makeText(getActivity(), getString(R.string.text_validate_name), Toast.LENGTH_LONG).show();
            tvFullNameValue.setFocusableInTouchMode(true);
            tvFullNameValue.requestFocus();
        } else if (StringUtil.isNullOrEmpty(email) && !StringUtil.isNullOrEmpty(JoCoApplication.getInstance().getProfile()
                .getUserData().getEmail())) {
            Toast.makeText(getActivity(), getString(R.string.text_validate_email_phone), Toast.LENGTH_LONG).show();
            tvEmailValue.setFocusableInTouchMode(true);
            tvEmailValue.requestFocus();
        } else if (!email.matches(StringUtil.getString(R.string.regex_format_email))) {
            Toast.makeText(getActivity(), getString(R.string.text_validate_fortmat_email), Toast.LENGTH_LONG).show();
            tvEmailValue.setFocusableInTouchMode(true);
            tvEmailValue.requestFocus();
        } else if (StringUtil.isNullOrEmpty(phone) && !StringUtil.isNullOrEmpty(JoCoApplication.getInstance()
                .getProfile().getUserData().getPhone())) {
            Toast.makeText(getActivity(), getString(R.string.text_validate_phone), Toast.LENGTH_LONG).show();
            tvPhoneValue.setFocusableInTouchMode(true);
            tvPhoneValue.requestFocus();
        } else if (!phone.matches(StringUtil.getString(R.string.regex_format_phone_number))) {
            Toast.makeText(getActivity(), getString(R.string.text_validate_phone_format), Toast.LENGTH_LONG).show();
            tvPhoneValue.setFocusableInTouchMode(true);
            tvPhoneValue.requestFocus();
        } else {
            check = true;
        }
        return check;
    }

    /**
     * Handle user follow
     *
     * @param userId
     * @param followerUserId
     */
    public void handleFollow(long userId, long followerUserId, FollowItemDTO dto) {
        showProgress();
        profileUserPresenterMgr.requestFollow(userId, followerUserId, dto);
    }

    @Override
    public void requestFollowSuccess(boolean check) {
        //check if user follow another user at their profile or list follow, at list handle here, at their profile go
        // to ProfileActivity to handle
        if (check) {
            //check un or add fiend
            if (isFriend == true) {
                showMessage(StringUtil.getString(R.string.text_un_friend_success));
            } else {
                showMessage(StringUtil.getString(R.string.text_add_friend_success));
            }
            closeProgress();
        } else {
            if (mActivity instanceof ProfileActivity) {
                closeProgress();
            }
        }
    }

    @Override
    public void renderVipMemberInfo(VipMemberDTO vipMemberDTO) {
        boolean isShowVipMember = false;
        if (vipMemberDTO == null) {
            tvDescription.setText(StringUtil.getString(R.string.text_description_user_normal));
            btRegister.setText(StringUtil.getString(R.string.text_register_vip_member));
        } else if (StatusTypeDTO.NEW.getValue() == vipMemberDTO.getStatus()) {
            tvDescription.setText(StringUtil.getString(R.string.text_description_user_vip));
            btRegister.setText(StringUtil.getString(R.string.text_active));
        } else {
            tvDescription.setVisibility(View.GONE);
            btRegister.setVisibility(View.GONE);
            isShowVipMember = true;
        }
//        ((ProfileActivity) mActivity).showVipMember(isShowVipMember);
    }

    @Override
    public void renderPasswordSuccess() {
        mActivity.closeProgressDialog();
        showMessage(Utils.getString(R.string.text_change_password_success));
    }

    /**
     * Enable data
     *
     * @param isEnable
     */
    private void enableData(boolean isEnable) {
        tvFullNameValue.setImageClearVisibile(R.drawable.ic_clear_grey, isEnable);
        tvEmailValue.setImageClearVisibile(R.drawable.ic_clear_grey, isEnable);
        tvPhoneValue.setImageClearVisibile(R.drawable.ic_clear_grey, isEnable);
        tvFullNameValue.setEnabled(isEnable);
        tvEmailValue.setEnabled(isEnable);
        tvEmailValue.setEnabled(isEnable);
        etSpecial.setEnabled(isEnable);
        etDescription.setEnabled(isEnable);
        if (isEnable) {
//            etSpecial.setBackground(Utils.getDrawable(R.drawable.border_edittext));
//            etSpecial.setVisibility(View.VISIBLE);
//            etDescription.setBackground(Utils.getDrawable(R.drawable.border_edittext));
//            etDescription.setVisibility(View.VISIBLE);
            tvPhoneValue.setFocusableInTouchMode(true);
            tvPhoneValue.requestFocus();
            tvEmailValue.setFocusableInTouchMode(true);
            tvEmailValue.requestFocus();
            tvFullNameValue.setFocusableInTouchMode(true);
            tvFullNameValue.requestFocus();
        } else {
            etSpecial.setBackgroundColor(Utils.getColor(R.color.white));
            etDescription.setBackgroundColor(Utils.getColor(R.color.white));
        }
    }

    /**
     * open popup to change user password
     */
    public void openPopUpPassword() {
        LayoutInflater li = LayoutInflater.from(mActivity);
        View promptsView = li.inflate(R.layout.popup_change_pass_word, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
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
                            mActivity.showProgressDialog(true);
                            ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
                            changePasswordDTO.setNewPass(etNewPass.getText().toString());
                            changePasswordDTO.setOldPass(etOldPass.getText().toString());
                            profileUserPresenterMgr.requestChangePassword(changePasswordDTO );
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
}
