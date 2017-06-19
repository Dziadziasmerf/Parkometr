package persistence.service;

import persistence.dao.interfaces.StreetDao;
import persistence.model.Street;
import persistence.service.interfaces.StreetService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class StreetServiceImpl implements StreetService {

    @Inject
    StreetDao streetDao;

    public void addStreet(Street street) {
        streetDao.addStreet(street);
    }

    public void updateStreet(Street street) {
        streetDao.updateStreet(street);
    }

    public void deleteStreet(Integer id) {
        streetDao.deleteStreet(id);
    }

    public Street findStreetById(Integer id) {
        return streetDao.findStreetById(id);
    }

    public List<Street> findAll() {
        return streetDao.findAll();
    }
}
