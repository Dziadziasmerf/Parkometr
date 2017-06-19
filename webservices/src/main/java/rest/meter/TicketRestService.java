package rest.meter;

import eventmanagement.TicketsEventManagement;
import persistence.model.Space;
import persistence.model.Ticket;
import persistence.service.interfaces.SpaceService;
import persistence.service.interfaces.TicketService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/spaces")
public class TicketRestService {

    @Inject
    TicketService ticketService;

    @Inject
    SpaceService spaceService;

    @Inject
    TicketsEventManagement ticketsEventManagement;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{spaceId}/ticket")
    public Response createTicket(Ticket ticket, @PathParam("spaceId") Integer spaceId) {

        Space space = spaceService.findSpaceById(spaceId);
        ticket.setSpace(space);

        Integer ticketId = ticketService.addTicket(ticket);
        ticketsEventManagement.ticketBought(ticketId);
        return Response.status(Response.Status.CREATED).build();
    }

}
