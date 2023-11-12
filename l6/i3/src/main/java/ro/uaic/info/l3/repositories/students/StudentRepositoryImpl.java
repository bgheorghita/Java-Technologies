package ro.uaic.info.l3.repositories.students;

import ro.uaic.info.l3.entities.Student;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@Stateless
public class StudentRepositoryImpl implements StudentRepository {

    @PersistenceContext(unitName = "MyWebApplicationPU")
    private EntityManager entityManager;

    @Override
    @Transactional
    public Student save(Student student) {
        entityManager.persist(student);
        return student;
    }

    @Override
    @Transactional
    public Optional<Student> findById(int id) {
        return Optional.ofNullable(entityManager.find(Student.class, id));
    }

    @Override
    @Transactional
    public List<Student> findAll() {
        return entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }

//private final AbstractDatabase abstractDatabase;
//    @Override
//    public Student save(Student student) {
//        int affectedRows = 0;
//
//        try (Connection connection = abstractDatabase.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO students (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
//
//            preparedStatement.setString(1, student.getName());
//
//            affectedRows = preparedStatement.executeUpdate();
//
//            if (affectedRows > 0) {
//                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        student.setId(generatedKeys.getInt(1));
//                    }
//                }
//                saveStudentPreferredProjects(connection, student);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        if (affectedRows > 0) {
//            return student;
//        }
//
//        throw new RuntimeException("Student could not be saved");
//    }
//
//    private void saveStudentPreferredProjects(Connection connection, Student student) {
//        List<Project> preferredProjects = student.getPreferredProjects();
//
//        if (preferredProjects != null && !preferredProjects.isEmpty()) {
//            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO students_projects (student_id, project_id) VALUES (?, ?)")) {
//                for (Project project : preferredProjects) {
//                    preparedStatement.setInt(1, student.getId());
//                    preparedStatement.setInt(2, project.getId());
//                    preparedStatement.addBatch();
//                }
//                preparedStatement.executeBatch();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public Optional<Student> findById(int id) {
//        try (Connection connection = abstractDatabase.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(
//                        "select s.id, s.name, c.id, c.name, p.id, p.name, p.description, p.deadline from students_projects sp " +
//                             "join projects p on p.id = sp.project_id " +
//                             "join students s on s.id = sp.student_id " +
//                             "join categories c on c.id = p.category_id " +
//                             "where s.id = ?;")) {
//
//            preparedStatement.setInt(1, id);
//
//            Student student = null;
//            List<Project> preferredProjects = new ArrayList<>();
//
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                while (resultSet.next()) {
//                    if(student == null) {
//                        int studentId = resultSet.getInt(1);
//                        String studentName = resultSet.getString(2);
//                        student = new Student(studentId, studentName, preferredProjects);
//                    }
//
//                    int categoryId = resultSet.getInt(3);
//                    String categoryName = resultSet.getString(4);
//
//                    int projectId = resultSet.getInt(5);
//                    String projectName = resultSet.getString(6);
//                    String projectDescription = resultSet.getString(7);
//                    Date projectDeadline = resultSet.getDate(8);
//                    Calendar projectDeadlineCalendar = Calendar.getInstance();
//                    projectDeadlineCalendar.setTime(projectDeadline);
//
//                    Category category = new Category(categoryId, categoryName);
//                    Project project = new Project(projectId, projectName, category, projectDescription, projectDeadlineCalendar);
//                    preferredProjects.add(project);
//                }
//                if(student != null){
//                    return Optional.of(student);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return Optional.empty();
//    }
//
//    @Override
//    public List<Student> findAll() {
//        List<Student> students = new ArrayList<>();
//
//        try (Connection connection = abstractDatabase.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM students");
//             ResultSet resultSet = preparedStatement.executeQuery()) {
//
//            while (resultSet.next()) {
//                int studentId = resultSet.getInt("id");
//                String studentName = resultSet.getString("name");
//                Student student = new Student(studentId, studentName, new ArrayList<>());
//
//                try(PreparedStatement stmt = connection.prepareStatement(
//                        "select c.id, c.name, p.id, p.name, p.description, p.deadline from projects p " +
//                        "join students_projects sp on p.id = sp.project_id " +
//                        "join categories c on c.id = p.category_id " +
//                        "where sp.student_id = ?;")) {
//
//                    stmt.setInt(1, studentId);
//
//                    try(ResultSet rs = stmt.executeQuery()) {
//                        while(rs.next()){
//                            int categoryId = rs.getInt(1);
//                            String categoryName = rs.getString(2);
//
//                            int projectId = rs.getInt(3);
//                            String projectName = rs.getString(4);
//                            String projectDescription = rs.getString(5);
//                            Date projectDeadline = rs.getDate(6);
//                            Calendar projectDeadlineCalendar = Calendar.getInstance();
//                            projectDeadlineCalendar.setTime(projectDeadline);
//
//                            Category category = new Category(categoryId, categoryName);
//                            Project project = new Project(projectId, projectName, category, projectDescription, projectDeadlineCalendar);
//
//                            student.getPreferredProjects().add(project);
//                        }
//                    }
//                }
//                students.add(student);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return students;
//    }
}
