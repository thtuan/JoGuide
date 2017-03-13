package com.boot.accommodation.vp.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;
import com.boot.accommodation.R;
import com.boot.accommodation.dto.view.StatisticsDTO;
import com.boot.accommodation.dto.view.StatisticsViewDTO;
import com.boot.accommodation.listener.OnEventControl;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 11:29 AM 17-06-2016
 */
public class StatisticsAdapter extends ExpandableRecyclerAdapter<StatisticsAdapter.StatisticsListItem>{
//    public static final int TYPE_SUGGEST = 199;
    public static final int TYPE_INFOR = 200;
    List<StatisticsListItem> listItems = new ArrayList<>();

    protected OnEventControl listener; // listener
    public void setListener(OnEventControl listener) {
        this.listener = listener;
    }

    public StatisticsAdapter(Context context, StatisticsViewDTO statisticsviewDTO) {
        super(context);
        setItems(getListItem(statisticsviewDTO));
    }

    /**
     * Set data
     * @param statisticsviewDTO
     */
    public void setData(StatisticsViewDTO statisticsviewDTO){
        setItems(getListItem(statisticsviewDTO));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new StatisticsHeader(inflate(R.layout.item_header_statistics, parent));
            case TYPE_INFOR:
                return new StatisticsView((inflate(R.layout.item_statistics_infor, parent)));
            default:
                return new StatisticsView((inflate(R.layout.item_statistics_infor, parent)));
        }
    }

    @Override
    public void onBindViewHolder(ExpandableRecyclerAdapter.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ((StatisticsHeader) holder).bind(position);
                break;
            case TYPE_INFOR:
                ((StatisticsView) holder).bind(position);
                break;
        }

    }

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        switch (viewType){
//            case TYPE_HEADER:
//                return new StatisticsHeader(inflate(R.layout.item_header_statistics,parent));
//            case TYPE_DETAIL:
//                return new StatisticsView((inflate(R.layout.item_statistics_infor,parent)));
//            default:
//                return new StatisticsView((inflate(R.layout.item_statistics_infor,parent)));
//        }
//    }

//    @Override
//    public void onBindViewHolder(ExpandableRecyclerAdapter.ViewHolder holder, int position) {
//        switch (getItemViewType(position)){
//            case TYPE_HEADER:
//                ((StatisticsHeader)holder).bind(position);
//                break;
//            case TYPE_DETAIL:
//                ((StatisticsView)holder).bind(position);
//                break;
//        }
//    }


    public static class StatisticsListItem extends ExpandableRecyclerAdapter.ListItem{
        private String header;
        private int value;
        private boolean isFeedback;
        private StatisticsDTO statisticsDTO;
        public StatisticsListItem(String header, boolean isFeedback,int value) {
            super(TYPE_HEADER);
            this.header = header;
            this.isFeedback = isFeedback;
            this.value = value;
        }
        public StatisticsListItem(StatisticsDTO statisticsDTO){
            super(TYPE_INFOR);
            this.statisticsDTO = statisticsDTO;
        }
    }
    public class StatisticsHeader extends ExpandableRecyclerAdapter.HeaderViewHolder implements View.OnClickListener {
        TextView tvHeader;
        TextView tvValue;
        LinearLayout llViewDetail;
        public StatisticsHeader(View view) {
            super(view, (ImageView) view.findViewById(R.id.ivAccross));
            tvHeader = (TextView) view.findViewById(R.id.tvHeader);
            tvValue = (TextView) view.findViewById(R.id.tvValue);
            llViewDetail = (LinearLayout)view.findViewById(R.id.llViewDetail);
            llViewDetail.setOnClickListener(StatisticsHeader.this);
        }
        public void bind(int position){
            super.bind(position);
            tvHeader.setText(visibleItems.get(position).header);
            tvValue.setText(""+visibleItems.get(position).value);
            tvValue.setTag(position);
        }

        @OnClick(R.id.llViewDetail)
        public void onClick(View v) {
            int posti = (Integer) tvValue.getTag();
            StatisticsDTO dto = visibleItems.get(posti).statisticsDTO;
            boolean isFeedback = visibleItems.get(posti).isFeedback;
            int action = 0;
            switch (v.getId()) {
                case R.id.llViewDetail:
                    if (visibleItems.get(posti).value != 0) {
                        if (isFeedback) {
                            action = StatisticsActivity.ACTION_VIEW_FEEDBACK;
                        } else {
                            action = StatisticsActivity.ACTION_VIEW_LISTTOUR;
                        }
                    }
                    break;
            }
            if (listener != null ) {
                listener.onEventControl(action, dto, v, posti);
            }

        }
    }
    public class StatisticsView extends ExpandableRecyclerAdapter.ViewHolder {
        private TextView tvTitle; // title
        private TextView tvValue; // value
        public StatisticsView(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvValue = (TextView) view.findViewById(R.id.tvValue);
        }
        public void bind(int position){
            tvTitle.setText(visibleItems.get(position).statisticsDTO.getTitle());
            tvValue.setText(""+visibleItems.get(position).statisticsDTO.getValue());
        }
    }

    /**
     * get gia tri
     * @param statisticsViewDTO
     * @return
     */
    private List<StatisticsListItem> getListItem(StatisticsViewDTO statisticsViewDTO){
        listItems.clear();
        listItems.add(new StatisticsListItem(Utils.getString(R.string.text_number_tour_of_day) ,false,statisticsViewDTO
                 .getNumTour()));
        for (StatisticsDTO statisticsDTO : statisticsViewDTO.getStatistics()){
            listItems.add(new StatisticsListItem(statisticsDTO));
        }
        listItems.add(new StatisticsListItem(Utils.getString(R.string.text_number_feedback),true,statisticsViewDTO
                .getNumFeedback()));
        return listItems;
    }

}
