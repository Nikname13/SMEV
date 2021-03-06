package Iteractor;

import Model.Supply.SupplyModel;
import Model.Supply.Supplys;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;

public class IteractorSupply extends GenericIteractor<SupplyModel>  {

    private static String sURL="/supply_servlet";
    private static String sLoadFileURL = "/load_supply_servlet";

    public IteractorSupply() {
        super(sURL,SupplyModel.class, new TypeToken<ArrayList<SupplyModel>>(){}.getType());
    }

    @Override
    public void setList(ObservableList<SupplyModel> list) {
        Supplys.get().setEntityList(list, Comparator.comparing(SupplyModel::getNameToLowerCase));
    }

    @Override
    public void setEntity(SupplyModel entity) {
        if(Supplys.get().getEntity(entity.getId())!=null){
            Supplys.get().replace(entity);
        }else{
            Supplys.get().addEntity(entity, Comparator.comparing(SupplyModel::getNameToLowerCase));
        }
    }

    @Override
    public String getLoadFileURL() {
        return sLoadFileURL;
    }

    @Override
    public void deleteEntity(int id) {
        Supplys.get().deleteEntity(id);
    }
}
