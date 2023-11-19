### A JSF application for collecting timetable preferences. Each preference will belong to a user (teacher) and will have a content (text). A techer can have multiple submissions. 
### Application functionalities:
  ##### An authentication mechanism based on username and password
  ##### A Register mechanism - at registration, users do not have an associated role and they are redirected to the welcome.xhtml page
  ##### A time frame is specified in the PreferenceBean, in which teachers can submit preferences
  ##### A teacher user can submit preferences and view them, and an admin user can view all the submited preferences
  ##### Each submission has a uniquely generated registration number (UUID)


### Contexts and Dependency Injection (CDI)
  #### @Inject, @Transactional
  #### Decoupled components with @Produces - for preference registration number
  #### decoupled orthogonal concerns - logging: @Interceptor - submitPreference method in PreferenceBean is logged, and the logs are written to a file 
  #### decoupled bussines concerns: verified the time frame for the preferences submissions using @Decorator
  #### One event-based communication. The decorator emmits a new event when a preferences is submitted
  #### One observator that observes the submitted preferences (@Observes) which prints the preference at the console
  #### Data validation (at the entity level) using Bean Validation annotations such as @NotNull, @Size. 
