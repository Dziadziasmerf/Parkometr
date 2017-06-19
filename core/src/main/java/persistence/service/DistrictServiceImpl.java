package persistence.service;

import persistence.dao.interfaces.DistrictDao;
import persistence.model.District;
import persistence.model.User;
import persistence.service.interfaces.DistrictService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class DistrictServiceImpl implements DistrictService {

    @Inject
    DistrictDao districtDao;

    public void addDistrict(District district) {
        districtDao.addDistrict(district);
    }

    public void updateDistrict(District district) {
        districtDao.updateDistrict(district);
    }

    public void deleteDistrict(Integer id) {
        districtDao.deleteDistrict(id);
    }

    public District findDistrictById(Integer id) {
        return districtDao.findDistrictById(id);
    }

    public List<District> findAll() {
        return districtDao.findAll();
    }

    public void addUserToDistrict(Integer districtId, String login) {
        districtDao.addUserToDistrict(districtId,login);
    }

    @Override
    public List<District> findDistrictsByUserLogin(String userLogin) {
        return districtDao.findDistrictsByUserLogin(userLogin);
    }

}
