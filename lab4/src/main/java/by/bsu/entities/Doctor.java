package by.bsu.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "doctors")
@NamedQueries({
        @NamedQuery(name = "Doctor.findAll", query = "SELECT d FROM Doctor d"),
        @NamedQuery(name = "Doctor.findBySpecialization", query = "SELECT d FROM Doctor d WHERE d.specialization = :specialization")
})
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String specialization;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
