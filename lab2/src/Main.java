import Controller.HospitalController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HospitalController controller = new HospitalController();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nМеню:");
            System.out.println("1. Показать всех врачей");
            System.out.println("2. Показать пациентов врача");
            System.out.println("3. Добавить пациента к врачу");
            System.out.println("4. Назначить диагноз пациенту");
            System.out.println("5. Показать диагноз пациента");
            System.out.println("6. Добавить назначение пациенту");
            System.out.println("7. Показать назначения пациента");
            System.out.println("8. Выписать пациента");
            System.out.println("0. Выйти");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    controller.showDoctors();
                    break;
                case 2:
                    controller.showPatientsOfDoctor();
                    break;
                case 3:
                    controller.addPatientToDoctor();
                    break;
                case 4:
                    controller.diagnosePatient();
                    break;
                case 5:
                    controller.showDiagnosisOfPatient();
                    break;
                case 6:
                    controller.addAppointmentToPatient();
                    break;
                case 7:
                    controller.showAppointmentsOfPatient();
                    break;
                case 8:
                    controller.dischargePatient();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }
}
