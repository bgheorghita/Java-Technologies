--- Created a RESTful Web services using JAX-RS that allows the interaction with the Preference entity, and implemented CRUD operations. Furthermore, I have documented the endpoints using an API documentation tool
--- Created a filter that acts as a cache for the service that returns the preferences. If the preferences are modified by the application, the cache is updated accordingly 
--- Modified the model from last lab such that there is a predefined list of events that must be scheduled in the timetable, and each submission specifies a list of preferences of the form event_1 < event_2, which states that event_1 must be scheduled before event_2
----- There is a service (PreferenceTimetableEventService) that verifies if there are circular preferences: e_1 < e2 < ... < e_k < e_1
----- There is a service (TimetableEventService) that establishes a possible chronological order of all the events, based on their preferences
----- I have added the possibility to test the TimetableEventService on large data models generated at runtime through a JAX-RS web service (endpoint: /timetable-event) where two attributes are neccessary: number of events to generate, number of preferences to generate for each event
----- Also, the JAX-RS web service found at `/timetable-event` is documented


The documentation can be reached by accessing `http://localhost:8080/l7-1.0-SNAPSHOT/resources/api-docs/`
