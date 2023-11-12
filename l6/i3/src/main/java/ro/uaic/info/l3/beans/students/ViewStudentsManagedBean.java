package ro.uaic.info.l3.beans.students;

import ro.uaic.info.l3.entities.Student;
import ro.uaic.info.l3.services.students.StudentService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class ViewStudentsManagedBean implements Serializable {
    private List<Student> students = new ArrayList<>();
    @EJB
    private StudentService studentService;

    @PostConstruct
    public void init() {
        loadStudents();
    }
    private void loadStudents() {
        students = studentService.findAll();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
