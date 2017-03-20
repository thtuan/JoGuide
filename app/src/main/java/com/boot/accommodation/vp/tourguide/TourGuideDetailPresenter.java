package com.boot.accommodation.vp.tourguide;

import com.boot.accommodation.dto.view.TourGuideEventDTO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by thtuan on 3/20/17.
 */

public class TourGuideDetailPresenter implements TourGuideDetailPresenterMgr {

    List<TourGuideEventDTO> events = new ArrayList<>();
    TourGuideDetailActivityMgr tourGuideDetailActivityMgr;
    public TourGuideDetailPresenter(TourGuideDetailActivityMgr tourGuideDetailActivityMgr) {
        this.tourGuideDetailActivityMgr = tourGuideDetailActivityMgr;
    }

    @Override
    public List<TourGuideEventDTO> getListEvents() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017,2,22);
        TourGuideEventDTO event1 = new TourGuideEventDTO();
        event1.setName("Di hop");
        event1.setDate(calendar.getTime());
        calendar.set(2017,2,24);
        TourGuideEventDTO event2 = new TourGuideEventDTO();
        event2.setName("Di choi");
        event2.setDate(calendar.getTime());
        calendar.set(2017,2,28);
        TourGuideEventDTO event3 = new TourGuideEventDTO();
        event3.setName("Di quay");
        event3.setDate(calendar.getTime());
        calendar.set(2017,3,2);
        TourGuideEventDTO event4 = new TourGuideEventDTO();
        event4.setName("Di nhau");
        event4.setDate(calendar.getTime());
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        return events;
    }
}
