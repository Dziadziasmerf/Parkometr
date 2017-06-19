package persistence.service.interfaces;

import persistence.model.User;
import utils.Role;
import utils.WrongPasswordException;

import java.util.List;

public interface UserService {

    void addUser(String login, String password, String[] roles);
    void deleteUser(Integer id);
    void updateUser(User user);
    void changePassword(String login, String oldPassword, String newPassword) throws WrongPasswordException;
    User findUserById(Integer id);
    User findUserByLogin(String login);
    List<User> findAllUsers();
    List<User> findUsersWithRole(Role role);
}
