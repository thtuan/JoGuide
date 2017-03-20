package com.boot.accommodation.vp.tourguide;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.common.control.JoCoEditText;
import com.boot.accommodation.dto.view.TourGuideDTO;
import com.boot.accommodation.listener.OnEditTextControlListener;
import com.boot.accommodation.listener.OnEventControl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thtuan on 3/14/17.
 */

public class TourguideFragment extends BaseFragment implements OnEventControl, View.OnClickListener, TourGuideFragmentMgr{
    private static final TourguideFragment instance = new TourguideFragment();
    private List<TourGuideDTO> listTourguide = new ArrayList<>();
    private TourguideAdapter adapter;
    private RecyclerView rvTourguide;
    private JoCoEditText etSearch;
    private ImageView ivFilter;
    private TourGuideFilterAdapter filterAdapter;
    private TourGuideFragmentPresenterMgr tourGuideFragmentPresenterMgr;
    public static final int PROFESSIONAL = 1;
    public static final int NATIVE = 2;
    public static final int STUDENT = 3;

    public static TourguideFragment newInstance(){
        return instance;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_tourguide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tourGuideFragmentPresenterMgr = new TourGuideFragmentPresenter(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTourguide = (RecyclerView) view.findViewById(R.id.rvTourguide);
        etSearch = (JoCoEditText) view.findViewById(R.id.etSearch);
        ivFilter = (ImageView) view.findViewById(R.id.ivFilter);
        etSearch.setImageSearchVisible( R.drawable.ic_search_blue, true);
        etSearch.setImageClearVisibile(R.drawable.ic_clear_grey, true);
        ivFilter.setOnClickListener(this);
        //get event : enter from keyboard for search
        etSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    handleSearch(etSearch.getText().toString());
//                    Utils.hideKeyboardInput(, etSearch);
                    return true;
                }
                return false;
            }
        });
        etSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Utils.hideKeyboardInput(T.this, etSearch);
                handleSearch(etSearch.getText().toString());
            }
        });
        etSearch.setListener(new OnEditTextControlListener() {
            @Override
            public void onTouchControl() {
                if (filterAdapter != null) {
                    etSearch.showDropDown();
                } else {
                    renderArea(tourGuideFragmentPresenterMgr.getData());
//                    get filter
//                    accommodationPresenterMgr.getAreaFilter();
                }
            }

            @Override
            public void onClear() {

            }
        });
        listTourguide = tourGuideFragmentPresenterMgr.getData();
        updateData(listTourguide);

    }

    private void renderArea(List<TourGuideDTO> tourguides){
        if (filterAdapter == null){
            filterAdapter = new TourGuideFilterAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, tourguides);
            etSearch.setAdapter(filterAdapter);
        }
    }
    private void handleSearch(String stringFilter) {
        // send request
        listTourguide = tourGuideFragmentPresenterMgr.getData();
        adapter.getFilter().filter(stringFilter);
    }

    private void updateData(List<TourGuideDTO> listTourguide) {
        if (adapter == null){
            adapter = new TourguideAdapter(listTourguide);
            adapter.setListener(this);
            rvTourguide.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            rvTourguide.setAdapter(adapter);
        }else {
            adapter.setData(listTourguide);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onEventControl(int action, Object item, View view, int position) {
        Intent intent = new Intent(getContext(), TourGuideDetailActivity.class);
        intent.putExtra("data", (TourGuideDTO)item);
        getContext().startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivFilter:
                showFilterDialog();
                break;
        }
    }

    private void handleFilter(int type) {
        switch (type){
            case PROFESSIONAL:
                // send request
                updateData(tourGuideFragmentPresenterMgr.getListFilter(PROFESSIONAL));
                break;
            case NATIVE:
                // send request
                updateData(tourGuideFragmentPresenterMgr.getListFilter(NATIVE));
                break;
        }

    }

    private void showFilterDialog(){
        LayoutInflater li = LayoutInflater.from(getContext());
        final View layout = li.inflate(R.layout.dialog_filter, null);
        AlertDialog.Builder buider = new AlertDialog.Builder(getContext());
        buider.setTitle("Tour guide filter");
        buider.setView(layout);
        buider.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RadioGroup group = (RadioGroup) layout.findViewById(R.id.rbFilter);
                switch (group.getCheckedRadioButtonId()){
                    case R.id.rbPro:
                        updateData(tourGuideFragmentPresenterMgr.getListFilter(PROFESSIONAL));
                        break;
                    case R.id.rbNative:
                        updateData(tourGuideFragmentPresenterMgr.getListFilter(NATIVE));
                        break;
                    case R.id.rbStudent:
                        updateData(tourGuideFragmentPresenterMgr.getListFilter(STUDENT));
                        break;
                }
            }
        });
        buider.show();
    }
}
