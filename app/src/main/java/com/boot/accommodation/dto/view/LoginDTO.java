package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 3:42 PM 27-05-2016
 */
public class LoginDTO implements Parcelable {
    private String userId;
    private String userName; // user name login
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String token;
    private int loginType; // Login type
    private String avatarUrl; // avatar
    private String locate; // locate
    private String phone; // phone
    private int loginFrom = LoginFromTypeDTO.MOBILE.getValue(); // Login from mobile/ web

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl( String avatarUrl ) {
        this.avatarUrl = avatarUrl;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate( String locate ) {
        this.locate = locate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId( String userId ) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone( String phone ) {
        this.phone = phone;
    }

    public int getLoginFrom() {
        return loginFrom;
    }

    public void setLoginFrom( int loginFrom ) {
        this.loginFrom = loginFrom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags ) {
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.email);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.password);
        dest.writeString(this.token);
        dest.writeInt(this.loginType);
        dest.writeString(this.avatarUrl);
        dest.writeString(this.locate);
        dest.writeString(this.phone);
        dest.writeInt(this.loginFrom);
    }

    protected LoginDTO( Parcel in ) {
        this.userId = in.readString();
        this.userName = in.readString();
        this.email = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.password = in.readString();
        this.token = in.readString();
        this.loginType = in.readInt();
        this.avatarUrl = in.readString();
        this.locate = in.readString();
        this.phone = in.readString();
        this.loginFrom = in.readInt();
    }

    public static final Creator<LoginDTO> CREATOR = new Creator<LoginDTO>() {
        @Override
        public LoginDTO createFromParcel( Parcel source ) {
            return new LoginDTO(source);
        }

        @Override
        public LoginDTO[] newArray( int size ) {
            return new LoginDTO[size];
        }
    };
}
