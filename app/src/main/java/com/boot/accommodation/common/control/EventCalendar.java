package com.boot.accommodation.common.control;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.common.adapter.CalendarAdapter;
import com.boot.accommodation.dto.view.CalendarDTO;
import com.boot.accommodation.dto.view.TourGuideEventDTO;
import com.boot.accommodation.listener.OnEventControl;
import com.boot.accommodation.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * EventCalendar
 *
 * @author thtuan
 * @since 3:27 PM 13-03-2017
 */
public class EventCalendar extends LinearLayout implements OnEventControl, AdapterView.OnItemLongClickListener {
    private Context context;
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private RecyclerView rvCalendar;
    private CalendarAdapter calendarAdapter;
    private boolean isInEditMode = false;
    private Calendar calendar;
    private int month;
    private String dateFormat;
    private List<TourGuideEventDTO> events;
    private List<CalendarDTO> cells = new ArrayList<>(); // cell of calendar
    private List<Date> dateInEdit = new ArrayList<>();
    private static final int DAYS_COUNT = 42; // view display in month

    int[] rainbow = new int[]{R.color.summer, R.color.fall, R.color.winter, R.color.spring};

    int[] monthSeason = new int[]{2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    public EventCalendar(Context context) {
        this(context, null, 0);
    }

    public EventCalendar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarDateElement);
        dateFormat = ta.getString(R.styleable.CalendarDateElement_dateFormat);
        if (dateFormat == null) {
            dateFormat = "MMM yyyy";
        }
        this.events = loadEvents();
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
        cells.clear();
        month = calendar.get(Calendar.MONTH);
        int season = monthSeason[month]; // spring, summer, fall, winter
        int color = rainbow[season]; // color for season
        header.setBackgroundColor(Utils.getColor(color));

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        txtDate.setText(sdf.format(calendar.getTime()));

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);
        // fill cells (42 days calendar as per our business logic)
        while (cells.size() < DAYS_COUNT) {

            CalendarDTO calendarDTO1 = new CalendarDTO();
            calendarDTO1.setDate(calendar.getTime());
            calendarDTO1.setCurrentViewMonth(month);
            cells.add(calendarDTO1);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        if (calendarAdapter == null) {
            calendarAdapter = new CalendarAdapter(cells);
            calendarAdapter.setListener(this);
            rvCalendar.setAdapter(calendarAdapter);
        } else {
            calendarAdapter.setData(cells);
        }

    }

    private void updateCalendar(List<TourGuideEventDTO> events) {
        cells.clear();
        month = calendar.get(Calendar.MONTH);
        int season = monthSeason[month]; // spring, summer, fall, winter
        int color = rainbow[season]; // color for season
        header.setBackgroundColor(Utils.getColor(color));

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
        txtDate.setText(sdf.format(calendar.getTime()));

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);
        // fill cells (42 days calendar as per our business logic)
        while (cells.size() < DAYS_COUNT) {

            CalendarDTO calendarDTO1 = new CalendarDTO();
            calendarDTO1.setDate(calendar.getTime());
            calendarDTO1.setCurrentViewMonth(month);
//            /**
//             * CalendarDTO get from event
//             * calendarDTO1 get date of display calendar
//             */
//            if (events != null) {
//                for (TourGuideEventDTO calendarDTO : events) {
//                    if (calendarDTO.getDate().getDate() == calendarDTO1.getDate().getDate() && calendarDTO.getDate().getMonth()
//                            == calendarDTO1.getDate().getMonth() && calendarDTO.getDate().getYear() == calendarDTO1.getDate().getYear()) {
//                        calendarDTO1 = calendarDTO;
//                    }
//                }
//            }
            cells.add(calendarDTO1);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        // update grid
        if (calendarAdapter == null) {
            calendarAdapter = new CalendarAdapter(cells);
            calendarAdapter.setListener(this);
            rvCalendar.setAdapter(calendarAdapter);
        } else {
            updateEvents(events);
        }

    }

    private void onPrev() {
        calendar.set(Calendar.MONTH, month - 1);
        updateCalendar();
    }

    private void onNext() {
        calendar.set(Calendar.MONTH, month + 1);
        updateCalendar();
    }


    @Override
    public void onEventControl(int action, Object item, View view, int position) {
        switch (action) {
            case CalendarAdapter.ACTION_CLICK:
                if (isInEditMode) {
                    /*view.setBackgroundColor(Utils.getColor(R.color.grey_bg));
                    ivEdit.setVisibility(VISIBLE);
                    etEditName.setVisibility(VISIBLE);*/
                } else {

                }
                break;
            case CalendarAdapter.ACTION_LONG_CLICK:
                CalendarDTO calendarDTO = (CalendarDTO) item;
                dateInEdit.add((calendarDTO.getDate()));
                if (dateInEdit.size() > 0) {
                    isInEditMode = true;
                }
                break;
            case CalendarAdapter.ACTION_CANCEL_EDIT:
                dateInEdit.remove(item);
                if (dateInEdit.size() == 0) {
                    isInEditMode = false;
                }
                break;
        }
    }

    @Override
    public void onLoadMore(int position) {

    }

    private void editEvents(List<Date> dates, String eventName) {
        if (dates.size() > 0) {
            for (Date date : dates) {
                TourGuideEventDTO tourGuideEvent;
                tourGuideEvent = isHaveEvent(date);
                if (tourGuideEvent == null) {
                    tourGuideEvent = new TourGuideEventDTO();
                    tourGuideEvent.setName(eventName);
                    tourGuideEvent.setDate(date);
                    events.add(tourGuideEvent);
                } else {
                    tourGuideEvent.setName(eventName);
                }
            }
        }
    }

    /**
     * check whether
     *
     * @param
     * @return
     */
    private TourGuideEventDTO isHaveEvent(Date date) {
        for (TourGuideEventDTO haveEvent : events) {
            if (haveEvent.getDate().compareTo(date) == 0) {
                return haveEvent;
            }
        }
        return null;
    }

    private List<TourGuideEventDTO> loadEvents() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("event", Context.MODE_APPEND);
        List<TourGuideEventDTO> events = new ArrayList<>();
        if (sharedPreferences != null) {
            Type listType = new TypeToken<List<TourGuideEventDTO>>() {
            }.getType();
            events = new Gson().fromJson(sharedPreferences.getString("data", ""), listType);
            return events;
        } else {
            return events;
        }
    }

    private void saveEvents(List<TourGuideEventDTO> events) {
        String data = new Gson().toJson(events);
        SharedPreferences sharedPreferences = context.getSharedPreferences("event", Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("data", data);
        editor.apply();
    }

    public void updateEvents(List<TourGuideEventDTO> events) {
        calendarAdapter.setEvents(events);
    }
}
