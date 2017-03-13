package com.boot.accommodation.constant;

/**
 * Error code
 * @author tuanlt
 * @date 12/03/2016
 * @since 1.0
 */
public class ErrorCode {
    // Error same server return
    public static final int NO_ERROR = 0;
    public static final int ERR_COMMON = 1;
    public static final int USER_EXIST_TRY_LOGIN_SUCCESS = 100;
    public static final int USER_EXIST_TRY_LOGIN_FAIL = 101;
    public static final int LOGIN_FAIL_USERNAME_PASSWORD_MISMATCH = 102;
    public static final int INVALID_ACCOUNT = 103;
    public static final int AUTHENTICATE_REQUIRED = 104;
    public static final int BUS_USER_ALREADY_INVITED_TO_TOUR_PLAN = 550;
    public static final int BUS_USER_ALREADY_BEEN_IN_TOUR_PLAN_WAITING_LIST = 551;
    public static final int BUS_USER_HAS_ALREADY_SENT_JOINING_TOUR_REQUEST = 552;
    public static final int BUS_USER_HAS_ALREADY_BEEN_MEMBER_OF_TOUR = 553;
    public static final int BUS_VIP_MEMBER_MISSING_ADDITION_INFORMATION = 561;
    public static final int BUS_VIP_MEMBER_EMAIL_ALREADY_TAKEN = 562;
    public static final int BUS_VIP_MEMBER_ID_NUMBER_ALREADY_REGISTER = 563;

    // Error client auto gen
    public static final int UN_AUTHORIZE = 401;  // Error authentication
    public static final int NO_CONNECTION = 404;  // loi ko ket noi
    public static final int TIMED_OUT = 500;

    private long value;

    ErrorCode(long value) {
        this.value = value;
    }

}
