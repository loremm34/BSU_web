package Entities;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private int id;
    private String name;
    private Diagnosis diagnosis;
    private List<Appointment> appointments = new ArrayList<>();
    private Doctor doctor;
    public Patient(int id, String name) {
        this.name = name;
        this.id =  id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void discharge() {
        System.out.println("Пациент " + name + " был выписан из больницы.");
        diagnosis = null;
        appointments.clear();
    }

    public void viewAppointments() {
        System.out.println("Назначения для пациента " + name + ":");
        for (Appointment appointment : appointments) {
            System.out.println(appointment.getDescription());
        }
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Doctor getDoctor() {
        return doctor;
    }
}
