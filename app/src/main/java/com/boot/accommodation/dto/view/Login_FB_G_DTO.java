package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 3:23 PM 03-06-2016
 */
public class Login_FB_G_DTO implements Parcelable {
    String id;
    String name;
    String photo;
    String token;
    String email;

    public Login_FB_G_DTO(String token, String id, String name, String photo, String email) {
        this.token = token;
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.email = email;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.photo);
        dest.writeString(this.token);
        dest.writeString(this.email);
    }

    protected Login_FB_G_DTO(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.photo = in.readString();
        this.token = in.readString();
        this.email = in.readString();
    }

    public static final Creator<Login_FB_G_DTO> CREATOR = new Creator<Login_FB_G_DTO>() {
        @Override
        public Login_FB_G_DTO createFromParcel(Parcel source) {
            return new Login_FB_G_DTO(source);
        }

        @Override
        public Login_FB_G_DTO[] newArray(int size) {
            return new Login_FB_G_DTO[size];
        }
    };
}
