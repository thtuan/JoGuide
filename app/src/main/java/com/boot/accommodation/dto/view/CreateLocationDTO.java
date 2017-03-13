package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * CreateLocationDTO
 *
 * @author thtuan
 * @since 7:22 PM 02-08-2016
 */
public class CreateLocationDTO extends BaseDTO implements Parcelable {

    private String name;
    private String address;
    private ContactDTO contact;
    private String description;
    private String outstanding;
    private LocationDTO coordinate;
    private MediaDTO avatar;
    private List<MediaDTO> medias;
    private List<Long> categories;//list id category
    private int locationType; //Location types
    private List<AreaDTO> areas; //Area
    private String website; //Website
    private String openingText; //Opening text
    private List<Long> bestInTimes;//list id category
    private PriceDTO price; //Price
    public List<Long> getCategories() {
        return categories;
    }

    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ContactDTO getContact() {
        return contact;
    }

    public void setContact(ContactDTO contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(String outstanding) {
        this.outstanding = outstanding;
    }

    public LocationDTO getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(LocationDTO coordinate) {
        this.coordinate = coordinate;
    }

    public MediaDTO getAvatar() {
        return avatar;
    }

    public void setAvatar(MediaDTO avatar) {
        this.avatar = avatar;
    }

    public List<MediaDTO> getMedias() {
        return medias;
    }

    public void setMedias(List<MediaDTO> medias) {
        this.medias = medias;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeParcelable(this.contact, flags);
        dest.writeString(this.description);
        dest.writeString(this.outstanding);
        dest.writeParcelable(this.coordinate, flags);
        dest.writeParcelable(this.avatar, flags);
        dest.writeTypedList(this.medias);
    }

    public CreateLocationDTO() {
    }

    protected CreateLocationDTO(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
        this.contact = in.readParcelable(ContactDTO.class.getClassLoader());
        this.description = in.readString();
        this.outstanding = in.readString();
        this.coordinate = in.readParcelable(LocationDTO.class.getClassLoader());
        this.avatar = in.readParcelable(MediaDTO.class.getClassLoader());
        this.medias = in.createTypedArrayList(MediaDTO.CREATOR);
    }

    public static final Parcelable.Creator<CreateLocationDTO> CREATOR = new Parcelable.Creator<CreateLocationDTO>() {
        @Override
        public CreateLocationDTO createFromParcel(Parcel source) {
            return new CreateLocationDTO(source);
        }

        @Override
        public CreateLocationDTO[] newArray(int size) {
            return new CreateLocationDTO[size];
        }
    };

    public int getLocationType() {
        return locationType;
    }

    public void setLocationType(int locationType) {
        this.locationType = locationType;
    }

    public List<AreaDTO> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaDTO> areas) {
        this.areas = areas;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getOpeningText() {
        return openingText;
    }

    public void setOpeningText(String openingText) {
        this.openingText = openingText;
    }

    public List<Long> getBestInTimes() {
        return bestInTimes;
    }

    public void setBestInTimes(List<Long> bestInTimes) {
        this.bestInTimes = bestInTimes;
    }

    public PriceDTO getPrice() {
        return price;
    }

    public void setPrice(PriceDTO price) {
        this.price = price;
    }
}
