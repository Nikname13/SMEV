package Model.Type;

import Iteractor.IteractorType;
import Model.GenericList;

public class Types extends GenericList<TypeModel> {

    private static Types sTypes;

    public static Types get(){
        if(sTypes==null){
            sTypes=new Types();
            new IteractorType().loadData();
        }
        return sTypes;
    }

    @Override
    public void update() {
        clear();
        new IteractorType().loadData();
    }

    @Override
    public void replace(TypeModel entity) {
        TypeModel type=Types.get().getEntity(entity.getId());
        type.setName(entity.getName());
        type.setLoad(entity.isLoad());
        type.setEntityList(entity.getEntityList());
    }

}
