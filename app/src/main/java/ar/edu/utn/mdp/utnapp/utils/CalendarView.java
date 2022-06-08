package ar.edu.utn.mdp.utnapp.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

import ar.edu.utn.mdp.utnapp.R;
import ar.edu.utn.mdp.utnapp.fetch.models.CalendarSchema;


/**
 * Created by a7med on 28/06/2015.
 */
public class CalendarView extends LinearLayout {

    // how many days to show, defaults to six weeks, 42 days
    private static final int DAYS_COUNT = 42;

    // default date format
    private static final String DATE_FORMAT = "MMM yyyy";

    // date format
    private String dateFormat;

    // current displayed month
    private final Calendar currentDate = Calendar.getInstance();

    private HashSet<CalendarSchema> events = new HashSet<>();

    //event handling
    private EventHandler eventHandler = null;

    private View selectedView;
    private Drawable selectedResource;

    // internal components
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;

    public CalendarView(Context context) {
        super(context);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    /**
     * Load control xml layout
     */
    private void initControl(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_calendar, this);

        loadDateFormat(attrs);
        assignUiElements();
        assignClickHandlers();

        updateCalendar();
    }

    private void loadDateFormat(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

        try {
            // try to load provided date format, and fallback to default otherwise
            dateFormat = ta.getString(R.styleable.CalendarView_dateFormat);
            if (dateFormat == null)
                dateFormat = DATE_FORMAT;
        } finally {
            ta.recycle();
        }
    }

    private void assignUiElements() {
        // layout is inflated, assign local variables to components
        btnPrev = findViewById(R.id.calendar_prev_button);
        btnNext = findViewById(R.id.calendar_next_button);
        txtDate = findViewById(R.id.calendar_date_display);
        grid = findViewById(R.id.calendar_grid);
    }

    private void assignClickHandlers() {
        // add one month and refresh UI
        btnNext.setOnClickListener(v -> {
            currentDate.add(Calendar.MONTH, 1);
            updateCalendar(events);
        });

        // subtract one month and refresh UI
        btnPrev.setOnClickListener(v -> {
            currentDate.add(Calendar.MONTH, -1);
            updateCalendar(events);
        });
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar() {
        updateCalendar(events);
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar(HashSet<CalendarSchema> events) {

        ArrayList<LocalDate> cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();

        updateButtons(calendar);

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < DAYS_COUNT) {
            cells.add(convertToLocalDateViaInstant(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        grid.setAdapter(new CalendarAdapter(getContext(), cells, events));

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        txtDate.setText(sdf.format(currentDate.getTime()));
    }

    private void updateButtons(Calendar calendar) {
        LocalDate date = convertToLocalDateViaInstant(calendar.getTime());
        btnPrev.setVisibility(date.getMonthValue() - 1 == Calendar.JANUARY ? View.INVISIBLE : View.VISIBLE);
        btnNext.setVisibility(date.getMonthValue() - 1 == Calendar.DECEMBER ? View.INVISIBLE : View.VISIBLE);
    }

    private class CalendarAdapter extends ArrayAdapter<LocalDate> implements ListAdapter {
        // days with events
        private final HashSet<CalendarSchema> eventDays;

        // for view inflation
        private final LayoutInflater inflater;

        public CalendarAdapter(Context context, ArrayList<LocalDate> days, HashSet<CalendarSchema> eventDays) {
            super(context, R.layout.control_calendar_day, days);
            this.eventDays = eventDays;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            // inflate item if it does not exist yet
            if (view == null)
                view = inflater.inflate(R.layout.control_calendar_day, parent, false);

            TextView text = view.findViewById(R.id.calendar_date_display);
            ImageView eventIcon = view.findViewById(R.id.calendar_event);

            // day in question
            LocalDate date = getItem(position);
            int day = date.getDayOfMonth();
            int month = date.getMonthValue();
            int year = date.getYear();

            // today
            LocalDate currentLocalDate = convertToLocalDateViaInstant(currentDate.getTime());
            LocalDate today = LocalDate.now();
            boolean isCurrentDay = day == today.getDayOfMonth() && month == today.getMonthValue() && year == today.getYear();

            // clear styling
            text.setTypeface(null, Typeface.NORMAL);
            text.setTextColor(Color.WHITE);
            text.setBackgroundResource(0);

            if (isCurrentDay) {
                // if it is today, set it to blue/bold
                text.setTypeface(null, Typeface.BOLD);
                text.setTextColor(ResourcesCompat.getColor(getResources(), R.color.secondary_500, null));
            }

            if (month != currentLocalDate.getMonthValue() || year != currentLocalDate.getYear()) {
                // if this day is outside current month, grey it out
                view.setAlpha(0.5f);
            }

            // if this day has an event, specify event image
            if (eventDays != null) {
                for (CalendarSchema event : eventDays) {
                    LocalDate eventStartDate = event.getStart().toLocalDate();
                    LocalDate eventEndDate = event.getEnd().toLocalDate();

                    boolean startDate = equalsDate(eventStartDate, date);
                    boolean endDate = equalsDate(eventEndDate, date);
                    boolean betweenDates = betweenDates(date, eventStartDate, eventEndDate);

                    if (startDate || endDate || betweenDates) {
                        eventIcon.setVisibility(View.VISIBLE);
                    }
                }
            }

            // set text
            text.setText(String.valueOf(date.getDayOfMonth()));

            View finalView = view;
            text.setOnClickListener(v -> {
                if (eventHandler == null) return;
                if (selectedView != null) {
                    selectedView.setBackground(selectedResource);
                }
                selectedView = finalView;
                selectedResource = finalView.getBackground();
                finalView.setBackgroundResource(R.drawable.ic_circumference);
                eventHandler.onDayPress(date);
            });

            return view;
        }
    }

    private boolean betweenDates(LocalDate date, LocalDate start, LocalDate end) {
        return date.compareTo(start) >= 0 && date.compareTo(end) <= 0;
    }

    private boolean equalsDate(LocalDate eventDate, LocalDate date) {
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();
        return eventDate.getDayOfMonth() == day && eventDate.getMonthValue() == month && eventDate.getYear() == year;
    }

    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * Assign event handler to be passed needed events
     */
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void setEvents(HashSet<CalendarSchema> events) {
        this.events = events;
        updateCalendar(events);
    }

    public void addEvents(HashSet<CalendarSchema> events) {
        this.events.addAll(events);
        updateCalendar(this.events);
    }

    /**
     * This interface defines what events to be reported to
     * the outside world
     */
    public interface EventHandler {
        void onDayPress(LocalDate date);
    }
}