package com.boot.accommodation.dto.view;

import java.io.Serializable;

/**
 * DTO for setting view
 *
 * @author vuongbv
 * @since: 11:08 AM 8/9/2016
 */
public class SettingViewDTO implements Serializable {
    public static final int SEND_LOCATION = 1; //send lotion  to server
    public static final int NOT_SEND_LOCATION = 0; //not send lotion  to server
    public static final int TURN_ON_NOTIFY_TOUR = 1; //turn on  notification tour
    public static final int TURN_OFF_NOTIFY_TOUR = 0; //turn off notification tour
    public static final int TURN_ON_NOTIFY_REVIEW = 1; //turn on notification review
    public static final int TURN_OFF_NOTIFY_REVIEW = 0; //turn of notification review
    public static final int SHOW_LOCATION = 1; //SHOW lotion  to server
    public static final int HIDE_LOCATION = 0; //HIDE send lotion  to server

}
