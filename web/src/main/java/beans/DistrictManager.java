package beans;

import persistence.model.District;
import persistence.model.User;
import persistence.service.interfaces.DistrictService;
import utils.Role;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ManagedBean
@SessionScoped
public class DistrictManager {

    private District district;
    private String districtName;
    private String userLogin;

    @Inject
    HttpServletRequest httpServletRequest;

    @Inject
    private DistrictService districtService;

    public void addUserToDistrict() {
        districtService.addUserToDistrict(district.getId(), userLogin);
        getDistrict();
        userLogin = null;
        updateDistrict();
    }


    public void addDistrict() {
        System.out.println(districtName);
        District district = new District();
        district.setName(districtName);
        districtService.addDistrict(district);
    }

    public void deleteDistrict(District district) {
        districtService.deleteDistrict(district.getId());
    }

    public void deleteUserFromDistrict(User user) {
        district.getUsers().remove(user);
        districtService.updateDistrict(district);
        updateDistrict();
    }

    public void updateDistrict() {
        district = districtService.findDistrictById(district.getId());
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        System.out.println(districtName);
        this.districtName = districtName;
    }

    public List<District> getDistricts() {
        return httpServletRequest.isUserInRole(Role.ADMIN.getRoleName()) ? districtService.findAll() : districtService.findDistrictsByUserLogin(httpServletRequest.getUserPrincipal().getName());
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}
