package UI.Department.DepartmentFiles;

import Model.Department.DepartmentModel;
import Model.FileDumpModel;
import Presenter.DepartmentPresenter;
import UI.BaseFileController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;

public class PhotosController extends BaseFileController {

    private DepartmentModel mDepartmentModel;
    @FXML
    private AnchorPane mPanePhoto;

    public PhotosController() {
        mDepartmentModel = DepartmentPresenter.get().getDepartmentModel();
        setTypeDocument(DepartmentPresenter.get().getTypeDocuments());

    }

    @FXML
    public void initialize() {

    }

    @Override
    protected ObservableList<FileDumpModel> getFileList() {
        return mDepartmentModel.getFilesList(getTypeDocument());
    }

    @Override
    protected File getTempFile(String path) {
        return DepartmentPresenter.get().getTempFile(path);
    }

    @Override
    protected Stage getStage() {
        return (Stage) mPanePhoto.getScene().getWindow();
    }

    @Override
    public void primaryClick(String id) {

    }
}
