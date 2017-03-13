package com.boot.accommodation.vp.tour;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dialog.FullImageDialog;
import com.boot.accommodation.dto.view.ItemTypeDTO;
import com.boot.accommodation.dto.view.PhotoDTO;
import com.boot.accommodation.dto.view.ReviewItemDTO;
import com.boot.accommodation.dto.view.TouristDTO;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.TraceExceptionUtils;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * DiscussFragment
 *
 * @author vuong
 * @since 15:47 PM 5/25/2016
 */
public class DiscussFragment extends BaseFragment implements DiscussViewMgr {
    public static final int ACTION_GO_TO_PROFILE = 1003;//go to profile
    public static final int ACTION_LIKE_THIS_COMENT = 1001;
    public static final int ACTION_VIEW_PHOTO = 1002;// view hinh anh
    public static final int ACTION_SELECT_FILE = 1000; // chon file
    DiscussAdapter discussAdapter;
    DiscussPresenterMgr discussPresenterMgr;

    @Bind(R.id.rvTourRated)
    RecyclerView rvTourRated;
    @Bind(R.id.ivCamera)
    ImageView ivCamera;
    @Bind(R.id.etContent)
    EditText etContent;
    @Bind(R.id.ivSend)
    ImageView ivSend;
    @Bind(R.id.llRatingTour)
    LinearLayout llRatingTour;
    @Bind(R.id.ivImageReview)
    ImageView ivImageReview;
    @Bind(R.id.ibDeleteImage)
    ImageButton ibDeleteImage;
    @Bind(R.id.frImageReview)
    FrameLayout frImageReview;
    @Bind(R.id.prUpload)
    ProgressBar prUpload;
    private long tourPlanId;// id tour plan
    private long tourId; // Tour id
    private boolean isJoin; // Have join tour or not
    private boolean isSend = false;// bien kiem tra phai send comment hay ko
    private String fileNameUpload; // file name upload

