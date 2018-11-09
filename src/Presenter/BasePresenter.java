package Presenter;

import Model.Area.AreaModel;
import Model.Area.Areas;
import Model.Department.DepartmentModel;
import Model.Department.Departments;
import Model.Equipment.EquipmentModel;
import Model.Equipment.Equipments;
import Model.FileDumpModel;
import Model.Inventory_number.InventoryNumberModel;
import Model.Inventory_number.InventoryNumbers;
import Model.Location.LocationModel;
import Model.Location.Locations;
import Model.Movement.MovementModel;
import Model.Movement.Movements;
import Model.Parameter.ParameterModel;
import Model.Parameter.Parameters;
import Model.Post.PostModel;
import Model.Post.Posts;
import Model.Provider.ProviderModel;
import Model.Provider.Providers;
import Model.State.StateModel;
import Model.State.States;
import Model.Supply.SupplyModel;
import Model.Supply.Supplys;
import Model.Type.TypeModel;
import Model.Type.Types;
import Model.Worker.WorkerModel;
import Model.Worker.Workers;
import Service.ErrorService;
import Service.IUpdateData;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class BasePresenter implements IUpdateData {

    private static Object sSelectedObject;

    public static Object getSelectedObject() {
        return sSelectedObject;
    }

    protected void setSelectedObject(Object selectedObject) {
        sSelectedObject = selectedObject;
    }

    void loadEntity(int id) {
        ErrorService.get().overrideError("loadEntity", this.getClass());
    }

    public ObservableList<AreaModel> getObservableArea() {
        return Areas.get().getObsEntityList();
    }

    public ObservableList<DepartmentModel> getObservableDepartment() {
        return Departments.get().getObsEntityList();
    }

    public ObservableList<WorkerModel> getObservableWorker() {
        return Workers.get().getObsEntityList();
    }

    public ObservableList<LocationModel> getObservableLocation() {
        return Locations.get().getObsEntityList();
    }

    public ObservableList<EquipmentModel> getObservableEquipment() {
        return Equipments.get().getObsEntityList();
    }

    public ObservableList<TypeModel> getObservableType() {
        return Types.get().getObsEntityList();
    }

    public ObservableList<StateModel> getObservableState() {
        return States.get().getObsEntityList();
    }

    public ObservableList<InventoryNumberModel> getObservableInventory() {
        return InventoryNumbers.get().getObsEntityList();
    }

    public ObservableList<ParameterModel> getObservableParameter() {
        return Parameters.get().getObsEntityList();
    }

    public ObservableList<MovementModel> getObservableMovement() {
        return Movements.get().getObsEntityList();
    }

    public ObservableList<ProviderModel> getObservableProvider() {
        return Providers.get().getObsEntityList();
    }

    public ObservableList<SupplyModel> getObservableSupply() {
        return Supplys.get().getObsEntityList();
    }

    public ObservableList<PostModel> getObservablePost() {
        return Posts.get().getObsEntityList();
    }

    protected List<File> uploadDocFiles(Stage stage) {
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Open Document");//Заголовок диалога
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("Word doc (*.doc)", "*.doc"),
                new FileChooser.ExtensionFilter("Open Document (*.odt)", "*.odt")
        );
        return fileChooser.showOpenMultipleDialog(stage);//Указываем текущую сцену CodeNote.mainStage
    }

    protected File saveFile(FileDumpModel file, Stage stage) {
        String fileName = file.getName();
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Open Document");//Заголовок диалога
        fileChooser.setInitialFileName(file.getName());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File", "*" + fileName.substring(fileName.length() - 4, fileName.length())));
        return fileChooser.showSaveDialog(stage.getScene().getWindow());

    }

    protected void openFile(String path) {
        try {
            File savePathFile = File.createTempFile(path.substring(0, path.lastIndexOf(".")), path.substring(path.lastIndexOf(".")));
            System.out.println("path " + path + " File save path " + savePathFile.getPath());
            Desktop desktop = null;
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
            }
            desktop.open(savePathFile);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ErrorService.get().showError(ex.getMessage());
        }
    }

    protected File getFile(File savePathFile) {
        return null;
    }

    @Override
    public void update(Object equipment) {
        ErrorService.get().overrideError("update", this.getClass());
    }

    @Override
    public void delete() {
        ErrorService.get().overrideError("delete", this.getClass());
    }
}
