package UI.Mains;


import UI.Coordinator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Controller {

    private static Stage sStage;

    @FXML
    private AnchorPane anchorPaneMain;

    @FXML
    private Button mParametrsButton;

    @FXML
    public void initialize(){

        System.out.println("init");

    }

    @FXML
    private void onClickParametrsButton(){
            new Coordinator().goToParametersWindow((Stage) anchorPaneMain.getScene().getWindow(),100.0,200.0);
    }

    @FXML
    private void  onClickStatesButton(){
        new Coordinator().goToStatesWindow((Stage) anchorPaneMain.getScene().getWindow(),100.0,200.0);
    }

    @FXML
    private void onClickAreasButton(){
        new Coordinator().goToAreaWindow((Stage) anchorPaneMain.getScene().getWindow(),100.0,200.0);
    }

    @FXML
    private void onClickWorkersButton(){
        new Coordinator().goToWorkersWindow((Stage) anchorPaneMain.getScene().getWindow(),100.0,200.0);
    }

    @FXML
    private void onClickTypesButton(){
        new Coordinator().goToTypesWindow((Stage) anchorPaneMain.getScene().getWindow(),100.0,200.0);
    }

    @FXML
    private void onClickSupplysButton(){
        new Coordinator().goToSupplysWindow((Stage)anchorPaneMain.getScene().getWindow(),100.0,200.0);
    }

    @FXML
    private void onClickEquipmentsButton(){
        new Coordinator().goToEquipmentsWindow((Stage)anchorPaneMain.getScene().getWindow(),100.0,200.0);
    }

    @FXML
    private void onClickAddInventoryNumber(){
        new Coordinator().goToAddInventoryNumberWindow((Stage) anchorPaneMain.getScene().getWindow(),100.0,200.0);
    }

    @FXML
    private void onClickDepartmentsButton(){
        new Coordinator().goToDepartmentsWindow((Stage)anchorPaneMain.getScene().getWindow(),100.0,200.0);
    }

    @FXML
    private void onClickMovementsButton(){
        new Coordinator().goToMovementsWindow((Stage)anchorPaneMain.getScene().getWindow(),100.0,200.0);
    }

    @FXML
    private void onClickProvidersButton(){
        new Coordinator().goToProvidersWindows((Stage)anchorPaneMain.getScene().getWindow(),100.0,200.0);
    }

    @FXML
    private void onClickInventoryNumbers(){
        new Coordinator().goToInventoryNumbersWindow((Stage)anchorPaneMain.getScene().getWindow(),100.0,200.0);
    }
    @FXML
    private void onClickMove(){
        new Coordinator().goToMoveEquipmentInventoryWindow((Stage) anchorPaneMain.getScene().getWindow());
    }

}
