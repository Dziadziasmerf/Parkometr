package eventmanagement;

import jms.QueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.model.Space;
import persistence.model.Ticket;
import persistence.service.interfaces.SpaceService;
import persistence.service.interfaces.TicketService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Timer;
import java.util.TimerTask;

@ApplicationScoped
public class TicketsEventManagement {

    private static final long NOTIFICATION_DELAY_MIN = 2;
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketsEventManagement.class);

    @Inject
    TicketService ticketService;

    @Inject
    SpaceService spaceService;

    @Inject
    QueueService queueService;

    public void ticketBought(Integer ticketId) {

        Ticket ticket = ticketService.findTicketById(ticketId);

        Timer expirationTimer = new Timer();
        expirationTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Ticket ticket1 = ticketService.findTicketById(ticketId);
                ticket1.setValid(false);
                ticketService.updateTicket(ticket1);

            }
        }, ticket.getDurationMin() * 1000 * 60);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Space space = spaceService.findSpaceById(ticket.getSpace().getId());
                if(space.isTaken())
                queueService.sendMessage("Warning! Ticket for parking space with id: " + space.getId() + " has expired but parking space is still occupied!");
            }
        },(ticket.getDurationMin() + NOTIFICATION_DELAY_MIN) * 1000 * 60);
    }
}
