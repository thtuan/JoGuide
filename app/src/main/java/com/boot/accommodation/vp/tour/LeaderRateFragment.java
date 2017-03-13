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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dialog.FullImageDialog;
import com.boot.accommodation.dto.view.ItemTypeDTO;
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
 * fragment leader rating
 *
 * @author vuong
 * @since 15:47 PM 5/13/2016
 */
public class LeaderRateFragment extends BaseFragment implements LeaderRateViewMgr, RatingBar.OnRatingBarChangeListener {
    public static final int ACTION_VIEW_PHOTO = 1002;// view hinh anh
    public static final int ACTION_LIKE_THIS_COMENT = 1001;
    public static final int ACTION_SELECT_FILE = 1000; // chon file
    public static final int ACTION_GO_TO_PROFILE = 1003;// go to profile tourist
    boolean like = false;
    LeaderRateAdapter leaderRateAdapter; // adapter cho tourist
    LeaderRatePresenterMgr leaderRatePresenterMgr; // presenter cho man hinh leader
    @Bind(R.id.rvLeaderRated)
    RecyclerView rvLeaderRated;
    @Bind(R.id.rbRatingLeader)
    RatingBar rbRatingLeader;
    @Bind(R.id.etContentRatingLeader)
    EditText etContentRatingLeader;
    @Bind(R.id.ivSend)
    ImageView ivSend;
    @Bind(R.id.ivCameraRateLeader)
    ImageView ivCameraRateLeader;
    @Bind(R.id.llRatingLeader)
    LinearLayout llRatingLeader;
    @Bind(R.id.tvRatingStatus)
    TextView tvRatingStatus;
    @Bind(R.id.ivImageReview)
    ImageView ivImageReview;
    @Bind(R.id.ibDeleteImage)
    ImageButton ibDeleteImage;
    @Bind(R.id.frImageReview)
    FrameLayout frImageReview;
    @Bind(R.id.prUpload)
    ProgressBar prUpload;
    private long leaderId; // id leader
    private boolean isSend = false;// bien kiem tra phai send comment hay ko
    private String fileNameUpload; // file name upload
    private boolean isJoin;//is member of tour

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_leader_rated;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        showProgress();
        mActivity.hideFloatingButton(true, 0);
    }

    /**
     * khoi tao leader review
     *
     * @param bundle bunle nhan duoc
     * @return tra ve fragment
     */
    public static LeaderRateFragment newInstance(Bundle bundle) {
        LeaderRateFragment fragment = new LeaderRateFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            TouristDTO touristDTO = args.getParcelable(Constants.IntentExtra.TOURIST_DTO);
            leaderId = touristDTO.getId();
            isJoin = args.getBoolean(Constants.IntentExtra.IS_JOIN);

        }
    }

    /**
     * Khoi tao data
     */
    private void initData() {
        leaderRatePresenterMgr = new LeaderRatePresenter(this);
        leaderRatePresenterMgr.getReview(leaderId);
    }

    /**
     * Khoi tao view
     */
    private void initView() {
        rvLeaderRated.setLayoutManager(new LinearLayoutManager(mActivity));
        rvLeaderRated.setHasFixedSize(true);
        rbRatingLeader.setOnRatingBarChangeListener(this);
        if (Long.valueOf(JoCoApplication.getInstance().getProfile().getUserData().getId()) == leaderId) {
            llRatingLeader.setVisibility(View.GONE);
        }
    }


    @Override
    public void onLoadMore(int position) {
        leaderRatePresenterMgr.getMoreReview(ItemTypeDTO.REVIEW.getValue(), leaderId, leaderRateAdapter);
    }

    /**
     * render layout
     *
     * @param leaderRatedDTO goi tin leader review
     */
    @Override
    public void renderLayout(ArrayList<ReviewItemDTO> leaderRatedDTO) {
        rvLeaderRated.getLayoutParams().height = RecyclerView.LayoutParams.MATCH_PARENT;
        if (leaderRateAdapter == null) {
            leaderRateAdapter = new LeaderRateAdapter(leaderRatedDTO);
            leaderRateAdapter.setEnableLoadMore(true);
            leaderRateAdapter.setListener(this);
            rvLeaderRated.setAdapter(leaderRateAdapter);
        } else {
            leaderRateAdapter.setData(leaderRatedDTO);
        }
        leaderRateAdapter.notifyDataSetChanged();
        if (isSend) {
            isSend = false;
            rvLeaderRated.scrollToPosition(0);
            frImageReview.setVisibility(View.GONE);
            etContentRatingLeader.setText("");
            mActivity.takenPhoto = null;
            fileNameUpload = "";
        }
        Utils.hideKeyboardInput(mActivity, ivSend);
        stopRefresh();
        closeProgress();
        mActivity.closeProgressDialog();
    }

    @OnClick({R.id.ivSend, R.id.ivCameraRateLeader, R.id.ibDeleteImage})
    public void OnClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ivSend:
                isSend = true;
                String content = etContentRatingLeader.getText().toString();
                float rating = rbRatingLeader.getRating();
                ReviewItemDTO reviewDTO = new ReviewItemDTO();
                reviewDTO.setContent(content);
                reviewDTO.setItemId(leaderId);
                reviewDTO.setItemType(ItemTypeDTO.USER.getValue()); // loai review
                reviewDTO.setRating(rating);
                reviewDTO.setDateTime(DateUtil.formatNow(DateUtil.FORMAT_DATE_TIME_ZONE));
                reviewDTO.setImage(fileNameUpload);
                //                reviewDTO.setDateTime("2016-07-13T19:20:00.789+07:00");
                reviewDTO.setUserId(Long.valueOf(JoCoApplication.getInstance().getProfile().getUserData().getId()));
                mActivity.showProgressDialog(true);
                leaderRatePresenterMgr.requestReviewLeader(reviewDTO);
                break;
            case R.id.ibDeleteImage: {
                mActivity.takenPhoto = null;//xoa file hinh
                frImageReview.setVisibility(View.GONE);
                break;
            }
            case R.id.ivCameraRateLeader: {
                ImageUtil.selectImage(mActivity,this);
                break;
            }
        }
    }


    @Override
    public void onEventControl(int action, Object item, View View, int position) {
        final ReviewItemDTO discussDTO = ((ReviewItemDTO) item);
        switch (action) {
            case ACTION_LIKE_THIS_COMENT:
                discussDTO.setUserId(Long.valueOf(JoCoApplication.getInstance().getProfile().getUserData().getId()));
                leaderRatePresenterMgr.requestLikeReviewLeader((ReviewItemDTO) item);
                break;
            case ACTION_VIEW_PHOTO:
                FragmentManager fm = mActivity.getSupportFragmentManager();
                FullImageDialog fullImageDialog = new FullImageDialog();
                fullImageDialog.setData(mActivity, discussDTO.getImage());
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
                if (discussDTO.getUserId().longValue()  == Long.valueOf(JoCoApplication.getInstance().getProfile().getUserData().getId()).longValue() ) {
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
    public void deleteComment(ReviewItemDTO dto) {
        leaderRatePresenterMgr.deleteReview(dto);
        showProgress();
    }
    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * xu ly khi tao review thanh cong
     */
    @Override
    public void createReviewSuccess() {
        etContentRatingLeader.setText("");
        etContentRatingLeader.clearFocus();
    }

    @Override
    public void updateFileNameUpload(String fileName) {
        ivSend.setEnabled(true);
        prUpload.setVisibility(View.INVISIBLE);
        fileNameUpload = fileName;
    }

    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action) {
            case Constants.ActionEvent.NOTIFY_REFRESH:
                leaderRatePresenterMgr.getReview(leaderId);
                break;
        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if (rbRatingLeader == ratingBar && fromUser) {
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

    /**
     * ket qua image tra ve
     *
     * @param requestCode code request
     * @param resultCode  code result
     * @param data        data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
//                case BaseActivity.REQUEST_IMAGE_CAPTURE: {
//                    // luu hinh anh
//                    mActivity.saveImageToTakenPhotoFolder();
//
//                    if (resultCode == Activity.RESULT_OK && mActivity.takenPhoto != null && mActivity.takenPhoto.exists()) {
//                        updatePhoto(mActivity.takenPhoto.getAbsolutePath());
//                    } else {
//                        //Xoa file rac
//                        if (mActivity.takenPhoto != null && mActivity.takenPhoto.exists()) {
//                            mActivity.takenPhoto.delete();
//                        }
//                    }
//                    break;
//                }
//                case ACTION_SELECT_FILE:
//                    onSelectFromGalleryResult(data);
//                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        } catch (Throwable ex) {
            Log.w("", TraceExceptionUtils.getReportFromThrowable(ex));
        }
    }

    @Override
    public void updatePhoto(String url) {
        ivSend.setEnabled(false);
        frImageReview.setVisibility(View.VISIBLE);
        ImageUtil.loadImage(ivImageReview, url);
        prUpload.setVisibility(View.VISIBLE);
        prUpload.setMax(100);
        prUpload.setProgress(0);
        leaderRatePresenterMgr.uploadPhoto(mActivity.takenPhoto);
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

    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        if(rvLeaderRated.getChildCount() == 0){
            rvLeaderRated.getLayoutParams().height = RecyclerView.LayoutParams.WRAP_CONTENT;
        }
        closeProgress();
        stopRefresh();
        mActivity.closeProgressDialog();
        switch (errorCode){
            default:
                handleError(errorCode,error);
        }
    }
}
