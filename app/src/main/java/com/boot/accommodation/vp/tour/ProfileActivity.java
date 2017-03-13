package com.boot.accommodation.vp.tour;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dialog.FullImageDialog;
import com.boot.accommodation.dto.view.TouristDTO;
import com.boot.accommodation.dto.view.UserItemDTO;
import com.boot.accommodation.listener.OnEventControl;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.TraceExceptionUtils;
import com.boot.accommodation.vp.location.LocationDetailReviewFragment;
import com.boot.accommodation.vp.profile.ProfileUserFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * activity of profile
 *
 * @author Vuong-bv
 * @since: 6/3/2016
 */
public class ProfileActivity extends BaseActivity implements ProfileViewMgr {

    OnEventControl listener;
    TouristDTO infoDto = new TouristDTO();
    @Bind(R.id.ivTourPhoto)
    ImageView ivTourPhoto;
    @Bind(R.id.ivAvatar)
    CircularImageView ivAvatar;
    @Bind(R.id.tvUserName)
    TextView tvUserName;
    @Bind(R.id.fbFunction)
    FloatingActionButton fbFunction;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvTitleProfile)
    TextView tvTitleProfile;
    @Bind(R.id.tvChangeAvatar)
    TextView tvChangeAvatar;
//    @Bind(R.id.tabs)
//    TabLayout tabs;
//    @Bind(R.id.appBar)
//    AppBarLayout appBar;
//    @Bind(R.id.vpProfie)
//    CustomViewPager vpProfile;
    @Bind(R.id.prUpload)
    ProgressBar prUpload;
    Boolean isMyProfile = true;//check is my profile or not
    Boolean isFriend = false;//check is friend with user login or not
    Boolean isEdit = false;// check editting or not
    @Bind(R.id.ivVipMember)
    ImageView ivVipMember;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rootLayout)
    CoordinatorLayout rootLayout;
    private String fileNameUpload; // file name upload
    private ProfilePresenterMgr profilePresenterMgr; // profile
    private boolean isJoin;//is member of tour
    private ProfileUserFragment profileUserFragment; //Profile user fragment

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableSlidingMenu(false);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        initData();
        initView();
        renderLayout(infoDto);
    }

    /**
     * Init data
     */
    private void initData() {
        profilePresenterMgr = new ProfilePresenter(this);
    }

    /**
     * render layout innit
     *
     * @param touristDTO
     */
    public void renderLayout(TouristDTO touristDTO) {
        showAvatar(touristDTO.getImage());
        //tvUserName.setText(touristDTO.getName());
        //check if : this is user login : show fbFunction can be edit info
        if (UserItemDTO.TYPE_ADMIN != JoCoApplication.getInstance().getProfile().getUserData().getUserType() &&
                String.valueOf(touristDTO.getId()).equals(JoCoApplication.getInstance().getProfile().getUserData().getId())) {
            fbFunction.setImageResource(R.drawable.ic_pen_white);
            fbFunction.setVisibility(View.VISIBLE);
            tvChangeAvatar.setVisibility(View.VISIBLE);
        } else if (UserItemDTO.TYPE_ADMIN == JoCoApplication.getInstance().getProfile().getUserData().getUserType() &&
                String.valueOf(touristDTO.getId()).equals(JoCoApplication.getInstance().getProfile().getUserData().getId())) {
            tvChangeAvatar.setVisibility(View.VISIBLE);
        } else {
            fbFunction.setVisibility(View.GONE);
            tvChangeAvatar.setVisibility(View.GONE);
            isMyProfile = false;
        }
    }


    /**
     * Show avatar
     *
     * @param image
     */
    private void showAvatar(String image) {
        if (!StringUtil.isNullOrEmpty(image)) {
            ImageUtil.loadImage(ivAvatar, ImageUtil.getImageUrlThumb(image));
        }
    }

    /**
     * s
     * Khoi tao view pager
     */
    private void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            isJoin = intent.getBooleanExtra(Constants.IntentExtra.IS_JOIN, false);
            infoDto = intent.getParcelableExtra(Constants.IntentExtra.TOURIST_DTO);
        }
