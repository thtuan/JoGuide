package com.boot.accommodation.vp.profile;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.TouristDTO;
import com.boot.accommodation.dto.view.TripItemDTO;

import java.util.List;

import butterknife.Bind;

/**
 * Admin collection fragment
 *
 * @author Vuong-bv
 * @since: 13/07/2016
 */
public class AdminCollectionFragment extends BaseFragment implements AdminCollectionFragmentMgr {


    @Bind(R.id.rvCollection)
    RecyclerView rvCollection;
    private AdminCollectionPresenterMgr adminCollectionPresenterMgr;
    private AdminCollectionAdapter adminCollectionAdapter;
    private TouristDTO touristDTO;
    public static AdminCollectionFragment newInstance(Bundle bundle) {
        AdminCollectionFragment t = new AdminCollectionFragment();
        t.setArguments(bundle);
        return t;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_admin_collection;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle b = getArguments();
        touristDTO = b.getParcelable(Constants.IntentExtra.TOURIST_DTO);
        initView();
        initData();
    }

    private void initView() {
        rvCollection.setLayoutManager(new LinearLayoutManager(mActivity));
        rvCollection.setHasFixedSize(true);
    }

    private void initData() {
        adminCollectionPresenterMgr = new AdminCollectionPresenter(this);
        adminCollectionPresenterMgr.getCollection(touristDTO.getId());
        showProgress();
    }

    @Override
    public void renderLayout(List<TripItemDTO> mListTrips) {
        if( adminCollectionAdapter == null) {
            adminCollectionAdapter = new AdminCollectionAdapter(mListTrips);
            adminCollectionAdapter.setListener(this);
            adminCollectionAdapter.setEnableLoadMore(true);
            rvCollection.setAdapter(adminCollectionAdapter);
        } else {
            adminCollectionAdapter.setData(mListTrips);
        }
        adminCollectionAdapter.notifyDataSetChanged();
        closeProgress();
        stopRefresh();
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        closeProgress();
        switch (errorCode){
            default:
                handleError(errorCode,error);
        }
    }

    @Override
    public void onLoadMore(int position) {
        adminCollectionPresenterMgr.getMoreCollection(adminCollectionAdapter, touristDTO.getId());
    }

    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action) {
            case Constants.ActionEvent.NOTIFY_REFRESH:
                adminCollectionPresenterMgr.getCollection(touristDTO.getId());
                break;
        }
    }
}
