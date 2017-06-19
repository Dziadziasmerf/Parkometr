package persistence.service;

import persistence.dao.interfaces.SpaceDao;
import persistence.model.Space;
import persistence.service.interfaces.SpaceService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class SpaceServiceImpl implements SpaceService {

    @Inject
    SpaceDao spaceDao;

    public void addSpace(Space space) {
        spaceDao.addSpace(space);
    }

    public void updateSpace(Space space) {
        spaceDao.updateSpace(space);
    }

    public void deleteSpace(Integer id) {
        spaceDao.deleteSpace(id);
    }

    public Space findSpaceById(Integer id) {
        return spaceDao.findSpaceById(id);
    }

    public List<Space> findAll() {
        return spaceDao.findAll();
    }
}
