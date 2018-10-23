package Presenter;

import Iteractor.IteractorDepartment;
import Iteractor.IteractorLocation;
import Iteractor.IteractorPurchase;
import Model.Area.AreaModel;
import Model.Department.DepartmentModel;
import Model.Department.Departments;
import Model.Department.PurchaseModel;
import Model.Equipment.EquipmentInventoryModel;
import Model.Location.LocationModel;
import Model.Worker.WorkerModel;
import Service.IUpdateData;
import Service.ListenersService;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class DepartmentPresenter extends BasePresenter implements IUpdateData {

    private static DepartmentModel sDepartmentModel;
    private static PurchaseModel sPurchaseModel;
    private static String sTypeDocuments;
    private static DepartmentPresenter sDepartmentPresenter;

    public static DepartmentPresenter get() {
        if (sDepartmentPresenter == null) sDepartmentPresenter = new DepartmentPresenter();
        return sDepartmentPresenter;
    }

    private DepartmentPresenter() {
        ListenersService.get().addListenerData(this);
    }

    public DepartmentModel getDepartmentModel() {
        return sDepartmentModel;
    }

    public void setDepartmentModel(Object departmentModel) {
        sDepartmentModel = (DepartmentModel) departmentModel;
    }

    public PurchaseModel getPurchaseModel() {
        return sPurchaseModel;
    }

    public void setPurchaseModel(PurchaseModel purchaseModel) {
        sPurchaseModel = purchaseModel;
    }


    public void addDepartment(String number, String name, boolean eleсtronicQ, boolean renting, String description, AreaModel area, String address) {
        DepartmentModel department = new IteractorDepartment().addNew(new DepartmentModel(0, number, name, eleсtronicQ, renting, description, area));
        new IteractorLocation().addNew(new LocationModel(-1, address, department));
        ListenersService.get().updateControl(DepartmentModel.class, department);
    }

    public void addDepartment(String number, String name, boolean eleсtronicQ, boolean renting, String description, AreaModel area, LocationModel address) {
        DepartmentModel department = new IteractorDepartment().addNew(new DepartmentModel(0, number, name, eleсtronicQ, renting, description, area));
        address.addLocation(department);
        new IteractorLocation().edit(address);
        ListenersService.get().updateControl(DepartmentModel.class, department);
    }

    public void editDepartment(String number, String name, boolean eleсtronicQ, boolean renting, String description, AreaModel area) {
        new IteractorDepartment().edit(new DepartmentModel(sDepartmentModel.getId(), number, name, eleсtronicQ, renting, description, area));
        ListenersService.get().refreshControl(Departments.class);
    }

    public void addPurchase(String url, String description, LocalDate date) {
        new IteractorPurchase().addNew(new PurchaseModel(0, url, description, date, sDepartmentModel));
        ListenersService.get().updateControl(PurchaseModel.class);
    }

    public void editPurchase() {
    }


    public void deleteDepartment(int id) {
        new IteractorDepartment().delete(id);
    }

    public void uploadConfig(List<File> files) throws IOException {
        sDepartmentModel.addFileDumpDocList(files);
    }

    public String getTypeDocuments() {
        return sTypeDocuments;
    }

    public void setTypeDocuments(String type) {
        sTypeDocuments = type;
    }

    public void downloadOpenFile(String path, String typeDocuments) {
        try {
            File file = File.createTempFile(path.substring(0, path.length() - 4), path.substring(path.length() - 4));
            Desktop desktop = null;
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
            }
            desktop.open(new IteractorDepartment().downloadFile(sDepartmentModel.getId(), typeDocuments, path, file));
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void downloadSaveFile(String path, String typeDocuments, File savePath) {
        new IteractorDepartment().downloadFile(sDepartmentModel.getId(), typeDocuments, path, savePath);
    }

    @Override
    public void update(Object object) {
        System.out.println(object.getClass() + " " + EquipmentInventoryModel.class);
        if (object.getClass().equals(EquipmentInventoryModel.class)) {
            EquipmentInventoryModel equipment = (EquipmentInventoryModel) object;
            Departments.get().getEntity(equipment.getDepartmentModel().getId()).setEquipmentList(null);
        }
        if (object.getClass().equals(WorkerModel.class)) {
            WorkerModel workerModel = (WorkerModel) object;
            Departments.get().getEntity(workerModel.getDepartmentModel().getId()).setWorkerList(null);
        }
        if (object.getClass().equals(LocationModel.class)) {
            LocationModel locationModel = (LocationModel) object;
            for (DepartmentModel departmentModel : locationModel.getDepartmentList()) {
                Departments.get().getEntity(departmentModel.getId()).setLocationList(null);
            }
        }
/*        if(object.getClass().equals(AreaModel.class)){
            AreaModel area= (AreaModel) object;
            for(DepartmentModel departmentModel:Departments.get().getObsEntityList()){
                if(departmentModel.getAreaModel().getId()==area.getId()){
                    departmentModel.setAreaModel(area);
                }
            }
        }*/
    }

    private void setLoadFalse(int id) {
        Departments.get().getEntity(id).setLoad(false);
    }

    @Override
    public void delete() {
        if (getSelectedObject() != null) {
            if (getSelectedObject().equals(sDepartmentModel)) {
                if (new IteractorDepartment().delete(sDepartmentModel.getId()))
                    ListenersService.get().updateControl(DepartmentModel.class);
            }
            if (getSelectedObject().equals(sPurchaseModel)) {
                if (new IteractorPurchase().delete(sPurchaseModel))
                    ListenersService.get().updateControl(PurchaseModel.class);
            }
        }
    }

    @Override
    public void loadEntity(int id) {
        if (!sDepartmentModel.isLoad()) {
            new IteractorDepartment().loadData(id);
        }
    }
}
