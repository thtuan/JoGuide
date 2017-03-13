package com.boot.accommodation.dto.view;

/**
 * DTO category
 *
 * @author vuong-bv
 * @since 4:47 PM  8/20/2016.
 */
public class CategoryItemDTO extends BaseDTO {
    private long id;//id
    private String name;// name item
    private String value; //Value
    private boolean isSelected;// name item
    private long parentCategoryId; //Parent category id

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

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
