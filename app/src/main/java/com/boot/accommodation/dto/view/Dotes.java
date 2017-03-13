package com.boot.accommodation.dto.view;

import java.util.ArrayList;
import java.util.List;

/**
 * Mo ta class
 *
 * @author Vuong-bv
 * @since: 6/1/2016
 */
public class Dotes extends BaseDTO{
    private TopLeftCoordinate topLeftCoordinate;
    private BottomRightCoordinate bottomRightCoordinate;
    private List<Categories> categories;

    public Dotes( TopLeftCoordinate topLeftCoordinate,
                  BottomRightCoordinate bottomRightCoordinate, ArrayList<Categories> categories) {
        this.topLeftCoordinate = topLeftCoordinate;
        this.bottomRightCoordinate = bottomRightCoordinate;
        this.categories = categories;
    }

    public TopLeftCoordinate getTopLeftCoordinate() {
        return topLeftCoordinate;
    }

    public void setTopLeftCoordinate(TopLeftCoordinate topLeftCoordinate) {
        this.topLeftCoordinate = topLeftCoordinate;
    }

    public BottomRightCoordinate getBottomRightCoordinate() {
        return bottomRightCoordinate;
    }

    public void setBottomRightCoordinate(BottomRightCoordinate bottomRightCoordinate) {
        this.bottomRightCoordinate = bottomRightCoordinate;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Categories> categories) {
        this.categories = categories;
    }
}
