package persistence.dao;

import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dao.interfaces.SpaceDao;
import persistence.model.Space;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class SpaceDaoImpl implements SpaceDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpaceDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void addSpace(Space space) {
        entityManager.persist(space);
    }

    @Transactional
    public void updateSpace(Space space) {
        entityManager.merge(space);
    }

    @Transactional
    public void deleteSpace(Integer id) {
        Space space = findSpaceById(id);
        entityManager.remove(space);
    }

    @Transactional
    public Space findSpaceById(Integer id) {
        return entityManager.find(Space.class, id);
    }

    @Transactional
    public List<Space> findAll() {
        return entityManager.createQuery("SELECT s FROM Space s", Space.class).getResultList();
    }

    @Override
    public List<Space> findMostFreq() {
        return null;
    }
}
