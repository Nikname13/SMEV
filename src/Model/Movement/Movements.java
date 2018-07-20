package Model.Movement;

import Iteractor.IteractorMovement;
import Model.GenericList;

public class Movements extends GenericList<MovementModel> {

    private static Movements sMovements;

    public static Movements get(){
        if (sMovements==null){
            sMovements=new Movements();
            new IteractorMovement().loadData();
        }
        return sMovements;
    }

    @Override
    public void update() {
        clear();
        new IteractorMovement().loadData();
    }

    @Override
    public void replace(MovementModel entity) {
        MovementModel movement=Movements.get().getEntity(entity.getId());
        movement.setName(entity.getName());
        movement.setDate(entity.getDate());
        movement.setEntityList(entity.getEntityList());
        movement.setWorkerList(entity.getWorkerList());
        movement.setDepartmentList(entity.getDepartmentList());
    }
}
