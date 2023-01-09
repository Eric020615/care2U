package com.example.care2u;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;

public class TimetableActivity extends AppCompatActivity {

    private ArrayList<CalendarDay> eventDateList = new ArrayList<>();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://care2u-99f78-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        ImageView back_iv = findViewById(R.id.IV_back);
        MaterialCalendarView calendarView = findViewById(R.id.calendar_view);
        LinearLayout appointment_event = findViewById(R.id.appointment_event_ly);
        TextView no_event = findViewById(R.id.show_no_event_tv);
        calendarView.setDateSelected(CalendarDay.today(),true);
        TextView doctor_name = findViewById(R.id.doctor_name);
        TextView appointment_date = findViewById(R.id.appointment_date_tv);
        TextView appointment_slot_time = findViewById(R.id.time_slot_tv);

//        initiateEventDateMark(calendarView, eventDateList);
        CalendarDay today = CalendarDay.today();
        initiateEvent(today);

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

            }
        });

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initiateEvent(CalendarDay today) {

    }


//    private void initiateEventDateMark(MaterialCalendarView calendarView, ArrayList<CalendarDay> eventDateList) {
//        databaseReference.child("Appointment").child(doctorName).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String[] date = dataSnapshot.getKey().split("-");
//                    eventDateList.add(CalendarDay.from(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])));
//                    calendarView.addDecorator(new DayViewDecorator() {
//                        @Override
//                        public boolean shouldDecorate(CalendarDay day) {
//                            return eventDateList.contains(day);
//                        }
//
//                        @Override
//                        public void decorate(DayViewFacade view) {
//                            view.addSpan(new DotSpan(15, Color.RED));
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}