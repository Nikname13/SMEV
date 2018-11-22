package UI.Equipment.EquipmentFiles;

import Model.Equipment.EquipmentInventoryModel;
import Model.FileDumpModel;
import Presenter.EquipmentInventoryPresenter;
import Presenter.EquipmentPresenter;
import UI.BaseFileController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;

public class PhotoEquipmentController extends BaseFileController {

    private EquipmentInventoryModel mEquipmentInventoryModel;
    @FXML
    private AnchorPane mPanePhoto;

    public PhotoEquipmentController() {
        mEquipmentInventoryModel = EquipmentInventoryPresenter.get().getEquipmentInventoryModel();
        setTypeDocument(EquipmentPresenter.get().getTypeDocuments());

    }

    @FXML
    public void initialize() {

    }

    @Override
    protected ObservableList<FileDumpModel> getFileList() {
        return mEquipmentInventoryModel.getFilesList(getTypeDocument());
    }

    @Override
    protected File getTempFile(String path) {
        return EquipmentInventoryPresenter.get().getTempFile(path);
    }

    @Override
    protected Stage getStage() {
        return (Stage) mPanePhoto.getScene().getWindow();
    }

    @Override
    public void primaryClick(String id) {

    }
}
