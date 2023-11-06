package ro.uaic.info.l3.repositories.students;

import ro.uaic.info.l3.databases.AbstractDatabase;
import ro.uaic.info.l3.entities.AssignedProject;
import ro.uaic.info.l3.entities.Project;
import ro.uaic.info.l3.entities.Student;
import ro.uaic.info.l3.repositories.projects.ProjectRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AssignedProjectRepositoryImpl implements AssignedProjectRepository {
    private final AbstractDatabase abstractDatabase;
    private final StudentRepository studentRepository;
    private final ProjectRepository projectRepository;

    public AssignedProjectRepositoryImpl(AbstractDatabase abstractDatabase, StudentRepository studentRepository, ProjectRepository projectRepository) {
        this.abstractDatabase = abstractDatabase;
        this.studentRepository = studentRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public AssignedProject save(AssignedProject assignedProject) {
        Connection connection = abstractDatabase.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String insertSQL = "INSERT INTO assigned_projects (student_id, project_id) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, assignedProject.getStudent().getId());
            preparedStatement.setInt(2, assignedProject.getProject().getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    assignedProject.setId(generatedKeys.getInt(1));
                    return assignedProject;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        throw new RuntimeException("AssignedProject could not be saved.");
    }


    @Override
    public Optional<AssignedProject> findById(int id) {
        String selectSQL = "SELECT student_id, project_id FROM assigned_projects WHERE id = ?";
        try (Connection connection = abstractDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                int projectId = resultSet.getInt("project_id");

                Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student could not be found"));
                Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project could not be found"));

                AssignedProject assignedProject = new AssignedProject(project, student);
                assignedProject.setId(id);

                return Optional.of(assignedProject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    @Override
    public Optional<AssignedProject> findByProjectId(int projectId) {
        String selectSQL = "SELECT id, student_id FROM assigned_projects WHERE project_id = ?";
        try (Connection connection = abstractDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int assignedProjectId = resultSet.getInt("id");
                int studentId = resultSet.getInt("student_id");

                Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student could not be found"));
                Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project could not be found"));

                AssignedProject assignedProject = new AssignedProject(project, student);
                assignedProject.setId(assignedProjectId);

                return Optional.of(assignedProject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    @Override
    public Optional<AssignedProject> findByStudentId(int studentId) {
        String selectSQL = "SELECT id, project_id FROM assigned_projects WHERE student_id = ?";
        try (Connection connection = abstractDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int assignedProjectId = resultSet.getInt("id");
                int projectId = resultSet.getInt("project_id");

                Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student could not be found"));
                Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project could not be found"));

                AssignedProject assignedProject = new AssignedProject(project, student);
                assignedProject.setId(assignedProjectId);

                return Optional.of(assignedProject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    @Override
    public List<AssignedProject> findAll() {
        List<AssignedProject> assignedProjects = new ArrayList<>();

        try (Connection connection = abstractDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM assigned_projects");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int assignedProjectId = resultSet.getInt("id");
                int studentId = resultSet.getInt("student_id");
                int projectId = resultSet.getInt("project_id");

                Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student could not be found"));
                Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project could not be found"));

                AssignedProject assignedProject = new AssignedProject(project, student);
                assignedProject.setId(assignedProjectId);
                assignedProjects.add(assignedProject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assignedProjects;
    }

    @Override
    public void deleteById(int id) {
        String deleteSQL = "DELETE FROM assigned_projects WHERE id = ?";
        try (Connection connection = abstractDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete AssignedProject with ID: " + id);
        }
    }
}
