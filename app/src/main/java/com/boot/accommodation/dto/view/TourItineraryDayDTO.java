package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Lich trinh tour theo ngay
 *
 * @author tuanlt
 * @since 2:01 PM 6/14/2016
 */
public class TourItineraryDayDTO extends BaseDTO implements Parcelable {
    private ArrayList<TourItineraryItemDTO> tourItineraryItemDTOs = new ArrayList<>();// lst cac dia diem theo ngay

    public ArrayList<TourItineraryItemDTO> getTourItineraryItemDTOs() {
        return tourItineraryItemDTOs;
    }

    public void setTourItineraryItemDTOs( ArrayList<TourItineraryItemDTO> tourItineraryItemDTOs ) {
        this.tourItineraryItemDTOs = tourItineraryItemDTOs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags ) {
        dest.writeTypedList(this.tourItineraryItemDTOs);
    }

    public TourItineraryDayDTO() {
    }

    protected TourItineraryDayDTO( Parcel in ) {
        this.tourItineraryItemDTOs = in.createTypedArrayList(TourItineraryItemDTO.CREATOR);
    }

    public static final Creator<TourItineraryDayDTO> CREATOR = new Creator<TourItineraryDayDTO>() {
        @Override
        public TourItineraryDayDTO createFromParcel( Parcel source ) {
            return new TourItineraryDayDTO(source);
        }

        @Override
        public TourItineraryDayDTO[] newArray( int size ) {
            return new TourItineraryDayDTO[size];
        }
    };
}
