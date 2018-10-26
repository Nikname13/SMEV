package Model.Supply;

import Iteractor.IteractorSupply;
import Model.GenericList;
import javafx.collections.ObservableList;

public class Supplys extends GenericList<SupplyModel> {

    private static Supplys sSupplys;

    public static Supplys get(){
        if(sSupplys==null){
            sSupplys=new Supplys();
            return sSupplys;
        }
        return sSupplys;
    }

    @Override
    public ObservableList<SupplyModel> getObsEntityList() {
        new IteractorSupply().loadData();
        return super.getObsEntityList();
    }

    @Override
    public void update() {
        clear();
        new IteractorSupply().loadData();
    }

    @Override
    public void replace(SupplyModel entity) {
        SupplyModel supply = Supplys.get().getEntity(entity.getId());
        supply.setName(entity.getName());
        supply.setTypeSupply(entity.getTypeSupply());
        supply.setDate(entity.getDate());
        supply.setDescription(entity.getDescription());
        supply.setProviderModel(entity.getProviderModel());
        supply.setEntityList(entity.getEntityList());
    }
}
