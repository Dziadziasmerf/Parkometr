package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Map;

@SessionScoped
@ManagedBean(name = "logout")
public class LogoutManager implements Serializable {

    public String logout() {
        System.out.println("Logout");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        Map m = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        for (Object o : m.keySet()) {
            m.put(o, null);
        }

        return "/districts.xhtml?faces-redirect=true";
    }

    public String getUserCredential() {

        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
    }
}
