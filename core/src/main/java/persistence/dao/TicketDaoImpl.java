package persistence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dao.interfaces.TicketDao;
import persistence.model.Ticket;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class TicketDaoImpl implements TicketDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Integer addTicket(Ticket ticket) {
        entityManager.persist(ticket);
        entityManager.flush();
        return ticket.getId();
    }

    @Transactional
    public void updateTicket(Ticket ticket) {
        entityManager.merge(ticket);
    }

    @Transactional
    public void deleteTicket(Integer id) {
        Ticket ticket = findTicketById(id);
        entityManager.remove(ticket);
    }

    @Transactional
    public Ticket findTicketById(Integer id) {
        return entityManager.find(Ticket.class,id);
    }

    @Transactional
    public List<Ticket> findTicketsBySpaceId(Integer spaceId) {
        Query query = entityManager.createQuery("SELECT t FROM Ticket t WHERE t.space.id = :id",Ticket.class);
        query.setParameter("id",spaceId);
        return query.getResultList();
    }

    @Transactional
    public List<Ticket> findValidTicketsBySpaceId(Integer spaceId) {
        Query query = entityManager.createQuery("SELECT t FROM Ticket t WHERE t.space.id = :id AND t.valid = true",Ticket.class);
        query.setParameter("id",spaceId);
        return query.getResultList();
    }

    @Transactional
    public List<Ticket> findValidTicketsByStreetId(Integer streetId) {
        Query query = entityManager.createQuery("SELECT t FROM Ticket t WHERE t.space.street.id = :id AND t.valid = true",Ticket.class);
        query.setParameter("id",streetId);
        return query.getResultList();
    }

    @Transactional
    public List<Ticket> findAll() {
        return entityManager.createQuery("SELECT t FROM Ticket t",Ticket.class).getResultList();
    }
}
