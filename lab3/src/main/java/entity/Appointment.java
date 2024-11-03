package entity;


import jakarta.persistence.*;
import java.util.Date;



@Entity
@Table(name = "appointments")
@NamedQueries({
        @NamedQuery(name = "Appointment.findAll", query = "SELECT a FROM Appointment a"),
        @NamedQuery(name = "Appointment.findByPatient", query = "SELECT a FROM Appointment a WHERE a.patient = :patient"),
        @NamedQuery(name = "Appointment.findByDoctor", query = "SELECT a FROM Appointment a WHERE a.doctor = :doctor"),
        @NamedQuery(name = "Appointment.findByDate", query = "SELECT a FROM Appointment a WHERE a.appointmentDate = :appointmentDate")
})
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(nullable = false)
    private String treatment;

    @Temporal(TemporalType.DATE)
    @Column(name = "appointment_date", nullable = false)
    private Date appointmentDate;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}

