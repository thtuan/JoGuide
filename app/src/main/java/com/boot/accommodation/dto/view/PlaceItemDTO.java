package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

import com.boot.accommodation.constant.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 12/10/2015.
 */
public class PlaceItemDTO extends BaseDTO implements Parcelable {

    private long id; // id dia diem
    private String name; // ten dia diem
    private String code; // ma code
    private String photo; // photo dai dien cho dia diem
    private String address; // dia chi
    private int typeId;
    private String typeIcon;
    private double ratePoint;
    private int totalCheckIn;
    private int numVoted; // so vote
    private String phoneNumber; // so dien thoai
    private double lat = Constants.LAT_LNG_NULL;
    private double lng = Constants.LAT_LNG_NULL;
    private boolean isFavourite = false; // co favourite hay ko
    private String website; // website
    private String description; // mo ta
    private int dateSeq;
    private int activitySeq;
    private int locationType; //Location type
    private String outStanding; //Out standing
    private String openingTime; //Opening time
    private List<Long> categoryId; //CategoryId
    private List<String> photos; //Multi photo, using when create location from google
    private String workingTime; //Working time
    private String placeIdGG; //Place id from google
    private String bestTimeTogo; //Best time to go
    private int famousLocationId = 0;
    private String specialFood; //Special food
    private String vehicle; //Vehicle
    private double priceFrom; //Price
    private String priceUnit; //Price unit
    private int numLike; //Num like

    public double getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(double priceTo) {
        this.priceTo = priceTo;
    }

    private double priceTo; //Price

    public String getSpecialFood() {
        return specialFood;
    }

    public void setSpecialFood(String specialFood) {
        this.specialFood = specialFood;
    }

    public PlaceItemDTO() {
    }

    public int getLocationType() {
        return locationType;
    }

    public void setLocationType(int locationType) {
        this.locationType = locationType;
    }

    public int getActivitySeq() {
        return activitySeq;
    }

    public void setActivitySeq(int activitySeq) {
        this.activitySeq = activitySeq;
    }

    public int getDateSeq() {
        return dateSeq;
    }

    public void setDateSeq(int dateSeq) {
        this.dateSeq = dateSeq;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeIcon() {
        return typeIcon;
    }

    public void setTypeIcon(String typeIcon) {
        this.typeIcon = typeIcon;
    }

    public double getRatePoint() {
        return ratePoint;
    }

    public void setRatePoint(double ratePoint) {
        this.ratePoint = ratePoint;
    }

    public int getTotalCheckIn() {
        return totalCheckIn;
    }

    public void setTotalCheckIn(int totalCheckIn) {
        this.totalCheckIn = totalCheckIn;
    }

    public int getNumVoted() {
        return numVoted;
    }

    public void setNumVoted(int numVoted) {
        this.numVoted = numVoted;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Boolean getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getOutStanding() {
        return outStanding;
    }

    public void setOutStanding(String outStanding) {
        this.outStanding = outStanding;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<Long> getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(List<Long> categoryId) {
        this.categoryId = categoryId;
    }

    public String getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(String workingTime) {
        this.workingTime = workingTime;
    }

    public String getPlaceIdGG() {
        return placeIdGG;
    }

    public void setPlaceIdGG(String placeIdGG) {
        this.placeIdGG = placeIdGG;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getBestTimeTogo() {
        return bestTimeTogo;
    }

    public void setBestTimeTogo(String bestTimeTogo) {
        this.bestTimeTogo = bestTimeTogo;
    }

    public int getFamousLocationId() {
        return famousLocationId;
    }

    public void setFamousLocationId(int famousLocationId) {
        this.famousLocationId = famousLocationId;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public double getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(double priceFrom) {
        this.priceFrom = priceFrom;
    }

    public int getNumLike() {
        return numLike;
    }

    public void setNumLike(int numLike) {
        this.numLike = numLike;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.code);
        dest.writeString(this.photo);
        dest.writeString(this.address);
        dest.writeInt(this.typeId);
        dest.writeString(this.typeIcon);
        dest.writeDouble(this.ratePoint);
        dest.writeInt(this.totalCheckIn);
        dest.writeInt(this.numVoted);
        dest.writeString(this.phoneNumber);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
        dest.writeByte(this.isFavourite ? (byte) 1 : (byte) 0);
        dest.writeString(this.website);
        dest.writeString(this.description);
        dest.writeInt(this.dateSeq);
        dest.writeInt(this.activitySeq);
        dest.writeInt(this.locationType);
        dest.writeString(this.outStanding);
        dest.writeString(this.openingTime);
        dest.writeList(this.categoryId);
        dest.writeStringList(this.photos);
        dest.writeString(this.workingTime);
        dest.writeString(this.placeIdGG);
        dest.writeString(this.bestTimeTogo);
        dest.writeInt(this.famousLocationId);
        dest.writeString(this.specialFood);
        dest.writeString(this.vehicle);
        dest.writeDouble(this.priceFrom);
        dest.writeString(this.priceUnit);
        dest.writeInt(this.numLike);
        dest.writeDouble(this.priceTo);
    }

    protected PlaceItemDTO(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.code = in.readString();
        this.photo = in.readString();
        this.address = in.readString();
        this.typeId = in.readInt();
        this.typeIcon = in.readString();
        this.ratePoint = in.readDouble();
        this.totalCheckIn = in.readInt();
        this.numVoted = in.readInt();
        this.phoneNumber = in.readString();
        this.lat = in.readDouble();
        this.lng = in.readDouble();
        this.isFavourite = in.readByte() != 0;
        this.website = in.readString();
        this.description = in.readString();
        this.dateSeq = in.readInt();
        this.activitySeq = in.readInt();
        this.locationType = in.readInt();
        this.outStanding = in.readString();
        this.openingTime = in.readString();
        this.categoryId = new ArrayList<Long>();
        in.readList(this.categoryId, Long.class.getClassLoader());
        this.photos = in.createStringArrayList();
        this.workingTime = in.readString();
        this.placeIdGG = in.readString();
        this.bestTimeTogo = in.readString();
        this.famousLocationId = in.readInt();
        this.specialFood = in.readString();
        this.vehicle = in.readString();
        this.priceFrom = in.readDouble();
        this.priceUnit = in.readString();
        this.numLike = in.readInt();
        this.priceTo = in.readDouble();
    }

    public static final Creator<PlaceItemDTO> CREATOR = new Creator<PlaceItemDTO>() {
        @Override
        public PlaceItemDTO createFromParcel(Parcel source) {
            return new PlaceItemDTO(source);
        }

        @Override
        public PlaceItemDTO[] newArray(int size) {
            return new PlaceItemDTO[size];
        }
    };
}
