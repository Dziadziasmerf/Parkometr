package persistence.service;

import persistence.dao.interfaces.TicketDao;
import persistence.model.Ticket;
import persistence.service.interfaces.TicketService;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class TicketServiceImpl implements TicketService {

    @Inject
    TicketDao ticketDao;

    public Integer addTicket(Ticket ticket) {
        return ticketDao.addTicket(ticket);
    }

    public void updateTicket(Ticket ticket) {
        ticketDao.updateTicket(ticket);
    }

    public void deleteTicket(Integer id) {
        ticketDao.deleteTicket(id);
    }

    public Ticket findTicketById(Integer id) {
        return ticketDao.findTicketById(id);
    }

    public List<Ticket> findTicketsBySpaceId(Integer spaceId) {
        return ticketDao.findTicketsBySpaceId(spaceId);
    }

    public List<Ticket> findValidTicketsBySpaceId(Integer spaceId) {
        return ticketDao.findValidTicketsBySpaceId(spaceId);
    }

    public List<Ticket> findValidTicketsByStreetId(Integer streetId) {
        return ticketDao.findValidTicketsByStreetId(streetId);
    }

    public List<Ticket> findAll() {
        return ticketDao.findAll();
    }
}
