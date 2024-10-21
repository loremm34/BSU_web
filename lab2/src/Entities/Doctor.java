package Entities;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private int id;
    private String name;
    private List<Patient> patients = new ArrayList<>();

    public Doctor(int id, String name) {
        this.id = id; this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
        patient.setDoctor(this);
    }

    public void diagnose(Patient patient, Diagnosis diagnosis) {
        patient.setDiagnosis(diagnosis);
        addPatient(patient);
    }
}
