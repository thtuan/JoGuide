package com.boot.accommodation.vp.location;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dialog.FullImageDialog;
import com.boot.accommodation.dto.view.ItemTypeDTO;
import com.boot.accommodation.dto.view.PhotoDTO;
import com.boot.accommodation.dto.view.ReviewItemDTO;
import com.boot.accommodation.dto.view.TouristDTO;
import com.boot.accommodation.dto.view.UserItemDTO;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.tour.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by xdung on 3/8/2016.
 */
public class LocationDetailReviewFragment extends BaseFragment implements LocationDetailReviewMgr, RatingBar.OnRatingBarChangeListener {
    public static final int ACTION_FULL_IMAGE_VIEW = 1000;
    public static final int ACTION_LIKE_REVIEW = 1001; // action like review
    public static final int ACTION_GO_TO_PROFILE = 1002;// action go to profile user

    LocationDetailReviewPresenterMgr presenter;
    @Bind(R.id.rvCommentItemList)
    RecyclerView rvCommentItemList;
    @Bind(R.id.llRating)
    LinearLayout llRating;
    @Bind(R.id.rbRating)
    RatingBar rbRating;
    @Bind(R.id.tvRatingStatus)
    TextView tvRatingStatus;
    @Bind(R.id.etContent)
    EditText etContent;
    @Bind(R.id.frImageReview)
    FrameLayout frImageReview;
    @Bind(R.id.ivImageReview)
    ImageView ivImageReview;
    @Bind(R.id.prUpload)
    ProgressBar prUpload;
    @Bind(R.id.ivImageSendReview)
    ImageView ivImageSendReview;
    @Bind(R.id.llRatingStar)
    LinearLayout llRatingStar;
    private LocationDetailReviewAdapter placeDetailReviewAdapter; // place detail review adapter
    private boolean isSend = false;// bien kiem tra phai send comment hay ko
    private String fileNameUpload; // file name upload
    public static final int TYPE_REVIEW_LOCATION = 1; //Type review location
    public static final int TYPE_REVIEW_TOUR = 2; //Type review tour
    public static final int TYPE_REVIEW_USER = 3; //Type review user
    private int typeReview = TYPE_REVIEW_LOCATION; //Type review
    private long itemId; //Item id review (userId/tourId/locationId)

    @Override
    public int contentViewLayout() {
        return R.layout.activity_place_detail_review_fragment;
    }

    public static Fragment newInstance(Bundle data) {
        LocationDetailReviewFragment fragment = new LocationDetailReviewFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enableRefresh(true);
        Bundle b = getArguments();
        if (b != null) {
            typeReview = b.getInt(Constants.IntentExtra.TYPE_REVIEW, TYPE_REVIEW_LOCATION);
            itemId = b.getLong(Constants.IntentExtra.ITEM_ID);
        }
        initView();
        initData();
    }

