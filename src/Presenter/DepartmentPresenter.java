package Presenter;

import Iteractor.IteractorDepartment;
import Iteractor.IteractorLocation;
import Iteractor.IteractorPurchase;
import Model.Area.AreaModel;
import Model.Department.DepartmentModel;
import Model.Department.Departments;
import Model.Department.PurchaseModel;
import Model.FileDumpModel;
import Model.Location.LocationModel;
import Service.ListenersService;
import UI.MainTabs.Controller.DepartmentTabController;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class DepartmentPresenter extends BaseFilePresenter<DepartmentModel> {

    private static DepartmentModel sDepartmentModel;
    private static PurchaseModel sPurchaseModel;

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

    public void setDepartmentModel(DepartmentModel departmentModel) {
        sDepartmentModel = departmentModel;
        setSelectedObject(departmentModel);
    }

    public PurchaseModel getPurchaseModel() {
        return sPurchaseModel;
    }

    public void setPurchaseModel(PurchaseModel purchaseModel) {
        sPurchaseModel = purchaseModel;
        setSelectedObject(purchaseModel);
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

    public void editDepartment(DepartmentModel departmentModel) {
        new IteractorDepartment().edit(departmentModel);
        ListenersService.get().refreshControl(Departments.class);
    }

    public void addPurchase(String url, String description, LocalDate date) {
        new IteractorPurchase().addNew(new PurchaseModel(0, url, description, date, sDepartmentModel));
        ListenersService.get().updateControl(PurchaseModel.class);
    }

    @Override
    protected void editFile(FileDumpModel editableFile) {
        new IteractorDepartment().editFile(editableFile, sDepartmentModel.getId(), getTypeDocuments());
    }

    @Override
    protected void setAvatar(List<File> fileList) {
        sDepartmentModel.setAvatar(uploadFiles(fileList).get(0));
        editDepartment(sDepartmentModel);
    }

    @Override
    protected List<FileDumpModel> uploadFiles(List<File> fileList) {
        return new IteractorDepartment().uploadFile(sDepartmentModel.getId(), fileList, getTypeDocuments());
    }

    @Override
    protected File getFile(File savePathFile) {
        System.out.println("file path " + savePathFile.getPath());
        return new IteractorDepartment().downloadFile(sDepartmentModel.getId(), getTypeDocuments(), FileDumpPresenter.get().getFileDumpModel().getPath(), savePathFile);
    }

    @Override
    protected DepartmentModel getAvatarEntity() {
        return sDepartmentModel;
    }

    @Override
    public void delete() {
        if (getSelectedObject() != null) {
            if (getSelectedObject().equals(sDepartmentModel)) {
                if (new IteractorDepartment().delete(sDepartmentModel.getId())) {
                    ListenersService.get().updateControl(DepartmentModel.class);
                    ListenersService.get().updateUI(DepartmentTabController.class);
                }
                return;
            }
            if (getSelectedObject().equals(sPurchaseModel)) {
                if (new IteractorPurchase().delete(sPurchaseModel)) {
                    ListenersService.get().updateControl(PurchaseModel.class);
                }
                return;
            }
            if (getSelectedObject().equals(FileDumpPresenter.get().getFileDumpModel())) {
                System.out.println("selected object " + getSelectedObject());
                if (new IteractorDepartment().delete(sDepartmentModel.getId(), FileDumpPresenter.get().getFileDumpModel().getId(), getTypeDocuments())) {
                    ListenersService.get().updateControl(FileDumpModel.class);
                    return;
                }
            }
        }
    }

    @Override
    public void loadEntity(int id) {
            new IteractorDepartment().loadData(id);
    }


}
