package com.boot.accommodation.vp.tour;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.FollowItemDTO;
import com.boot.accommodation.dto.view.ProfileUserViewDTO;
import com.boot.accommodation.dto.view.TouristDTO;
import com.boot.accommodation.dto.view.UserItemDTO;
import com.boot.accommodation.listener.OnEventControl;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FollowActivity extends BaseActivity implements FollowViewMgr, OnEventControl {
    public static final int ACTION_VIEW_TOURIST_INFO = 1000;//  action for even click
    public static final int ACTION_ADD_FRIEND = 1001;//  action for even click
    public static final int TYPE_FOLLOWER = 1; //  type for get data
    public static final int TYPE_FOLLOWING = 2; //  type for get data

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.ll_header)
    RelativeLayout llHeader;
    @Bind(R.id.rvFolowers)
    RecyclerView rvFolowers;
    ProfileUserAdapter profileUserAdapter;
    ProfileUserViewDTO profileUserDTO = new ProfileUserViewDTO();
    List<FollowItemDTO> list = new ArrayList<>();
    TouristDTO touristDTO = new TouristDTO();
    FollowPresenterMgr followPresenterMgr;
    int type; //Type follower or following
    boolean isFriend = false;//check user add or un friend
    @Bind(R.id.tvTitle)
    TextView tvTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    /**
     * Init view
     */
    private void initView() {
        if (TYPE_FOLLOWER == type) {
            followPresenterMgr.getFollower(touristDTO.getId());
            tvTitle.setText(Utils.getString(R.string.text_follower));
            showProgress();
        } else if (TYPE_FOLLOWING == type) {
            followPresenterMgr.getFollowing(touristDTO.getId());
            tvTitle.setText(Utils.getString(R.string.text_following));
            showProgress();
        }
    }

    /**
     * Init data
     */
    private void initData() {
        touristDTO = getIntent().getParcelableExtra(Constants.IntentExtra.TOURIST_DTO);
        type = getIntent().getIntExtra(Constants.IntentExtra.TYPE_FOLLOW, -1);
        followPresenterMgr = new FollowPresenter(this);
        rvFolowers.setHasFixedSize(true);
        rvFolowers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @OnClick({R.id.iv_back, R.id.ll_header, R.id.rvFolowers})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_header:
                break;
            case R.id.rvFolowers:
                break;
        }
    }

    @Override
    public void renderLayout(List<FollowItemDTO> listFollower) {
        list = listFollower;
        if (profileUserAdapter == null) {
            profileUserAdapter = new ProfileUserAdapter(listFollower);
            profileUserAdapter.setListener(this);
            rvFolowers.setAdapter(profileUserAdapter);
        }
            /*//check if this is user login : show follower
            if (touristDTO.getId() == Long.valueOf(JoCoApplication.getInstance().getProfile().getUserData().getId())) {
                int num = listFollower.size();
                tvNumberFolower.setText(num + Constants.STR_SPACE + StringUtil.getString(R.string.text_title_follower_user));
            } else {
                llListFolow.setVisibility(View.GONE);
            }*/
        profileUserAdapter.notifyDataSetChanged();
        closeProgress();
    }

    @Override
    public void requestFollowSuccess(boolean check) {
        //check if user follow another user at their profile or list follow, at list handle here, at their profile go
        // to ProfileActivity to handle
        if (check == true) {
            //check un or add fiend
            if (isFriend == true) {
                showMessage(StringUtil.getString(R.string.text_un_friend_success));
            } else {
                showMessage(StringUtil.getString(R.string.text_add_friend_success));
            }
            closeProgress();
        } else {
            /*if (mActivity instanceof ProfileActivity) {
                ((ProfileActivity) getActivity()).updateButonAddFriend();
                closeProgress();
            }*/
            refreshFollow(touristDTO.getId());
        }
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        closeProgress();
        switch (errorCode) {
            default:
                handleError(errorCode, error);
        }
    }

    /**
     * refresh follow
     *
     * @param idUser
     */
    private void refreshFollow(long idUser) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == idUser) {
                list.get(i).setIsFriend(!list.get(i).getIsFriend());
            }
            break;
        }
        renderLayout(list);
    }

    /**
     * Handle user follow
     *
     * @param userId
     * @param followerUserId
     */
    public void handleFollow(long userId, long followerUserId, FollowItemDTO dto) {
        showProgress();
        followPresenterMgr.requestFollow(userId, followerUserId, dto);
    }

    @Override
    public void onEventControl(int action, Object item, View View, int position) {
        final FollowItemDTO dto = (FollowItemDTO) item;
        isFriend = dto.getIsFriend();
        switch (action) {
            case ACTION_VIEW_TOURIST_INFO:
                TouristDTO tourist = new TouristDTO();
                tourist.setId(dto.getId());
                tourist.setName(dto.getName());
                tourist.setImage(dto.getImage());
                tourist.setUserType(UserItemDTO.TYPE_TOURIST);
                tourist.setIsFriend(dto.getIsFriend());
                Bundle bundle = putData(tourist);
                goNextScreen(ProfileActivity.class, bundle);
                break;
            case ACTION_ADD_FRIEND:
                handleFollow(dto.getId(), Long.valueOf(JoCoApplication.getInstance().getProfile().getUserData().getId()), dto);
                break;
        }
    }

    /**
     * method put data to another activity
     *
     * @param dto
     * @return
     */
    public Bundle putData(TouristDTO dto) {
        Bundle data = new Bundle();
        data.putParcelable(Constants.IntentExtra.TOURIST_DTO, dto);
        return data;
    }
}
