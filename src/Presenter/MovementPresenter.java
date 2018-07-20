package Presenter;

import Model.Movement.MovementModel;
import Model.Movement.Movements;
import javafx.collections.ObservableList;

public class MovementPresenter {
    private static MovementModel sMovementModel;

    public MovementModel getMovementModel() {
        return sMovementModel;
    }

    public void setMovementModel(MovementModel movementModel) {
        sMovementModel = movementModel;
    }

    public ObservableList<MovementModel> getObservableMovement(){
        return Movements.get().getEntityList();
    }


}
