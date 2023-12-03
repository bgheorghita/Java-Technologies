package ro.uaic.info.l7.controllers;

import com.wordnik.swagger.annotations.*;
import ro.uaic.info.l7.entities.PreferenceTimetableEvent;
import ro.uaic.info.l7.entities.TimetableEvent;
import ro.uaic.info.l7.services.TimetableEventService;
import ro.uaic.info.l7.utils.generators.PreferenceTimetableEventGenerator;
import ro.uaic.info.l7.utils.generators.TimetableEventGenerator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.Map;

@Path(TimetableEventController.TIMETABLE_EVENT_PATH)
@ApplicationScoped
@Api(value = TimetableEventController.TIMETABLE_EVENT_PATH, description = "Operations related to timetable events")
public class TimetableEventController {
    public final static String TIMETABLE_EVENT_PATH = "/timetable-event";
    @Inject
    private TimetableEventService timetableEventService;
    @Inject
    private TimetableEventGenerator timetableEventGenerator;
    @Inject
    private PreferenceTimetableEventGenerator preferenceTimetableEventGenerator;

    @GET
    @ApiOperation(value = "Generate and compute chronological order of the events based on input parameters.",
            notes = "Generates timetable events and computes their chronological order based on preferences. It returns a map having a priority associated to each event.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Map<TimetableEvent, Integer> generateAndComputeChronologicalOrder(
            @ApiParam(value = "Number of timetable events to generate", required = true)
            @QueryParam("noOfTimetableEvents") Integer noOfTimetableEvents,

            @ApiParam(value = "Number of preferences for each timetable event to generate", required = true)
            @QueryParam("noOfPreferencesForEachTimetableEvent") Integer noOfPreferencesForEachTimetableEvent
    ) {
        List<TimetableEvent> events = timetableEventGenerator.generateTimetableEvents(noOfTimetableEvents);
        List<PreferenceTimetableEvent> preferenceTimetableEvents = preferenceTimetableEventGenerator.generatePreferenceTimetableEvents(events, noOfPreferencesForEachTimetableEvent);
        return timetableEventService.computeChronologicalOrderOfTimetableEvents(preferenceTimetableEvents);
    }
}
