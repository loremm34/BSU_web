package by.bsu.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "patients")
@NamedQueries({
        @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p"),
        @NamedQuery(name = "Patient.findByDiagnosis", query = "SELECT p FROM Patient p WHERE p.diagnosis = :diagnosis")
})
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String diagnosis;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
