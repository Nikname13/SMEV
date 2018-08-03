package UI.Equipment.Controller;

import Model.Equipment.EquipmentParameterModel;
import Model.Parameter.ParameterModel;
import Model.Type.TypeModel;
import Presenter.EquipmentPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;


public class AddEquipmentController {

    private String mConfig;
    private Object mType;
    private List<EquipmentParameterModel> mValue;

    @FXML
    private TextField textFieldName, textFieldNameFact;

    @FXML
    private TextArea textAreaDescription;

    @FXML
    private Button buttonConfig;

    @FXML
    private ComboBox<TypeModel> comboBoxType;

    @FXML
    private TableView<ParameterModel> tableViewParameter;

    @FXML
    private TableColumn<ParameterModel,String> columnNameType;

    @FXML
    private Label labelConfig;

    @FXML
    public void initialize(){
        mValue=new ArrayList<>();
        comboBoxType.setCellFactory(p->new ListCell<TypeModel>(){
            @Override
            protected void updateItem(TypeModel type,boolean empty){
                super.updateItem(type,empty);
                if(type!=null && !empty){
                    setText(type.getName());
                }else{
                    setText(null);
                }
            }
        });
        comboBoxType.setItems(EquipmentPresenter.get().getObservableType());
        comboBoxType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedType(newValue));
        columnNameType.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        tableViewParameter.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedParameter(newValue));
    }

    private void selectedType(Object type){
        tableViewParameter.setItems(((TypeModel) type).getObservableEntityList());
        mType=type;
    }

    private void selectedParameter(Object parameter){
        mValue.add(new EquipmentParameterModel(0,"new value"+((ParameterModel)parameter).getId(),(ParameterModel)parameter));
    }

    @FXML
    private void onClickButtonConfig(){
        labelConfig.setText("configNew//....");
        mConfig=labelConfig.getText();
    }

    @FXML
    private void onClickAdd(){
        EquipmentPresenter.get().addEquipment(
                textFieldName.getText(),
                textFieldNameFact.getText(),
                textAreaDescription.getText(),
                mConfig,
                mType,
                mValue
        );
    }
}
