package com.boot.accommodation.common.control;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.common.adapter.CalendarAdapter;
import com.boot.accommodation.dto.view.CalendarDTO;
import com.boot.accommodation.dto.view.Meeting;
import com.boot.accommodation.dto.view.User;
import com.boot.accommodation.listener.OnEventControl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * EventCalendar
 *
 * @author thtuan
 * @since 3:27 PM 13-03-2017
 */
public class EventCalendar extends LinearLayout implements OnEventControl {
    private Context context;
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private RecyclerView rvCalendar;
    private CalendarAdapter calendarAdapter;
    private Calendar calendar;
    private OnEventControl listener;
    private static final int DAYS_COUNT = 42;
    int[] rainbow = new int[]{R.color.summer, R.color.fall, R.color.winter, R.color.spring};

    int[] monthSeason = new int[]{2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};

    public EventCalendar(Context context) {
        this(context, null, 0);
    }

    public EventCalendar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initControl();
    }

    public EventCalendar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void initControl() {
        calendar = Calendar.getInstance();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.event_calendar, this);

        // layout is inflated, assign local variables to components
        header = (LinearLayout) findViewById(R.id.calendar_header);
        btnPrev = (ImageView) findViewById(R.id.calendar_prev_button);
        btnNext = (ImageView) findViewById(R.id.calendar_next_button);
        txtDate = (TextView) findViewById(R.id.calendar_date_display);
        rvCalendar = (RecyclerView) findViewById(R.id.calendar_grid);
        rvCalendar.setHasFixedSize(true);
        rvCalendar.setLayoutManager(new GridLayoutManager(context, 7));
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onPrev();
            }
        });
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onNext();
            }
        });
        updateCalendar();
    }

    private void updateCalendar() {

        int month = calendar.get(Calendar.MONTH);
        int season = monthSeason[month];
        int color = rainbow[season];

        header.setBackgroundColor(getResources().getColor(color));
        List<CalendarDTO> cells = new ArrayList<>();


        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);
        // fill cells (42 days calendar as per our business logic)
        while (cells.size() < DAYS_COUNT) {

            CalendarDTO calendarDTO1 = new CalendarDTO();
            calendarDTO1.setDate(calendar.getTime());
            /**
             * CalendarDTO get from event
             * calendarDTO1 get date of display calendar
             */
            for (CalendarDTO calendarDTO : getListEvents()) {
                if (calendarDTO.getDate().getDate() == calendarDTO1.getDate().getDate() && calendarDTO.getDate().getMonth()
                        == calendarDTO1.getDate().getMonth() && calendarDTO.getDate().getYear() == calendarDTO1.getDate().getYear()) {
                    calendarDTO1 = calendarDTO;
                }
            }
            cells.add(calendarDTO1);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        if (calendarAdapter == null) {
            calendarAdapter = new CalendarAdapter(cells);
            calendarAdapter.setListener(this);
            rvCalendar.setAdapter(calendarAdapter);
        }
        calendarAdapter.notifyDataSetChanged();

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
        txtDate.setText(sdf.format(Calendar.getInstance().getTime()));
    }

    private void onPrev() {
        calendar.add(Calendar.MONTH, -1);
        updateCalendar();
    }

    private void onNext() {
        calendar.add(Calendar.MONTH, 1);
        updateCalendar();
    }


    @Override
    public void onEventControl(int action, Object item, View View, int position) {
        listener.onEventControl(1, item, View,position);
       /* List<CalendarDTO> eventInDate = new ArrayList<>();
        for (CalendarDTO day : eventDays){
            if (day.getDate().getDay() == item.getDate().getDay()&&
                    day.getDate().getMonth() == item.getDate().getMonth()&&
                    day.getDate().getYear() == item.getDate().getYear()){
                    eventDays.add(day);
            }
        }*/
    }

    public void setListener(OnEventControl listener) {
        this.listener = listener;
    }



    @Override
    public void onLoadMore(int position) {

    }

    private List<CalendarDTO> getListEvents() {
        List<CalendarDTO> calendarDTOs = new ArrayList<>();
        List<Meeting> meetings = new ArrayList<>();
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setStatus(User.Status.COMING);
        user1.setName("Tuan");
        user1.setLat(10.8726371);
        user1.setLng(106.8025529);
        User user3 = new User();
        user3.setStatus(User.Status.COMING);
        user3.setName("Nhi nho");
        user3.setLat(10.873870 );
        user3.setLng(106.804675);
        User user2 = new User();
        user2.setStatus(User.Status.WAITING);
        user2.setName("Thao");
        user2.setLat(10.878579);
        user2.setLng(106.806315);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        Meeting meeting1 = new Meeting();
        meeting1.setDate(Calendar.getInstance().getTime());
        meeting1.setAddress("Trường ĐH Công nghệ thông tin");
        meeting1.setLat(10.869915);
        meeting1.setLng(106.8023442);
        meeting1.setName("Họp nhóm chuẩn bị đồ án");
        meeting1.setStatus(Meeting.TypeSchedule.WORK);
        meeting1.setUsers(users);

        Meeting meeting2 = new Meeting();
        meeting2.setDate(Calendar.getInstance().getTime());
        meeting2.setAddress("KTX khu B ĐHQG HCM");
        meeting2.setLat(10.869915);
        meeting2.setLng(106.8023442);
        meeting2.setName("Dự sinh nhật của thủy");
        meeting2.setStatus(Meeting.TypeSchedule.BIRTHDAY);
        meeting2.setUsers(users);

        Meeting meeting3 = new Meeting();
        meeting3.setDate(Calendar.getInstance().getTime());
        meeting3.setAddress("Thư viện trung tâm");
        meeting3.setLat(10.869795);
        meeting3.setLng(106.796029);
        meeting3.setName("Tham khảo tài liệu");
        meeting3.setStatus(Meeting.TypeSchedule.WORK);
        meeting3.setUsers(users);
        meetings.add(meeting1);
        meetings.add(meeting2);
        meetings.add(meeting3);
        CalendarDTO calendarDTO1 = new CalendarDTO();
        calendarDTO1.setMeetings(meetings);
        calendarDTO1.setDate(Calendar.getInstance().getTime());
        calendarDTOs.add(calendarDTO1);


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,1);
        List<Meeting> meetings2 = new ArrayList<>();
        List<User> users2 = new ArrayList<>();
        User user4 = new User();
        user4.setStatus(User.Status.COMING);
        user4.setName("Tuan");
        user4.setLat(10.8726371);
        user4.setLng(106.8025529);
        User user5 = new User();
        user5.setStatus(User.Status.WAITING);
        user5.setName("Thao");
        user5.setLat(10.878579);
        user5.setLng(106.806315);
        users2.add(user1);
        users2.add(user2);
        Meeting meeting4 = new Meeting();
        meeting4.setDate(calendar.getTime());
        meeting4.setAddress("Trường ĐH Công nghệ thông tin");
        meeting4.setLat(10.869915);
        meeting4.setLng(106.8023442);
        meeting4.setName("Họp nhóm chuẩn bị đồ án");
        meeting4.setStatus(Meeting.TypeSchedule.WORK);
        meeting4.setUsers(users2);

        Meeting meeting5 = new Meeting();
        meeting5.setDate(calendar.getTime());
        meeting5.setAddress("KTX khu B ĐHQG HCM");
        meeting5.setLat(10.869915);
        meeting5.setLng(106.8023442);
        meeting5.setName("Dự sinh nhật của thủy");
        meeting5.setStatus(Meeting.TypeSchedule.BIRTHDAY);
        meeting5.setUsers(users2);

        meetings2.add(meeting4);
        meetings2.add(meeting5);

        CalendarDTO calendarDTO2 = new CalendarDTO();
        calendarDTO2.setMeetings(meetings2);
        calendarDTO2.setDate(calendar.getTime());
        calendarDTOs.add(calendarDTO2);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DAY_OF_MONTH,5);
        List<Meeting> meetings3 = new ArrayList<>();
        meetings3.add(meeting1);

        CalendarDTO calendarDTO3 = new CalendarDTO();
        calendarDTO3.setMeetings(meetings3);
        calendarDTO3.setDate(calendar2.getTime());
        calendarDTOs.add(calendarDTO3);

        return calendarDTOs;
    }
}
