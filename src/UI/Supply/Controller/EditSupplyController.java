package UI.Supply.Controller;

import Model.Provider.ProviderModel;
import Model.Supply.SupplyModel;
import Presenter.SupplyPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class EditSupplyController {

    private SupplyModel mSupplyModel;
    private String mTypeSupply;
    private Object mProvider;

    @FXML
    private RadioButton supplyButton,auctionButton;

    @FXML
    private ComboBox<ProviderModel> providerBox;

    @FXML
    private TextField numberTextField;

    @FXML
    private TextArea textAreaDescription;

    @FXML
    private Label documentationLabel;

    public EditSupplyController(){
        mSupplyModel= new SupplyPresenter().getSupplyModel();
    }

    @FXML
    public void initialize(){
        ToggleGroup group=new ToggleGroup();
        supplyButton.setToggleGroup(group);
        auctionButton.setToggleGroup(group);
        providerBox.setCellFactory(p->new ListCell<ProviderModel>(){
            @Override
            protected void updateItem(ProviderModel provider,boolean empty){
                super.updateItem(provider,empty);
                if(provider!=null && !empty){
                    setText(provider.getName());
                }else{
                    setText(null);
                }
            }
        });
        providerBox.setItems(new SupplyPresenter().getObservableProvider());
        providerBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->providerListener(newValue) );
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> selectedButton(newValue));

        numberTextField.setText(mSupplyModel.getName());
        textAreaDescription.setText(mSupplyModel.getDescription());
        documentationLabel.setText(mSupplyModel.getDocumentation());
    }

    private void providerListener(Object provider){
        mProvider=provider;
        System.out.println(((ProviderModel)provider).getId());
    }

    private void selectedButton(Object o){
        mTypeSupply=((RadioButton)o).getText();
        System.out.println(mTypeSupply);
    }

    @FXML
    private void onClickEdit(){
        new SupplyPresenter().editSupply(numberTextField.getText(), mTypeSupply, LocalDate.now(), textAreaDescription.getText(), documentationLabel.getText(),(ProviderModel) mProvider);
    }

    @FXML
    private void onClickDelete(){
        new SupplyPresenter().deleteSupply(mSupplyModel.getId());
    }

}
