package com.boot.accommodation.base;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.TabletActionLogDTO;
import com.boot.accommodation.listener.OnEventControl;
import com.boot.accommodation.util.TraceExceptionUtils;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.common.CommonPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import tourguide.tourguide.Overlay;
import tourguide.tourguide.Pointer;
import tourguide.tourguide.ToolTip;
import tourguide.tourguide.TourGuide;

/**
 * Created by Admin on 14/11/2015.
 */
public abstract class BaseFragment<T extends BaseActivity> extends Fragment implements Animation.AnimationListener, OnEventControl {

    private static final int DELAY_TIME = 1000;
    private static final String SINGLE_SHOW_ID = "single_show";
    private static final String SEQ_SHOW_ID = "seq_show";
    LinearLayout llMain;
    @Nullable
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private Animation animFadein, animRotate;
    protected T mActivity;
    private ProgressBar prLoading; // loading


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (!isDetached()) {
                    int action = intent.getExtras().getInt(Constants.ACTION_BROADCAST);
                    int hasCode = intent.getExtras().getInt(Constants.HASHCODE_BROADCAST);
                    if (hasCode != BaseFragment.this.hashCode()) {
                        receiveBroadcast(action, intent.getExtras());
                    }
                }
            } catch (RuntimeException ex) {
                Log.e(Constants.LogName.EXCEPTION, TraceExceptionUtils.getReportFromThrowable(ex));
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (T) context;
    }

    @LayoutRes
    public abstract int contentViewLayout();

    public String tag() {
        return this.getClass().getName();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        IntentFilter filter = new IntentFilter(Constants.JOCO_ACTION);
        mActivity.registerReceiver(receiver, filter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = contentViewLayout();
        View view = inflater.inflate(R.layout.fragment_base, null, false);
        View viewChild = inflater.inflate(layoutId, container, false);
        //        ButterKnife.bind(this,view);
        llMain = (LinearLayout) view.findViewById(R.id.llMain);
        llMain.addView(viewChild);
        prLoading = (ProgressBar) view.findViewById(R.id.prLoading);
        prLoading.getIndeterminateDrawable().setColorFilter(Utils.getColor(R.color.primary_500), PorterDuff.Mode.SRC_IN);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        animFadein = AnimationUtils.loadAnimation(mActivity, R.anim.fade_in);
        animFadein.setAnimationListener(this);
        animRotate = AnimationUtils.loadAnimation(mActivity, R.anim.rotate);
        animRotate.setAnimationListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        JoCoApplication.getInstance().trackScreenView(Utils.getTag(this.getClass()));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onEventControl(int action, Object item, View View, int position) {

    }

    @Override
    public void onLoadMore(int position) {

    }

    /**
     * Nhan xu li broadcast
     *
     * @param action
     * @param extras
     */
    protected void receiveBroadcast(int action, Bundle extras) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Cancel list request
        CommonPresenter.getInstance().cancelListRequest(Utils.getTag(this.getClass()));
        ButterKnife.unbind(this);
        mActivity.unregisterReceiver(receiver);
    }

    /**
     * Khoi tao view
     */
    private void initView() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //               sendBroadcast(Constants.ActionEvent.NOTIFY_REFRESH,new Bundle());
                    // just refresh one fragment
                    receiveBroadcast(Constants.ActionEvent.NOTIFY_REFRESH, new Bundle());
                }
            });
            swipeRefreshLayout.setColorSchemeResources(R.color.primary_500, R.color.primary_500, R.color.primary_500);
        }
    }

    /**
     * send broadcast
     *
     * @param action
     * @param bundle
     */
    public void sendBroadcast(int action, Bundle bundle) {
        Intent intent = new Intent(Constants.JOCO_ACTION);
        bundle.putInt(Constants.ACTION_BROADCAST, action);
        bundle.putInt(Constants.HASHCODE_BROADCAST, intent.getClass().hashCode());
        intent.putExtras(bundle);
        mActivity.sendBroadcast(intent, Constants.BROACAST_PERMISSION);
    }

    /**
     * An/show swipe refresh
     */
    protected void enableRefresh(boolean isEnable) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(isEnable);
        }
    }

    /**
     * Dung refresh
     */
    protected void stopRefresh() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * Show refresh
     */
    protected void showRefresh(){
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    /**
     * Animation floatting button with recycleview
     *
     * @param recyclerView
     * @param floatingActionButton
     */
    protected void animationFloatButton(RecyclerView recyclerView, final FloatingActionButton floatingActionButton) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && floatingActionButton.isShown()) floatingActionButton.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    floatingActionButton.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    /**
     * Lay tag fragment
     *
     * @return
     */
    public String getTAG() {
        String tag = Utils.getTag(this.getClass());
        Log.d("Fragment getTAG", tag);
        return tag;
    }

    /**
     * Start activity fragment
     *
     * @param intent
     * @param requestCode
     * @param actionName
     */
    public void startActivityForResult(Intent intent, int requestCode, String actionName) {
        try {
            startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            Log.w(getClass().toString(), TraceExceptionUtils.getReportFromThrowable(e));
        } catch (Exception e) {
            Log.w(getClass().toString(), TraceExceptionUtils.getReportFromThrowable(e));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case BaseActivity.REQUEST_IMAGE_SELECTOR:
                    mActivity.onSelectFromGalleryResult(data);
                    handlePicture(requestCode, resultCode, null);
                    break;
                case BaseActivity.REQUEST_IMAGE_CAPTURE: {
                    // luu hinh anh
                    mActivity.saveImageToTakenPhotoFolder();
                    handlePicture(requestCode, resultCode, data);
                    break;
                }
                default:
                    super.onActivityResult(requestCode,resultCode,data);
                    break;
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handle picture taken or choose from gallery
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handlePicture(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && mActivity.takenPhoto != null && mActivity.takenPhoto
                .exists()) {
//            String filePath = mActivity.takenPhoto.getAbsolutePath();
//            ImageValidatorTakingPhoto validator = new ImageValidatorTakingPhoto(mActivity, filePath, Constants
//                    .MAX_FULL_IMAGE_HEIGHT_UPLOAD);
//            validator.setDataIntent(data);
//            if (validator.execute()) {
                updatePhoto(mActivity.takenPhoto.getAbsolutePath());
//            }
        } else {
            //Xoa file rac
            if (mActivity.takenPhoto != null && mActivity.takenPhoto.exists()) {
                mActivity.takenPhoto.delete();
            }
        }
    }

    /**
     * Hien thi loading
     */
    public void showProgress() {
        if (prLoading != null && !mActivity.isFinishing()) {
            prLoading.setIndeterminate(true);
        }
    }

    /**
     * Close progress
     */
    public void closeProgress() {
        if (prLoading != null && !mActivity.isFinishing()) {
            prLoading.setIndeterminate(false);
        }
    }

    /**
     * Handle error
     *
     * @param errorCode
     * @param error
     */
    protected void handleError(int errorCode, String error) {
        stopRefresh();
        closeProgress();
        switch (errorCode) {
            default:
                mActivity.handleError(errorCode, error);
                break;
        }
    }

    /**
     * init show case with target
     *
     * @param target
     */
    public void initSingleShowCase(View target, @StringRes int ResTitle, @StringRes int ResContent) {
        TourGuide mTourGuideHandler = TourGuide.init(mActivity).with(TourGuide.Technique.Click)
                .setPointer(new Pointer())
                .setToolTip(new ToolTip().setTitle(Utils.getString(ResTitle))
                        .setDescription(Utils.getString(ResContent)))
                .setOverlay(new Overlay())
                .playOn(target);
    }

    /**
     * init multi show case
     * @param target
     * @param ResTitle
     * @param ResContent
     */
    public void initSingleShowCase(View[] target, String[] ResTitle, String[] ResContent) {
        /*ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(DELAY_TIME);

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(mActivity, SEQ_SHOW_ID);

        sequence.setConfig(config);
        if (target.length == ResTitle.length && target.length == ResContent.length){
            for (int i = 0; i < target.length; i++){
                sequence.addSequenceItem(target[i],
                        ResTitle[i], ResContent[i], Utils.getString(R.string.text_ok));
            }

            sequence.start();
        }*/

    }

    /**
     * File taken
     * @param fileTaken
     */
    protected void updatePhoto(String fileTaken){

    }

    /**
     * Request update tablet log
     * @param tabletActionLogDTO
     */
    protected void requestUpdateLog(TabletActionLogDTO tabletActionLogDTO) {
        CommonPresenter.getInstance().requestUpdateLog(tabletActionLogDTO);
    }
}
