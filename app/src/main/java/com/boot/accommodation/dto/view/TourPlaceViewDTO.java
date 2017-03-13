package com.boot.accommodation.dto.view;

import java.util.List;

/**
 * DTO cho man hinh chi tiet dia diem tour
 *
 * @author tuanlt
 * @since 6:16 PM 5/26/2016
 */
public class TourPlaceViewDTO extends BaseDTO {
    private String startTime; // tg bat dau
    private String endTime; // tg ket thuc ghe tham
    private String description; // mo ta chuyen di
    private List<String> todos; // ds cac cong viec can lam
    private List<String> tips; // cac loai tip

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime( String startTime ) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime( String endTime ) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public List<String> getTodos() {
        return todos;
    }

    public void setTodos( List<String> todos ) {
        this.todos = todos;
    }

    public List<String> getTips() {
        return tips;
    }

    public void setTips( List<String> tips ) {
        this.tips = tips;
    }
}
