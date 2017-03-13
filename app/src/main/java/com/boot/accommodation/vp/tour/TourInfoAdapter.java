package com.boot.accommodation.vp.tour;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;
import com.boot.accommodation.R;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.TourInfoDTO;
import com.boot.accommodation.dto.view.TouristDTO;
import com.boot.accommodation.dto.view.TourInformationDTO;
import com.boot.accommodation.listener.OnEventControl;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.TraceExceptionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * Adapter cho tour
 *
 * @author tuanlt
 * @since 11:17 AM 6/1/2016
 */
public class TourInfoAdapter extends ExpandableRecyclerAdapter<TourInfoAdapter.TourInfoListItem> {
    public static final int TYPE_TOUR_INFO = 1001; //thong tin tour
    public static final int TYPE_TOUR_LEADER = 1002; //thong tin leader
    public static final int TYPE_TOUR_TOURIST = 1003; //thong tin tourist
    public static final int TYPE_TOUR_RELATE = 1004; //Tour relate
    protected OnEventControl listener; // listener
    private ArrayList<TouristDTO> lstTourist = new ArrayList();// list ds tourrist dung de test
    private List<TourInfoListItem> items = new ArrayList<>(); // list items
    private int positionCollapse = 0; // Position collapse tourist

    public void setListener(OnEventControl listener) {
        this.listener = listener;
    }

    public TourInfoAdapter( Context context, TourInformationDTO tourtInfomationDTO ) {
        super(context);
        setData(tourtInfomationDTO);
    }

    /**
     * Set data
     */
    public void setData( TourInformationDTO tourInformationDTO ) {
        items.clear();
        getSampleItems(tourInformationDTO);
        setItems(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderViewHolder(inflate(R.layout.item_group_tour_info, parent));
            case TYPE_TOUR_INFO:
                return new TourInfoViewHolder(inflate(R.layout.item_tour_info, parent));
            case TYPE_TOUR_TOURIST:
                return new TouristViewHolder(inflate(R.layout.item_tourist, parent));
            default:
                return new LeaderViewHolder(inflate(R.layout.item_leader, parent));
        }
    }

