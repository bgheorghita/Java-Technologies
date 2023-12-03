# Project Title

## Overview

This project includes RESTful Web services and features aimed at managing preferences and scheduling events. The following functionalities have been implemented:

1. **Preference Management:**
   - Created RESTful Web services using JAX-RS for interacting with the Preference entity.
   - Implemented CRUD operations.
   - Documented the endpoints using an API documentation tool.
   
2. **Caching Mechanism:**
   - Implemented a filter acting as a cache for the service returning preferences.
   - The cache is dynamically updated when preferences are modified.

3. **Model Modification:**
   - Modified the model from last lab to include a predefined list of events for scheduling.
   - Introduced preferences of the form `event_1 < event_2`, indicating that `event_1` must be scheduled before `event_2`. This was accomplished by giving to each of the event a priority.

4. **Circular Preferences:**
   - Implemented a `PreferenceTimetableEventService` to verify circular preferences (`e_1 < e_2 < ... < e_k < e_1`).

5. **Chronological Order:**
   - Created a `TimetableEventService` that establishes a possible chronological order for all events based on their preferences.

6. **Testing with Large Data Models:**
   - Added the capability to test the `TimetableEventService` on large data models generated at runtime through a JAX-RS web service.
   - The endpoint for testing is `/timetable-event`.
   - Required attributes: number of events to generate, number of preferences to generate for each event.
   - This JAX-RS web service is also documented.

7. **API Documentation:**
   - Access the API documentation at [http://localhost:8080/l7-1.0-SNAPSHOT/resources/api-docs/](http://localhost:8080/l7-1.0-SNAPSHOT/resources/api-docs/).

## License

This project is open-source and free to use by anyone. There is no specific license attached, and you are welcome to use, modify, and distribute the code as needed.

