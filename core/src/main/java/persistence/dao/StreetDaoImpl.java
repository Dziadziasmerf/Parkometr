package persistence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dao.interfaces.StreetDao;
import persistence.model.Street;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class StreetDaoImpl implements StreetDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreetDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void addStreet(Street street) {
        LOGGER.debug("Adding street:  " + street.getName());
        entityManager.persist(street);
    }

    @Transactional
    public void updateStreet(Street street) {
        entityManager.merge(street);
    }

    @Transactional
    public void deleteStreet(Integer id) {
        Street street= findStreetById(id);
        entityManager.remove(street);
    }

    @Transactional
    public Street findStreetById(Integer id) {
        return entityManager.find(Street.class,id);
    }

    @Transactional
    public List<Street> findAll() {
        return entityManager.createQuery("SELECT s FROM Street s",Street.class).getResultList();
    }
}
