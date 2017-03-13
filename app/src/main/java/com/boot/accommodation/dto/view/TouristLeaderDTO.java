package com.boot.accommodation.dto.view;

/**
 * Mo ta class
 *
 * @author Vuong-bv
 * @since: 5/19/2016
 */
public class TouristLeaderDTO extends  BaseDTO {
    private  String describe;
    private String skill;

    public TouristLeaderDTO( ) {
    }

    public TouristLeaderDTO(String describe, String skill) {
        this.describe = describe;
        this.skill = skill;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
