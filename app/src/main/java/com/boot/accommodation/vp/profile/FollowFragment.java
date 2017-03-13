package com.boot.accommodation.vp.profile;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.boot.accommodation.R;
import com.boot.accommodation.common.adapter.SimpleRecyclerAdapter;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.constant.VersionModel;

import java.util.ArrayList;
import java.util.List;

public class FollowFragment extends BaseFragment {

    public static FollowFragment newInstance() {
        return new FollowFragment();
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < VersionModel.data.length; i++) {
            list.add(VersionModel.data[i]);
        }

        SimpleRecyclerAdapter adapter = new SimpleRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);
    }

}
