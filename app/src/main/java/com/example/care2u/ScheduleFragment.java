package com.example.care2u;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.care2u.adapter.DoctorInfoAdapter;
import com.example.care2u.entity.Doctor;

import java.util.ArrayList;

public class ScheduleFragment extends Fragment {

    private ArrayList<Doctor> doctorList = new ArrayList<Doctor>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initial();
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);
        RecyclerView doctor_info_rv = root.findViewById(R.id.order_info_rv);

        DoctorInfoAdapter doctorInfoAdapter = new DoctorInfoAdapter(getContext(),doctorList);
        doctor_info_rv.setAdapter(doctorInfoAdapter);
        doctor_info_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }

    private void initial() {
        doctorList.add(new Doctor("Dr Elizabeth Smith","Mental Health Specialist 10 Years Experience","Hawaii, United States",4.5));
        doctorList.add(new Doctor("Dr John Smith","Mental Health Specialist 8 Years Experience","London, United Kingdom",4.8));
        doctorList.add(new Doctor("Dr Natalia Ivanova","Mental Health Specialist 7 Years Experience","Moscow, Russia",4.7));
        doctorList.add(new Doctor("Dr Maria Garcia","Mental Health Specialist 7 Years Experience","New York City, United States",4.5));
        doctorList.add(new Doctor("Dr David Satcher","Mental Health Specialist 6 Years Experience","Tuskegee, United States",4.6));
    }
}