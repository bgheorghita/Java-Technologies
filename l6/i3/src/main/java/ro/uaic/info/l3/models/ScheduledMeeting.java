package ro.uaic.info.l3.models;

import ro.uaic.info.l3.entities.AssignedProject;

import java.util.List;

public class ScheduledMeeting {
    private int id;
    private final List<Teacher> teachers;
    private final List<AssignedProject> assignedProjects;
    private Meeting meeting;

    public ScheduledMeeting(int id, List<Teacher> teachers, List<AssignedProject> assignedProjects, Meeting meeting) {
        this.id = id;
        this.teachers = teachers;
        this.assignedProjects = assignedProjects;
        this.meeting = meeting;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
    }

    public void addAssignedProject(AssignedProject assignedProject) {
        this.assignedProjects.add(assignedProject);
    }

    public void removeTeacher(Teacher teacher) {
        this.teachers.remove(teacher);
    }

    public void removeAssignedProject(AssignedProject assignedProject) {
        this.assignedProjects.remove(assignedProject);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduledMeeting that = (ScheduledMeeting) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public List<AssignedProject> getAssignedProjects() {
        return assignedProjects;
    }

    @Override
    public String toString() {
        return "ScheduledMeeting{" +
                "id=" + id +
                ", teachers=" + teachers +
                ", assignedProjects=" + assignedProjects +
                ", meeting=" + meeting +
                '}';
    }

    public String getFriendlyPresentation() {
        return teachers.get(0).getName() + " - " + assignedProjects.get(0).getStudent().getName() + " - " + assignedProjects.get(0).getProject().getName();
    }
}
