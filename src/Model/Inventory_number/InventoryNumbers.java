package Model.Inventory_number;

import Iteractor.IteractorInventoryNumber;
import Model.GenericList;

public class InventoryNumbers extends GenericList<InventoryNumberModel>{

    private static InventoryNumbers sInventoryNumbers;

    public static InventoryNumbers get(){
        if(sInventoryNumbers==null) {
        sInventoryNumbers=new InventoryNumbers();
        new IteractorInventoryNumber().loadData();
        }
        return sInventoryNumbers;
    }

    @Override
    public void update() {
        clear();
        new IteractorInventoryNumber().loadData();
    }

    @Override
    public void replace(InventoryNumberModel entity) {
        InventoryNumberModel number= InventoryNumbers.get().getEntity(entity.getId());
        number.setName(entity.getName());
        number.setGroup(entity.isGroup());
        number.setDescription(entity.getDescription());
        number.setSupply(entity.getSupply());
    }
}
