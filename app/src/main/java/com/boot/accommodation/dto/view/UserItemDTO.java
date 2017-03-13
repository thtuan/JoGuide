package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 12/10/2015.
 */
public class UserItemDTO extends BaseDTO implements Serializable, Parcelable, Comparable<UserItemDTO> {

    public static final int TYPE_ADMIN = 1; // user admin
    public static final int TYPE_USER = 2; // mac dinh dang nhap la type user
    public static final int TYPE_LEADER = 3; // leader
    public static final int TYPE_TOURIST = 4; // user tourist
    private String id; // id cua user
    private String fullName; // ten user
    private String avatar = ""  ; // avatar user
    private String cover; // cover photo
    private String status; // trang thai user
    private int userType = TYPE_USER;
    private String phone; // sdt
    private String email; // email
    private String token; // thong tin token
    private String firstName; // first name
    private String lastName; // last name
    private boolean isFriend;//check is friend or not with user login
    private String website;//address website of admin
    private String description;//content about admin
    private String name;//name of company
    private String userName; // username login
    private int loginType = LoginTypeDTO.NORMAL.getValue(); // type login
    private int countNotification;
    private boolean isLogin; // state whether user was login or not
    private List<ConfigDTO> config; // list config when login
    private int mobileType = 1; // 1: Android, 2 iOS
    private String cmnd; //Cmnd
    private String cmndDate; //cmnd date
    private String cmndPlace; //cmnd place
    private String selfIntroduce; //Self introduce
    @SerializedName(value = "specility", alternate="speciality")
    private String speciality; //Specility

    public AppVersionDTO getAppVersion() {
        return appVersion;
    }

    public void setAppVersion( AppVersionDTO appVersion ) {
        this.appVersion = appVersion;
    }

    private AppVersionDTO appVersion; // App version
    public boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean login) {
        isLogin = login;
    }
    public UserItemDTO() {

    }

    public List<ConfigDTO> getConfig() {
        return config;
    }

    public void setConfig(List<ConfigDTO> config) {
        this.config = config;
    }

    public int getCountNotification() {
        return countNotification;
    }

    public void setCountNotification(int countNotification) {
        this.countNotification = countNotification;
    }

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


    @Override
    public int compareTo(UserItemDTO item) {
        return this.getName().compareTo(item.getName());
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar( String avatar ) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    public String getCover() {
        return cover;
    }

    public void setCover( String cover ) {
        this.cover = cover;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType( int userType ) {
        this.userType = userType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone( String phone ) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken( String token ) {
        this.token = token;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite( String website ) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName( String userName ) {
        this.userName = userName;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType( int loginType ) {
        this.loginType = loginType;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getCmndDate() {
        return cmndDate;
    }

    public void setCmndDate(String cmndDate) {
        this.cmndDate = cmndDate;
    }

    public String getCmndPlace() {
        return cmndPlace;
    }

    public void setCmndPlace(String cmndPlace) {
        this.cmndPlace = cmndPlace;
    }

    public int getMobileType() {
        return mobileType;
    }

    public void setMobileType(int mobileType) {
        this.mobileType = mobileType;
    }

    public String getSelfIntroduce() {
        return selfIntroduce;
    }

    public void setSelfIntroduce(String selfIntroduce) {
        this.selfIntroduce = selfIntroduce;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.fullName);
        dest.writeString(this.avatar);
        dest.writeString(this.cover);
        dest.writeString(this.status);
        dest.writeInt(this.userType);
        dest.writeString(this.phone);
        dest.writeString(this.email);
        dest.writeString(this.token);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeByte(this.isFriend ? (byte) 1 : (byte) 0);
        dest.writeString(this.website);
        dest.writeString(this.description);
        dest.writeString(this.name);
        dest.writeString(this.userName);
        dest.writeInt(this.loginType);
        dest.writeInt(this.countNotification);
        dest.writeByte(this.isLogin ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.config);
        dest.writeInt(this.mobileType);
        dest.writeString(this.cmnd);
        dest.writeString(this.cmndDate);
        dest.writeString(this.cmndPlace);
        dest.writeString(this.selfIntroduce);
        dest.writeString(this.speciality);
        dest.writeParcelable(this.appVersion, flags);
    }

    protected UserItemDTO(Parcel in) {
        this.id = in.readString();
        this.fullName = in.readString();
        this.avatar = in.readString();
        this.cover = in.readString();
        this.status = in.readString();
        this.userType = in.readInt();
        this.phone = in.readString();
        this.email = in.readString();
        this.token = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.isFriend = in.readByte() != 0;
        this.website = in.readString();
        this.description = in.readString();
        this.name = in.readString();
        this.userName = in.readString();
        this.loginType = in.readInt();
        this.countNotification = in.readInt();
        this.isLogin = in.readByte() != 0;
        this.config = in.createTypedArrayList(ConfigDTO.CREATOR);
        this.mobileType = in.readInt();
        this.cmnd = in.readString();
        this.cmndDate = in.readString();
        this.cmndPlace = in.readString();
        this.selfIntroduce = in.readString();
        this.speciality = in.readString();
        this.appVersion = in.readParcelable(AppVersionDTO.class.getClassLoader());
    }

    public static final Creator<UserItemDTO> CREATOR = new Creator<UserItemDTO>() {
        @Override
        public UserItemDTO createFromParcel(Parcel source) {
            return new UserItemDTO(source);
        }

        @Override
        public UserItemDTO[] newArray(int size) {
            return new UserItemDTO[size];
        }
    };
}
