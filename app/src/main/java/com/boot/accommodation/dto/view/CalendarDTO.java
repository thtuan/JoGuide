package com.boot.accommodation.dto.view;

import java.io.Serializable;
import java.util.Date;


/**
 * CalendarDTO
 *
 * @author thtuan
 * @since 2:58 PM 04-09-2016
 */
public class CalendarDTO implements Serializable {
    private long id;
    private int currentViewMonth;
    private boolean isEdit = false;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCurrentViewMonth() {
        return currentViewMonth;
    }

    public void setCurrentViewMonth(int currentViewMonth) {
        this.currentViewMonth = currentViewMonth;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }
}