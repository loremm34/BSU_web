package Entities;

import java.util.ArrayList;
import java.util.List;

public class Hospital {
    private static Hospital instance;
    private List<Doctor> doctors = new ArrayList<>();
    private List<Patient> patients = new ArrayList<>();

    private Hospital() {}

    public static Hospital getInstance() {
        if (instance == null) {
            instance = new Hospital();
        }
        return instance;
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public List<Patient> getPatientsByDoctor(Doctor doctor) {
        List<Patient> result = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.getDiagnosis() != null && patient.getDiagnosis().getDescription().contains(doctor.getName())) {
                result.add(patient);
            }
        }
        return result;
    }

    public Patient getPatientByName(String name) {
        for (Patient patient : patients) {
            if (patient.getName().equalsIgnoreCase(name)) {
                return patient;
            }
        }
        return null;
    }
}
