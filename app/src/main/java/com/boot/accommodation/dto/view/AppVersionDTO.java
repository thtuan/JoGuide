package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * App version model
 * 
 * @author tuanlt
 * @date 31-08-2016
 * @since 1.0
 */
public class AppVersionDTO extends BaseDTO implements Parcelable {
	private Long id; // id
	private int force; // 0: force, 1: no force
	private String description; // Description download
	private int status; // Status
	private String version; // Version
	private String link; // Link download
	public static final int TYPE_FORCE = 1; // Type force download

	protected AppVersionDTO(Parcel in) {
		force = in.readInt();
		description = in.readString();
		status = in.readInt();
		version = in.readString();
		link = in.readString();
	}

	public static final Creator<AppVersionDTO> CREATOR = new Creator<AppVersionDTO>() {
		@Override
		public AppVersionDTO createFromParcel(Parcel in) {
			return new AppVersionDTO(in);
		}

		@Override
		public AppVersionDTO[] newArray(int size) {
			return new AppVersionDTO[size];
		}
	};

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getForce() {
		return force;
	}

	public void setForce(int force) {
		this.force = force;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(force);
		dest.writeString(description);
		dest.writeInt(status);
		dest.writeString(version);
		dest.writeString(link);
	}
}
