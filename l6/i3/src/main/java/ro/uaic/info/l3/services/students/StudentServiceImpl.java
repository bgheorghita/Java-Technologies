package ro.uaic.info.l3.services.students;

import ro.uaic.info.l3.entities.Student;
import ro.uaic.info.l3.repositories.students.StudentRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class StudentServiceImpl implements StudentService {
    @EJB
    private StudentRepository studentRepository;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(int id) {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found."));
    }
}
