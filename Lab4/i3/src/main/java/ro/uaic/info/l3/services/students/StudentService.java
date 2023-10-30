package ro.uaic.info.l3.services.students;

import ro.uaic.info.l3.models.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();
    Student findById(int id);
}
