package com.example.care2u.entity;

public class Doctor {
    private String name;
    private String date;
    private String time_slot;
    private String appointment_patient_name;
    private String description;
    private String original;
    private double rating;

    public Doctor(String name, String description, String original, double rating) {
        this.name = name;
        this.description = description;
        this.original = original;
        this.rating = rating;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(String time_slot) {
        this.time_slot = time_slot;
    }

    public String getAppointment_patient_name() {
        return appointment_patient_name;
    }

    public void setAppointment_patient_name(String appointment_patient_name) {
        this.appointment_patient_name = appointment_patient_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