//        setupViewPager(vpProfile);
//        tabs.setupWithViewPager(vpProfile);
//        vpProfile.setSwipeable(true);
//        typeUser = infoDto.getUserType();
        Bundle data = new Bundle();
        data.putBoolean(Constants.IntentExtra.FROM_COLLECTION, true);
        data.putString(Constants.IntentExtra.USER_ID, String.valueOf(infoDto.getId()));
        //Put item id, type for review
        data.putLong(Constants.IntentExtra.ITEM_ID, infoDto.getId());
        data.putInt(Constants.IntentExtra.TYPE_REVIEW, LocationDetailReviewFragment.TYPE_REVIEW_USER);
        data.putParcelable(Constants.IntentExtra.TOURIST_DTO, infoDto);
        data.putBoolean(Constants.IntentExtra.IS_JOIN, isJoin);
        switchFragment(profileUserFragment = ProfileUserFragment.newInstance(data), R.id.frDetail, true);
//        adapter.addFrag(ProfileUserFragment.newInstance(data), getString(R.string.text_tour_information).toUpperCase())
    }

//    /**
//     * Khoi tao du lieu view pager
//     *
//     * @param viewPager
//     */
//    private void setupViewPager(ViewPager viewPager) {
//        adapter = new HomeAdapter(getSupportFragmentManager());
//        typeUser = infoDto.getUserType();
//        Bundle data = new Bundle();
//        data.putBoolean(Constants.IntentExtra.FROM_COLLECTION, true);
//        data.putString(Constants.IntentExtra.USER_ID, String.valueOf(infoDto.getId()));
//        //Put item id, type for review
//        data.putLong(Constants.IntentExtra.ITEM_ID, infoDto.getId());
//        data.putInt(Constants.IntentExtra.TYPE_REVIEW, LocationDetailReviewFragment.TYPE_REVIEW_USER);
//        data.putParcelable(Constants.IntentExtra.TOURIST_DTO, infoDto);
//        data.putBoolean(Constants.IntentExtra.IS_JOIN, isJoin);
//        adapter.addFrag(ProfileUserFragment.newInstance(data), getString(R.string.text_tour_information).toUpperCase());
//        adapter.addFrag(ProfileCollectionFragment.newInstance(data), getString(R.string.text_collection).toUpperCase());
//        adapter.addFrag(LocationDetailReviewFragment.newInstance(data), getString(R.string.text_review).toUpperCase());
//        viewPager.setOffscreenPageLimit(adapter.getFragmentList().size());
//        viewPager.setAdapter(adapter);
//    }

    @OnClick({R.id.ivBack, R.id.fbFunction, R.id.tvChangeAvatar, R.id.ivAvatar})
    public void onClick(View v) {
        int action = 0;
        switch (v.getId()) {
            case R.id.ivBack:
                handleBack();
                break;
            case R.id.fbFunction:
                if (isMyProfile) {
                    profileUserFragment.editUserInfo();
                    if (!isEdit) {
                        isEdit = true;
                        profileUserFragment.editUserInfo();
                        fbFunction.setImageResource(R.drawable.ic_done_white);
                    } else {
                        saveInfo();
                    }
                }
                break;
            case R.id.tvChangeAvatar:
                if (isMyProfile) {
                    ImageUtil.selectImage(this);
                } else {
                    showImage();
                }
                break;
            case R.id.ivAvatar:
                if (isMyProfile) {
                    ImageUtil.selectImage(this);
                } else {
                    showImage();
                }
                break;
        }
    }

    /**
     * Handle back
     */
    private void handleBack() {
        if (isEdit == true) {
//            vpProfile.setCurrentItem(0);
            showAlert(getString(R.string.text_confirm_not_change_info), null, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    leaveWithoughtChange();
                }
            }, getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }, getString(R.string.text_cancel), true);
            return;
        } else {
            super.onBackPressed();
        }
    }

    /**
     * show full image
     */
    private void showImage() {
        FragmentManager fm = this.getSupportFragmentManager();
        FullImageDialog fullImageDialog = new FullImageDialog();
        fullImageDialog.setData(this, infoDto.getImage());
        fullImageDialog.show(fm, "fragment_help");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        } catch (Throwable ex) {
            Log.e(Constants.LogName.EXCEPTION, TraceExceptionUtils.getReportFromThrowable(ex));
        }
    }

    /**
     * updadate image
     *
     * @param url
     */
    public void updatePhoto(String url) {
//        JoCoApplication.getInstance().getProfile().getUserData().setAvatar(url);
        ImageUtil.loadImage(ivAvatar, url);
        ivAvatar.setAlpha(Constants.ALPHA_UPLOAD_PHOTO);
        prUpload.setVisibility(View.VISIBLE);
        prUpload.setMax(100);
        prUpload.setProgress(0);
        profilePresenterMgr.uploadPhoto(takenPhoto.getAbsoluteFile());
    }

    /**
     * method leave page but not save info user edit
     */
    private void leaveWithoughtChange() {
        isEdit = false;
        fbFunction.setImageResource(R.drawable.ic_pen_white);
        super.onBackPressed();
    }

    /**
     * Co luu  thong tin hay ko
     */
    public void saveInfo() {
        showAlert(getString(R.string.text_confirm_edit), null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                   profileUserFragment.requestEditUserInfo();
            }
        }, getString(R.string.text_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }, getString(R.string.text_cancel), true);
    }

    /**
     * save info but not change
     */
    public void saveNotChange() {
        isEdit = false;
        fbFunction.setImageResource(R.drawable.ic_pen_white);
    }

    /**
     * set username when they edit success
     *
     * @param name
     */
    public void updateInfo(String name, boolean editting) {
        if (!StringUtil.isNullOrEmpty(name)) {
            tvUserName.setText(name);
            if (isEdit) {
                UserItemDTO userItemDTO = JoCoApplication.getInstance().getProfile().getUserData();
                userItemDTO.setFullName(name);
                JoCoApplication.getInstance().getProfile().save();
                sendBroadcast(Constants.ActionEvent.ACTION_CHANGE_PROFILE, new Bundle());
            }
        }
        if (editting) {
            isEdit = false;
            fbFunction.setImageResource(R.drawable.ic_pen_white);
        }
    }

    /**
     * update icon for btfunction
     *
     * @param friend
     */
    public void updateIcon(boolean friend, boolean isMyprofile) {
        isFriend = friend;
        if (!isMyprofile) {
            fbFunction.setVisibility(View.GONE);
        } else {
            fbFunction.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void updateFileNameUpload(String fileName) {
        prUpload.setVisibility(View.INVISIBLE);
        ivAvatar.setAlpha(0.99f);
        fileNameUpload = fileName;
        profilePresenterMgr.requestUpLoadPhoto(infoDto.getId(), fileNameUpload);
    }

    @Override
    public void updateProgressBar(int percent) {
        prUpload.setProgress(percent);
    }

    @Override
    public void finishUpdatePhoto() {
    }

    @Override
    public void updateError(String message) {
        prUpload.setVisibility(View.INVISIBLE);
        showMessage(message);
    }

    /**
     * Update avatar
     *
     * @param url
     */
    public void updateAvatar(String url) {
        UserItemDTO userItemDTO = JoCoApplication.getInstance().getProfile().getUserData();
        userItemDTO.setAvatar(url);
        JoCoApplication.getInstance().getProfile().save();
        sendBroadcast(Constants.ActionEvent.ACTION_CHANGE_PROFILE, new Bundle());
    }

    @Override
    public void onBackPressed() {
        handleBack();
    }


//    /**
//     * Show vip member
//     *
//     * @param isShow
//     */
//    public void showVipMember(boolean isShow) {
//        ivVipMember.setVisibility(isShow ? View.VISIBLE : View.GONE);
//    }
}
