package Presenter;

import Iteractor.IteractorLocation;
import Model.Department.DepartmentModel;
import Model.Location.LocationModel;
import Service.IUpdateData;
import Service.ListenersService;

public class LocationPresenter extends BasePresenter implements IUpdateData {

    private static LocationModel sLocationModel;
    private static LocationPresenter sLocationPresenter;

    public static LocationPresenter get(){
        if(sLocationPresenter==null)sLocationPresenter=new LocationPresenter();
        return sLocationPresenter;
    }

    private LocationPresenter() {
        ListenersService.get().addListenerData(this);
    }

    public LocationModel getLocation(){
        return sLocationModel;
    }

    public void setLocation(LocationModel location){
        sLocationModel=location;
    }

    public void addLocation(String address, DepartmentModel department){
        ListenersService.get().updateData(new IteractorLocation().addNew(new LocationModel(0, address, department)));
        ListenersService.get().updateControl(LocationModel.class);
    }

    public void editLocation(String address, DepartmentModel department){
        new IteractorLocation().edit(new LocationModel(sLocationModel.getId(),address,department));
    }

    public void editLocation(LocationModel location){
        ListenersService.get().updateData(new IteractorLocation().edit(location));
        ListenersService.get().updateControl(LocationModel.class);
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
                System.out.println("delete " + sLocationModel.getName() + "  " + sLocationModel.getDepartmentList());
                ListenersService.get().updateData(sLocationModel);
                for (DepartmentModel departmentModel : sLocationModel.getDepartmentList()) {
                    if (departmentModel.getId() == DepartmentPresenter.get().getDepartmentModel().getId()) {
                        sLocationModel.getDepartmentList().remove(departmentModel);
                        break;
                    }
                }
                new IteractorLocation().edit(sLocationModel);
                ListenersService.get().updateControl(LocationModel.class);
            }
        }
    }

    @Override
    void loadEntity(int id) {

    }
}
