package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ContactDTO
 *
 * @author thtuan
 * @since 7:28 PM 02-08-2016
 */
public class ContactDTO extends BaseDTO implements Parcelable {
    private String phone;
    private String email;
    private String fax;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phone);
        dest.writeString(this.email);
        dest.writeString(this.fax);
    }

    public ContactDTO() {
    }

    protected ContactDTO(Parcel in) {
        this.phone = in.readString();
        this.email = in.readString();
        this.fax = in.readString();
    }

    public static final Parcelable.Creator<ContactDTO> CREATOR = new Parcelable.Creator<ContactDTO>() {
        @Override
        public ContactDTO createFromParcel(Parcel source) {
            return new ContactDTO(source);
        }

        @Override
        public ContactDTO[] newArray(int size) {
            return new ContactDTO[size];
        }
    };
}
