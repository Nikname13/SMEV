package UI.Movement.Controller;

import Model.Movement.MovementModel;
import Model.Movement.Movements;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MovementsController {

    @FXML
    private TableView<MovementModel> tableViewMovement;

    @FXML
    private TableColumn<MovementModel,String> firstDepartmentColumn, secondDepartmentColumn, baseColumn;

}
