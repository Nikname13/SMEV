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
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class DepartmentPresenter extends BasePresenter {

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

    public void addPurchase(String url, String description, LocalDate date) {
        new IteractorPurchase().addNew(new PurchaseModel(0, url, description, date, sDepartmentModel));
        ListenersService.get().updateControl(PurchaseModel.class);
    }

    public void editPurchase() {
    }


    public void deleteDepartment(int id) {
        new IteractorDepartment().delete(id);
    }

    public String getTypeDocuments() {
        return sTypeDocuments;
    }

    public void setTypeDocuments(String type) {
        sTypeDocuments = type;
    }

    public void editFile(String name) {
        FileDumpModel ediFile = FileDumpPresenter.get().getFileDumpModel();
        ediFile.setName(name.concat(ediFile.getName().substring(ediFile.getName().lastIndexOf("."))));
        new IteractorDepartment().editFile(ediFile, sDepartmentModel.getId(), sTypeDocuments);
        ListenersService.get().updateControl(FileDumpModel.class);
    }

    public boolean uploadFiles(Stage stage) {
        List<File> fileList = uploadDocFiles(stage);
        if (fileList != null) {
            System.out.println("Процесс открытия файла");
            new IteractorDepartment().uploadFile(sDepartmentModel.getId(), fileList, sTypeDocuments);
            ListenersService.get().updateControl(FileDumpModel.class);
            return true;
        }
        return false;
    }

    public void saveSelectedFile(Stage stage) {
        File savePathFile = saveFile(FileDumpPresenter.get().getFileDumpModel(), stage);
        if (savePathFile != null) {
            getFile(savePathFile);
        }
    }

    public void openSelectedFile() {
        openFile(FileDumpPresenter.get().getFileDumpModel().getPath());
    }

    @Override
    protected File getFile(File savePathFile) {
        return new IteractorDepartment().downloadFile(sDepartmentModel.getId(), sTypeDocuments, FileDumpPresenter.get().getFileDumpModel().getPath(), savePathFile);
    }

    @Override
    public void delete() {
        if (getSelectedObject() != null) {
            if (getSelectedObject().equals(sDepartmentModel)) {
                if (new IteractorDepartment().delete(sDepartmentModel.getId()))
                    ListenersService.get().updateControl(DepartmentModel.class);
                return;
            }
            if (getSelectedObject().equals(sPurchaseModel)) {
                if (new IteractorPurchase().delete(sPurchaseModel))
                    ListenersService.get().updateControl(PurchaseModel.class);
                return;
            }
            if (getSelectedObject().equals(FileDumpPresenter.get().getFileDumpModel())) {
                System.out.println("selected object " + getSelectedObject());
                if (new IteractorDepartment().delete(sDepartmentModel.getId(), FileDumpPresenter.get().getFileDumpModel().getId(), sTypeDocuments)) {
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
