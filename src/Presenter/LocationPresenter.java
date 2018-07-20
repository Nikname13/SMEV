package Presenter;

import Iteractor.IteractorLocation;
import Model.Department.DepartmentModel;
import Model.Location.LocationModel;
import Model.Location.Locations;
import javafx.collections.ObservableList;

public class LocationPresenter {

    private static LocationModel sLocationModel;
    private static LocationPresenter sLocationPresenter;

    public static LocationPresenter get(){
        if(sLocationPresenter==null)sLocationPresenter=new LocationPresenter();
        return sLocationPresenter;
    }

    private LocationPresenter(){}

    public LocationModel getLocation(){
        return sLocationModel;
    }

    public void setLocation(LocationModel location){
        sLocationModel=location;
    }

    public ObservableList<LocationModel> getObservableLocations(){
        return Locations.get().getEntityList();
    }

    public void addLocation(String address, DepartmentModel department){
        new IteractorLocation().addNew(new LocationModel(0, address, department));
    }

    public void editLocation(String address, DepartmentModel department){
        new IteractorLocation().edit(new LocationModel(sLocationModel.getId(),address,department));
    }

    public void editLocation(LocationModel location){
        new IteractorLocation().edit(location);
    }

    public void delete(int id){
        new IteractorLocation().delete(id);
    }
}
