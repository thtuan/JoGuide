package com.boot.accommodation.vp.tour;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.dto.view.TouristLeaderDTO;

import butterknife.Bind;

/**
 * TouristLeaderInfoFragment
 *
 * @author Vuong-bv
 * @since: 5/19/2016
 */
public class TouristLeaderInfoFragment extends BaseFragment implements TouristLeaderViewMgr {

    @Bind(R.id.tvDescrible)
    TextView tvDescrible;
    @Bind(R.id.tvLeaderSkill)
    TextView tvLeaderSkill;
    private TouristLeaderPresenterMgr leaderPresenterMgr;
    TouristLeaderDTO touristLeaderDTO = new TouristLeaderDTO();
    private String touristId;

    public static TouristLeaderInfoFragment newInstance(Bundle data) {
        return new TouristLeaderInfoFragment();
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_leader_info;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showProgress();
        innitData();
    }

    /**
     * Khoi tao data
     */
    private void innitData() {
        leaderPresenterMgr = new TouristLeaderPresenter(this);
    }

    @Override
    public void renderLayout(TouristLeaderDTO leaderDTO) {
        closeProgress();
        tvDescrible.setText(leaderDTO.getDescribe());
        tvLeaderSkill.setText(leaderDTO.getSkill());
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        closeProgress();
        switch (errorCode){
            default:
                handleError(errorCode,error);
        }
    }

}