    /**
     * Initview
     */
    private void initView(){
        rbRating.setOnRatingBarChangeListener(this);
        rvCommentItemList.setLayoutManager(new LinearLayoutManager(mActivity));
        rvCommentItemList.setHasFixedSize(true);
        if (TYPE_REVIEW_TOUR == typeReview) {
            llRatingStar.setVisibility(View.GONE);
        } else if(TYPE_REVIEW_USER == typeReview) {
            if (Long.valueOf(JoCoApplication.getInstance().getProfile().getUserData().getId()) == itemId) {
                llRating.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Init data
     */
    private void initData(){
        presenter = new LocationDetailReviewPresenter(this);
        showProgress();
        getReview();
    }
    /**
     * Get review
     */
    private void getReview(){
        presenter.getReview(typeReview, itemId);
    }

    @Override
    public void renderLayout(ArrayList<ReviewItemDTO> lstReview) {
        rvCommentItemList.getLayoutParams().height = RecyclerView.LayoutParams.MATCH_PARENT;
        if (placeDetailReviewAdapter == null) {
            placeDetailReviewAdapter = new LocationDetailReviewAdapter(lstReview);
            placeDetailReviewAdapter.setListener(this);
            rvCommentItemList.setAdapter(placeDetailReviewAdapter);
        }
        placeDetailReviewAdapter.setData(lstReview);
        placeDetailReviewAdapter.notifyDataSetChanged();
        if (isSend) {
            isSend = false;
            rvCommentItemList.scrollToPosition(0);
            fileNameUpload = "";
            frImageReview.setVisibility(View.GONE);
            etContent.setText("");
            mActivity.takenPhoto = null;
        }
        closeProgress();
        stopRefresh();
        mActivity.closeProgressDialog();
    }

    @Override
    public void likeReviewSuccess() {
//        mActivity.showMessage("likeReviewSuccess");
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        if(rvCommentItemList.getChildCount() == 0){
            rvCommentItemList.getLayoutParams().height = RecyclerView.LayoutParams.WRAP_CONTENT;
        }
        stopRefresh();
        closeProgress();
        mActivity.closeProgressDialog();
        switch (errorCode) {
            default:
                handleError(errorCode, error);
        }
    }

    @Override
    public void reviewSuccess(ArrayList<ReviewItemDTO> lstReview, boolean isHaveContent) {
        Utils.hideKeyboardInput(mActivity, ivImageSendReview);
        if (isHaveContent) {
            mActivity.showMessage(Utils.getString(R.string.text_review_location_success));
        } else {
            mActivity.showMessage(Utils.getString(R.string.text_rating_success));
        }
        sendBroadcast(Constants.ActionEvent.ACTION_UPDATE_INFO_LOCATION, new Bundle());
        renderLayout(lstReview);
    }

    @Override
    public void finishProcessDialog() {

    }


    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action) {
            case Constants.ActionEvent.NOTIFY_REFRESH:
                presenter.getReview(typeReview, itemId);
                break;
        }
    }

    @Override
    public void updateFileNameUpload(String fileName) {
        ivImageSendReview.setEnabled(true);
        prUpload.setVisibility(View.INVISIBLE);
        fileNameUpload = fileName;
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if (rbRating == ratingBar && fromUser) {
            switch ((int) rating) {
                case 1:
                    tvRatingStatus.setText(StringUtil.getString(R.string.text_rating_status_1));
                    break;
                case 2:
                    tvRatingStatus.setText(StringUtil.getString(R.string.text_rating_status_2));
                    break;
                case 3:
                    tvRatingStatus.setText(StringUtil.getString(R.string.text_rating_status_3));
                    break;
                case 4:
                    tvRatingStatus.setText(StringUtil.getString(R.string.text_rating_status_4));
                    break;
                case 5:
                    tvRatingStatus.setText(StringUtil.getString(R.string.text_rating_status_5));
                    break;
            }
        }
    }

    @OnClick({R.id.ivCameraRateTour, R.id.ibDeleteImage, R.id.ivImageSendReview})
    public void onClick(View view) {
        if (view.getId() == R.id.ivCameraRateTour) {
            //            takePicture();
            ImageUtil.selectImage(mActivity, this);
        } else if (view.getId() == R.id.ibDeleteImage) {
            mActivity.takenPhoto = null;//xoa file hinh
            frImageReview.setVisibility(View.GONE);
        } else if (view.getId() == R.id.ivImageSendReview) {
            String content = etContent.getText().toString();
            if (TYPE_REVIEW_TOUR == typeReview && StringUtil.isNullOrEmpty(content)) {
                showMessage(StringUtil.getString(R.string.text_validate_comment));
            } else {
                isSend = true;
                ReviewItemDTO reviewDTO = new ReviewItemDTO();
                reviewDTO.setContent(content);
                reviewDTO.setItemId(itemId);
                if (TYPE_REVIEW_USER == typeReview) {
                    reviewDTO.setItemType(ItemTypeDTO.USER.getValue());// loai review
                } else if (TYPE_REVIEW_TOUR == typeReview) {
                    reviewDTO.setItemType(ItemTypeDTO.TOUR.getValue());// loai review
                } else {
                    reviewDTO.setItemType(ItemTypeDTO.LOCATION.getValue());// loai review
                }
                UserItemDTO userItemDTO = JoCoApplication.getInstance().getProfile().getUserData();
                if (!StringUtil.isNullOrEmpty(userItemDTO.getId())) {
                    reviewDTO.setUserName(JoCoApplication.getInstance().getProfile().getUserData().getFullName());
                    reviewDTO.setUserId(Long.valueOf(JoCoApplication.getInstance().getProfile().getUserData().getId()));
                    reviewDTO.setUserAvatar(JoCoApplication.getInstance().getProfile().getUserData().getAvatar());
                }
                reviewDTO.setRating(rbRating.getRating());
                reviewDTO.setDateTime(DateUtil.formatNow(DateUtil.FORMAT_DATE_TIME_ZONE));
                if (!StringUtil.isNullOrEmpty(fileNameUpload)) {
                    reviewDTO.setImage(fileNameUpload);
                }

                presenter.requestCreateReview(reviewDTO);
                mActivity.showProgressDialog(true);
            }
        }
    }

    /**
     * Update photo
     *
     * @param url
     */
    public void updatePhoto(String url) {
        ivImageSendReview.setEnabled(false);
        prUpload.setVisibility(View.VISIBLE);
        prUpload.setMax(100);
        prUpload.setProgress(0);
        frImageReview.setVisibility(View.VISIBLE);
        ImageUtil.loadImage(ivImageReview, url);
        presenter.uploadPhoto(mActivity.takenPhoto);
    }

    @Override
    public void onEventControl(int action, Object data, View View, int position) {
        final ReviewItemDTO reviewItemDTO = (ReviewItemDTO) data;
        if (action == ACTION_FULL_IMAGE_VIEW && data != null) {
            PhotoDTO dtoPhoto = new PhotoDTO();
            dtoPhoto.setCreateUser(reviewItemDTO.getUserName());
            dtoPhoto.setCreateDate(reviewItemDTO.getDateTime());
            dtoPhoto.setComment(reviewItemDTO.getContent());
            dtoPhoto.setUrl(reviewItemDTO.getImage());
            dtoPhoto.setCreateUserAvatar(reviewItemDTO.getUserAvatar());
            FragmentManager fm = mActivity.getSupportFragmentManager();
            FullImageDialog fullImageDialog = new FullImageDialog();
            fullImageDialog.setData(mActivity, dtoPhoto, 0);
            fullImageDialog.setShowInfo(true);
            fullImageDialog.show(fm, "fragment_help");
        } else if (action == ACTION_LIKE_REVIEW) {
            reviewItemDTO.setUserId(Long.valueOf(JoCoApplication.getInstance().getProfile().getUserData().getId()));
            presenter.requestLikeReview(reviewItemDTO);
        } else if (action == ACTION_GO_TO_PROFILE) {
            TouristDTO dto = new TouristDTO();
            dto.setId(reviewItemDTO.getUserId());
            dto.setName(reviewItemDTO.getUserName());
            dto.setImage(reviewItemDTO.getUserAvatar());
            dto.setUserType(reviewItemDTO.getUserType());
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.IntentExtra.TOURIST_DTO, dto);
            mActivity.goNextScreen(ProfileActivity.class, bundle);
        } else if (action == Constants.ActionEvent.ACTION_DELETE_REVIEW) {
            if (reviewItemDTO.getUserId().longValue() == Long.valueOf(JoCoApplication.getInstance().getProfile().getUserData().getId()).longValue() ) {
                mActivity.showAlert(getString(R.string.text_confirm_delete), null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteReview(reviewItemDTO);
                    }
                }, getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }, getString(R.string.text_cancel), true);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onLoadMore(int position) {
        presenter.getMoreReview(placeDetailReviewAdapter);
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
        mActivity.showMessage(message);
    }

    @Override
    public void showMessage(String message) {
        showMessage(message);
        closeProgress();
    }

    @Override
    public void deleteReviewSuccess(List<ReviewItemDTO> lstReview) {
        showRefresh();
        getReview();
        sendBroadcast(Constants.ActionEvent.ACTION_UPDATE_INFO_LOCATION, new Bundle());
    }

}
