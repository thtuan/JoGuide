package com.boot.accommodation.vp.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.LanguagesDTO;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * setting activity
 *
 * @author Vuong-bv
 * @since: 9/8/2016
 */
public class ChangeLanguageActivity extends BaseActivity {

    public static final int ACTION_CHOOSE_LANGUAGES = 100;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.rvLanguages)
    RecyclerView rvLanguages;
    ChangeLanguagesAdapter languagesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_languages);
        ButterKnife.bind(this);
        initView();
        renderLayour(JoCoApplication.getInstance().getListLanguage());
    }

    /**
     * Khoi tao view
     */
    private void initView() {
        rvLanguages.setLayoutManager(new LinearLayoutManager(this));
        rvLanguages.setHasFixedSize(true);
    }

    @Override
    public void onEventControl(int action, Object item, View View, int position) {
        switch (action) {
            case ACTION_CHOOSE_LANGUAGES:
                JoCoApplication.getInstance().saveLanguages(((LanguagesDTO) item).getValue());
                sendBroadcast(Constants.ActionEvent.ACTION_FINISH_ALL_AND_START_ACTIVITY, new Bundle());
                break;
        }
    }

    @OnClick({R.id.ivBack})
    public void OnClick(View v) {
        int action = 0;
        switch (v.getId()) {
            case R.id.ivBack:
                super.onBackPressed();
                break;
        }
    }

    /**
     * renderlayout : how many option about languages
     *
     * @param listLanguages
     */
    private void renderLayour(ArrayList<LanguagesDTO> listLanguages) {
        closeProgress();
        if (languagesAdapter == null) {
            languagesAdapter = new ChangeLanguagesAdapter(listLanguages);
            languagesAdapter.setListener(this);
            rvLanguages.setAdapter(languagesAdapter);
        } else {
            languagesAdapter.setData(listLanguages);
        }
        languagesAdapter.notifyDataSetChanged();
    }

}
