package com.boot.accommodation.vp.tour;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.dto.view.TourVoteDTO;
import com.boot.accommodation.dto.view.VoteTypeDTO;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 3:36 PM 20-06-2016
 */
public class TourVoteAdapter extends BaseRecyclerViewAdapter<TourVoteDTO, TourVoteAdapter.ViewHolder> {

    public TourVoteAdapter(List<TourVoteDTO> items) {
        super(items);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_tour_vote;
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        holder.bind(items.get(position),position);
    }

    public class ViewHolder extends BaseRecyclerViewHolder {

        @Bind(R.id.tvTitle)
        TextView tvTitle;
        @Bind(R.id.radioExcellent)
        RadioButton radioExcellent;
        @Bind(R.id.radioGood)
        RadioButton radioGood;
        @Bind(R.id.radioFair)
        RadioButton radioFair;
        @Bind(R.id.radioBad)
        RadioButton radioBad;
        @Bind(R.id.radioGroup)
        RadioGroup radioGroup;
        @Bind(R.id.llItem)
        LinearLayout llItem;

        @OnClick({R.id.radioExcellent,R.id.radioGood,R.id.radioFair,R.id.radioBad})
        public void onClick() {
            TourVoteDTO item = ((TourVoteDTO)data);
            switch (radioGroup.getCheckedRadioButtonId()){
                case R.id.radioExcellent:
                    item.setVoteValue(VoteTypeDTO.EXCELLENT.getValue());
                    break;
                case R.id.radioGood:
                    item.setVoteValue(VoteTypeDTO.GOOD.getValue());
                    break;
                case R.id.radioFair:
                    item.setVoteValue(VoteTypeDTO.FAIR.getValue());
                    break;
                case R.id.radioBad:
                    item.setVoteValue(VoteTypeDTO.BAD.getValue());
                    break;
                default:
                    break;
            }
        }
        public ViewHolder(View view) {
            super(view);
        }

        void bind(TourVoteDTO data, int position) {
            this.data = data;
            this.position = position;
            tvTitle.setText(data.getTitle());
        }
    }
}
