package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 11:04 AM 17-05-2016
 */
public class TourNotificationDTO implements Parcelable {
    private Long id;
    private String title;
    private String tourName;
    private String content;
    private String photo;
    private int notifyType;
    private String remindTime;
    private String createAt;// ngay tao thong bao
    private boolean isNewNotification;

    public boolean getIsNewNotification() {
        return isNewNotification;
    }

    public void setIsNewNotification(boolean newNotification) {
        isNewNotification = newNotification;
    }


    private UserItemDTO user = new UserItemDTO();
    public final static int NOTICE = 1;
    public final static int REMIND = 2;
    public final static int ATTENTION = 3;

    public TourNotificationDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserItemDTO getUser() {
        return user;
    }

    public void setUser(UserItemDTO user) {
        this.user = user;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(int notifyType) {
        this.notifyType = notifyType;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createDate) {
        this.createAt = createDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.tourName);
        dest.writeString(this.content);
        dest.writeString(this.photo);
        dest.writeInt(this.notifyType);
        dest.writeString(this.remindTime);
        dest.writeString(this.createAt);
        dest.writeByte(this.isNewNotification ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.user, flags);
    }

    protected TourNotificationDTO(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.title = in.readString();
        this.tourName = in.readString();
        this.content = in.readString();
        this.photo = in.readString();
        this.notifyType = in.readInt();
        this.remindTime = in.readString();
        this.createAt = in.readString();
        this.isNewNotification = in.readByte() != 0;
        this.user = in.readParcelable(UserItemDTO.class.getClassLoader());
    }

    public static final Creator<TourNotificationDTO> CREATOR = new Creator<TourNotificationDTO>() {
        @Override
        public TourNotificationDTO createFromParcel(Parcel source) {
            return new TourNotificationDTO(source);
        }

        @Override
        public TourNotificationDTO[] newArray(int size) {
            return new TourNotificationDTO[size];
        }
    };
}
