package com.example.care2u;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookingAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    Button slot1;
    Button slot2;
    Button slot3;
    Button slot4;
    static Button current_pressed;
    ArrayList<CalendarDay> bookedDateList = new ArrayList<>();
    CalendarDay selected_date = CalendarDay.today();
    HashMap<CalendarDay, String[]> booked_slot_time = new HashMap<>();
    Button[] slot_button;
    MaterialCalendarView calendarView;
    private String[] bookedslot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_appointment);
        CircleImageView circleImageView = findViewById(R.id.imageProfile);
        TextView doctor_name = findViewById(R.id.doctor_name);
        Button book_appointment_btn = findViewById(R.id.book_btn);
        ImageView back_iv = findViewById(R.id.IV_back);
        slot1 = findViewById(R.id.slot1);
        slot2 = findViewById(R.id.slot2);
        slot3 = findViewById(R.id.slot3);
        slot4 = findViewById(R.id.slot4);
        slot_button = new Button[]{slot1, slot2, slot3, slot4};
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        circleImageView.setImageResource(bundle.getInt("image"));
        String doctorName = bundle.getString("name");
        doctor_name.setText(doctorName);

        calendarView = findViewById(R.id.calendar_view);
        calendarView.setDateSelected(CalendarDay.today(), true);
        initiateBookedSlot(CalendarDay.today());
        initiateBookingDate(calendarView, bookedDateList);

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
//                Toast.makeText(getApplicationContext(), String.valueOf(date.getDate()),Toast.LENGTH_SHORT).show();
                if (date.isBefore(CalendarDay.today())) {
                    calendarView.clearSelection();
                    calendarView.setDateSelected(CalendarDay.today(), true);
                    Toast.makeText(getApplicationContext(), "Cannot select previous date", Toast.LENGTH_SHORT).show();
                }
                initiateBookedSlot(date);
                selected_date = date;
            }
        });

        slot1.setOnClickListener(this);
        slot2.setOnClickListener(this);
        slot3.setOnClickListener(this);
        slot4.setOnClickListener(this);
        book_appointment_btn.setOnClickListener(this);

    }

    private void initiateBookedSlot(CalendarDay date) {
        if (current_pressed != null) {
            current_pressed.setSelected(false);
        }
        bookedslot = booked_slot_time.get(date);
        for (int i = 0; i < slot_button.length; i++) {
            slot_button[i].setClickable(true);
            slot_button[i].setTextColor(Color.BLACK);
            slot_button[i].setBackgroundResource(R.drawable.slot_time_selector);
        }
        if (bookedslot == null) {
            bookedslot = new String[]{"1", "1", "1", "1"};
            booked_slot_time.put(date, bookedslot);
        } else {
            for (int i = 0; i < bookedslot.length; i++) {
                if (bookedslot[i] == "0") {
                    slot_button[i].setClickable(false);
                    slot_button[i].setTextColor(Color.RED);
                    slot_button[i].setBackgroundColor(Color.TRANSPARENT);
                } else {
                    slot_button[i].setClickable(true);
                }

            }
        }
    }

    private void initiateBookingDate(MaterialCalendarView calendarView, ArrayList<CalendarDay> bookedDateList) {
//        bookedDateList.add(CalendarDay.from(2023, 1, 23));
//        bookedDateList.add(CalendarDay.from(2023, 2, 12));
//        bookedDateList.add(CalendarDay.from(2023, 1, 11));
        calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return bookedDateList.contains(day);
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new DotSpan(15, Color.RED));
            }
        });
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.slot1:
                if (current_pressed != null) {
                    current_pressed.setSelected(false);
                }
                current_pressed = slot1;
                slot1.setSelected(true);
                break;
            case R.id.slot2:

                if (current_pressed != null) {
                    current_pressed.setSelected(false);
                }
                current_pressed = slot2;
                slot2.setSelected(true);
                break;
            case R.id.slot3:
                if (current_pressed != null) {
                    current_pressed.setSelected(false);
                }
                current_pressed = slot3;
                slot3.setSelected(true);
                break;
            case R.id.slot4:
                if (current_pressed != null) {
                    current_pressed.setSelected(false);
                }
                current_pressed = slot4;
                slot4.setSelected(true);
                break;
            case R.id.book_btn:
                try {
                    String slot_time = getTimeSlot(current_pressed.getId());
                    bookedDateList.add(selected_date);
                    String [] list = updatedbookedslot(slot_time, bookedslot);
                    booked_slot_time.put(selected_date, updatedbookedslot(slot_time, bookedslot));
                    String str_book_date = String.valueOf(selected_date);
                    Toast.makeText(getApplicationContext(), "Selected date: " + str_book_date + "time slot: " + slot_time, Toast.LENGTH_SHORT).show();
                    initiateBookingDate(calendarView, bookedDateList);
                    initiateBookedSlot(selected_date);
                    System.out.println(Arrays.asList(booked_slot_time));
                    current_pressed = null;
                    System.out.println(Arrays.asList(list));
                } catch (NullPointerException e) {
                    Toast.makeText(this, "No slot time is selected", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private String[] updatedbookedslot(String slot_time, String[] bookedslot) {
        if (slot_time.equals("slot1")) {
            bookedslot[0] = "0";
        } else if (slot_time.equals("slot2")) {
            bookedslot[1] = "0";
        } else if (slot_time.equals("slot3")) {
            bookedslot[2] = "0";
        } else if (slot_time.equals("slot4")) {
            bookedslot[3] = "0";
        }

        return bookedslot;
    }

    private String getTimeSlot(int id) {
        String slot = "";
        switch (id) {
            case R.id.slot1:
                slot = "slot1";
                break;
            case R.id.slot2:
                slot = "slot2";
                break;
            case R.id.slot3:
                slot = "slot3";
                break;
            case R.id.slot4:
                slot = "slot4";
                break;
        }
        return slot;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        current_pressed = null;
    }
}