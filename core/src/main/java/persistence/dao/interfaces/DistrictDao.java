package persistence.dao.interfaces;

import persistence.model.District;

import java.util.List;


public interface DistrictDao {
    void addDistrict(District district);
    void updateDistrict(District district);
    void deleteDistrict(Integer id);
    District findDistrictById(Integer id);
    List<District> findAll();
    void addUserToDistrict(Integer districtId, String userLogin);
    List<District> findDistrictsByUserLogin(String userLogin);

}
