package UI.Department.DepartmentFiles;

import Model.Department.DepartmentModel;
import Model.FileDumpModel;
import Presenter.DepartmentPresenter;
import Service.ListenersService;
import UI.BaseFileController;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FilesDepartmentController extends BaseFileController {

    private DepartmentModel mDepartmentModel;

    public FilesDepartmentController (){
        ListenersService.get().addListenerUI(this);
        mDepartmentModel=DepartmentPresenter.get().getDepartmentModel();
        setTypeDocument(DepartmentPresenter.get().getTypeDocuments());
    }

    @FXML
    private StackPane mFilesPane;
    @FXML
    private JFXListView<FileDumpModel> mFileDumpList;

    @FXML
    public void initialize(){
        initListView(mFileDumpList);
        initPopup(mFileDumpList);
    }

    @Override
    protected void initListView(JFXListView<FileDumpModel> list) {
        super.initListView(list);
        list.setItems(mDepartmentModel.getFilesList(getTypeDocument()));
    }

    @FXML
    private void onClickAdd() {
        DepartmentPresenter.get().uploadFiles(getStage());
    }

    @Override
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(FileDumpModel.class.getName())) {
            mFileDumpList.setItems(mDepartmentModel.getFilesList(getTypeDocument()));
        }
    }

    @Override
    protected Stage getStage() {
        return (Stage) mFilesPane.getScene().getWindow();
    }

    @Override
    public void destroy() {
        ListenersService.get().removeListenerUI(this);
    }

    @Override
    public void primaryClick(String id) {
        switch (id) {
            case "saveFile":
                DepartmentPresenter.get().saveSelectedFile(getStage());
                break;
            case "renameFile":
                createDialogEditFile(mFilesPane);
                break;
            case "mFileDumpList":
                System.out.println("double click");
                DepartmentPresenter.get().openSelectedFile();
                break;
        }
    }

    @Override
    protected void editFile(String name) {
        DepartmentPresenter.get().editFile(name);
    }
}
