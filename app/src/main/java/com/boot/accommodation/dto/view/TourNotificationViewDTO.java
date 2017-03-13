package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 11:16 AM 17-05-2016
 */
public class TourNotificationViewDTO implements Parcelable, Serializable {
    private PagingDTO paging;
    private ArrayList<TourNotificationDTO> notification;

    public TourNotificationViewDTO() {
    }

    public PagingDTO getPaging() {
        return paging;
    }

    public void setPaging(PagingDTO paging) {
        this.paging = paging;
    }

    public ArrayList<TourNotificationDTO> getNotification() {
        return notification;
    }

    public void setNotification(ArrayList<TourNotificationDTO> notification) {
        this.notification = notification;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.paging);
        dest.writeTypedList(this.notification);
    }

    protected TourNotificationViewDTO(Parcel in) {
        this.paging = (PagingDTO) in.readSerializable();
        this.notification = in.createTypedArrayList(TourNotificationDTO.CREATOR);
    }

    public static final Creator<TourNotificationViewDTO> CREATOR = new Creator<TourNotificationViewDTO>() {
        @Override
        public TourNotificationViewDTO createFromParcel(Parcel source) {
            return new TourNotificationViewDTO(source);
        }

        @Override
        public TourNotificationViewDTO[] newArray(int size) {
            return new TourNotificationViewDTO[size];
        }
    };
}
