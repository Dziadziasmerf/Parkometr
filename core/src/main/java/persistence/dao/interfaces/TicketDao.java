package persistence.dao.interfaces;

import persistence.model.Ticket;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface TicketDao {

    Integer addTicket(Ticket ticket);
    void updateTicket(Ticket ticket);
    void deleteTicket(Integer id);
    Ticket findTicketById(Integer id);
    List<Ticket> findTicketsBySpaceId(Integer spaceId);
    List<Ticket> findValidTicketsBySpaceId(Integer spaceId);
    List<Ticket> findValidTicketsByStreetId(Integer streetId);
    List<Ticket> findAll();
}
