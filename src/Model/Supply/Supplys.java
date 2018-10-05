package Model.Supply;

import Iteractor.IteractorSupply;
import Model.GenericList;

public class Supplys extends GenericList<SupplyModel> {

    private static Supplys sSupplys;

    public static Supplys get(){
        if(sSupplys==null){
            sSupplys=new Supplys();
            new IteractorSupply().loadData();
            return sSupplys;
        }
        return sSupplys;
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
