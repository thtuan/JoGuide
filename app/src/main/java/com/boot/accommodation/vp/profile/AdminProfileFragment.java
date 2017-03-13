package com.boot.accommodation.vp.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.common.control.JoCoEditText;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.ProfileUserViewDTO;
import com.boot.accommodation.dto.view.UserItemDTO;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.vp.tour.ProfileActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Admin profile fragment
 *
 * @author Vuong-bv
 * @since: 13/07/2016
 */
public class AdminProfileFragment extends BaseFragment implements AdminProfileViewMgr {

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
    @Bind(R.id.tvPhoneValue)
    JoCoEditText tvPhoneValue;
    @Bind(R.id.llCall)
    LinearLayout llCall;
    @Bind(R.id.tvWebsite)
    TextView tvWebsite;
    @Bind(R.id.tvWebsiteValue)
    JoCoEditText tvWebsiteValue;
    @Bind(R.id.tvAboutWho)
    TextView tvAboutWho;
    @Bind(R.id.tvAboutAdmin)
    TextView tvAboutAdmin;
    @Bind(R.id.llListFolow)
    LinearLayout llListFolow;
    private AdminProfilePresenterMgr adminProfilePresenterMgr;//presenter mgr
    private long id;//id of admin

    public static AdminProfileFragment newInstance() {
        return new AdminProfileFragment();
    }

    public static Fragment newInstance(Bundle data) {
        AdminProfileFragment fragment = new AdminProfileFragment();
        fragment.setArguments(data);
        return fragment;
    }


    @Override
    public int contentViewLayout() {
        return R.layout.fragment_admin_profile;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            id = args.getLong(Constants.IntentExtra.TOURIST_ID);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tvFullNameValue.setImageClearVisibile(false);
        tvEmailValue.setImageClearVisibile(false);
        tvPhoneValue.setImageClearVisibile(false);
        tvWebsiteValue.setImageClearVisibile(false);
        showProgress();
        initData();
        enableRefresh(false);
    }

    /**
     * data innit
     */
    private void initData() {
        adminProfilePresenterMgr = new AdminProfilePresenter(this);
        adminProfilePresenterMgr.getAdminProfile(id);
    }

    @Override
    public void renderLayout(ProfileUserViewDTO profileUserViewDTO) {
        UserItemDTO adminProfileDTO = profileUserViewDTO.getUser();
        tvFullNameValue.setText(adminProfileDTO.getFullName());
        tvEmailValue.setText(adminProfileDTO.getEmail());
        tvPhoneValue.setText(adminProfileDTO.getPhone());
        tvWebsiteValue.setText(adminProfileDTO.getWebsite());
        if (!StringUtil.isNullOrEmpty(adminProfileDTO.getName())) {
            tvAboutWho.setText(StringUtil.getString(R.string.text_about) + Constants.STR_SPACE + adminProfileDTO.getName());
        }
        tvAboutAdmin.setText(adminProfileDTO.getDescription());
        //check is user login or another user go to this profile :set icon add or un friend , when user login go to admin profile
        boolean isMyprofile = false;
        if (id == Long.valueOf(JoCoApplication.getInstance().getProfile().getUserData().getId())) {
            isMyprofile = true;
        }
        if (mActivity instanceof ProfileActivity) {
            ((ProfileActivity) mActivity).updateIcon(adminProfileDTO.getIsFriend(), isMyprofile);
            ((ProfileActivity) mActivity).updateInfo(adminProfileDTO.getName(), false);
        }
        closeProgress();
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        closeProgress();
        switch (errorCode) {
            default:
                handleError(errorCode, error);
        }
    }

    @Override
    public void requestFollowSuccess() {
        closeProgress();
    }


    @OnClick({R.id.ivCallPhone, R.id.ivTextMesssage})
    public void OnClick(View v) {
        int oftion = v.getId();
        switch (oftion) {
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
        }
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
     * handle follow admin
     *
     * @param id
     * @param idUserFollow
     */
    public void handleFollow(long id, long idUserFollow) {
        adminProfilePresenterMgr.followAdmin(id, idUserFollow);
    }

}
