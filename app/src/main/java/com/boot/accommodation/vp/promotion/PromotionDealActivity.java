package com.boot.accommodation.vp.promotion;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.CategoryItemDTO;
import com.boot.accommodation.dto.view.PromotionDealDTO;
import com.boot.accommodation.dto.view.PromotionDealFilterDTO;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.category.ListCategoryAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Promotion activity
 *
 * @author tuanlt
 * @since 5:10 PM 12/12/16
 */
public class PromotionDealActivity extends BaseActivity implements PromotionViewMgr {

    @Bind(R.id.tvTitle)
    TextView tvTitle;
//    @Bind(R.id.etPromotionType)
//    JoCoEditText etPromotionType;
    @Bind(R.id.rvPromotionDeal)
    RecyclerView rvPromotionDeal;
    private PromotionPresenterMgr promotionPresenterMgr; //Promotion presenter
    private ListCategoryAdapter promotionDealTypeAdapter; //Promotion deal type
    private PromotionDealFilterDTO promotionDealFilterDTO = new PromotionDealFilterDTO(); //Promotion  filter
    private PromotionDealAdapter promotionDealAdapter; //Promotion deal adapter
    AlertDialog alertDialog; //Alert dialog

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_deal);
        enableSlidingMenu(false);
        initView();
        initData();
    }

    /**
     * Init view
     */
    private void initView() {
        enableRefresh(true);
        rvPromotionDeal.setLayoutManager(new LinearLayoutManager(this));
        rvPromotionDeal.setHasFixedSize(true);
        tvTitle.setText(StringUtil.getString(R.string.text_promotion));
    }

    /**
     * Init data
     */
    private void initData() {
        showProgress();
        if (promotionPresenterMgr == null) {
            promotionPresenterMgr = new PromotionPresenter(this);
        }
        promotionPresenterMgr.getPromotionDeal(promotionDealFilterDTO);
    }

    @OnClick({R.id.ivBack, R.id.ivFilter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivFilter:
                if(alertDialog == null) {
                    promotionPresenterMgr.getPromotionDealType();
                } else {
                    showPopupDealType(null);
                }
                break;
        }
    }

    @Override
    public void renderPromotionType(final List<CategoryItemDTO> categoryItemDTOs) {
        showPopupDealType(categoryItemDTOs);
    }

    /**
     * Get promotion deal
     */
    private void getPromotionDeal() {
        showProgress();
        Utils.hideSoftKeyboard(PromotionDealActivity.this);
        promotionPresenterMgr.getPromotionDeal(promotionDealFilterDTO);
    }

    @Override
    public void renderLayout(List<PromotionDealDTO> promotionDealDTOs) {
        closeProgress();
        stopRefresh();
        if (promotionDealAdapter == null) {
            promotionDealAdapter = new PromotionDealAdapter(promotionDealDTOs);
            promotionDealAdapter.setListener(this);
            rvPromotionDeal.setAdapter(promotionDealAdapter);
        } else {
            promotionDealAdapter.setData(promotionDealDTOs);
        }
        promotionDealAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(int position) {
        promotionPresenterMgr.getMorePromotionDeal(promotionDealAdapter, promotionDealFilterDTO);
    }

    @Override
    public void handleError(int errorCode, String error) {
        closeProgress();
        super.handleError(errorCode, error);
    }

    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action) {
            case Constants.ActionEvent.NOTIFY_REFRESH: {
                showProgress();
                promotionPresenterMgr.getPromotionDeal(promotionDealFilterDTO);
                break;
            }
        }
    }

    @Override
    public void onEventControl(int action, Object item, View View, int position) {
        switch (action) {
            case Constants.ActionEvent.ACTION_GO_TO_PROMOTION_DEAL_INFO:
                PromotionDealDTO data = (PromotionDealDTO)item;
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.IntentExtra.PROMOTION_DEAL, data);
                goNextScreen(PromotionDealInfoActivity.class,bundle);
                break;
            case Constants.ActionEvent.ACTION_CHOOSE_CATEGORY:
                alertDialog.dismiss();
                CategoryItemDTO categoryItemDTO = (CategoryItemDTO)item;
                if (!StringUtil.isNullOrEmpty(categoryItemDTO.getValue())) {
                    promotionDealFilterDTO.setDealType(Integer.parseInt(categoryItemDTO.getValue()));
                } else {
                    promotionDealFilterDTO.setDealType(null);
                }
                getPromotionDeal();
                break;
        }
    }

    /**
     * Show popup deal type
     */
    private void showPopupDealType(final List<CategoryItemDTO> categoryItemDTOs) {
        if (alertDialog == null) {
            final Context context = this;
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.popup_best_time_go, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setView(promptsView);
            final RecyclerView recyclerView = (RecyclerView) promptsView.findViewById(R.id.rvListMonth);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            TextView tvTitle = (TextView) promptsView.findViewById(R.id.tvTitle);
            tvTitle.setText(StringUtil.getString(R.string.text_choose_promotion_type));
            //render list category
            if (promotionDealTypeAdapter == null) {
                CategoryItemDTO itemAll = new CategoryItemDTO();
                itemAll.setName(StringUtil.getString(R.string.all));
                categoryItemDTOs.add(0, itemAll);
                promotionDealTypeAdapter = new ListCategoryAdapter(categoryItemDTOs);
                promotionDealTypeAdapter.setShowCheck(false);
                promotionDealTypeAdapter.setListener(this);
                recyclerView.setAdapter(promotionDealTypeAdapter);
            }
            // set dialog message
            alertDialogBuilder.setCancelable(true).setNegativeButton(StringUtil.getString(R.string.text_cancel), new
                    DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            // create alert dialog
            alertDialog = alertDialogBuilder.create();
        }
        alertDialog.show();
    }


}
