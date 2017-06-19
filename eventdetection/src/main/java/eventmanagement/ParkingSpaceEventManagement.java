package eventmanagement;

import jms.QueueService;
import persistence.model.Ticket;
import persistence.service.interfaces.SpaceService;
import persistence.service.interfaces.TicketService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@ApplicationScoped
public class ParkingSpaceEventManagement {

    private static final long NOTIFICATION_DELAY_MIN = 2;

    @Inject
    QueueService topicService;

    @Inject
    TicketService ticketService;

    @Inject
    SpaceService spaceService;

    public void spaceTakenEvent(Integer spaceId) {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                List<Ticket> tickets = ticketService.findValidTicketsBySpaceId(spaceId);
                if(tickets.isEmpty()) {
                    topicService.sendMessage("Warning! Parking space with id: " + spaceId + " is taken but owner didn't buy ticket!");
                }
            }
        }, NOTIFICATION_DELAY_MIN*60*1000);
    }

    public void spaceFreedEvent(Integer spaceId) {
        List<Ticket> tickets = ticketService.findValidTicketsBySpaceId(spaceId);
        for (Ticket ticket : tickets) {
            ticket.setValid(false);
            ticketService.updateTicket(ticket);
        }
    }
}
