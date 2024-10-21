package Controller;

import DAO.*;
import Entities.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class HospitalController {
    private static final Scanner scanner = new Scanner(System.in);
    private final DoctorDAO doctorDAO = new DoctorDAO();
    private final PatientDAO patientDAO = new PatientDAO();
    private final DiagnosisDAO diagnosisDAO = new DiagnosisDAO();
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();

    // Показать всех врачей
    public void showDoctors() {
        try {
            List<Doctor> doctors = doctorDAO.getAllDoctors();
            System.out.println("\nСписок врачей:");
            for (Doctor doctor : doctors) {
                System.out.println(doctor.getId() + ". " + doctor.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Показать пациентов врача
    public void showPatientsOfDoctor() {
        try {
            System.out.print("\nВведите ID врача: ");
            int doctorId = scanner.nextInt();
            List<Patient> patients = patientDAO.getPatientsByDoctor(doctorId);

            System.out.println("\nПациенты врача:");
            for (Patient patient : patients) {
                System.out.println(patient.getId() + ". " + patient.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Добавить пациента к врачу
    public void addPatientToDoctor() {
        try {
            System.out.print("\nВведите имя пациента: ");
            String patientName = scanner.next();
            System.out.print("Введите ID врача: ");
            int doctorId = scanner.nextInt();

            patientDAO.addPatient(patientName, doctorId);
            System.out.println("Пациент добавлен.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // Назначить диагноз пациенту
    public void diagnosePatient() {
        try {
            System.out.print("\nВведите имя пациента: ");
            String patientName = scanner.next();
            Patient patient = patientDAO.getPatientByName(patientName);
            if (patient == null) {
                System.out.println("Пациент не найден.");
                return;
            }

            System.out.print("Введите описание диагноза: ");
            String diagnosisDescription = scanner.next();

            diagnosisDAO.addDiagnosis(diagnosisDescription, patient.getId());
            System.out.println("Диагноз назначен.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Показать диагноз пациента
    public void showDiagnosisOfPatient() {
        try {
            System.out.print("\nВведите имя пациента: ");
            String patientName = scanner.next();
            Patient patient = patientDAO.getPatientByName(patientName);

            if (patient != null) {
                Diagnosis diagnosis = diagnosisDAO.getDiagnosisByPatient(patient.getId());
                if (diagnosis != null) {
                    System.out.println("Диагноз пациента " + patientName + ": " + diagnosis.getDescription());
                } else {
                    System.out.println("У пациента нет диагноза.");
                }
            } else {
                System.out.println("Пациент не найден.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Добавить назначение пациенту
    public void addAppointmentToPatient() {
        try {
            System.out.print("\nВведите имя пациента: ");
            String patientName = scanner.next();
            Patient patient = patientDAO.getPatientByName(patientName);

            if (patient != null) {
                System.out.print("Введите описание назначения: ");
                String appointmentDescription = scanner.next();

                appointmentDAO.addAppointment(appointmentDescription, patient.getId());
                System.out.println("Назначение добавлено.");
            } else {
                System.out.println("Пациент не найден.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Показать назначения пациента
    public void showAppointmentsOfPatient() {
        try {
            System.out.print("\nВведите имя пациента: ");
            String patientName = scanner.next();
            Patient patient = patientDAO.getPatientByName(patientName);

            if (patient != null) {
                List<Appointment> appointments = appointmentDAO.getAppointmentsByPatient(patient.getId());

                System.out.println("\nНазначения пациента " + patientName + ":");
                for (Appointment appointment : appointments) {
                    System.out.println("- " + appointment.getDescription());
                }
            } else {
                System.out.println("Пациент не найден.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Выписать пациента
    public void dischargePatient() {
        try {
            System.out.print("\nВведите имя пациента: ");
            String patientName = scanner.next();
            Patient patient = patientDAO.getPatientByName(patientName);

            if (patient != null) {
                patient.discharge();
                System.out.println("Пациент выписан.");
            } else {
                System.out.println("Пациент не найден.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
