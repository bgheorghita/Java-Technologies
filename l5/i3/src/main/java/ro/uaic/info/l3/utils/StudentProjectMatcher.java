package ro.uaic.info.l3.utils;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.MatchingAlgorithm;
import org.jgrapht.alg.matching.HopcroftKarpMaximumCardinalityBipartiteMatching;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import ro.uaic.info.l3.entities.AssignedProject;
import ro.uaic.info.l3.entities.Project;
import ro.uaic.info.l3.entities.Student;
import ro.uaic.info.l3.services.projects.ProjectService;
import ro.uaic.info.l3.services.students.StudentService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentProjectMatcher {

    private final ProjectService projectService;
    private final StudentService studentService;

    public StudentProjectMatcher(ProjectService projectService, StudentService studentService) {
        this.projectService = projectService;
        this.studentService = studentService;
    }
    public List<AssignedProject> getAssignedProjects(List<Student> students) {
        Graph<String, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);

        Set<String> partition1 = new HashSet<>();
        Set<String> partition2 = new HashSet<>();

        for (Student student : students) {
            int studentId = student.getId();
            String studentName = student.getName();
            String studentVertex = studentId + "_" + studentName;
            partition1.add(studentVertex);
            graph.addVertex(studentVertex);

            for (Project project : student.getPreferredProjects()) {
                int projectId = project.getId();
                String projectName = project.getName();
                String projectVertex = projectId + "_" + projectName;
                partition2.add(projectVertex);
                graph.addVertex(projectVertex);
                graph.addEdge(studentVertex, projectVertex);
            }
        }

        HopcroftKarpMaximumCardinalityBipartiteMatching<String, DefaultEdge> matchingAlgorithm =
                new HopcroftKarpMaximumCardinalityBipartiteMatching<>(graph, partition1, partition2);

        MatchingAlgorithm.Matching<String, DefaultEdge> matching = matchingAlgorithm.getMatching();

        List<AssignedProject> assignedProjects = new ArrayList<>();
        matching.getEdges().forEach(edge -> {
            String student = graph.getEdgeSource(edge);
            String project = graph.getEdgeTarget(edge);

            Student retrievedStudent = studentService.findById(Integer.parseInt(student.split("_")[0]));
            Project retrievedProject = projectService.findById(Integer.parseInt(project.split("_")[0]));
            assignedProjects.add(new AssignedProject(retrievedProject, retrievedStudent));
        });

        return assignedProjects;
    }
}
