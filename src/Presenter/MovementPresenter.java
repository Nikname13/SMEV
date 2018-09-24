package Presenter;

import Iteractor.IteractorMovement;
import Model.Movement.MovementModel;
import Model.Movement.Movements;
import Service.IUpdateData;
import Service.LisenersService;
import javafx.collections.ObservableList;

public class MovementPresenter extends BasePresenter implements IUpdateData {
    private static MovementPresenter sMovementPresenter;
    private static MovementModel sMovementModel;

    private MovementPresenter() {
        LisenersService.get().addListenerData(this);
    }

    public static MovementPresenter get() {
        if (sMovementPresenter == null) {
            sMovementPresenter = new MovementPresenter();
        }
        return sMovementPresenter;
    }

    public MovementModel getMovementModel() {
        return sMovementModel;
    }

    public void setMovementModel(MovementModel movementModel) {
        sMovementModel = movementModel;
    }

    public ObservableList<MovementModel> getObservableMovement(){
        return Movements.get().getEntityList();
    }


    @Override
    public void loadEntity(int id) {
        new IteractorMovement().loadData(id);
    }

    @Override
    public void update(Object equipment) {

    }

    @Override
    public void delete() {

    }
}
