package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

import com.boot.accommodation.util.DateUtil;

/**
 * Mo ta class
 *
 * @author Vuong-bv
 * @since: 5/19/2016
 */
public class TourItineraryItemDTO extends BaseDTO implements Comparable<TourItineraryItemDTO>,Parcelable {

    private PlaceItemDTO location = new PlaceItemDTO(); // thong tin dia diem cua mot tour

    private String fromDate; // thoi gian di
    private String toDate; // thoi gian ket thuc
    private String description;// mo ta noi di
    private String tip; // tip
    private String todo; // todo
    private boolean isFavourite; // dc minh favourite hay chua

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public TourItineraryItemDTO(){

    }

    @Override
    public int compareTo(TourItineraryItemDTO another) {
        return DateUtil.compareDate(this.getFromDate(), another.getFromDate());
    }

    public String getFromDate() {
        return fromDate;
    }


    public void setFromDate( String fromDate ) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public PlaceItemDTO getLocation() {
        return location;
    }

    public void setLocation(PlaceItemDTO location) {
        this.location = location;
    }

    public void setToDate(String toDate ) {
        this.toDate = toDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.location, flags);
        dest.writeString(this.fromDate);
        dest.writeString(this.toDate);
        dest.writeString(this.description);
        dest.writeString(this.tip);
        dest.writeString(this.todo);
        dest.writeByte(this.isFavourite ? (byte) 1 : (byte) 0);
    }

    protected TourItineraryItemDTO(Parcel in) {
        this.location = in.readParcelable(PlaceItemDTO.class.getClassLoader());
        this.fromDate = in.readString();
        this.toDate = in.readString();
        this.description = in.readString();
        this.tip = in.readString();
        this.todo = in.readString();
        this.isFavourite = in.readByte() != 0;
    }

    public static final Creator<TourItineraryItemDTO> CREATOR = new Creator<TourItineraryItemDTO>() {
        @Override
        public TourItineraryItemDTO createFromParcel(Parcel source) {
            return new TourItineraryItemDTO(source);
        }

        @Override
        public TourItineraryItemDTO[] newArray(int size) {
            return new TourItineraryItemDTO[size];
        }
    };
}
