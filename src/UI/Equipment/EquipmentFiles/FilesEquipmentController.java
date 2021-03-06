package UI.Equipment.EquipmentFiles;

import Model.Equipment.EquipmentModel;
import Model.FileDumpModel;
import Presenter.EquipmentPresenter;
import Service.ListenersService;
import UI.BaseFileController;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FilesEquipmentController extends BaseFileController {

    private EquipmentModel mEquipmentModel;
    @FXML
    private StackPane mPaneFiles;
    @FXML
    private JFXListView<FileDumpModel> mListFileDump;

    public FilesEquipmentController() {
        ListenersService.get().addListenerUI(this);
        mEquipmentModel = EquipmentPresenter.get().getEquipmentModel();
        setTypeDocument(EquipmentPresenter.get().getTypeDocuments());
    }

    @FXML
    public void initialize() {
        initListView(mListFileDump);
        initPopup(mListFileDump);
    }

    @Override
    protected void initListView(JFXListView<FileDumpModel> list) {
        super.initListView(list);
        list.setItems(mEquipmentModel.getFilesList(getTypeDocument()));
    }

    @Override
    protected void editFile(String name) {
        EquipmentPresenter.get().editFile(name);
    }

    @FXML
    private void onClickAdd() {
        EquipmentPresenter.get().uploadFiles(getStage());
    }

    @Override
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(FileDumpModel.class.getName())) {
            mListFileDump.setItems(mEquipmentModel.getFilesList(getTypeDocument()));
        }
    }

    @Override
    public void primaryClick(String id) {
        switch (id) {
            case "saveFile":
                EquipmentPresenter.get().saveSelectedFile(getStage());
                break;
            case "renameFile":
                createDialogEditFile(mPaneFiles);
                break;
            case "mListFileDump":
                EquipmentPresenter.get().openSelectedFile();
                break;
        }
    }

    @Override
    protected Stage getStage() {
        return (Stage) mPaneFiles.getScene().getWindow();
    }

    @Override
    public void destroy() {
        ListenersService.get().removeListenerUI(this);
    }
}
