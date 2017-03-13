package com.boot.accommodation.vp.promotion;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.PromotionDealDTO;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * PromotionDealInfoActivity
 *
 * @author tuanlt
 * @since 11:51 PM 12/13/16
 */
public class PromotionDealInfoActivity extends BaseActivity {

    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.wvPromotionDealInfo)
    WebView wvPromotionDealInfo;
    @Bind(R.id.tvPromotionDealName)
    TextView tvPromotionDealName;

    private PromotionDealDTO promotionDealDTO = new PromotionDealDTO(); //Promotion deal

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_deal_info);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * Init view
     */
    private void initView() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            promotionDealDTO = b.getParcelable(Constants.IntentExtra.PROMOTION_DEAL);
        }
        tvTitle.setText(StringUtil.getString(R.string.text_promotion_detail));
        wvPromotionDealInfo.loadDataWithBaseURL(null,
                StringUtil.getHtmlResizeImage(promotionDealDTO.getDescription()), "text/html", "utf-8", null);
        tvPromotionDealName.setText(promotionDealDTO.getName());
    }

    @OnClick({R.id.ivBack, R.id.ivNext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivNext:
                Utils.openLink(this, promotionDealDTO.getPartnerSite() );
                break;
        }
    }

}
