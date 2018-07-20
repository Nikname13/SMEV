package UI.Department.DepartmentFiles;

import Model.Department.DepartmentModel;
import Model.FileDumpModel;
import Presenter.DepartmentPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FilesDepartmentController {

    private DepartmentModel mDepartmentModel;
    private String mTypeDocument;

    public FilesDepartmentController (){
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
    }

    @FXML
    private void onClickAdd() throws IOException {
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Open Document");//Заголовок диалога
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("Word doc (*.doc)", "*.doc"),
                new FileChooser.ExtensionFilter("Open Document (*.odt)", "*.odt")
        );
        List<File> files = fileChooser.showOpenMultipleDialog((Stage)anchorPaneFiles.getScene().getWindow());//Указываем текущую сцену CodeNote.mainStage
        if (files != null) {
            //Open
            System.out.println("Процесс открытия файла");
            DepartmentPresenter.get().uploadConfig(files);
        }
    }

    @FXML
    private void selectFile(FileDumpModel file) {
        //new DepartmentPresenter().downloadOpenFile(file.getPath(),mTypeDocument);
        String fileName=file.getName();
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Open Document");//Заголовок диалога
        fileChooser.setInitialFileName(file.getName());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File", "*"+fileName.substring(fileName.length()-4,fileName.length())));
        File saveFile=fileChooser.showSaveDialog((Stage)anchorPaneFiles.getScene().getWindow());
        if(saveFile!= null){
            System.out.println(saveFile.getPath());
            DepartmentPresenter.get().downloadSaveFile(file.getPath(), mTypeDocument, saveFile);
        }

    }
}
