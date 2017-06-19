package persistence.service.interfaces;

import persistence.model.Space;

import java.util.List;

public interface SpaceService {

    void addSpace(Space space);
    void updateSpace(Space space);
    void deleteSpace(Integer id);
    Space findSpaceById(Integer id);
    List<Space> findAll();
}
