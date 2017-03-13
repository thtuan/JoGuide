package com.boot.accommodation.vp.profile;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.common.control.JoCoEditText;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.constant.ErrorCode;
import com.boot.accommodation.dto.view.ProfileUserViewDTO;
import com.boot.accommodation.dto.view.UserItemDTO;
import com.boot.accommodation.dto.view.VipMemberConfigDTO;
import com.boot.accommodation.dto.view.VipMemberDTO;
import com.boot.accommodation.dto.view.VipMemberRegistrationDTO;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * VipMemberRegisterActivity
 *
 * @author tuanlt
 * @since 9:55 AM 12/26/16
 */
public class VipMemberRegisterActivity extends BaseActivity implements VipMemberRegisterViewMgr {

    @Bind(R.id.ivPhoto)
    ImageView ivPhoto;
    @Bind(R.id.prLoading)
    ProgressBar prLoading;
    @Bind(R.id.etFirstName)
    JoCoEditText etFirstName;
    @Bind(R.id.etLastName)
    JoCoEditText etLastName;
    @Bind(R.id.etEmail)
    JoCoEditText etEmail;
    @Bind(R.id.etId)
    JoCoEditText etId;
    @Bind(R.id.etIdDate)
    JoCoEditText etIdDate;
    @Bind(R.id.etIdPlace)
    JoCoEditText etIdPlace;
    @Bind(R.id.btRegister)
    Button btRegister;
    @Bind(R.id.wvRegisterInfo)
    WebView wvRegisterInfo;
    private VipMemberRegisterPresenterMgr vipMemberRegisterPresenterMgr; //Vip member register
    private String replaceCode = ""; //Replace code

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_member_register);
        enableSlidingMenu(false);
        ButterKnife.bind(this);
        initData();
    }

    /**
     * Init data
     */
    private void initData() {
        if (vipMemberRegisterPresenterMgr == null) {
            vipMemberRegisterPresenterMgr = new VipMemberRegisterPresenter(this);
        }
        vipMemberRegisterPresenterMgr.getVipMemberConfig();
    }


    @Override
    public void renderVipMemberConfig(VipMemberConfigDTO vipMemberConfigDTO) {
        if (vipMemberConfigDTO != null) {
            ImageUtil.loadImage(ivPhoto, ImageUtil.getImageUrl(vipMemberConfigDTO.getImage()), prLoading);
            if (!StringUtil.isNullOrEmpty(vipMemberConfigDTO.getReplaceCode())) {
                replaceCode = vipMemberConfigDTO.getReplaceCode();
            }
        }
        vipMemberRegisterPresenterMgr.getInfoVipMember();
    }

    @Override
    public void requestRegisterVipMemberSuccess() {
        showMessage(R.string.text_register_vip_member_success);
        vipMemberRegisterPresenterMgr.getInfoVipMember();
    }

    @Override
    public void showMessageErr(int errorCode, String message) {
        switch (errorCode) {
            case ErrorCode.BUS_VIP_MEMBER_ID_NUMBER_ALREADY_REGISTER:
                showMessage(R.string.text_already_register_vip_member);
                break;
            default:
                handleError(errorCode, message);
                break;
        }
    }

    @Override
    public void renderLayout(VipMemberDTO vipMemberDTO) {
        if (vipMemberDTO == null) {
            renderInfo();
            btRegister.setVisibility(View.VISIBLE);
        } else {
            btRegister.setVisibility(View.GONE);
            etFirstName.setEnabled(false);
            etLastName.setEnabled(false);
            etEmail.setEnabled(false);
            etId.setEnabled(false);
            etIdDate.setEnabled(false);
            etIdPlace.setEnabled(false);
            String registrationCode = !StringUtil.isNullOrEmpty(vipMemberDTO.getRegistrationCode()) ? vipMemberDTO
                    .getRegistrationCode() : Constants.STR_BLANK;
            if (!StringUtil.isNullOrEmpty(vipMemberDTO.getDescription())) {
                String description = vipMemberDTO.getDescription().replace(replaceCode, registrationCode);
                wvRegisterInfo.loadDataWithBaseURL(null,
                        StringUtil.getHtmlResizeImage(description), "text/html", "utf-8", null);
            }
            vipMemberRegisterPresenterMgr.getUserInfo(Long.valueOf(JoCoApplication.getInstance().getProfile().getUserData()
                    .getId()));
        }
    }

    @Override
    public void getUserProfileSuccess(ProfileUserViewDTO profileUserViewDTO) {
        UserItemDTO info = JoCoApplication.getInstance().getProfile().getUserData();
        UserItemDTO userItemDTO = profileUserViewDTO.getUser();
        String firstName = !StringUtil.isNullOrEmpty(userItemDTO.getFirstName()) ? userItemDTO.getFirstName() :
                Constants.STR_BLANK;
        String lastName = !StringUtil.isNullOrEmpty(userItemDTO.getLastName()) ? userItemDTO.getLastName() :
                Constants.STR_BLANK;
        info.setFirstName(firstName);
        info.setLastName(lastName);
        info.setFullName(!StringUtil.isNullOrEmpty(firstName) ? firstName + Constants.STR_SPACE + lastName : lastName);
        info.setCmnd(userItemDTO.getCmnd());
        info.setCmndDate(userItemDTO.getCmndDate());
        info.setCmndPlace(userItemDTO.getCmndPlace());
        JoCoApplication.getInstance().getProfile().save();
        renderInfo();
    }

    /**
     * Render info
     */
    private void renderInfo() {
        UserItemDTO userItemDTO = JoCoApplication.getInstance().getProfile().getUserData();
        etFirstName.setText(userItemDTO.getFirstName());
        etLastName.setText(userItemDTO.getLastName());
        etEmail.setText(userItemDTO.getEmail());
        etId.setText(userItemDTO.getCmnd());
        etIdDate.setText(DateUtil.convertDateWithFormat(userItemDTO.getCmndDate(), DateUtil.FORMAT_DATE_SQL,
                DateUtil.FORMAT_DATE));
        etIdPlace.setText(userItemDTO.getCmndPlace());
    }

    @OnClick({R.id.ivBack, R.id.btRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btRegister:
                if (validateInfoRegister()) {
                    VipMemberRegistrationDTO dto = new VipMemberRegistrationDTO();
                    dto.setFirstName(etFirstName.getText().toString());
                    dto.setLastName(etLastName.getText().toString());
                    dto.setEmail(etEmail.getText().toString());
                    dto.setIdNumber(etId.getText().toString());
                    dto.setIdNumberIssueDate(etIdDate.getText().toString());
                    dto.setIdNumberIssuePlace(etIdPlace.getText().toString());
                    vipMemberRegisterPresenterMgr.requestRegisterVipMember(dto);
                }
                break;
        }
    }

    /**
     * Validate info register vip
     * @return
     */
    public boolean validateInfoRegister() {
        boolean check = false;
        if (StringUtil.isNullOrEmpty(etFirstName.getText().toString())) {
            showMessage(R.string.text_validate_firstname);
            etFirstName.setFocusableInTouchMode(true);
            etFirstName.requestFocus();
        } else if (StringUtil.isNullOrEmpty(etLastName.getText().toString())) {
            showMessage(R.string.text_validate_lastname);
            etLastName.setFocusableInTouchMode(true);
            etLastName.requestFocus();
        }else if (StringUtil.isNullOrEmpty(etEmail.getText().toString())) {
            showMessage(R.string.text_validate_lastname);
            etLastName.setFocusableInTouchMode(true);
            etLastName.requestFocus();
        } else if (!etEmail.getText().toString().matches(StringUtil.getString(R.string.regex_format_email))) {
            showMessage(R.string.text_validate_fortmat_email);
            etEmail.setFocusableInTouchMode(true);
            etEmail.requestFocus();
        } else if (StringUtil.isNullOrEmpty(etId.getText().toString())) {
            showMessage(R.string.text_validate_id);
            etId.setFocusableInTouchMode(true);
            etId.requestFocus();
        } else if (StringUtil.isNullOrEmpty(etIdDate.getText().toString())) {
            showMessage(R.string.text_validate_id_date);
            etIdDate.setFocusableInTouchMode(true);
            etIdDate.requestFocus();
        } else if (StringUtil.isNullOrEmpty(DateUtil.formatTime(etIdDate.getText().toString(), DateUtil.FORMAT_DATE))) {
            showMessage(R.string.text_date_wrong_format);
            etIdDate.setFocusableInTouchMode(true);
            etIdDate.requestFocus();
        } else if (StringUtil.isNullOrEmpty(etIdPlace.getText().toString())) {
            showMessage(R.string.text_validate_id_place);
            etIdPlace.setFocusableInTouchMode(true);
            etIdPlace.requestFocus();
        } else {
            check = true;
        }
        return check;
    }
}
