package by.bsu.repository;

import by.bsu.entities.Doctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {
    List<Doctor> findAll();
}