    public static DiscussFragment newInstance(Bundle bundle) {
        DiscussFragment fragment = new DiscussFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_discuss;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        showProgress();
        mActivity.hideFloatingButton(true, 0);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            tourPlanId = args.getLong(Constants.IntentExtra.TOUR_PLAN_ID);
            isJoin = args.getBoolean(Constants.IntentExtra.IS_JOIN);
            tourId = args.getLong(Constants.IntentExtra.TOUR_ID);
        }
    }

    /**
     * Khoi tao data
     */
    private void initData() {
        discussPresenterMgr = new DiscussPresenter(this);
        discussPresenterMgr.getDiscuss(tourId);
    }

    /**
     * Khoi tao view
     */
    private void initView() {
        rvTourRated.setLayoutManager(new LinearLayoutManager(mActivity));
        rvTourRated.setHasFixedSize(true);
    }


    @Override
    public void onLoadMore(int position) {
        discussPresenterMgr.getMoreDiscuss(tourId, discussAdapter);
    }

    @Override
    public void renderLayout(ArrayList<ReviewItemDTO> discussDTO) {
        rvTourRated.getLayoutParams().height = RecyclerView.LayoutParams.MATCH_PARENT;
        if (discussAdapter == null) {
            discussAdapter = new DiscussAdapter(discussDTO);
            discussAdapter.setListener(this);
            rvTourRated.setAdapter(discussAdapter);
        } else {
            discussAdapter.setData(discussDTO);
        }
        discussAdapter.notifyDataSetChanged();
        if (isSend) {
            isSend = false;
            rvTourRated.scrollToPosition(0);
            frImageReview.setVisibility(View.GONE);
            etContent.setText("");
            fileNameUpload = "";
            mActivity.takenPhoto = null;
        }
        Utils.hideKeyboardInput(mActivity, ivSend);
        closeProgress();
        stopRefresh();
        mActivity.closeProgressDialog();
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        if (rvTourRated.getChildCount() == 0) {
            rvTourRated.getLayoutParams().height = RecyclerView.LayoutParams.WRAP_CONTENT;
        }
        isSend = false;
        stopRefresh();
        closeProgress();
        mActivity.closeProgressDialog();
        switch (errorCode) {
            default:
                handleError(errorCode, error);
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * Rating tour thanh cong
     *
     * @param discussDTO
     */
    @Override
    public void ratingTourSuccess(ReviewItemDTO discussDTO) {
        closeProgress();
    }

    @Override
    public void updateFileNameUpload(String fileName) {
        prUpload.setVisibility(View.GONE);
        ivSend.setEnabled(true);
        fileNameUpload = fileName;
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

    @Override
    public void deleteComment(ReviewItemDTO dto) {
        discussPresenterMgr.deleteReview(dto);
        showProgress();
    }

    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action) {
            case Constants.ActionEvent.NOTIFY_REFRESH:
                discussPresenterMgr.getDiscuss(tourId);
                break;
        }
    }

    @OnClick({R.id.ivSend, R.id.ivCamera, R.id.ibDeleteImage})
    public void OnClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ivSend:
                String content = etContent.getText().toString().trim();
                if (StringUtil.isNullOrEmpty(content)) {
                    showMessage(StringUtil.getString(R.string.text_validate_comment));
                } else {
                    ReviewItemDTO reviewDTO = new ReviewItemDTO();
                    reviewDTO.setContent(content.trim());
                    reviewDTO.setItemId(tourId);
                    reviewDTO.setItemType(ItemTypeDTO.TOUR.getValue()); // loai review
                    reviewDTO.setUserId(Long.valueOf(JoCoApplication.getInstance().getProfile().getUserData().getId()));
                    reviewDTO.setUserName(JoCoApplication.getInstance().getProfile().getUserData().getFullName());
                    reviewDTO.setUserAvatar(JoCoApplication.getInstance().getProfile().getUserData().getAvatar());
                    reviewDTO.setDateTime(DateUtil.formatNow(DateUtil.FORMAT_DATE_TIME_ZONE));
                    reviewDTO.setImage(fileNameUpload);
                    //                reviewDTO.setMediaType(MediaTypeDTO.IMAGE.getValue()); // loai media uplen
                    if (!isSend) {
                        discussPresenterMgr.requestCreateReviewTour(reviewDTO);
                        isSend = true;
                    }
                    mActivity.showProgressDialog(true);
                }
                break;
            case R.id.ibDeleteImage: {
                mActivity.takenPhoto = null;//xoa file hinh
                frImageReview.setVisibility(View.GONE);
                break;
            }
            case R.id.ivCamera: {
                ImageUtil.selectImage(mActivity, this);
                break;
            }
        }
    }

    @Override
    public void onEventControl(int action, Object item, View View, int position) {
        final ReviewItemDTO discussDTO = (ReviewItemDTO) item;
        switch (action) {
            case ACTION_LIKE_THIS_COMENT:
                discussDTO.setUserId(Long.valueOf(JoCoApplication.getInstance().getProfile().getUserData().getId()));
                discussPresenterMgr.requestLikeDiscuss(discussDTO);
                break;
            case ACTION_VIEW_PHOTO:
                PhotoDTO dtoPhoto = new PhotoDTO();
                dtoPhoto.setCreateUser(discussDTO.getUserName());
                dtoPhoto.setCreateDate(discussDTO.getDateTime());
                dtoPhoto.setComment(discussDTO.getContent());
                dtoPhoto.setUrl(discussDTO.getImage());
                dtoPhoto.setCreateUserAvatar(discussDTO.getUserAvatar());
                FragmentManager fm = mActivity.getSupportFragmentManager();
                FullImageDialog fullImageDialog = new FullImageDialog();
                fullImageDialog.setData(mActivity, dtoPhoto , 0);
                fullImageDialog.setShowInfo(true);
                fullImageDialog.show(fm, "fragment_help");
                break;
            case ACTION_GO_TO_PROFILE:
                TouristDTO dto = new TouristDTO();
                dto.setId(discussDTO.getUserId());
                dto.setName(discussDTO.getUserName());
                dto.setImage(discussDTO.getUserAvatar());
                dto.setUserType(discussDTO.getUserType());
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.IntentExtra.TOURIST_DTO, dto);
                mActivity.goNextScreen(ProfileActivity.class, bundle);
                break;
            case Constants.ActionEvent.ACTION_DELETE_REVIEW:
                if (discussDTO.getUserId().longValue() == Long.valueOf(JoCoApplication.getInstance().getProfile().getUserData().getId()).longValue()) {
                    mActivity.showAlert(getString(R.string.text_confirm_delete), null, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteComment(discussDTO);
                        }
                    }, getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }, getString(R.string.text_cancel), true);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                default:
                    super.onActivityResult(requestCode, resultCode, data);
            }
        } catch (Throwable ex) {
            Log.w("", TraceExceptionUtils.getReportFromThrowable(ex));
        }
    }

    @Override
    public void updatePhoto(String url) {
        ivSend.setEnabled(false);
        prUpload.setVisibility(View.VISIBLE);
        prUpload.setMax(100);
        prUpload.setProgress(0);
        discussPresenterMgr.uploadPhoto(mActivity.takenPhoto);
        frImageReview.setVisibility(View.VISIBLE);
        ImageUtil.loadImage(ivImageReview, url);
    }
}
