package UI.Department.DepartmentFiles;

import Model.Department.DepartmentModel;
import Model.FileDumpModel;
import Presenter.DepartmentPresenter;
import Presenter.FileDumpPresenter;
import Service.IOnMouseClick;
import Service.ListenersService;
import UI.BaseController;
import UI.Popup.Controller.BasePopup;
import UI.Validator.BaseValidator;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FilesDepartmentController extends BaseController implements IOnMouseClick {

    private DepartmentModel mDepartmentModel;
    private String mTypeDocument;
    private BaseValidator mBaseValidatorDialog = new BaseValidator();

    public FilesDepartmentController (){
        ListenersService.get().addListenerUI(this);
        mDepartmentModel=DepartmentPresenter.get().getDepartmentModel();
        mTypeDocument=DepartmentPresenter.get().getTypeDocuments();
    }

    @FXML
    private StackPane mFilesPane;
    @FXML
    private JFXListView<FileDumpModel> mFileDumpList;

    @FXML
    public void initialize(){
        initListView();
        initPopup();
    }

    private void initPopup() {
        new BasePopup(mFileDumpList, BasePopup.getFileDumpListPopup(), this, true);
    }

    private void initListView() {
        mFileDumpList.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(FileDumpModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else setText(null);
            }
        });
        mFileDumpList.setItems(mDepartmentModel.getFilesList(mTypeDocument));
        mFileDumpList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedFile(newValue)));
    }

    private void selectedFile(FileDumpModel newValue) {
        if (newValue != null) {
            FileDumpPresenter.get().setFileDumpModel(newValue);
        } else {
            FileDumpPresenter.get().setFileDumpModel(null);
        }
    }

    private void createDialog() {
        String oldFileName = FileDumpPresenter.get().getFileDumpModel().getName();
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Редактирование"));

        JFXTextField text = new JFXTextField();
        text.setText(oldFileName.substring(0, oldFileName.lastIndexOf(".")));
        text.setPrefWidth(200);
        text.setLabelFloat(true);
        text.setPromptText("Наименование файла");
        text.setFocusColor(Paint.valueOf("#40a85f"));
        initPromptText(text, "Введите наименование файла", "Наименование файла");
        mBaseValidatorDialog.setJFXTextFields(text);

        JFXButton button = new JFXButton("Сохранить");
        button.setRipplerFill(Paint.valueOf("#40a85f"));
        button.setPrefHeight(35.0);

        content.setBody(text);
        content.setActions(button);
        JFXDialog dialog = new JFXDialog(mFilesPane, content, JFXDialog.DialogTransition.TOP);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (mBaseValidatorDialog.validate()) {
                    DepartmentPresenter.get().editFile(text.getText());
                    dialog.close();
                }
            }
        });
        dialog.show();
    }

    @FXML
    private void onClickAdd() {
        DepartmentPresenter.get().uploadFiles(getStage());
    }

    @FXML
    private void selectFile(FileDumpModel file) {
        //new DepartmentPresenter().downloadOpenFile(file.getPath(),mTypeDocument);

    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if(updateClass.getName().equals(this.getClass().getName())){
        }
    }

    @Override
    protected Stage getStage() {
        return (Stage) mFilesPane.getScene().getWindow();
    }

    @Override
    public void destroy() {
        System.out.println("destroy files");
        ListenersService.get().removeListenerUI(this);
    }

    @Override
    public void primaryClick(String id) {
        switch (id) {
            case "saveFile":
                DepartmentPresenter.get().saveSelectedFile(getStage());
                break;
            case "renameFile":
                createDialog();
                break;
            case "mFileDumpList":
                System.out.println("double click");
                DepartmentPresenter.get().openSelectedFile();
                break;
        }
    }

}
