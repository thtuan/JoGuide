package com.boot.accommodation.dto.view;

import java.util.List;

/**
 * Opening hours
 *
 * @author tuanlt
 * @since 10:13 AM 9/17/16
 */
public class OpeningHoursDTO {
    public List<String> getWeekday_text() {
        return weekday_text;
    }

    public void setWeekday_text(List<String> weekday_text) {
        this.weekday_text = weekday_text;
    }

    private List<String> weekday_text;

}
