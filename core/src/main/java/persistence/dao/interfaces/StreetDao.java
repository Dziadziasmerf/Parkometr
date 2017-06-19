package persistence.dao.interfaces;

import persistence.model.Street;

import java.util.List;

public interface StreetDao {
    void addStreet(Street street);
    void updateStreet(Street street);
    void deleteStreet(Integer id);
    Street findStreetById(Integer id);
    List<Street> findAll();
}
