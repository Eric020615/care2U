package com.example.care2u.entity;

import java.util.List;

public class Appointment {
    private String DoctorName;
    private String PatientName;
    private String date;
    private List<String> timeslot;

    public Appointment(String doctorName, String patientName, String date, List<String> timeslot) {
        DoctorName = doctorName;
        PatientName = patientName;
        this.date = date;
        this.timeslot = timeslot;
    }

    public Appointment(){

    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(List<String> timeslot) {
        this.timeslot = timeslot;
    }
}
