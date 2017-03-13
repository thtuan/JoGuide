package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

import com.boot.accommodation.dto.response.BaseResponseDTO;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 5:16 PM 18-05-2016
 */
public class ScheduleDTO extends BaseResponseDTO implements Parcelable {
    private String title;
    private String content;
    private int notifyType;
    private String remindTime;
    private String tourName;
    private Long userId;
    private String createAt;

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public ScheduleDTO() {
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

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeInt(this.notifyType);
        dest.writeString(this.remindTime);
        dest.writeString(this.tourName);
        dest.writeValue(this.userId);
        dest.writeString(this.createAt);
    }

    protected ScheduleDTO(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        this.notifyType = in.readInt();
        this.remindTime = in.readString();
        this.tourName = in.readString();
        this.userId = (Long) in.readValue(Long.class.getClassLoader());
        this.createAt = in.readString();
    }

    public static final Creator<ScheduleDTO> CREATOR = new Creator<ScheduleDTO>() {
        @Override
        public ScheduleDTO createFromParcel(Parcel source) {
            return new ScheduleDTO(source);
        }

        @Override
        public ScheduleDTO[] newArray(int size) {
            return new ScheduleDTO[size];
        }
    };
}
