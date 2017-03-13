package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

/**
 * UploadPhotoDTO
 *
 * @author thtuan
 * @since 5:03 PM 31-07-2016
 */
public class UploadPhotoDTO extends BaseDTO implements Parcelable {
    String path;
    File file;
    int progressPercentage;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public UploadPhotoDTO() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeSerializable(this.file);
        dest.writeInt(this.progressPercentage);
    }

    protected UploadPhotoDTO(Parcel in) {
        this.path = in.readString();
        this.file = (File) in.readSerializable();
        this.progressPercentage = in.readInt();
    }

    public static final Creator<UploadPhotoDTO> CREATOR = new Creator<UploadPhotoDTO>() {
        @Override
        public UploadPhotoDTO createFromParcel(Parcel source) {
            return new UploadPhotoDTO(source);
        }

        @Override
        public UploadPhotoDTO[] newArray(int size) {
            return new UploadPhotoDTO[size];
        }
    };
}
