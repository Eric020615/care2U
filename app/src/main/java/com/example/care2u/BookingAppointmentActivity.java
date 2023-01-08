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

import com.example.care2u.entity.Appointment;
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
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookingAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    private Button slot1, slot2, slot3, slot4;
    private static Button current_pressed;
    private ArrayList<CalendarDay> bookedDateList = new ArrayList<>();
    private CalendarDay selected_date = CalendarDay.today();
    private Button[] slot_button;
    private MaterialCalendarView calendarView;
    private String doctorName;
    private String patientName;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://care2u-99f78-default-rtdb.firebaseio.com/");

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
        doctorName = bundle.getString("name");
        doctor_name.setText(doctorName);

        calendarView = findViewById(R.id.calendar_view);
        calendarView.setDateSelected(CalendarDay.today(), true);
        //initiateBookedSlot(CalendarDay.today());
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
                databaseReference.child("Appointment").child(doctorName).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String[] date_1 = dataSnapshot.getKey().split("-");
                            CalendarDay day = CalendarDay.from(Integer.parseInt(date_1[0]), Integer.parseInt(date_1[1]), Integer.parseInt(date_1[2]));
                            initiateBookedSlot(date);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                selected_date = date;
            }
        });

        slot1.setOnClickListener(this);
        slot2.setOnClickListener(this);
        slot3.setOnClickListener(this);
        slot4.setOnClickListener(this);
        book_appointment_btn.setOnClickListener(this);

    }

    private void initiateBookedSlot(CalendarDay day) {
        if (current_pressed != null) {
            current_pressed.setSelected(false);
        }
        databaseReference.child("Appointment").child(doctorName).child(day.getDate().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Appointment appointment = snapshot.getValue(Appointment.class);
                // List<String>temp=snapshot.child("timeslot").getValue(List<String>.class);
                String[] temp_slot;
                if (appointment == null) {
                    temp_slot = new String[]{"1", "1", "1", "1"};
                } else {
                    List<String> temp = appointment.getTimeslot();
                    temp_slot = new String[4];
                    for (int i = 0; i < temp.size(); i++) {
                        temp_slot[i] = temp.get(i);
                        System.out.println(temp_slot[i]);
                    }
                }

                for (int i = 0; i < slot_button.length; i++) {
                    slot_button[i].setClickable(true);
                    slot_button[i].setTextColor(Color.BLACK);
                    slot_button[i].setBackgroundResource(R.drawable.slot_time_selector);
                }
                for (int i = 0; i < temp_slot.length; i++) {
                    if (temp_slot[i].equals("0")) {
                        slot_button[i].setClickable(false);
                        slot_button[i].setTextColor(Color.RED);
                        slot_button[i].setBackgroundColor(Color.TRANSPARENT);
                    } else {
                        slot_button[i].setClickable(true);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initiateBookingDate(MaterialCalendarView calendarView, ArrayList<CalendarDay> bookedDateList) {
        databaseReference.child("Appointment").child(doctorName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String[] date = dataSnapshot.getKey().split("-");
                    bookedDateList.add(CalendarDay.from(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])));
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                    databaseReference.child("Appointment").child(selected_date.getDate().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Appointment appointment = snapshot.getValue(Appointment.class);
                            String[] temp_slot;
                            if (appointment == null) {
                                temp_slot = new String[]{"1", "1", "1", "1"};
                            } else {
                                List<String> temp = appointment.getTimeslot();
                                temp_slot = new String[4];
                                for (int i = 0; i < temp.size(); i++) {
                                    temp_slot[i] = temp.get(i);
                                    System.out.println(temp_slot[i]);
                                }
                            }
                            String[] list = updatedbookedslot(slot_time, temp_slot);//add
                            List<String> timeslot = Arrays.asList(list);
                            String str_book_date = String.valueOf(selected_date.getDate());//add
                            Toast.makeText(getApplicationContext(), "Selected date: " + str_book_date + "time slot: " + slot_time, Toast.LENGTH_SHORT).show();
                            initiateBookingDate(calendarView, bookedDateList);
                            initiateBookedSlot(selected_date);
                            current_pressed = null;
                            databaseReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    patientName = snapshot.child("username").getValue(String.class);
                                    Appointment appointment = new Appointment(doctorName, patientName, str_book_date, timeslot);
                                    databaseReference.child("Appointment").child(doctorName).child(str_book_date).setValue(appointment);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            System.out.println(patientName);
                            System.out.println(Arrays.asList(list));
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
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