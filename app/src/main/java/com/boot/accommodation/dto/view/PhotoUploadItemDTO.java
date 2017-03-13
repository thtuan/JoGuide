package com.boot.accommodation.dto.view;

import com.google.gson.annotations.SerializedName;

/**
 * item upload
 *
 * @author tuanlt
 * @since 5:03 PM 7/21/2016
 */
public class PhotoUploadItemDTO {
    @SerializedName(value="Name", alternate={"name"})
    private String Name;
    @SerializedName(value="URL", alternate={"uri"})
    private String URL;


    public String getName() {
        return Name;
    }

    public void setName( String name ) {
        Name = name;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
