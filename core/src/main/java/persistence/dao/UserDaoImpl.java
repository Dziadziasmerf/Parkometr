package persistence.dao;

import org.jboss.security.auth.spi.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dao.interfaces.UserDao;
import persistence.model.User;
import persistence.model.UserRole;
import utils.Role;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class UserDaoImpl implements UserDao {


    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void addUser(String login, String password, String[] roles) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(Util.createPasswordHash("MD5", Util.BASE64_ENCODING, null, null, password));
        Set<UserRole> userRoleSet = new HashSet<>();
        for (String role : roles) {
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRoleName(role);
            userRoleSet.add(userRole);
        }
        user.setUserRoles(userRoleSet);

        entityManager.persist(user);
    }

    @Transactional
    public void deleteUser(Integer id) {
        User user = findUserById(id);
        entityManager.remove(user);
    }

    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Transactional
    public User findUserById(Integer id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    public User findUserByLogin(String login) {
        return entityManager.createQuery("Select u FROM User u WHERE u.login = :login",User.class).setParameter("login",login).getSingleResult();
    }

    @Transactional
    public List<User> findAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public List<User> findUsersWithRole(Role role) {
        Query query = entityManager.createQuery("SELECT u FROM User u JOIN u.userRoles r WHERE r.roleName = :role", User.class);
        query.setParameter("role", role.getRoleName());
        return query.getResultList();
    }
}
