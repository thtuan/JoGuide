package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Info tourist
 *
 * @author Vuong-bv
 * @since: 5/13/2016
 */
public class TouristInfoDTO extends BaseDTO implements Parcelable {

    private long id; //touristID
    private String name;//tourist name
    private String image;//tourist image
    private String phone;//phone number
    private int isHeader = 1;// check is header(Title) or item
    private int isLeader = 1;// check leader or not
    private String timeLocation = ""; // thoi gian location
    private LocationDTO location;

    public TouristInfoDTO( ) {
    }

    public TouristInfoDTO(long id ,String name, String phone, String image, int isLeader, int isHeader, LocationDTO
        location) {
        this.location = location;
        this.name = name;
        this.image = image;
        this.phone = phone;
        this.isHeader = isHeader;
        this.isLeader = isLeader;
        this.id = id;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIsHeader() {
        return isHeader;
    }

    public void setIsHeader(int isHeader) {
        this.isHeader = isHeader;
    }

    public int getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(int isLeader) {
        this.isLeader = isLeader;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }



    public String getTimeLocation() {
        return timeLocation;
    }

    public void setTimeLocation( String timeLocation ) {
        this.timeLocation = timeLocation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags ) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.phone);
        dest.writeInt(this.isHeader);
        dest.writeInt(this.isLeader);
        dest.writeString(this.timeLocation);
        dest.writeParcelable(this.location, flags);
    }

    protected TouristInfoDTO( Parcel in ) {
        this.id = in.readLong();
        this.name = in.readString();
        this.image = in.readString();
        this.phone = in.readString();
        this.isHeader = in.readInt();
        this.isLeader = in.readInt();
        this.timeLocation = in.readString();
        this.location = in.readParcelable(LocationDTO.class.getClassLoader());
    }

    public static final Creator<TouristInfoDTO> CREATOR = new Creator<TouristInfoDTO>() {
        @Override
        public TouristInfoDTO createFromParcel( Parcel source ) {
            return new TouristInfoDTO(source);
        }

        @Override
        public TouristInfoDTO[] newArray( int size ) {
            return new TouristInfoDTO[size];
        }
    };

}
