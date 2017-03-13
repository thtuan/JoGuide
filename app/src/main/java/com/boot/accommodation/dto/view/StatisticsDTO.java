package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 3:42 PM 14-06-2016
 */
public class StatisticsDTO implements Parcelable {
    private String title;
    private String value;

    public StatisticsDTO() {
    }

    public StatisticsDTO(String title, String value) {
        this.title = title;
        this.value = value;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.value);
    }

    protected StatisticsDTO(Parcel in) {
        this.title = in.readString();
        this.value = in.readString();
    }

    public static final Creator<StatisticsDTO> CREATOR = new Creator<StatisticsDTO>() {
        @Override
        public StatisticsDTO createFromParcel(Parcel source) {
            return new StatisticsDTO(source);
        }

        @Override
        public StatisticsDTO[] newArray(int size) {
            return new StatisticsDTO[size];
        }
    };
}
