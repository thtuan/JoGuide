package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Dto tourist for expand list tourinfo
 *
 * @author Vuong-bv
 * @since: 5/30/2016
 */
public class TouristDTO extends  BaseDTO implements Parcelable {
    private long id; //touristID
    private String name;//tourist name
    private String image;//tourist image
    private String phone;//phone number
    private LocationDTO location;//loction of tourist
    private int userType;
    private String timeLocation = ""; // thoi gian location
    private String mail;
    private boolean isFriend;

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public boolean getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(boolean friend) {
        isFriend = friend;
    }
    public String getMail() {
        return mail;
    }


    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTimeLocation() {
        return timeLocation;
    }

    public void setTimeLocation( String timeLocation ) {
        this.timeLocation = timeLocation;
    }

    public TouristDTO() {
    }

    public TouristDTO( long id, String name, String phone, int userType, String image, LocationDTO
            location) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.phone = phone;
        this.userType = userType;
        this.location = location;
    }



    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public long getId() {
        return id;
    }

    public void setId( long id) {
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

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public TouristDTO convertToTouristDTO(UserItemDTO userItemDTO){
        TouristDTO dto = new TouristDTO();
        dto.setId(Long.parseLong(userItemDTO.getId()));
        dto.setName(userItemDTO.getFullName());
        dto.setImage(userItemDTO.getAvatar());
        dto.setUserType(userItemDTO.getUserType());
        dto.setMail(userItemDTO.getEmail());
        dto.setPhone(userItemDTO.getPhone());
        dto.setIsFriend(userItemDTO.getIsFriend());
        return dto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.phone);
        dest.writeParcelable(this.location, flags);
        dest.writeInt(this.userType);
        dest.writeString(this.timeLocation);
        dest.writeString(this.mail);
        dest.writeByte(this.isFriend ? (byte) 1 : (byte) 0);
    }

    protected TouristDTO(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.image = in.readString();
        this.phone = in.readString();
        this.location = in.readParcelable(LocationDTO.class.getClassLoader());
        this.userType = in.readInt();
        this.timeLocation = in.readString();
        this.mail = in.readString();
        this.isFriend = in.readByte() != 0;
    }

    public static final Creator<TouristDTO> CREATOR = new Creator<TouristDTO>() {
        @Override
        public TouristDTO createFromParcel(Parcel source) {
            return new TouristDTO(source);
        }

        @Override
        public TouristDTO[] newArray(int size) {
            return new TouristDTO[size];
        }
    };
}
