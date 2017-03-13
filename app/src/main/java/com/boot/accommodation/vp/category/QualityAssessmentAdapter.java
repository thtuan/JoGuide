package com.boot.accommodation.vp.category;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;
import com.boot.accommodation.R;
import com.boot.accommodation.dto.view.QualityAssessmentViewDTO;
import com.boot.accommodation.dto.view.QuantityAssessmentValueDTO;
import com.boot.accommodation.dto.view.QualityAssessmentDTO;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Danh gia chat luong
 *
 * @author tuanlt1989
 * @since 11:29 AM 17-06-2016
 */
public class QualityAssessmentAdapter extends ExpandableRecyclerAdapter<QualityAssessmentAdapter.QualityAssessmentListItem>{
    public static final int TYPE_DETAIL = 1001; // chi tiet
    public static final int TYPE_HEADER_DETAIL = 1002; // header
    public QualityAssessmentAdapter( Context context, QualityAssessmentViewDTO viewDTO ) {
        super(context);
        setItems(getListItem(viewDTO));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new QualityAssessmentHeader(inflate(R.layout.item_header_quality_assessment, parent));
            case TYPE_DETAIL:
                return new QualityAssessmentDetail((inflate(R.layout.item_detail_quanlity_assessment, parent)));
            default:
                return new QualityAssessmentDetail((inflate(R.layout.item_header_detail_quanlity_assessment, parent)));
        }
    }

    @Override
    public void onBindViewHolder(ExpandableRecyclerAdapter.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ((QualityAssessmentHeader) holder).bind(position);
                break;
            case TYPE_DETAIL:
                ((QualityAssessmentDetail) holder).bind(position);
                break;
        }

    }


    static class QualityAssessmentListItem extends ExpandableRecyclerAdapter.ListItem{
        private String header;
        private QualityAssessmentDTO tourAssessmentDTO;
        private String value;
        public QualityAssessmentListItem( String header, String value) {
            super(TYPE_HEADER);
            this.header = header;
            this.value = value;
        }
        public QualityAssessmentListItem( QualityAssessmentDTO tourAssessmentDTO){
            super(TYPE_DETAIL);
            this.tourAssessmentDTO = tourAssessmentDTO;
        }

        public QualityAssessmentListItem(){
            super(TYPE_HEADER_DETAIL);
        }
    }

    /**
     * Header title quantity assessment
     */
    public class QualityAssessmentHeader extends HeaderViewHolder  {
        TextView tvHeader;
        TextView tvValue;
        public QualityAssessmentHeader( View view) {
            super(view, (ImageView) view.findViewById(R.id.ivAccross));
            tvHeader = (TextView) view.findViewById(R.id.tvHeader);
            tvValue = (TextView) view.findViewById(R.id.tvValue);
        }
        /**
         * Binding data
         * @param position
         */
        public void bind(int position){
            super.bind(position);
            tvHeader.setText(visibleItems.get(position).header);
            if(!StringUtil.isNullOrEmpty(visibleItems.get(position).value)){
                tvValue.setText(visibleItems.get(position).value);
            }
        }


    }
    public class QualityAssessmentDetail extends ViewHolder {
        private TextView tvTitle; // title
        private TextView tvExcellent; // value
        private TextView tvGood; // value
        private TextView tvFair; // value
        private TextView tvBad; // value
        public QualityAssessmentDetail( View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvExcellent = (TextView) view.findViewById(R.id.tvExcellent);
            tvGood = (TextView) view.findViewById(R.id.tvGood);
            tvFair = (TextView) view.findViewById(R.id.tvFair);
            tvBad = (TextView) view.findViewById(R.id.tvBad);
        }
        public void bind(int position){
            tvTitle.setText(visibleItems.get(position).tourAssessmentDTO.getName());
            tvExcellent.setText(StringUtil.parseMoneyWithTokenZero(visibleItems.get(position).tourAssessmentDTO
                .getVote().getExcellent()+""));
            tvGood.setText(StringUtil.parseMoneyWithTokenZero(visibleItems.get(position).tourAssessmentDTO
                .getVote().getGood()+""));
            tvFair.setText(StringUtil.parseMoneyWithTokenZero(visibleItems.get(position).tourAssessmentDTO
                .getVote().getFair()+""));
            tvBad.setText(StringUtil.parseMoneyWithTokenZero(visibleItems.get(position).tourAssessmentDTO
                .getVote().getBad()+""));
        }
    }

    public class QuantityAssessmentDetailHeader extends ViewHolder {
        public QuantityAssessmentDetailHeader( View view) {
            super(view);
        }
    }

    /**
     * get gia tri
     * @return
     */
    private List<QualityAssessmentListItem> getListItem( QualityAssessmentViewDTO qualityAssessmentViewDTO){
        List<QualityAssessmentListItem> listItems = new ArrayList<>();
        QuantityAssessmentValueDTO statusAssessmentDTO = new QuantityAssessmentValueDTO();
        qualityAssessmentViewDTO.setTotalQualityAssessment(statusAssessmentDTO);
//        ArrayList<QualityAssessmentDTO> lst = new ArrayList<>();
//        QualityAssessmentDTO item1 = new QualityAssessmentDTO();
//        item1.setTitle("Tour program");
//        item1.getStatusAssessmentDTO().getExcellent();
//        item1.getStatusAssessmentDTO().getBad();
//        item1.getStatusAssessmentDTO().getGood();
//        item1.getStatusAssessmentDTO().getFair();
//        QualityAssessmentDTO item2 = new QualityAssessmentDTO();
//        item2.setTitle("Tour guide");
//        item2.getStatusAssessmentDTO().getExcellent();
//        item2.getStatusAssessmentDTO().getBad();
//        item2.getStatusAssessmentDTO().getGood();
//        item2.getStatusAssessmentDTO().getFair();
//        QualityAssessmentDTO item3 = new QualityAssessmentDTO();
//        item3.setTitle("Hotel");
//        item3.getStatusAssessmentDTO().getExcellent();
//        item3.getStatusAssessmentDTO().getBad();
//        item3.getStatusAssessmentDTO().getGood();
//        item3.getStatusAssessmentDTO().getFair();
//        lst.add(item1);
//        lst.add(item2);
//        lst.add(item3);
//
//        qualityAssessmentViewDTO.setQuality(lst);
        listItems.add(new QualityAssessmentListItem(Utils.getString(R.string.text_quality_assessment_tour),""));
        listItems.add(new QualityAssessmentListItem());
        for (QualityAssessmentDTO statisticsDTO : qualityAssessmentViewDTO.getQuality()){
            listItems.add(new QualityAssessmentListItem(statisticsDTO));
        }
        return listItems;
    }

}
