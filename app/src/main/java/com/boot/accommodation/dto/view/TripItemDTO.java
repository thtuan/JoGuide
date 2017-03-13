package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Admin on 12/10/2015.
 */
public class TripItemDTO implements  Parcelable{

    private long tourId;
    private long tourPlanId; // tour plan id if member attend tour
    private String name;
    private List<String> photo;
    private double price;
    private String priceUnit;
    private int numDay;
    private int numPlace;
    private int numLike;
    private String startDate;
    private boolean isFavourite;
    private boolean isJoin; // have join tour or not
    private int tourRole; // type user join with a tour
    private UserItemDTO userOwner;//userDto have info of author
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public boolean getIsFavourite() {
        return isFavourite;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public void setIsFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public UserItemDTO getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(UserItemDTO userOwner) {
        this.userOwner = userOwner;
    }

    public  TripItemDTO(){};

    public TripItemDTO( long id, String name, List<String> photo, String price, String priceUnit,
                        int numDay, int numPlace, int numLike, UserItemDTO userItemDTO,
                        boolean isFavourite) {
        this.setTourId(id);
        this.setName(name);
        this.setPhoto(photo);
        this.setPriceUnit(priceUnit);
        this.setNumDay(numDay);
        this.setNumPlace(numPlace);
        this.setNumLike(numLike);
        this.userOwner =userItemDTO;
//        this.ownerId = ownerId;
//        this.ownerName = ownerName;
//        this.ownerAvatar = ownerAvatar;
        this.isFavourite = isFavourite;
    }

//    @Override
//    public int compareTo(TripItemDTO item) {
//        return this.createDate > item.createDate ? 1 : -1;
//    }

    public long getTourId() {
        return tourId;
    }

    public void setTourId( long tourId ) {
        this.tourId = tourId;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public List<String> getPhoto() {
        return photo;
    }

    public void setPhoto( List<String> photo ) {
        this.photo = photo;
    }


    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit( String priceUnit ) {
        this.priceUnit = priceUnit;
    }

    public int getNumDay() {
        return numDay;
    }

    public void setNumDay( int numDay ) {
        this.numDay = numDay;
    }

    public int getNumPlace() {
        return numPlace;
    }

    public void setNumPlace( int numPlace ) {
        this.numPlace = numPlace;
    }

    public int getNumLike() {
        return numLike;
    }

    public void setNumLike( int numLike ) {
        this.numLike = numLike;
    }

    public long getTourPlanId() {
        return tourPlanId;
    }

    public void setTourPlanId( long tourPlanId ) {
        this.tourPlanId = tourPlanId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice( double price ) {
        this.price = price;
    }

    public boolean getIsJoin() {
        return isJoin;
    }

    public void setIsJoin( boolean join ) {
        isJoin = join;
    }

    public int getTourRole() {
        return tourRole;
    }

    public void setTourRole(int tourRole) {
        this.tourRole = tourRole;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.tourId);
        dest.writeLong(this.tourPlanId);
        dest.writeString(this.name);
        dest.writeStringList(this.photo);
        dest.writeDouble(this.price);
        dest.writeString(this.priceUnit);
        dest.writeInt(this.numDay);
        dest.writeInt(this.numPlace);
        dest.writeInt(this.numLike);
        dest.writeString(this.startDate);
        dest.writeByte(this.isFavourite ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isJoin ? (byte) 1 : (byte) 0);
        dest.writeInt(this.tourRole);
        dest.writeParcelable(this.userOwner, flags);
    }

    protected TripItemDTO(Parcel in) {
        this.tourId = in.readLong();
        this.tourPlanId = in.readLong();
        this.name = in.readString();
        this.photo = in.createStringArrayList();
        this.price = in.readDouble();
        this.priceUnit = in.readString();
        this.numDay = in.readInt();
        this.numPlace = in.readInt();
        this.numLike = in.readInt();
        this.startDate = in.readString();
        this.isFavourite = in.readByte() != 0;
        this.isJoin = in.readByte() != 0;
        this.tourRole = in.readInt();
        this.userOwner = in.readParcelable(UserItemDTO.class.getClassLoader());
    }

    public static final Creator<TripItemDTO> CREATOR = new Creator<TripItemDTO>() {
        @Override
        public TripItemDTO createFromParcel(Parcel source) {
            return new TripItemDTO(source);
        }

        @Override
        public TripItemDTO[] newArray(int size) {
            return new TripItemDTO[size];
        }
    };
}
