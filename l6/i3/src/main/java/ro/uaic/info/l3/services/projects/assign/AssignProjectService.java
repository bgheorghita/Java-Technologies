package ro.uaic.info.l3.services.projects.assign;

import ro.uaic.info.l3.entities.AssignedProject;
import ro.uaic.info.l3.entities.Category;
import ro.uaic.info.l3.interceptors.GroupSelectionSelectorInterceptor;
import ro.uaic.info.l3.services.projects.AssignedProjectService;

import javax.ejb.*;
import javax.interceptor.Interceptors;
import java.util.*;

@Stateless
@Interceptors(GroupSelectionSelectorInterceptor.class)
public class AssignProjectService {
    public static int methodInvocations = 0;
    @EJB
    private AssignedProjectService assignedProjectService;

    /*
        returns a maximal group of students, all having projects from the same categories
     */
    public List<AssignedProject> getMaximumGroupOfAssignedProjects() {
        methodInvocations++;
        List<AssignedProject> allAssignedProjects = assignedProjectService.findAll();

        Map<Category, List<AssignedProject>> assignedProjectsByCategory = new HashMap<>();
        for (AssignedProject assignedProject : allAssignedProjects) {
            Category category = assignedProject.getProject().getCategory();
            assignedProjectsByCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(assignedProject);
        }

        Category maxCategory = Collections.max(assignedProjectsByCategory.entrySet(),
                        Comparator.comparingInt(entry -> entry.getValue().size()))
                .getKey();

        List<AssignedProject> maximalGroup = assignedProjectsByCategory.get(maxCategory);
        Collections.shuffle(maximalGroup);
        return maximalGroup;
    }
}
