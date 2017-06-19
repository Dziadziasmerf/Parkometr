package persistence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dao.interfaces.DistrictDao;
import persistence.dao.interfaces.UserDao;
import persistence.model.District;
import persistence.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class DistrictDaoImpl implements DistrictDao {

    @Inject
    UserDao userDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(DistrictDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void addDistrict(District district) {
        LOGGER.debug("Adding district:  " + district.getName());
        entityManager.persist(district);
    }

    @Transactional
    public void updateDistrict(District district) {
        LOGGER.debug("Adding district:  " + district.getName());
        entityManager.merge(district);
    }

    @Transactional
    public void deleteDistrict(Integer id) {
        LOGGER.debug("Deleting district with id:  " + id);
        District district = findDistrictById(id);
        entityManager.remove(district);
    }

    @Transactional
    public District findDistrictById(Integer id) {
        LOGGER.debug("Looking for district with id: " + id);
        return entityManager.find(District.class,id);
    }

    @Transactional
    public List<District> findAll() {
        LOGGER.debug("Looking for all districts");
        return entityManager.createQuery("SELECT d FROM District d",District.class).getResultList();
    }

    @Transactional
    public void addUserToDistrict(Integer districtId, String userLogin) {
        User user = userDao.findUserByLogin(userLogin);
        District district = findDistrictById(districtId);
        district.getUsers().add(user);
        entityManager.merge(district);
    }

    @Transactional
    public List<District> findDistrictsByUserLogin(String userLogin) {
        Query query = entityManager.createQuery("SELECT d FROM District d JOIN d.users u WHERE u.login=:login",District.class);
        query.setParameter("login",userLogin);
        return query.getResultList();
    }

}
