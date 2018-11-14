package UI.Supply.SupplyFiles;


import Model.FileDumpModel;
import Model.Supply.SupplyModel;
import Presenter.SupplyPresenter;
import Service.ListenersService;
import UI.BaseFileController;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FilesSupplyController extends BaseFileController {

    private SupplyModel mSupplyModel;
    @FXML
    private StackPane mPaneFiles;
    @FXML
    private JFXListView<FileDumpModel> mListFileDump;

    public FilesSupplyController() {
        ListenersService.get().addListenerUI(this);
        mSupplyModel = SupplyPresenter.get().getSupplyModel();
        setTypeDocument(SupplyPresenter.get().getTypeDocuments());
    }

    @FXML
    public void initialize() {
        initListView(mListFileDump);
        initPopup(mListFileDump);
    }

    @Override
    protected void initListView(JFXListView<FileDumpModel> list) {
        super.initListView(list);
        list.setItems(mSupplyModel.getFilesList(getTypeDocument()));
    }

    @Override
    protected void editFile(String name) {
        SupplyPresenter.get().editFile(name);
    }

    @FXML
    private void onClickAdd() {
        SupplyPresenter.get().uploadFiles(getStage());
    }

    @Override
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(FileDumpModel.class.getName())) {
            mListFileDump.setItems(mSupplyModel.getFilesList(getTypeDocument()));
        }
    }

    @Override
    public void primaryClick(String id) {
        switch (id) {
            case "saveFile":
                SupplyPresenter.get().saveSelectedFile(getStage());
                break;
            case "renameFile":
                createDialogEditFile(mPaneFiles);
                break;
            case "mListFileDump":
                SupplyPresenter.get().openSelectedFile();
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
