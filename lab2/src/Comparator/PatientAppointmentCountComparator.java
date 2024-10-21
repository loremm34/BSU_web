package Comparator;

import Entities.Patient;
import java.util.Comparator;

public class PatientAppointmentCountComparator implements Comparator<Patient> {
    @Override
    public int compare(Patient p1, Patient p2) {
        return Integer.compare(p1.getAppointments().size(), p2.getAppointments().size());
    }
}
