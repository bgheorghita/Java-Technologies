### Rewrited the data access layer of the application created in the previous laboratories, implementing the repository classes as Enterprise Java Beans
### Used the support offered by the EJB technology for implementing transactions
### Created an "assign" page, allowing manual assigning of projects to student
### Updated model: a student can receive multiple projects - a project must be assigned to at most one student
### Implemented a stateless session bean that offers methods for checking the availability of a project
### Implmeneted a stateful session bean responsible with the assignment of one or more projects (ATOMIC)
### Implemented a singleton session bean that keeps an in-memory map of the current assignments. The map will be instantiated at application startup and updated whenever the assignments change. 
### Created a bussines method that will return a maximal group of students, all having projects from the same categories (the view is: maximal-group.xhtml) -> at invocation, this method shufles the group for didactic reasons, also new assignments are taken into consideration
### Used an EJB interceptor in order to monitor how many times the method above was called
### Created a timer that will trigger the invocation of the method, using a specified schedule (5 seconds interval)
