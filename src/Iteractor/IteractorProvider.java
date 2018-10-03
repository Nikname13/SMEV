package Iteractor;

import Model.Provider.ProviderModel;
import Model.Provider.Providers;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class IteractorProvider extends GenericIteractor<ProviderModel>  {

    private static String sURL="/provider_servlet";

    public IteractorProvider() {
        super(sURL, ProviderModel.class, new TypeToken<ArrayList<ProviderModel>>(){}.getType());
    }

    @Override
    public void setList(ObservableList<ProviderModel> list) {
        Collections.sort(list, Comparator.comparing(ProviderModel::getNameToLowerCase));
        Providers.get().setEntityList(list);
    }

    @Override
    public void setEntity(ProviderModel entity) {
        if(Providers.get().getEntity(entity.getId())!= null) {
            Providers.get().replace(entity);
        }else{
            Providers.get().addEntity(entity);
        }
    }

    @Override
    public void deleteEntity(int id) {
        Providers.get().deleteEntity(id);
    }
}
