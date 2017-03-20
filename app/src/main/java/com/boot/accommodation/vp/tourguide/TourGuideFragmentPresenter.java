package com.boot.accommodation.vp.tourguide;

import com.boot.accommodation.dto.view.TourGuideDTO;
import com.boot.accommodation.dto.view.TourGuideEventDTO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by thtuan on 3/18/17.
 */

public class TourGuideFragmentPresenter implements TourGuideFragmentPresenterMgr {
    private TourGuideFragmentMgr tourGuideFragmentMgr;
    private List<TourGuideDTO> listTourguide = new ArrayList<>();

    public TourGuideFragmentPresenter(TourGuideFragmentMgr tourGuideFragmentMgr) {
        this.tourGuideFragmentMgr = tourGuideFragmentMgr;
        initData();

    }
    // test data
    @Override
    public List<TourGuideDTO> getData(){
        List<TourGuideDTO> list = new ArrayList<>();
        list.addAll(listTourguide);
        return list;
    }

    @Override
    public List<TourGuideDTO> getListFilter(int type) {
        List<TourGuideDTO> list = new ArrayList<>();
        TourGuideDTO tourguide1 = new TourGuideDTO();
        tourguide1.setName("Truong hn");
        tourguide1.setImage("https://scontent.fsgn2-1.fna.fbcdn.net/v/t1.0-9/16864972_10202741677700134_2402732164593269649_n.jpg?oh=9c5771fcbd064e9cfbf0aa23f267be9a&oe=5971907F");
        tourguide1.setPhone("01234567890");
        tourguide1.setTown("Ho Chi Minh");

        TourGuideDTO tourguide2 = new TourGuideDTO();
        tourguide2.setName("Tuan lt");
        tourguide2.setImage("https://scontent.fsgn2-1.fna.fbcdn.net/v/t31.0-8/11157366_10203109559059754_6002146575315660882_o.jpg?oh=89a91395e28e87d991a31bef098cb271&oe=59706DA7");
        tourguide2.setPhone("01234567890");
        tourguide2.setTown("Vung Tau");

        TourGuideDTO tourguide3 = new TourGuideDTO();
        tourguide3.setName("thanh tuan");
        tourguide3.setPhone("01234567890");
        tourguide3.setImage("https://scontent.fsgn2-1.fna.fbcdn.net/v/t31.0-8/11157366_10203109559059754_6002146575315660882_o.jpg?oh=89a91395e28e87d991a31bef098cb271&oe=59706DA7");
        tourguide3.setTown("Quang Ngai");

        switch (type){
            case TourguideFragment.PROFESSIONAL:
                list.add(tourguide1);
                list.add(tourguide2);
                break;
            case TourguideFragment.NATIVE:
                list.add(tourguide1);
                list.add(tourguide3);
                break;
            case TourguideFragment.STUDENT:
                list.add(tourguide3);
                break;
        }
        return list;
    }

    private void initData(){
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
        TourGuideDTO tourguide1 = new TourGuideDTO();
        tourguide1.setName("Truong hn");
        tourguide1.setImage("https://scontent.fsgn2-1.fna.fbcdn.net/v/t1.0-9/16864972_10202741677700134_2402732164593269649_n.jpg?oh=9c5771fcbd064e9cfbf0aa23f267be9a&oe=5971907F");
        tourguide1.setPhone("01234567890");
        tourguide1.setTown("Ho Chi Minh");
        List<TourGuideEventDTO> events = new ArrayList<>();
        List<TourGuideEventDTO> events2 = new ArrayList<>();
        List<TourGuideEventDTO> events3 = new ArrayList<>();

        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);

        tourguide1.setTourGuideEvents(events);
        TourGuideDTO tourguide2 = new TourGuideDTO();
        tourguide2.setName("Tuan lt");
        tourguide2.setImage("https://scontent.fsgn2-1.fna.fbcdn.net/v/t31.0-8/11157366_10203109559059754_6002146575315660882_o.jpg?oh=89a91395e28e87d991a31bef098cb271&oe=59706DA7");
        tourguide2.setPhone("01234567890");
        tourguide2.setTown("Vung Tau");

        events2.add(event2);
        events2.add(event3);
        tourguide2.setTourGuideEvents(events2);
        TourGuideDTO tourguide3 = new TourGuideDTO();
        tourguide3.setName("thanh tuan");
        tourguide3.setImage("https://scontent.fsgn2-1.fna.fbcdn.net/v/t31.0-8/11157366_10203109559059754_6002146575315660882_o.jpg?oh=89a91395e28e87d991a31bef098cb271&oe=59706DA7");
        tourguide3.setPhone("01234567890");
        tourguide3.setTown("Quang Ngai");
        events3.add(event1);
        events3.add(event4);
        tourguide3.setTourGuideEvents(events3);
        listTourguide.add(tourguide1);
        listTourguide.add(tourguide2);
        listTourguide.add(tourguide3);
    }
}
