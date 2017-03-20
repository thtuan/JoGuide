package com.boot.accommodation.vp.tourguide;

/**
 * Created by thtuan on 3/18/17.
 */

public class EventPresenter implements EventPresenterMgr {
    EventActivityMgr eventActivityMgr;
    public EventPresenter(EventActivityMgr eventActivityMgr) {
        this.eventActivityMgr = eventActivityMgr;
    }
}
