package ro.uaic.info.l3.services.students;

import ro.uaic.info.l3.entities.Student;
import ro.uaic.info.l3.repositories.students.StudentRepository;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(int id) {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found."));
    }
}
