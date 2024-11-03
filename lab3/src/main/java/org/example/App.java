package org.example;

import controller.MainController;
import entity.Appointment;
import entity.Doctor;
import entity.Patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class App 
{
    public static void main(String[] args) {
        MainController controller = new MainController();
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        boolean running = true;
        while (running) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Показать всех пациентов");
            System.out.println("2. Показать всех врачей");
            System.out.println("3. Показать все назначения");
            System.out.println("4. Показать назначения конкретного пациента");
            System.out.println("5. Создать новое назначение");
            System.out.println("6. Обновить назначение");
            System.out.println("7. Удалить назначение");
            System.out.println("8. Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Считываем конец строки

            switch (choice) {
                case 1:
                    List<Patient> patients = controller.getAllPatients();
                    patients.forEach(patient -> System.out.println("ID: " + patient.getId() + ", Имя: " + patient.getName() + ", Диагноз: " + patient.getDiagnosis()));
                    break;

                case 2:
                    List<Doctor> doctors = controller.getAllDoctors();
                    doctors.forEach(doctor -> System.out.println("ID: " + doctor.getId() + ", Имя: " + doctor.getName() + ", Специализация: " + doctor.getSpecialization()));
                    break;

                case 3:
                    List<Appointment> appointments = controller.getAllAppointments();
                    appointments.forEach(appointment -> System.out.println("ID: " + appointment.getId() + ", Врач: " + appointment.getDoctor().getName() + ", Пациент: " + appointment.getPatient().getName() + ", Дата: " + dateFormat.format(appointment.getAppointmentDate())));
                    break;

                case 4:
                    System.out.println("Введите ID пациента:");
                    Long patientId = scanner.nextLong();
                    List<Appointment> patientAppointments = controller.getAppointmentsByPatient(patientId);
                    if (patientAppointments == null || patientAppointments.isEmpty()) {
                        System.out.println("Назначения для пациента не найдены.");
                    } else {
                        patientAppointments.forEach(appointment -> System.out.println("ID назначения: " + appointment.getId() + ", Врач: " + appointment.getDoctor().getName() + ", Дата: " + dateFormat.format(appointment.getAppointmentDate())));
                    }
                    break;

                case 5:
                    System.out.println("Введите ID врача:");
                    Long doctorId = scanner.nextLong();
                    System.out.println("Введите ID пациента:");
                    patientId = scanner.nextLong();
                    scanner.nextLine(); // Очистка буфера
                    System.out.println("Введите описание лечения:");
                    String treatment = scanner.nextLine();
                    System.out.println("Введите дату назначения (yyyy-MM-dd):");
                    String dateInput = scanner.nextLine();
                    try {
                        Date appointmentDate = dateFormat.parse(dateInput);
                        if (controller.createAppointment(doctorId, patientId, treatment, appointmentDate)) {
                            System.out.println("Назначение успешно создано.");
                        } else {
                            System.out.println("Не удалось создать назначение. Проверьте данные.");
                        }
                    } catch (ParseException e) {
                        System.out.println("Неверный формат даты.");
                    }
                    break;

                case 6:
                    System.out.println("Введите ID назначения:");
                    Long appointmentId = scanner.nextLong();
                    scanner.nextLine(); // Очистка буфера
                    System.out.println("Введите новое описание лечения:");
                    treatment = scanner.nextLine();
                    System.out.println("Введите новую дату назначения (yyyy-MM-dd):");
                    dateInput = scanner.nextLine();
                    try {
                        Date appointmentDate = dateFormat.parse(dateInput);
                        if (controller.updateAppointment(appointmentId, treatment, appointmentDate)) {
                            System.out.println("Назначение успешно обновлено.");
                        } else {
                            System.out.println("Не удалось обновить назначение. Проверьте данные.");
                        }
                    } catch (ParseException e) {
                        System.out.println("Неверный формат даты.");
                    }
                    break;

                case 7:
                    System.out.println("Введите ID назначения для удаления:");
                    appointmentId = scanner.nextLong();
                    if (controller.deleteAppointment(appointmentId)) {
                        System.out.println("Назначение успешно удалено.");
                    } else {
                        System.out.println("Не удалось удалить назначение. Проверьте данные.");
                    }
                    break;

                case 8:
                    running = false;
                    break;

                default:
                    System.out.println("Некорректный выбор, попробуйте снова.");
            }
        }

        // Закрытие ресурсов
        controller.close();
        scanner.close();
    }
}
