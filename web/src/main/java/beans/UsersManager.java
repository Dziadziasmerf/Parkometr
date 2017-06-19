package beans;

import org.jboss.security.auth.spi.Util;
import persistence.model.User;
import persistence.model.UserRole;
import persistence.service.interfaces.UserService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SessionScoped
@ManagedBean(name = "users")
public class UsersManager {

    @Inject
    UserService userService;

    private User editedUser;

    private String userLogin;
    private String userPassword;
    private String repeatedUserPassword;
    private String[] roles;
    private String output;
    private List<SelectItem> availableItems;

    @PostConstruct
    public void init() {
        availableItems = new ArrayList<>();
        availableItems.add(new SelectItem("Admin", "Admin"));
        availableItems.add(new SelectItem("Employee", "Employee"));
    }

    public void updateUser() {
        String locale = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();

        editedUser.setLogin(userLogin);
        if (!userPassword.equals(repeatedUserPassword)) {
            output = locale.equals("pl") || locale.equals("PL") ? "Błąd, hasła się nie zgadzają" : "Error, passwords don't match";
            return;
        }
        if(!userPassword.isEmpty())
            editedUser.setPassword(Util.createPasswordHash("MD5", Util.BASE64_ENCODING, null, null, userPassword));
        Set<UserRole> userRoles = new HashSet<>(editedUser.getUserRoles());
        for (String role : roles) {
            boolean newRole = true;
            for(UserRole userRole : editedUser.getUserRoles()) {
                if(role.equals(userRole.getRoleName()))
                    newRole = false;
            }
            if(newRole) {
                UserRole userRole = new UserRole();
                userRole.setUser(editedUser);
                userRole.setRoleName(role);
                userRoles.add(userRole);
            }
        }
        editedUser.setUserRoles(userRoles);

        userService.updateUser(editedUser);
    }

    public void deleteUser(User user) {
        userService.deleteUser(user.getId());
    }

    public void saveUser() {
        String locale = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
        if (!userPassword.equals(repeatedUserPassword)) {
            output = locale.equals("pl") || locale.equals("PL") ? "Błąd, hasła się nie zgadzają" : "Error, passwords don't match";
            return;
        }
        userService.addUser(userLogin, userPassword, roles);
    }

    public void sendUser() {
        if(editedUser ==null ||editedUser.getId() == 0) {
            System.out.println("Save");
            editedUser = new User();
            saveUser();
        }
        else {
            System.out.println("Update");
            updateUser();
        }
        editedUser = null;
        userLogin = null;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("users.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getUsersList() {
        return userService.findAllUsers();
    }

    public String getUserRolesString(Set<UserRole> userRoles) {
        String roles = "";
        for (UserRole userRole : userRoles) {
            roles += "| " + userRole.getRoleName() + " ";
        }
        roles += roles.equals("") ? "" : "|";
        return roles;
    }

    public User getEditedUser() {
        return editedUser;
    }

    public void setEditedUser(User editedUser) {
        this.editedUser = editedUser;
        setUserLogin(editedUser.getLogin());
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getRepeatedUserPassword() {
        return repeatedUserPassword;
    }

    public void setRepeatedUserPassword(String repeatedUserPassword) {
        this.repeatedUserPassword = repeatedUserPassword;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public List<SelectItem> getAvailableItems() {
        return availableItems;
    }
}
