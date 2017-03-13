package com.boot.accommodation.dto.view;

/**
 * Dto tourguide for expand list tourinfo
 *
 * @author Vuong-bv
 * @since: 5/30/2016
 */
public class TourGuideDTO extends  BaseDTO {
    private String id; //TourGuideID
    private String name;//TourGuide name
    private String image;//TourGuide image
    private String email;//email of TourGuide
    private String phone;//phone number of TourGuide
    private LocationDTO location;//loction of TourGuide
    private int typeUser;

    public TourGuideDTO(){}

    public TourGuideDTO(String id, String name,String phone,  String email, int typeUser,String image,
                        LocationDTO location) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.typeUser = typeUser;
        this.image = image;
        this.location = location;
    }

    public int getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(int typeUser) {
        this.typeUser = typeUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
