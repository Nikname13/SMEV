package UI.Department.DepartmentFiles;

import Model.Department.DepartmentModel;
import Model.FileDumpModel;
import Presenter.DepartmentPresenter;
import Service.IUpdateUI;
import Service.ListenersService;
import UI.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FilesDepartmentController extends BaseController implements IUpdateUI {

    private DepartmentModel mDepartmentModel;
    private String mTypeDocument;

    public FilesDepartmentController (){
        ListenersService.get().addListenerUI(this);
        System.out.println("constructor file department bleat");
        mDepartmentModel=DepartmentPresenter.get().getDepartmentModel();
        mTypeDocument=DepartmentPresenter.get().getTypeDocuments();
    }

    @FXML
    private TableView<FileDumpModel> tableViewFile;
    @FXML
    private TableColumn<FileDumpModel,String> columnName;
    @FXML
    private AnchorPane anchorPaneFiles;

    @FXML
    public void initialize(){
        columnName.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        tableViewFile.setItems(mDepartmentModel.getObsFileDumpDocList());
        tableViewFile.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectFile(newValue)));
        System.out.println("initialize file department bleat");
       /* getStage().setOnCloseRequest(event -> {
            System.out.println("clooooose");
        });*/
    }

    @FXML
    private void onClickAdd() {
        DepartmentPresenter.get().uploadFiles(getStage());
    }

    @FXML
    private void selectFile(FileDumpModel file) {
        //new DepartmentPresenter().downloadOpenFile(file.getPath(),mTypeDocument);
        String fileName=file.getName();
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Open Document");//Заголовок диалога
        fileChooser.setInitialFileName(file.getName());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File", "*"+fileName.substring(fileName.length()-4,fileName.length())));
        File saveFile = fileChooser.showSaveDialog(anchorPaneFiles.getScene().getWindow());
        if(saveFile!= null){
            System.out.println(saveFile.getPath());
            DepartmentPresenter.get().downloadSaveFile(file.getPath(), mTypeDocument, saveFile);
        }

    }

    @Override
    public void updateUI(Class<?> updateClass) {

    }

    @Override
    public void refreshControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {

    }

    @Override
    protected Stage getStage() {
        return (Stage) anchorPaneFiles.getScene().getWindow();
    }
}
