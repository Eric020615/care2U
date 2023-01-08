package com.example.care2u;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.care2u.adapter.DoctorInfoAdapter;
import com.example.care2u.entity.Doctor;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {

    ArrayList<Doctor> doctorList = new ArrayList<Doctor>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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