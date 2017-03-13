package com.boot.accommodation.vp.home;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.dto.view.LanguagesDTO;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
/**
 * setting language adapter
 *
 * @author Vuong-bv
 * @since: 9/8/2016
 */
public class ChangeLanguagesAdapter extends BaseRecyclerViewAdapter<LanguagesDTO, ChangeLanguagesAdapter.ViewHolder> {

    public ChangeLanguagesAdapter(List<LanguagesDTO> items) {
        super(items);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_change_languages;
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        holder.bindData(items.get(position), position);
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        @Bind(R.id.rbCheck)
        RadioButton rbCheck;
        @Bind(R.id.tvNameLanguage)
        TextView tvNameLanguage;
        @Bind(R.id.rlChangeLanguage)
        RelativeLayout rlChangeLanguage;

        ViewHolder(View view) {
            super(view);
        }

        public void bindData(LanguagesDTO data, int position) {
            this.data = data;
            this.position = position;
            tvNameLanguage.setText(data.getName());
            //check language current
            LanguagesDTO dtoLang = JoCoApplication.getInstance().loadLanguages();
            if (data.getValue().equals(dtoLang.getValue())) {
                rbCheck.setChecked(true);
            } else {
                rbCheck.setChecked(false);
            }
        }

        @OnClick({R.id.rlChangeLanguage, R.id.rbCheck})
        public void OnClick(View v) {
            int action = 0;
            switch (v.getId()) {
                case R.id.rlChangeLanguage:
                case R.id.rbCheck:
                    action = ChangeLanguageActivity.ACTION_CHOOSE_LANGUAGES;
                    break;
            }
            if (listener != null && data != null) {
                listener.onEventControl(action, data, view, position);
            }
        }
    }
}
