package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 12:19 AM 20-06-2016
 */
public class PickContactDTO implements Parcelable,Comparable<PickContactDTO>, Serializable {
    String photo;
    String name;
    String address;
    boolean isSend = false;

    public PickContactDTO() {
    }

    public PickContactDTO(String photo, String name, String address) {
        this.photo = photo;
        this.name = name;
        this.address = address;
        this.isSend = false;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.photo);
        dest.writeString(this.name);
        dest.writeString(this.address);
    }

    protected PickContactDTO(Parcel in) {
        this.photo = in.readString();
        this.name = in.readString();
        this.address = in.readString();
    }

    public static final Parcelable.Creator<PickContactDTO> CREATOR = new Parcelable.Creator<PickContactDTO>() {
        @Override
        public PickContactDTO createFromParcel(Parcel source) {
            return new PickContactDTO(source);
        }

        @Override
        public PickContactDTO[] newArray(int size) {
            return new PickContactDTO[size];
        }
    };

    @Override
    public int compareTo(PickContactDTO another) {
        return this.getName().compareTo(another.getName());
    }
}
