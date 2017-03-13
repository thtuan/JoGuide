package com.boot.accommodation.vp.tour;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;
import com.boot.accommodation.R;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.LocationTypeDTO;
import com.boot.accommodation.dto.view.TourItineraryItemDTO;
import com.boot.accommodation.listener.OnEventControl;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter cua lich trinh tour
 *
 * @author tuanlt
 * @since 10:57 AM 17-05-2016
 */
public class TourItineraryAdapter extends ExpandableRecyclerAdapter<TourItineraryAdapter.TourItineraryItem> {
    public static final int TYPE_TOUR_INFO = 1001; //thong tin tour
    protected OnEventControl listener; // listener
    List<TourItineraryItemDTO> tourItinerary = new ArrayList<>(); // List item itinerary
    private String startDate = ""; // Start date
    public TourItineraryAdapter( Context context,OnEventControl listener ) {
        super(context);
        this.listener = listener;
    }

    /**
     * Set data cho adapter
     */
    public void setData( String startDate, List<TourItineraryItemDTO> lstTourItinerary ){
        tourItinerary = lstTourItinerary;
        this.startDate = startDate;
        List<TourItineraryItem>  lstTourIt =  getListItem(lstTourItinerary);
        setItems(lstTourIt);
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderViewHolder(inflate(R.layout.item_tour_itinerary_header, parent));
            default:
                return new TourItineraryViewHolder(inflate(R.layout.item_tour_itinerary_item, parent));
        }
    }

    @Override
    public void onBindViewHolder( ExpandableRecyclerAdapter.ViewHolder holder, int position ) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ((HeaderViewHolder) holder).bind(position);
                break;
            default:
                ((TourItineraryViewHolder) holder).bind(position);
                break;
        }
    }

    /**
     * Thong tin chi tiet tour
     */
    public class TourItineraryItem extends ExpandableRecyclerAdapter.ListItem {
        private String title; // title
        private String dateFrom; // from date
        TourItineraryItemDTO tourItineraryItemDTO; // dto thong tin tour

        public TourItineraryItem( String group , String date) {
            super(TYPE_HEADER);
            title = group;
            dateFrom = date;
        }

        public TourItineraryItem( TourItineraryItemDTO tourInfoDTO ) {
            super(TYPE_TOUR_INFO);
            this.tourItineraryItemDTO = tourInfoDTO;
        }
    }

    /**
     * Header tour
     */
    public class HeaderViewHolder extends ExpandableRecyclerAdapter.HeaderViewHolder {
        TextView tvDay;
        TextView tvDayValue;
        ImageView ivDay;

        public HeaderViewHolder( View view ) {
            super(view, (ImageView) view.findViewById(R.id.ivDay));
            ivDay = (ImageView) view.findViewById(R.id.ivDay);
            tvDay = (TextView) view.findViewById(R.id.tvDay);
            tvDayValue = (TextView) view.findViewById(R.id.tvDayValue);
        }

        /**
         * Bind du lieu
         *
         * @param position
         */
        public void bind( int position ) {
            tvDay.setText(visibleItems.get(position).title);
            tvDayValue.setText(DateUtil.convertDateWithFormat(visibleItems.get(position).dateFrom,DateUtil.FORMAT_DATE_WITHOUT_TIME_ZONE,
                    DateUtil
                    .FORMAT_DATE));
        }

        @Override
        protected void handleClick() {
            if(TourItineraryAdapter.this.toggleExpandedItems(this.getLayoutPosition(), false)) {
                ExpandableRecyclerAdapter.openArrow(this.ivDay);
            } else {
                ExpandableRecyclerAdapter.closeArrow(this.ivDay);
//                notifyDataSetChanged();
//                notifyItemChanged(this.getLayoutPosition());
            }
//            notifyDataSetChanged();

        }
    }

    public class TourItineraryViewHolder extends ViewHolder implements View.OnClickListener {
        CircularImageView ivPhotoLeft;
        TextView tvNameLeft;
        TextView tvDurationLeft;
        ImageView ivLineLeft;
        ImageView ivLineRight;
        CircularImageView ivPhotoRight;
        TextView tvNameRight;
        TextView tvDurationRight;
        LinearLayout llItineraryRight;
        LinearLayout llItineraryLeft;
        ImageView ivLineTop;
        ImageView ivLineBottom;
        ImageView ivMaskRight;
        ImageView ivMaskLeft;
        ImageView vLeft; // xu li hieu ung click
        ImageView vRight; // xu li hieu ung click

        public TourItineraryViewHolder( View view ) {
            super(view);
            llItineraryLeft = (LinearLayout) view.findViewById(R.id.llItineraryLeft);
            llItineraryRight = (LinearLayout) view.findViewById(R.id.llItineraryRight);
            tvNameLeft = (TextView) view.findViewById(R.id.tvNameLeft);
            tvNameRight = (TextView) view.findViewById(R.id.tvNameRight);
            ivLineTop = (ImageView) view.findViewById(R.id.ivLineTop);
            ivLineBottom = (ImageView) view.findViewById(R.id.ivLineBottom);
            ivPhotoLeft = (CircularImageView) view.findViewById(R.id.ivPhotoLeft);
            ivPhotoRight = (CircularImageView) view.findViewById(R.id.ivPhotoRight);
            tvDurationLeft = (TextView) view.findViewById(R.id.tvDurationLeft);
            tvDurationRight = (TextView) view.findViewById(R.id.tvDurationRight);
            ivMaskRight = (CircularImageView) view.findViewById(R.id.ivMaskRight);
            ivMaskLeft = (CircularImageView) view.findViewById(R.id.ivMaskLeft);
            vLeft = (CircularImageView) view.findViewById(R.id.vLeft);
            vLeft.setOnClickListener(this);
            vRight = (CircularImageView) view.findViewById(R.id.vRight);
            vRight.setOnClickListener(this);
        }

        /**
         * Bind du lieu
         *
         * @param position
         */
        public void bind( int position ) {
            TourItineraryItemDTO tourItineraryItemDTO = visibleItems.get(position).tourItineraryItemDTO;
            String date = "";
            if(visibleItems.get(position).ItemType == TYPE_HEADER){
                return;
            }
            ivLineBottom.setVisibility(View.VISIBLE);
            ivLineTop.setVisibility(View.VISIBLE);
            int realPosition = getRealPosition(position);
            if (tourItineraryItemDTO.getFromDate() != null) {
//                date = DateUtil.convertDateWithFormat(tourItineraryItemDTO.getFromDate(),DateUtil.FORMAT_DATE_WITHOUT_TIME_ZONE,DateUtil
//                        .FORMAT_TIME) + Constants.STR_SUBTRACTION
//                        +  DateUtil.convertDateWithFormat(tourItineraryItemDTO.getToDate(),DateUtil.FORMAT_DATE_WITHOUT_TIME_ZONE,DateUtil
//                        .FORMAT_TIME);
                date = StringUtil.splitStringWithCount(tourItineraryItemDTO.getFromDate(), Constants.STR_COLON, 2) +
                        Constants.STR_SUBTRACTION + StringUtil.splitStringWithCount(tourItineraryItemDTO.getToDate(),
                        Constants.STR_COLON, 2);
            }

            if (realPosition % 2 == 0) {
                llItineraryLeft.setVisibility(View.VISIBLE);
                llItineraryRight.setVisibility(View.INVISIBLE);
                tvNameLeft.setText(tourItineraryItemDTO.getLocation().getName());
                if (LocationTypeDTO.GOOGLE.getValue() == tourItineraryItemDTO.getLocation().getLocationType()){
                    ImageUtil.loadImage(ivPhotoLeft, ImageUtil.getGoogleImageUrlThumb(tourItineraryItemDTO.getLocation()
                            .getPhoto()));
                } else {
                    ImageUtil.loadImage(ivPhotoLeft, ImageUtil.getImageUrlThumb(tourItineraryItemDTO.getLocation().getPhoto()));
                }
                tvDurationLeft.setText(date);
            } else {
                llItineraryRight.setVisibility(View.VISIBLE);
                llItineraryLeft.setVisibility(View.INVISIBLE);
                tvNameRight.setText(tourItineraryItemDTO.getLocation().getName());
                if (LocationTypeDTO.GOOGLE.getValue() == tourItineraryItemDTO.getLocation().getLocationType()){
                    ImageUtil.loadImage(ivPhotoRight, ImageUtil.getGoogleImageUrlThumb(tourItineraryItemDTO.getLocation().getPhoto()));
                } else {
                    ImageUtil.loadImage(ivPhotoRight, ImageUtil.getImageUrlThumb(tourItineraryItemDTO.getLocation().getPhoto()));
                }
                tvDurationRight.setText(date);
            }

            vRight.setTag(position);
            vLeft.setTag(position);
            if(position == visibleItems.size() - 1){ // truong hop item cuoi cung
                ivLineBottom.setVisibility(View.INVISIBLE);
            }
        }

        /**
         * Dem header truoc mot vi tri bat ki trong mang
         * @return
         */
        private int getRealPosition( int position ) {
            int countHeader = 0;
            int realPosition = 0;
            for (int i = 0, size = allItems.size(); i < size; i++) {
                TourItineraryItemDTO visibleItem = visibleItems.get(position).tourItineraryItemDTO;
                TourItineraryItemDTO realItem = allItems.get(i)
                    .tourItineraryItemDTO;
                if (allItems.get(i).ItemType == TYPE_HEADER) {
                    countHeader++;
                }
                // Compare multi variable to avoid loop data
                if (visibleItem != null && realItem != null && visibleItem.getLocation().getId() ==
                    realItem.getLocation().getId() && visibleItem.getLocation().getDateSeq() == realItem.getLocation
                    ().getDateSeq() && visibleItem.getLocation().getActivitySeq() == realItem.getLocation
                    ().getActivitySeq()) {
                    realPosition = i;
                    break;
                }
            }
            return realPosition - countHeader;
        }

        /**
         * Get postion after collapse
         */
        private int getPositionAfterCollapse(int position) {
            for (int i = 0, size = allItems.size(); i < size; i++) {
                if (allItems.get(i).ItemType == TYPE_HEADER && i < position && !TourItineraryAdapter.this.isExpanded
                        (i)) {
                    position --;
                }
            }
            return position;
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick( View v ) {
            // Notify data set change to change position when collapse
            int position = (int)v.getTag();
            listener.onEventControl(TourItineraryFragment.ACTION_CLICK_TOUR_ITINERARY,visibleItems.get
                    (getPositionAfterCollapse(position)).tourItineraryItemDTO,v,
                position);
        }
    }

    private List<TourItineraryItem> getListItem( List<TourItineraryItemDTO> itemDTOs) {
        List<TourItineraryItem> items = new ArrayList<>();
        // Add header infomation
        for (int i = 0; i < itemDTOs.size(); i++) {
            boolean isTitle = false;
            if (i == 0) {
                isTitle = true;
            }
            if (itemDTOs.get(i).getLocation().getDateSeq() > 1 &&
                itemDTOs.get(i).getLocation().getDateSeq() != itemDTOs.get(i - 1).getLocation().getDateSeq()) {
                isTitle = true;
            }
            if (isTitle) {
                items.add(new TourItineraryItem(Utils.getString(R.string.text_day), DateUtil.convertDateWithSeq
                    (startDate, DateUtil.FORMAT_DATE_WITHOUT_TIME_ZONE, itemDTOs.get(i).getLocation().getDateSeq())));
                isTitle = false;
            }
            TourItineraryItemDTO tourInfoDTO = new TourItineraryItemDTO();
            tourInfoDTO = itemDTOs.get(i);
//            tourInfoDTO.getLocation().setId(itemDTOs.get(i).getLocation().getId());
//            tourInfoDTO.getLocation().setName(itemDTOs.get(i).getLocation().getName());
//            tourInfoDTO.setFromDate(itemDTOs.get(i).getFromDate());
//            tourInfoDTO.setToDate(itemDTOs.get(i).getToDate());
//            tourInfoDTO.getLocation().setPhoto(itemDTOs.get(i).getLocation().getPhoto());
//            tourInfoDTO.getLocation().setLat( itemDTOs.get(i).getLocation().getLat());
//            tourInfoDTO.getLocation().setLng( itemDTOs.get(i).getLocation().getLng());
//            tourInfoDTO.setTodo(itemDTOs.get(i).getTodo());
//            tourInfoDTO.setTip(itemDTOs.get(i).getTip());
//            tourInfoDTO.setDescription(itemDTOs.get(i).getDescription());
//            tourInfoDTO.setLocation(itemDTOs.get(i).getLocation());
            items.add(new TourItineraryItem(tourInfoDTO));
        }

        return items;
    }
}