    @Override
    public void onBindViewHolder(ExpandableRecyclerAdapter.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ((HeaderViewHolder) holder).bind(position);
                break;
            case TYPE_TOUR_INFO:
                ((TourInfoViewHolder) holder).bind(position);
                break;
            case TYPE_TOUR_TOURIST:
                ((TouristViewHolder) holder).bind(position);
                break;
            default:
                ((LeaderViewHolder) holder).bind(position);
                break;
        }
    }

    public int getPositionCollapse() {
        return positionCollapse;
    }

    public void setPositionCollapse( int positionCollapse ) {
        this.positionCollapse = positionCollapse;
    }

    public static class TourInfoListItem extends ExpandableRecyclerAdapter.ListItem {
        private String title; // title
        private boolean isHeaderTourist = false;// header tourist
        private boolean isHeaderTourRelate = false;// header tourist
        private TourInfoDTO tourInfoDTO; // dto thong tin tour
        String priceUnit = "";
        private TouristDTO touristDTO; // thong tin du khach
        private TouristDTO tourLeaderDTO; // thong tin leader

        public TourInfoDTO getTourInfoDTO() {
            return tourInfoDTO;
        }

        public void setTourInfoDTO( TourInfoDTO tourInfoDTO ) {
            this.tourInfoDTO = tourInfoDTO;
        }

        public TouristDTO getTouristDTO() {
            return touristDTO;
        }

        public void setTouristDTO( TouristDTO touristDTO ) {
            this.touristDTO = touristDTO;
        }

        public TourInfoListItem(String group, boolean isHeaderTourist, boolean isRelate) {
            super(TYPE_HEADER);
            title = group;
            this.isHeaderTourist = isHeaderTourist;
            this.isHeaderTourRelate = isRelate;
        }

        public TourInfoListItem(TourInfoDTO tourInfoDTO) {
            super(TYPE_TOUR_INFO);
            this.tourInfoDTO = tourInfoDTO;
        }

        public TourInfoListItem(TourInfoDTO tourInfoDTO, String priceUnit) {
            super(TYPE_TOUR_INFO);
            this.tourInfoDTO = tourInfoDTO;
            this.priceUnit = priceUnit;
        }

        public TourInfoListItem(TouristDTO tourLeadertDTO, boolean isLeader) {
            super(TYPE_TOUR_LEADER);
            this.tourLeaderDTO = tourLeadertDTO;
        }

        public TourInfoListItem(TouristDTO tourInfoDTO) {
            super(TYPE_TOUR_TOURIST);
            this.touristDTO = tourInfoDTO;
        }

    }

    public class HeaderViewHolder extends ExpandableRecyclerAdapter.HeaderViewHolder implements View.OnClickListener {
        TextView tvTitleGroup;
        ImageView ivPosition; // icon position
        ImageView ivAccross;

        public HeaderViewHolder(View view) {
            super(view, (ImageView) view.findViewById(R.id.ivAccross));
            tvTitleGroup = (TextView) view.findViewById(R.id.tvTitleGroup);
            ivPosition = (ImageView) view.findViewById(R.id.ivPosition);
            ivAccross = (ImageView) view.findViewById(R.id.ivAccross);
            ivPosition.setOnClickListener(this);
        }

        public void bind(int position) {
            super.bind(position);
            tvTitleGroup.setText(visibleItems.get(position).title);
            if (visibleItems.get(position).isHeaderTourRelate) {
                ivAccross.setVisibility(View.GONE);
            } else {
                ivAccross.setVisibility(View.VISIBLE);
            }
            if (visibleItems.get(position).isHeaderTourist) {
                ivPosition.setVisibility(View.VISIBLE);
            } else {
                ivPosition.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onEventControl(TourInfoFragment.ACTION_VIEW_TOURIST_POSITION, lstTourist, v, 0);
            }
        }
    }

    public class TourInfoViewHolder extends ExpandableRecyclerAdapter.ViewHolder {
        TextView tvTitle; // title
        TextView tvDescribe; // mo ta

        public TourInfoViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvDescribe = (TextView) view.findViewById(R.id.tvDescribe);
        }

        public void bind(int position) {
            TourInfoDTO tourInfoDTO = visibleItems.get(position).tourInfoDTO;
            tvTitle.setText(StringUtil.getStringResourceByName(visibleItems.get(position).tourInfoDTO.getTitle()));
            tvTitle.setTag(tourInfoDTO);
            try {
                if (tourInfoDTO.getDescribe() != null) {
                    //Handle duration
                    if (StringUtil.getString(R.string.Duration).equals(StringUtil.getStringResourceByName(tourInfoDTO
                            .getTitle().trim()))) {

                        Long duration = Long.valueOf(tourInfoDTO.getDescribe());
                        tvDescribe.setText(duration / 100 + Constants.STR_SPACE + StringUtil.getString(R.string.text_day) + Constants.STR_SPACE +
                                duration % 100 + Constants.STR_SPACE + StringUtil.getString(R.string.text_night));

                        //Handle price
                    } else if (StringUtil.getString(R.string.Price).equals(tourInfoDTO.getTitle().trim())) {
                        tvDescribe.setText(tourInfoDTO.getDescribe());
                    } else {
                        tvDescribe.setText(tourInfoDTO.getDescribe());
                    }
                } else {
                    tvDescribe.setText("");
                }
            } catch (NumberFormatException ex) {
                tvDescribe.setText(tourInfoDTO.getDescribe());
                Log.e(Constants.LogName.EXCEPTION, TraceExceptionUtils.getReportFromThrowable(ex));
            }
        }
    }

    public class LeaderViewHolder extends ExpandableRecyclerAdapter.ViewHolder implements View.OnClickListener {
        CircularImageView ivAvatar; // avatar leader
        TextView tvLeaderName; // ten leader
        ImageView ivPhone;
        RelativeLayout rlGoLeaderInfo;
        ImageView ivMessage;
        TextView tvPhone;


        public LeaderViewHolder(View view) {
            super(view);
            ivAvatar = (CircularImageView) view.findViewById(R.id.ivAvatar);
            tvLeaderName = (TextView) view.findViewById(R.id.tvTouristName);
            ivPhone = (ImageView) view.findViewById(R.id.ivCallPhone);
            ivMessage = (ImageView) view.findViewById(R.id.ivTextMesssage);
            rlGoLeaderInfo = (RelativeLayout) view.findViewById(R.id.rlGoLeaderProfile);
            rlGoLeaderInfo.setOnClickListener(LeaderViewHolder.this);
            ivMessage.setOnClickListener(LeaderViewHolder.this);
            ivPhone.setOnClickListener(LeaderViewHolder.this);
            tvPhone = (TextView) view.findViewById(R.id.tvTouristNumber);
        }


        public void bind(int position) {
            ImageUtil.loadImage(ivAvatar, ImageUtil.getImageUrl(visibleItems.get(position).tourLeaderDTO.getImage()));
            tvLeaderName.setText(visibleItems.get(position).tourLeaderDTO.getName());
            tvPhone.setText(visibleItems.get(position).tourLeaderDTO.getPhone());
            tvPhone.setTag(position);
            if (!StringUtil.isNullOrEmpty(visibleItems.get(position).tourLeaderDTO.getPhone())) {
                ivPhone.setVisibility(View.VISIBLE);
                ivMessage.setVisibility(View.VISIBLE);
            } else {
                ivPhone.setVisibility(View.GONE);
                ivMessage.setVisibility(View.GONE);
            }
        }

        @OnClick({R.id.ivTextMesssage, R.id.ivCallPhone, R.id.rlGoLeaderProfile})
        public void onClick(View v) {
            int position = (Integer) tvPhone.getTag();
            TouristDTO dto = visibleItems.get(position).tourLeaderDTO;
            int action = 0;
            switch (v.getId()) {
                case R.id.ivCallPhone:
                    action = TourInfoFragment.ACTION_CALL_TOURIST;
                    break;
                case R.id.ivTextMesssage:
                    action = TourInfoFragment.ACTION_SEND_MESSAGE;
                    break;
                case R.id.rlGoLeaderProfile:
                    action = TourInfoFragment.ACTION_VIEW_LEADER_INFO;
                    break;
            }
            if (listener != null && dto != null) {
                listener.onEventControl(action, dto, v, position);
            }

        }
    }

    public class TouristViewHolder extends ExpandableRecyclerAdapter.ViewHolder implements View.OnClickListener {
        TextView tvTouristName; // ten
        CircularImageView ivAvatar; // hinh
        RelativeLayout rlGoProfile;
        ImageView ivPosition; // vi tri tourist

        public TouristViewHolder(View view) {
            super(view);
            tvTouristName = (TextView) view.findViewById(R.id.tvTouristName);
            ivAvatar = (CircularImageView) view.findViewById(R.id.ivAvatar);
            rlGoProfile = (RelativeLayout) view.findViewById(R.id.rlGoTouristProfile);
            rlGoProfile.setOnClickListener(TouristViewHolder.this);
            ivPosition = (ImageView) view.findViewById(R.id.ivPosition);
            ivPosition.setOnClickListener(this);
        }

        public void bind(int position) {
            ImageUtil.loadImage(ivAvatar, ImageUtil.getImageUrl(visibleItems.get(position).touristDTO.getImage()));
            tvTouristName.setText(visibleItems.get(position).touristDTO.getName());
            tvTouristName.setTag(position);
            if (visibleItems.get(position).touristDTO.getLocation() != null) {
                ivPosition.setVisibility(View.VISIBLE);
            } else {
                ivPosition.setVisibility(View.GONE);
            }
        }

        @OnClick({R.id.rlGoTouristProfile})
        public void onClick(View v) {
            int position = (Integer) tvTouristName.getTag();
            TouristDTO dto = visibleItems.get(position).touristDTO;
            int action = 0;
            switch (v.getId()) {
                case R.id.rlGoTouristProfile:
                    action = TourInfoFragment.ACTION_VIEW_TOURIST_INFO;
                    if (listener != null && dto != null) {
                        listener.onEventControl(action, dto, v, position);
                    }
                    break;
                case R.id.ivPosition:
                    action = TourInfoFragment.ACTION_VIEW_TOURIST_POSITION;
                    ArrayList<TouristDTO> lstTourist = new ArrayList();
                    lstTourist.add(dto);
                    if (listener != null && dto != null) {
                        listener.onEventControl(action, lstTourist, v, position);
                    }
                    break;
            }

        }
    }

    /**
     * handle 3 list : add to a list and show in page
     *
     * @param tourInformationDTO
     * @return
     */
    private void getSampleItems(TourInformationDTO tourInformationDTO) {
        positionCollapse = tourInformationDTO.getTourInfoDto().size() + tourInformationDTO.getTourGuideDto().size
            () + 2; //
        // Default have
        // header tour info and
        // tour guide
        items.add(new TourInfoListItem(StringUtil.getString(R.string.text_tour_info), false, false));
        ArrayList<TourInfoDTO> listTourInfoDto = tourInformationDTO.getTourInfoDto();
        String price = "";
        TourInfoDTO priceDTO = new TourInfoDTO();
        for (int i = 0; i < listTourInfoDto.size(); i++) {
            TourInfoDTO dto = listTourInfoDto.get(i);
            if (StringUtil.getString(R.string.Price).equals(StringUtil.getStringResourceByName(dto
                    .getTitle()))) {
                priceDTO = dto;
                dto.setDescribe(StringUtil.parseMoneyByLocale(dto.getDescribe()));
                items.add(new TourInfoListItem(dto));
            } else if (StringUtil.getString(R.string.PriceUnit).equals(StringUtil
                    .getStringResourceByName(dto.getTitle()))) {
                positionCollapse--;
                price = StringUtil.parseMoneyByLocale(priceDTO.getDescribe()) +
                    Constants.STR_SPACE + StringUtil.getStringResourceByName(dto.getDescribe().trim());
                priceDTO.setDescribe(price);
            } else {
                items.add(new TourInfoListItem(dto));
            }
        }

        items.add(new TourInfoListItem(StringUtil.getString(R.string.text_tour_leader), false, false));
        ArrayList<TouristDTO> tourGuideDto = tourInformationDTO.getTourGuideDto();
        for (int i = 0; i < tourGuideDto.size(); i++) {
            TouristDTO dto = tourGuideDto.get(i);
            items.add(new TourInfoListItem(dto, true));
        }

        if (tourInformationDTO.getTouristDto().size() != 0) {
            items.add(new TourInfoListItem(StringUtil.getString(R.string.text_tour_tourist) + Constants.STR_SPACE +
                    Constants.STR_BRACKET_LEFT + tourInformationDTO.getTouristDto().size() + Constants.STR_BRACKET_RIGHT, true, false));
        } else {
            items.add(new TourInfoListItem(StringUtil.getString(R.string.text_tour_tourist), true, false));
        }
        ArrayList<TouristDTO> touristDto = tourInformationDTO.getTouristDto();
        for (int i = 0; i < touristDto.size(); i++) {
            TouristDTO dto = touristDto.get(i);
            items.add(new TourInfoListItem(dto));
            lstTourist.add(dto);
        }
        items.add(new TourInfoListItem(StringUtil.getString(R.string.text_tour_relate), false, true));
    }

}
