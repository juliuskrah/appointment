package org.appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;

import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

/**
 * @author Julius Krah
 */
@Path("/appointments")
public class GreetingResource {
    @Inject Application application;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Map<LocalDate, Set<LocalTime>> appointments(
        @QueryParam("date") @DefaultValue("2023-06-15") LocalDate date
    ) {
        System.out.println("Using date: " + date);
        return application.availableSlotsByDate(date);
    }
}
