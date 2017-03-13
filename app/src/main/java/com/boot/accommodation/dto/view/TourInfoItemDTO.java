package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * DTO tour info
 *
 * @author tuanlt
 * @since: 12:00 AM 5/13/2016
 */
public class TourInfoItemDTO extends BaseDTO implements Parcelable {
    private String id; // id tour
    private String code; // ma tour
    private String departureDay; // ngay di
    private int numOfPeople; // so nguoi
    private String priceTax; // thue
    private String totalPrice; // tong tien
    private String priceUnit; // don vi tien
    private String includes; // bao gom
    private String excludes; // ko bao gom
    private String categories; // category
    private int numDay; // so ngay di
    private int numPlace; // so noi di
    private int numLike; // so luong like
    private String ownerId; //nguoi tao tour
    private String ownerName; // ten nguoi tao
    private String ownerAvatar; // avatar tour

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    public String getDepartureDay() {
        return departureDay;
    }

    public void setDepartureDay( String departureDay ) {
        this.departureDay = departureDay;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople( int numOfPeople ) {
        this.numOfPeople = numOfPeople;
    }

    public String getPriceTax() {
        return priceTax;
    }

    public void setPriceTax( String priceTax ) {
        this.priceTax = priceTax;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice( String totalPrice ) {
        this.totalPrice = totalPrice;
    }

    public String getIncludes() {
        return includes;
    }

    public void setIncludes( String includes ) {
        this.includes = includes;
    }

    public String getExcludes() {
        return excludes;
    }

    public void setExcludes( String excludes ) {
        this.excludes = excludes;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories( String categories ) {
        this.categories = categories;
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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId( String ownerId ) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName( String ownerName ) {
        this.ownerName = ownerName;
    }

    public String getOwnerAvatar() {
        return ownerAvatar;
    }

    public void setOwnerAvatar( String ownerAvatar ) {
        this.ownerAvatar = ownerAvatar;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit( String priceUnit ) {
        this.priceUnit = priceUnit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags ) {
        dest.writeString(this.id);
        dest.writeString(this.code);
        dest.writeString(this.departureDay);
        dest.writeInt(this.numOfPeople);
        dest.writeString(this.priceTax);
        dest.writeString(this.totalPrice);
        dest.writeString(this.priceUnit);
        dest.writeString(this.includes);
        dest.writeString(this.excludes);
        dest.writeString(this.categories);
        dest.writeInt(this.numDay);
        dest.writeInt(this.numPlace);
        dest.writeInt(this.numLike);
        dest.writeString(this.ownerId);
        dest.writeString(this.ownerName);
        dest.writeString(this.ownerAvatar);
    }

    public TourInfoItemDTO() {
    }

    protected TourInfoItemDTO( Parcel in ) {
        this.id = in.readString();
        this.code = in.readString();
        this.departureDay = in.readString();
        this.numOfPeople = in.readInt();
        this.priceTax = in.readString();
        this.totalPrice = in.readString();
        this.priceUnit = in.readString();
        this.includes = in.readString();
        this.excludes = in.readString();
        this.categories = in.readString();
        this.numDay = in.readInt();
        this.numPlace = in.readInt();
        this.numLike = in.readInt();
        this.ownerId = in.readString();
        this.ownerName = in.readString();
        this.ownerAvatar = in.readString();
    }

    public static final Creator<TourInfoItemDTO> CREATOR = new Creator<TourInfoItemDTO>() {
        @Override
        public TourInfoItemDTO createFromParcel( Parcel source ) {
            return new TourInfoItemDTO(source);
        }

        @Override
        public TourInfoItemDTO[] newArray( int size ) {
            return new TourInfoItemDTO[size];
        }
    };
}
