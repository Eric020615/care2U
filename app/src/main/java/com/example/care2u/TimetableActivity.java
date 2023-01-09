package com.example.care2u;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.care2u.adapter.TimetableAdapter;
import com.example.care2u.entity.Timetable;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimetableActivity extends AppCompatActivity {

    private ArrayList<CalendarDay> eventDateList = new ArrayList<>();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://care2u-99f78-default-rtdb.firebaseio.com/");
    private TimetableAdapter timetableAdapter;
    private RecyclerView eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        timetableAdapter = new TimetableAdapter(this);
        ImageView back_iv = findViewById(R.id.IV_back);
        MaterialCalendarView calendarView = findViewById(R.id.calendar_view);
        LinearLayout appointment_event = findViewById(R.id.appointment_event_ly);
        calendarView.setDateSelected(CalendarDay.today(),true);
        TextView doctor_name = findViewById(R.id.doctor_name_tv);
        TextView appointment_date = findViewById(R.id.appointment_date_tv);
        TextView appointment_slot_time = findViewById(R.id.time_slot_tv);
        eventList = findViewById(R.id.appointment_list);
        eventList.setLayoutManager(new LinearLayoutManager(this));
        TextView NoEvent = findViewById(R.id.no_event_tv);


        initiateEventDateMark(calendarView, eventDateList);
//        CalendarDay today = CalendarDay.today();
//        initiateEvent(today);

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                databaseReference.child("Timetable").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            String temp = date.getDate().toString();
                            if (temp.equals(dataSnapshot.getKey())){
                                eventList.setVisibility(View.VISIBLE);
                                NoEvent.setVisibility(View.GONE);
                                initiateEvent(date);
                                break;
                            }
                            else {
                                timetableAdapter.clear();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
        databaseReference.child("Timetable").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String day = today.getDate().toString();
                timetableAdapter.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if (day.equals(dataSnapshot.getKey())){
                        for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            Timetable timetable = dataSnapshot1.getValue(Timetable.class);
                            timetableAdapter.add(timetable);
                        }
                        eventList.setAdapter(timetableAdapter);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void initiateEventDateMark(MaterialCalendarView calendarView, ArrayList<CalendarDay> eventDateList) {
        databaseReference.child("Timetable").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String[]date = dataSnapshot.getKey().split("-");
                    eventDateList.add(CalendarDay.from(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2])));
                    calendarView.addDecorator(new DayViewDecorator() {
                        @Override
                        public boolean shouldDecorate(CalendarDay day) {
                            return eventDateList.contains(day);
                        }

                        @Override
                        public void decorate(DayViewFacade view) {
                            view.addSpan(new DotSpan(15, Color.RED));
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}