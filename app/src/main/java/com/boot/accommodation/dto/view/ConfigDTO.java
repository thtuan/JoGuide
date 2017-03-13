package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ConfigDTO
 *
 * @author thtuan
 * @since 2:04 PM 15-08-2016
 */

public class ConfigDTO extends BaseDTO implements Parcelable {
    private Long id;
    private String key;
    private String value;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.key);
        dest.writeString(this.value);
        dest.writeString(this.description);
    }

    public ConfigDTO() {
    }

    protected ConfigDTO(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.key = in.readString();
        this.value = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<ConfigDTO> CREATOR = new Parcelable.Creator<ConfigDTO>() {
        @Override
        public ConfigDTO createFromParcel(Parcel source) {
            return new ConfigDTO(source);
        }

        @Override
        public ConfigDTO[] newArray(int size) {
            return new ConfigDTO[size];
        }
    };
}
