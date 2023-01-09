package com.example.care2u.entity;

public class Timetable {
    private String patient_name;
    private String doctor_name;
    private String date;
    private String timeslot;

    public Timetable(String patient_name, String doctor_name, String date, String timeslot) {
        this.patient_name = patient_name;
        this.doctor_name = doctor_name;
        this.date = date;
        this.timeslot = timeslot;
    }

    public Timetable(){

    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }
}
