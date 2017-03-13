package com.boot.accommodation.dto.view;

/**
 * DTO chua thong tin search
 *
 * @author tuanlt
 * @since 3:24 PM 5/19/2016
 */
public class SearchDTO extends BaseDTO {
    private long id; // id cua tour hoac dia diem
    private String photo; // link hinh anh cua tour hoac dia diem
    private String name; // ten tour hoac dia diem

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto( String photo ) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }
}
