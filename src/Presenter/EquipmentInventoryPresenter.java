package Presenter;

import Iteractor.IteractorEquipmentInventory;
import Model.Department.DepartmentModel;
import Model.Equipment.EquipmentInventoryLogModel;
import Model.Equipment.EquipmentInventoryModel;
import Model.Equipment.EquipmentModel;
import Model.Equipment.EquipmentStateLogModel;
import Model.FileDumpModel;
import Model.Inventory_number.InventoryNumberModel;
import Model.State.StateModel;
import Service.ListenersService;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class EquipmentInventoryPresenter extends BaseFilePresenter<EquipmentInventoryModel> {

    private static EquipmentInventoryModel sEquipmentInventoryModel;
    private static EquipmentModel sEquipmentModel;
    private static EquipmentInventoryPresenter sEquipmentInventoryPresenter;
    private static InventoryNumberModel sInventoryNumberModel;
    private static EquipmentStateLogModel sEquipmentStateLog;
    private static EquipmentInventoryLogModel sEquipmentInventoryLogModel;
    private static StateModel sStateModel;
    private static DepartmentModel sDepartmentModel;

    private EquipmentInventoryPresenter() {

    }

    public static EquipmentInventoryPresenter get() {
        if (sEquipmentInventoryPresenter == null) sEquipmentInventoryPresenter = new EquipmentInventoryPresenter();
        return sEquipmentInventoryPresenter;
    }

    public static EquipmentInventoryModel getEquipmentInventoryModel() {
        return sEquipmentInventoryModel;
    }

    public void setEquipmentInventoryModel(EquipmentInventoryModel equipmentInventoryModel) {
        sEquipmentInventoryModel = equipmentInventoryModel;
        if (equipmentInventoryModel != null) {
            sEquipmentModel = equipmentInventoryModel.getEquipmentModel();
        }
        setSelectedObject(equipmentInventoryModel);
    }

    public InventoryNumberModel getInventoryNumberModel() {
        return sInventoryNumberModel;
    }

    public void setInventoryNumberModel(InventoryNumberModel inventoryNumberModel) {
        sInventoryNumberModel = inventoryNumberModel;
    }

    public StateModel getStateModel() {
        return sStateModel;
    }

    public void setStateModel(Object stateModel) {
        sStateModel = (StateModel) stateModel;
    }

    public DepartmentModel getDepartmentModel() {
        return sDepartmentModel;
    }

    public void setDepartmentModel(DepartmentModel departmentModel) {
        sDepartmentModel = departmentModel;
    }

    public void editEquipmentInventory(InventoryNumberModel inventoryNumber, int guaranty, String description,
                                       DepartmentModel departmentModel, EquipmentModel equipmentModel, StateModel state) {
        /*      */
        EquipmentInventoryModel newEquipment = new IteractorEquipmentInventory().edit(new EquipmentInventoryModel(sEquipmentInventoryModel.getId(), inventoryNumber, guaranty,
                description, departmentModel, equipmentModel, state));
        if (newEquipment != null) {
            //ListenersService.get().updateData(newEquipment);
            sEquipmentInventoryModel.setInventoryNumber(inventoryNumber);
            sEquipmentInventoryModel.setGuaranty(guaranty);
            sEquipmentInventoryModel.setDescription(description);
            sEquipmentInventoryModel.setDepartmentModel(departmentModel);
            sEquipmentInventoryModel.setEquipmentModel(equipmentModel);
            sEquipmentInventoryModel.setStateModel(state);
            ListenersService.get().updateData(newEquipment);
            ListenersService.get().refreshControl(EquipmentInventoryModel.class);
        }
    }

    public void editEquipmentInventory(EquipmentInventoryModel equipment) {
        ListenersService.get().updateData(new IteractorEquipmentInventory().edit(equipment));
        ListenersService.get().refreshControl(EquipmentInventoryModel.class);
    }

    public void addEquipmentStateLog(String state, String description, LocalDate date) {
        sEquipmentStateLog = new EquipmentStateLogModel(0, state, description, date);
        sEquipmentInventoryModel.setStateModel(sStateModel);
        sEquipmentInventoryModel.addEntity(sEquipmentStateLog);
        editEquipmentInventory(sEquipmentInventoryModel);
        ListenersService.get().refreshControl(EquipmentInventoryModel.class);
    }

    public void addEquipmentInventoryLogModel(String description, LocalDate date) {
        sEquipmentInventoryLogModel = new EquipmentInventoryLogModel(0, sInventoryNumberModel.getName(), sInventoryNumberModel.getId(), date, description);
        sEquipmentInventoryModel.setInventoryNumber(sInventoryNumberModel);
        sEquipmentInventoryModel.addInventoryEditLog(sEquipmentInventoryLogModel);
        editEquipmentInventory(sEquipmentInventoryModel);
        ListenersService.get().refreshControl(EquipmentInventoryLogModel.class);
    }

    public EquipmentStateLogModel getEquipmentStateLog() {
        return sEquipmentStateLog;
    }

    public void setEquipmentStateLog(EquipmentStateLogModel equipmentState) {
        sEquipmentStateLog = equipmentState;
    }

    public EquipmentInventoryLogModel getEquipmentInventoryLogModel() {
        return sEquipmentInventoryLogModel;
    }

    public void setEquipmentInventoryLogModel(EquipmentInventoryLogModel equipmentInventoryLogModel) {
        sEquipmentInventoryLogModel = equipmentInventoryLogModel;
    }

    @Override
    protected void setAvatar(List<File> fileList) {
        sEquipmentInventoryModel.setAvatar(new IteractorEquipmentInventory().uploadFile(sEquipmentInventoryModel.getId(), fileList, getTypeDocuments()).get(0));
        editEquipmentInventory(sEquipmentInventoryModel);
    }


    @Override
    protected void editFile(FileDumpModel editableFile) {
        new IteractorEquipmentInventory().editFile(editableFile, sEquipmentInventoryModel.getId(), getTypeDocuments());
    }

    @Override
    protected List<FileDumpModel> uploadFiles(List<File> fileList) {
        return new IteractorEquipmentInventory().uploadFile(sEquipmentInventoryModel.getId(), fileList, getTypeDocuments());
    }

    @Override
    protected File getFile(File savePathFile) {
        return new IteractorEquipmentInventory().downloadFile(sEquipmentInventoryModel.getId(), getTypeDocuments(), FileDumpPresenter.get().getFileDumpModel().getPath(), savePathFile);
    }

    @Override
    protected EquipmentInventoryModel getAvatarEntity() {
        return sEquipmentInventoryModel;
    }

    @Override
    public void delete() {
        if (getSelectedObject() != null) {
            if (getSelectedObject().equals(sEquipmentInventoryModel)) {
                System.out.println("delete " + sEquipmentInventoryModel.getInventoryNumber().getName());
                if (new IteractorEquipmentInventory().delete(sEquipmentInventoryModel.getId())) {
                    ListenersService.get().updateData(sEquipmentInventoryModel);
                    ListenersService.get().updateControl(EquipmentInventoryModel.class);
                }
            }
        }
    }
}
