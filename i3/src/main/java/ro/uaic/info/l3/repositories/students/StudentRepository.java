package ro.uaic.info.l3.repositories.students;

import ro.uaic.info.l3.models.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    Student save(Student student);
    Optional<Student> findById(int id);
    List<Student> findAll();
}
