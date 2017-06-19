package persistence.dao.interfaces;

import persistence.model.User;
import utils.Role;

import java.util.List;

public interface UserDao {

    void addUser(String login, String password, String[] roles);
    void deleteUser(Integer id);
    void updateUser(User user);
    User findUserById(Integer id);
    User findUserByLogin(String login);
    List<User> findAllUsers();
    List<User> findUsersWithRole(Role role);
}
