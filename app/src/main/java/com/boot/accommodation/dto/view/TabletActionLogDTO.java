package com.boot.accommodation.dto.view;

/**
 * Log bug from client
 *
 * @author tuanlt
 * @since 10:12 CH 26/07/2016
 */
public class TabletActionLogDTO extends BaseDTO {
    // loi do vang exception
    public static final int LOG_EXCEPTION = 0;
    // loi do server tra ve
    public static final int LOG_SERVER = 1;
    // loi do xu ly duoi client
    public static final int LOG_CLIENT = 2;
    // loi do force close
    public static final int LOG_FORCE_CLOSE = 3;
    private long id;
    private String deviceImei; // device phone
    private String appVersion; // app version
    private int type; // type log
    private String content; // content
    private String description; // description
    private int androidVersion; // version android
    private long userId; // id user
    private String createAt; // time

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getDeviceImei() {
        return deviceImei;
    }

    public void setDeviceImei( String deviceImei ) {
        this.deviceImei = deviceImei;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion( String appVersion ) {
        this.appVersion = appVersion;
    }

    public int getType() {
        return type;
    }

    public void setType( int type ) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId( long userId ) {
        this.userId = userId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt( String createAt ) {
        this.createAt = createAt;
    }

    public int getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion( int androidVersion ) {
        this.androidVersion = androidVersion;
    }
}
