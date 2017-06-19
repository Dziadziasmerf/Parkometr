package beans;

import persistence.model.Space;
import persistence.model.Street;
import persistence.service.interfaces.SpaceService;
import persistence.service.interfaces.StreetService;
import persistence.service.interfaces.TicketService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.util.Set;

@SessionScoped
@ManagedBean(name = "streetManager")
public class StreetManager {

    @ManagedProperty(value = "#{districtManager}")
    DistrictManager districtManager;

    @Inject
    StreetService streetService;

    @Inject
    SpaceService spaceService;

    @Inject
    TicketService ticketService;

    private Street street;
    private String streetName;
    private Integer newSpacesAmount;
    private Integer spacesAmount;
    private long freeSpacesAmount;
    private long takenWithoutTicket;

    public void addStreetToDistrict() {
        Street street = new Street();
        street.setName(streetName);
        street.setDistrict(districtManager.getDistrict());
        streetService.addStreet(street);
        districtManager.updateDistrict();
        streetName = null;
    }

    public void deleteStreet(Street street) {
        streetService.deleteStreet(street.getId());
        districtManager.updateDistrict();
    }

    private void setData() {
        spacesAmount = street.getSpaces().size();
        freeSpacesAmount = street.getSpaces().stream().filter(space -> !space.isTaken()).count();
        takenWithoutTicket =street.getSpaces().stream().filter(space -> space.isTaken()).count() - ticketService.findValidTicketsByStreetId(street.getId()).size();
    }

    public void addSpaces() {
        for (int i = 0; i < newSpacesAmount; i++) {
            Space space = new Space();
            space.setStreet(street);
            street.getSpaces().add(space);
        }
        streetService.updateStreet(street);
        street = streetService.findStreetById(street.getId());
        setData();
        newSpacesAmount = 0;
    }

    public Set<Street> getStreets() {
        return districtManager.getDistrict().getStreets();
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
        setData();
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setDistrictManager(DistrictManager districtManager) {
        this.districtManager = districtManager;
    }

    public Integer getNewSpacesAmount() {
        return newSpacesAmount;
    }

    public void setNewSpacesAmount(Integer newSpacesAmount) {
        this.newSpacesAmount = newSpacesAmount;
    }

    public Integer getSpacesAmount() {
        return spacesAmount;
    }

    public void setSpacesAmount(Integer spacesAmount) {
        this.spacesAmount = spacesAmount;
    }

    public long getFreeSpacesAmount() {
        return freeSpacesAmount;
    }

    public void setFreeSpacesAmount(long freeSpacesAmount) {
        this.freeSpacesAmount = freeSpacesAmount;
    }

    public long getTakenWithoutTicket() {
        return takenWithoutTicket;
    }

    public void setTakenWithoutTicket(long takenWithoutTicket) {
        this.takenWithoutTicket = takenWithoutTicket;
    }
}
