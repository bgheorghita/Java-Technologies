package ro.uaic.info.l3.repositories.students;

import ro.uaic.info.l3.databases.AbstractDatabase;
import ro.uaic.info.l3.models.Project;
import ro.uaic.info.l3.models.Student;
import ro.uaic.info.l3.models.StudentProject;
import ro.uaic.info.l3.repositories.projects.ProjectRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentProjectRepositoryImpl implements StudentProjectRepository {
    private final AbstractDatabase abstractDatabase;
    private final StudentRepository studentRepository;
    private final ProjectRepository projectRepository;

    public StudentProjectRepositoryImpl(AbstractDatabase abstractDatabase, StudentRepository studentRepository, ProjectRepository projectRepository) {
        this.abstractDatabase = abstractDatabase;
        this.studentRepository = studentRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public StudentProject save(StudentProject studentProject) {
        try (Connection connection = abstractDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO students_projects (student_id, project_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, studentProject.getStudent().getId());
            preparedStatement.setInt(2, studentProject.getProject().getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        studentProject.setId(generatedKeys.getInt(1));
                        return studentProject;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("StudentProject could not be saved");
    }

    @Override
    public Optional<StudentProject> findById(int id) {
        try (Connection connection = abstractDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM students_projects WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int studentId = resultSet.getInt("student_id");
                    int projectId = resultSet.getInt("project_id");

                    Student student = studentRepository.findById(studentId)
                            .orElseThrow(() -> new RuntimeException("Student could not be found."));
                    Project project = projectRepository.findById(projectId)
                            .orElseThrow(() -> new RuntimeException("Project could not be found"));

                    StudentProject studentProject = new StudentProject(student, project);
                    studentProject.setId(id);

                    return Optional.of(studentProject);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<StudentProject> findByStudentId(int studentId) {
        List<StudentProject> studentProjects = new ArrayList<>();

        try (Connection connection = abstractDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM students_projects WHERE student_id = ?")) {

            preparedStatement.setInt(1, studentId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int studentProjectId = resultSet.getInt("id");
                    int projectId = resultSet.getInt("project_id");

                    createStudentProject(projectId, studentProjects, studentProjectId, studentId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentProjects;
    }

    @Override
    public List<StudentProject> findByProjectId(int projectId) {
        List<StudentProject> studentProjects = new ArrayList<>();

        try (Connection connection = abstractDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM students_projects WHERE project_id = ?")) {

            preparedStatement.setInt(1, projectId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int studentProjectId = resultSet.getInt("id");
                    int studentId = resultSet.getInt("student_id");

                    createStudentProject(projectId, studentProjects, studentProjectId, studentId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentProjects;
    }

    private void createStudentProject(int projectId, List<StudentProject> studentProjects, int studentProjectId, int studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student could not be found."));
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project could not be found"));

        StudentProject studentProject = new StudentProject(student, project);
        studentProject.setId(studentProjectId);

        studentProjects.add(studentProject);
    }
}
