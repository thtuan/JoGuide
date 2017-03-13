package com.boot.accommodation.dto.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



/**
 * CalendarDTO
 *
 * @author thtuan
 * @since 2:58 PM 04-09-2016
 */
public class CalendarDTO implements Serializable{
    private long id;
    private Date date;
    private List<Meeting> meetings;

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

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


}