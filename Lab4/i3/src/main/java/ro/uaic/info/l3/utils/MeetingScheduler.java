package ro.uaic.info.l3.utils;

import ro.uaic.info.l3.models.*;

import java.util.*;

public class MeetingScheduler {
    public List<ScheduledMeeting> scheduleMeetings(List<AssignedProject> assignedProjects, List<Meeting> availableSlots) {
        Map<Teacher, List<Meeting>> teacherMeetings = new HashMap<>();

        List<ScheduledMeeting> scheduledMeetings = new ArrayList<>();

        availableSlots.sort(Comparator.comparing(Meeting::getMeetingDate));

        for (AssignedProject assignedProject : assignedProjects) {
            Teacher teacher = assignedProject.getProject().getSupervisedBy();
            Student student = assignedProject.getStudent();

            List<Meeting> meetings = teacherMeetings.get(teacher);

            if (meetings == null) {
                meetings = new ArrayList<>();
                teacherMeetings.put(teacher, meetings);
            }

            Meeting scheduledMeeting = null;
            for (Meeting availableSlot : availableSlots) {
                // Check if this slot is not used by the teacher on the same day
                boolean slotAvailable = true;
                for (Meeting existingMeeting : meetings) {
                    if (existingMeeting.getMeetingDate().equals(availableSlot.getMeetingDate())) {
                        slotAvailable = false;
                        break;
                    }
                }

                if (slotAvailable) {
                    scheduledMeeting = availableSlot;
                    meetings.add(scheduledMeeting);
                    break;
                }
            }

            if (scheduledMeeting != null) {
                ScheduledMeeting scheduled = new ScheduledMeeting(
                        scheduledMeetings.size() + 1,
                        Collections.singletonList(teacher),
                        Collections.singletonList(assignedProject),
                        scheduledMeeting
                );
                scheduledMeetings.add(scheduled);
            } else {
                System.out.println("No available slots for teacher: " + teacher.getName() + " and student: " + student.getName());
            }
        }

        return scheduledMeetings;
    }


    public  List<ScheduledMeeting> getDemoData() {
        Category category1 = new Category();
        category1.setId(1);
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setId(2);
        category2.setName("Category 2");

        Teacher teacher1 = new Teacher();
        teacher1.setId(1);
        teacher1.setName("Teacher 1");

        Teacher teacher2 = new Teacher();
        teacher2.setId(2);
        teacher2.setName("Teacher 2");

        Student student1 = new Student();
        student1.setId(1);
        student1.setName("Student 1");

        Student student2 = new Student();
        student2.setId(2);
        student2.setName("Student 2");

        Student student3 = new Student();
        student3.setId(3);
        student3.setName("Student 3");

        AssignedProject assignedProject1 = new AssignedProject(createProject(teacher1, "Project 1", category1, "Description for Project 1", createCalendar(2023, Calendar.OCTOBER, 30)), student1);
        AssignedProject assignedProject2 = new AssignedProject(createProject(teacher2, "Project 2", category2, "Description for Project 2", createCalendar(2023, Calendar.NOVEMBER, 15)), student2);
        AssignedProject assignedProject3 = new AssignedProject(createProject(teacher2, "Project 3", category1, "Description for Project 3", createCalendar(2023, Calendar.DECEMBER, 20)), student3);

        List<AssignedProject> assignedProjects = new ArrayList<>();
        assignedProjects.add(assignedProject1);
        assignedProjects.add(assignedProject2);
        assignedProjects.add(assignedProject3);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2023, Calendar.OCTOBER, 31);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2023, Calendar.OCTOBER, 30);

        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(2023, Calendar.OCTOBER, 29);

        Calendar calendar4 = Calendar.getInstance();
        calendar4.set(2023, Calendar.OCTOBER, 28);

        List<Meeting> meetings = new ArrayList<>();
        Meeting meeting1 = new Meeting();
        meeting1.setId(1);
        meeting1.setMeetingDate(calendar1.getTime());
        meetings.add(meeting1);

        Meeting meeting2 = new Meeting();
        meeting2.setId(2);
        meeting2.setMeetingDate(calendar2.getTime());
        meetings.add(meeting2);

        Meeting meeting3 = new Meeting();
        meeting3.setId(2);
        meeting3.setMeetingDate(calendar3.getTime());
        meetings.add(meeting3);

        Meeting meeting4 = new Meeting();
        meeting4.setId(2);
        meeting4.setMeetingDate(calendar4.getTime());
        meetings.add(meeting4);

        return scheduleMeetings(assignedProjects, meetings);
    }

    private static Project createProject(Teacher teacher, String name, Category category, String description, Calendar deadline) {
        Project project = new Project(name, category, description, deadline);
        project.setSupervisedBy(teacher);
        return project;
    }

    private static Calendar createCalendar(int year, int month, int day) {
        return new GregorianCalendar(year, month, day);
    }
}
