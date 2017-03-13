package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * MediaDTO
 *
 * @author thtuan
 * @since 7:24 PM 02-08-2016
 */
public class MediaDTO extends BaseDTO implements Parcelable {
    private String name;
    private String uri;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.uri);
    }

    public MediaDTO() {
    }

    protected MediaDTO(Parcel in) {
        this.name = in.readString();
        this.uri = in.readString();
    }

    public static final Parcelable.Creator<MediaDTO> CREATOR = new Parcelable.Creator<MediaDTO>() {
        @Override
        public MediaDTO createFromParcel(Parcel source) {
            return new MediaDTO(source);
        }

        @Override
        public MediaDTO[] newArray(int size) {
            return new MediaDTO[size];
        }
    };
}
