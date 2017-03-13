package com.boot.accommodation.dto.view;

/**
 * Trang thai tour
 *
 * @author tuanlt
 * @since 5:09 PM 6/17/2016
 */
public class QuantityAssessmentValueDTO extends BaseDTO {
    private float excellent; // xuat sac
    private float good; // tot
    private float fair; // binh thuong
    private float bad; // te

    public float getExcellent() {
        return excellent;
    }

    public void setExcellent(float excellent) {
        this.excellent = excellent;
    }

    public float getGood() {
        return good;
    }

    public void setGood(float good) {
        this.good = good;
    }

    public float getFair() {
        return fair;
    }

    public void setFair(float fair) {
        this.fair = fair;
    }

    public float getBad() {
        return bad;
    }

    public void setBad(float bad) {
        this.bad = bad;
    }
}
