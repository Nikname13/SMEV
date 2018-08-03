package Presenter;

import Iteractor.IteractorLocation;
import Model.Department.DepartmentModel;
import Model.Location.LocationModel;
import Model.Location.Locations;
import Service.IUpdateData;
import Service.UpdateService;
import javafx.collections.ObservableList;

public class LocationPresenter extends BasePresenter implements IUpdateData {

    private static LocationModel sLocationModel;
    private static LocationPresenter sLocationPresenter;

    public static LocationPresenter get(){
        if(sLocationPresenter==null)sLocationPresenter=new LocationPresenter();
        return sLocationPresenter;
    }

    private LocationPresenter() {
        UpdateService.get().addListenerData(this);
    }

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
        UpdateService.get().updateData(new IteractorLocation().addNew(new LocationModel(0, address, department)));
        UpdateService.get().updateControl(LocationModel.class);
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


    @Override
    public void update(Object equipment) {

    }

    @Override
    public void delete() {
        if (getSelectedObject() != null) {
            if (getSelectedObject().equals(sLocationModel)) {
                System.out.println("delete " + sLocationModel.getName() + "hashCode " + sLocationModel.hashCode());
            }
        }
    }
}
