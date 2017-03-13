package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 16/03/2016.
 */
public class PlaceDetaiItemDTO extends BaseDTO implements Parcelable {

    private String id;
    private String code;
    private String name;
    private String address;
    private String description;
    private String website; // website
    private String phone; // phone
    private LocationDTO location;

    public PlaceDetaiItemDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress( String address ) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation( LocationDTO location ) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite( String website ) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone( String phone ) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags ) {
        dest.writeString(this.id);
        dest.writeString(this.code);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.description);
        dest.writeString(this.website);
        dest.writeString(this.phone);
        dest.writeParcelable(this.location, flags);
    }

    protected PlaceDetaiItemDTO( Parcel in ) {
        this.id = in.readString();
        this.code = in.readString();
        this.name = in.readString();
        this.address = in.readString();
        this.description = in.readString();
        this.website = in.readString();
        this.phone = in.readString();
        this.location = in.readParcelable(LocationDTO.class.getClassLoader());
    }

    public static final Creator<PlaceDetaiItemDTO> CREATOR = new Creator<PlaceDetaiItemDTO>() {
        @Override
        public PlaceDetaiItemDTO createFromParcel( Parcel source ) {
            return new PlaceDetaiItemDTO(source);
        }

        @Override
        public PlaceDetaiItemDTO[] newArray( int size ) {
            return new PlaceDetaiItemDTO[size];
        }
    };
}
