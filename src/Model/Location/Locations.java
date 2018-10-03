package Model.Location;

import Iteractor.IteractorLocation;
import Model.GenericList;
import javafx.collections.ObservableList;

public class Locations extends GenericList<LocationModel> {

    private static Locations sLocations;

    public static Locations get() {
        if (sLocations == null) {
            sLocations = new Locations();
            new IteractorLocation().loadData();
        }
        return sLocations;
    }

    @Override
    public ObservableList<LocationModel> getObsEntityList() {
        new IteractorLocation().loadData();
        return super.getObsEntityList();
    }

    @Override
    public void update() {
        clear();
        new IteractorLocation().loadData();
    }

    @Override
    public void replace(LocationModel entity) {
        LocationModel location =Locations.get().getEntity(entity.getId());
        location.setName(entity.getName());
    }
}
