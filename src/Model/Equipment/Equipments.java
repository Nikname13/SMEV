package Model.Equipment;

import Iteractor.IteractorEquipment;
import Model.GenericList;
import javafx.collections.ObservableList;

public class Equipments extends GenericList<EquipmentModel> {

    private static Equipments sEquipments;

    public static Equipments get(){
        if(sEquipments==null){
            sEquipments=new Equipments();
            return sEquipments;
        }
        return sEquipments;
    }

    @Override
    public ObservableList<EquipmentModel> getObsEntityList() {
        new IteractorEquipment().loadData();
        return super.getObsEntityList();
    }

    @Override
    public void update() {
        clear();
        new IteractorEquipment().loadData();
    }

    @Override
    public void replace(EquipmentModel entity) {
        EquipmentModel model=Equipments.get().getEntity(entity.getId());
        model.setLoad(entity.isLoad());
        model.setName(entity.getName());
        model.setNameFact(entity.getNameFact());
        model.setTypeModel(entity.getTypeModel());
        model.setDescription(entity.getDescription());
        model.setEquipmentInventoryList(entity.getEquipmentInventoryList());
        model.setEntityList(entity.getEntityList());
    }
}
