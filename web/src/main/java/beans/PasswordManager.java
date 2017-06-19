package beans;

import persistence.service.interfaces.UserService;
import utils.WrongPasswordException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@SessionScoped
@ManagedBean(name = "password")
public class PasswordManager {

    @Inject
    UserService userService;

    private String oldPassword;
    private String repeatedPassword;
    private String newPassword;
    private String output;

    public void changePassword(){
        String userLogin = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();

        String locale = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();

        if(!newPassword.equals(repeatedPassword) || newPassword.isEmpty()) {
            output = locale.equals("pl") || locale.equals("PL") ? "Błąd, hasła się nie zgadzają" : "Error, passwords don't match";
            return;
        }
        try {
            userService.changePassword(userLogin,oldPassword,newPassword);
            output = locale.equals("pl") || locale.equals("PL") ? "Hasło zmienione" : "Password changed";

        } catch (WrongPasswordException e) {
            output = locale.equals("pl") || locale.equals("PL") ? "Błędne hasło" : "Wrong password";
        }

    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
