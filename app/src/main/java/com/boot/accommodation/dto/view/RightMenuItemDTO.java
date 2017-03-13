package com.boot.accommodation.dto.view;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by Admin on 12/10/2015.
 */
public class RightMenuItemDTO extends BaseDTO {

    // id item
    public int id;
    // code item
    public String code;
    // name item
    public String name;
    // icon item
    public Drawable avatar;
    // is selected item
    public boolean isSelected;
    // is child item
    public boolean isChildItem;
    // is expand state
    public boolean isExpand;
    // list item
    public ArrayList<RightMenuItemDTO> listItem;

    public RightMenuItemDTO(){

    }

    public RightMenuItemDTO(int id, String name, Drawable avatar, ArrayList<RightMenuItemDTO> listItem){
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.listItem = listItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getAvatar() {
        return avatar;
    }

    public void setAvatar(Drawable avatar) {
        this.avatar = avatar;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isChildItem() {
        return isChildItem;
    }

    public void setIsChildItem(boolean isChildItem) {
        this.isChildItem = isChildItem;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setIsExpand(boolean isExpand) {
        this.isExpand = isExpand;
    }

    public ArrayList<RightMenuItemDTO> getListItem() {
        return listItem;
    }

    public void setListItem(ArrayList<RightMenuItemDTO> listItem) {
        this.listItem = listItem;
    }
}
