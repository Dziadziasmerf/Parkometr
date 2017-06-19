package persistence.service;

import org.jboss.security.auth.spi.Util;
import persistence.dao.interfaces.UserDao;
import persistence.model.User;
import persistence.service.interfaces.UserService;
import utils.Role;
import utils.WrongPasswordException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserDao userDao;

    public void addUser(String login, String password, String[] roles) {
        userDao.addUser(login, password, roles);
    }

    public void deleteUser(Integer id) {
        userDao.deleteUser(id);
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    public void changePassword(String login, String oldPassword, String newPassword) throws WrongPasswordException {

        User user = findUserByLogin(login);
        if(!user.getPassword().equals(Util.createPasswordHash("MD5", Util.BASE64_ENCODING, null, null, oldPassword)))
            throw new WrongPasswordException("Given password doesn't match password in database");
        user.setPassword(Util.createPasswordHash("MD5",Util.BASE64_ENCODING,null,null,newPassword));
        updateUser(user);
    }

    public User findUserById(Integer id) {
        return userDao.findUserById(id);
    }

    public User findUserByLogin(String login) {
        return userDao.findUserByLogin(login);
    }

    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

    public List<User> findUsersWithRole(Role role) {
        return userDao.findUsersWithRole(role);
    }
}
