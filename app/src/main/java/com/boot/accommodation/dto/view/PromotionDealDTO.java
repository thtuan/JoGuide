package com.boot.accommodation.dto.view;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author tuannd
 * @date 06/12/2016
 * @since 1.0
 */
public class PromotionDealDTO extends BaseDTO implements Parcelable {
    private long id;
    private String name;
    private String runningTime;
    private String description;
    private Integer discountType;
    private BigDecimal discountPercent;
    private BigDecimal discountAmount;
    private List<MediaDTO> mediaItems;
    private Integer status;
    private Integer type;
    private String partnerSite;

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Integer discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public List<MediaDTO> getMediaItems() {
        return mediaItems;
    }

    public void setMediaItems(List<MediaDTO> mediaItems) {
        this.mediaItems = mediaItems;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPartnerSite() {
        return partnerSite;
    }

    public void setPartnerSite(String partnerSite) {
        this.partnerSite = partnerSite;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.runningTime);
        dest.writeString(this.description);
        dest.writeValue(this.discountType);
        dest.writeSerializable(this.discountPercent);
        dest.writeSerializable(this.discountAmount);
        dest.writeTypedList(this.mediaItems);
        dest.writeValue(this.status);
        dest.writeValue(this.type);
        dest.writeString(this.partnerSite);
    }

    public PromotionDealDTO() {
    }

    protected PromotionDealDTO(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.runningTime = in.readString();
        this.description = in.readString();
        this.discountType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.discountPercent = (BigDecimal) in.readSerializable();
        this.discountAmount = (BigDecimal) in.readSerializable();
        this.mediaItems = in.createTypedArrayList(MediaDTO.CREATOR);
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.partnerSite = in.readString();
    }

    public static final Creator<PromotionDealDTO> CREATOR = new Creator<PromotionDealDTO>() {
        @Override
        public PromotionDealDTO createFromParcel(Parcel source) {
            return new PromotionDealDTO(source);
        }

        @Override
        public PromotionDealDTO[] newArray(int size) {
            return new PromotionDealDTO[size];
        }
    };
}
