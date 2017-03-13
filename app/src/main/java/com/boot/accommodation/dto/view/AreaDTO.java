package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * AreaDTO
 *
 * @author tuanlt
 * @since 12:00 PM 9/16/16
 */
public class AreaDTO extends BaseDTO implements Parcelable{
    private long id; // id area
    private String longName = ""; //Long name
    private String shortName = ""; //Short name
    private int type; //Type location
    private long parentAreaId; //Parent id
    private String fullLongName = ""; // Full name with province
    private boolean isSelected = false; //Is selected
    private String photo; //Image area

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentAreaId() {
        return parentAreaId;
    }

    public void setParentAreaId(long parentAreaId) {
        this.parentAreaId = parentAreaId;
    }


    public String getFullLongName() {
        return fullLongName;
    }

    public void setFullLongName(String fullLongName) {
        this.fullLongName = fullLongName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.longName);
        dest.writeString(this.shortName);
        dest.writeInt(this.type);
        dest.writeLong(this.parentAreaId);
        dest.writeString(this.fullLongName);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeString(this.photo);
    }

    public AreaDTO() {
    }

    protected AreaDTO(Parcel in) {
        this.id = in.readLong();
        this.longName = in.readString();
        this.shortName = in.readString();
        this.type = in.readInt();
        this.parentAreaId = in.readLong();
        this.fullLongName = in.readString();
        this.isSelected = in.readByte() != 0;
        this.photo = in.readString();
    }

    public static final Creator<AreaDTO> CREATOR = new Creator<AreaDTO>() {
        @Override
        public AreaDTO createFromParcel(Parcel source) {
            return new AreaDTO(source);
        }

        @Override
        public AreaDTO[] newArray(int size) {
            return new AreaDTO[size];
        }
    };
}
