package soap;

import eventmanagement.ParkingSpaceEventManagement;
import persistence.model.Space;
import persistence.service.interfaces.SpaceService;
import persistence.service.interfaces.UserService;

import javax.inject.Inject;
import javax.jws.WebService;

@WebService(serviceName = "CardDetectorService", portName = "CarDetector", name = "CarDetector", endpointInterface = "soap.CarDetectorService")
public class CarDetectorServiceImpl implements CarDetectorService {

    @Inject
    SpaceService spaceService;

    @Inject
    ParkingSpaceEventManagement parkingSpaceEventManagement;

    @Override
    public String setSpaceTaken(Integer id) {
        Space space = spaceService.findSpaceById(id);
        space.setTaken(true);
        spaceService.updateSpace(space);

        parkingSpaceEventManagement.spaceTakenEvent(id);

        return "Success";
    }

    @Override
    public void setSpaceFree(Integer id) {
        Space space = spaceService.findSpaceById(id);
        space.setTaken(false);
        spaceService.updateSpace(space);

        parkingSpaceEventManagement.spaceFreedEvent(id);
    }
}
