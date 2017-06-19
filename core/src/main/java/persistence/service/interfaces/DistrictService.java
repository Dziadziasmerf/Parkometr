package persistence.service.interfaces;

import persistence.model.District;
import persistence.model.User;

import java.util.List;


public interface DistrictService {
    void addDistrict(District district);
    void updateDistrict(District district);
    void deleteDistrict(Integer id);
    District findDistrictById(Integer id);
    List<District> findAll();
    void addUserToDistrict(Integer districtId, String login);
    List<District> findDistrictsByUserLogin(String userLogin);
}


