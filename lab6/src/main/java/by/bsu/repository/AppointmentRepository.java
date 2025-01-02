package by.bsu.repository;

import by.bsu.entities.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    List<Appointment> findAll();
}
