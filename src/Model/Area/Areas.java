package Model.Area;

import Iteractor.IteractorArea;
import Model.GenericList;

import java.util.Comparator;

public class Areas extends GenericList<AreaModel> {

    private static Areas sArea;

    public static Areas get()
    {
        if(sArea==null) {
            sArea=new Areas();
            new IteractorArea().loadData();
        }
        return sArea;
    }


    @Override
    public void update() {
        clear();
        new IteractorArea().loadData();
    }

    @Override
    public void replace(AreaModel entity) {
        AreaModel area= Areas.get().getEntity(entity.getId());
        Areas.get().getObsEntityList().remove(area);
        Areas.get().addEntity(entity, Comparator.comparing(AreaModel::getNameToLowerCase));

    }
}
