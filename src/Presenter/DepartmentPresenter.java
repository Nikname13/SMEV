package Presenter;

import Iteractor.IteractorDepartment;
import Iteractor.IteractorLocation;
import Iteractor.IteractorPurchase;
import Model.Area.AreaModel;
import Model.Area.Areas;
import Model.Department.DepartmentModel;
import Model.Department.Departments;
import Model.Department.PurchaseModel;
import Model.Location.LocationModel;
import Model.Location.Locations;
import Model.Worker.WorkerModel;
import Model.Worker.Workers;
import javafx.collections.ObservableList;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class DepartmentPresenter {

    private static DepartmentModel sDepartmentModel;
    private static String mTypeDocuments;
    private static DepartmentPresenter sDepartmentPresenter;

    public static DepartmentPresenter get() {
        if(sDepartmentPresenter==null)sDepartmentPresenter=new DepartmentPresenter();
        return sDepartmentPresenter;
    }
    private DepartmentPresenter(){}

    public DepartmentModel getDepartmentModel() {
        return sDepartmentModel;
    }

    public void setDepartmentModel(Object departmentModel) {
        sDepartmentModel = (DepartmentModel) departmentModel;
    }

    public ObservableList<DepartmentModel> getObservableDepartment(){
        return Departments.get().getEntityList();
    }

    public ObservableList<WorkerModel> getObservableWorker(){
        return Workers.get().getEntityList();
    }

    public ObservableList<AreaModel> getObservableArea(){
        return Areas.get().getEntityList();
    }

    public ObservableList<LocationModel> getObservableLocation(){return Locations.get().getEntityList();}

    public void addDepartment(String number, String name, boolean eleсtronicQ, boolean renting, String description, AreaModel area, String address){
        /*new IteractorDepartment().addNew(new DepartmentModel(0, number, name, eleсtronicQ, renting, description, area));*/
        new IteractorLocation().addNew(new LocationModel(-1, address,
                new IteractorDepartment().addNew(new DepartmentModel(0, number, name, eleсtronicQ, renting, description, area))));
    }

    public void addDepartment(String number, String name, boolean eleсtronicQ, boolean renting, String description, AreaModel area, LocationModel address){
        /*new IteractorDepartment().addNew(new DepartmentModel(0, number, name, eleсtronicQ, renting, description, area));*/
        DepartmentModel department=new IteractorDepartment().addNew(new DepartmentModel(0, number, name, eleсtronicQ, renting, description, area));
        address.addLocation(department);
        new IteractorLocation().edit(address);
    }

    public void editDepartment(String number, String name, boolean eleсtronicQ, boolean renting, String description, AreaModel area){
        new IteractorDepartment().edit(new DepartmentModel(sDepartmentModel.getId(), number, name, eleсtronicQ, renting, description, area));
    }

    public void addPurchase(String url, String description, LocalDate date){
      new IteractorPurchase().addNew(new PurchaseModel(0, url, description, date, sDepartmentModel));
    }

    public void editPurchase(){}


    public void deleteDepartment(int id){
        new IteractorDepartment().delete(id);
    }

    public void uploadConfig(List<File> files) throws IOException {
        sDepartmentModel.addFileDumpDocList(files);
    }

    public void setTypeDocuments(String type){
        mTypeDocuments=type;
    }

    public String getTypeDocuments(){
        return mTypeDocuments;
    }

    public void downloadOpenFile(String path, String typeDocuments){
        try {
            File file = File.createTempFile(path.substring(0, path.length() - 4), path.substring(path.length() - 4, path.length()));
            Desktop desktop = null;
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
            }
            desktop.open(new IteractorDepartment().downloadFile(sDepartmentModel.getId(), typeDocuments, path, file));
        }catch (IOException ex){
            System.out.println(ex);
        }
    }

    public void downloadSaveFile(String path, String typeDocuments, File savePath){
        new IteractorDepartment().downloadFile(sDepartmentModel.getId(), typeDocuments, path, savePath);
    }
}
