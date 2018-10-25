package Iteractor;

import Model.Parameter.ParameterModel;
import Model.Parameter.Parameters;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;

public class IteractorParameter extends GenericIteractor<ParameterModel> {

    private static String sURI = "/parameter_servlet";

    public IteractorParameter() {
        super(sURI, ParameterModel.class, new TypeToken<ArrayList<ParameterModel>>() {}.getType());
    }
    @Override
    public void setList(ObservableList<ParameterModel> list) {
        Parameters.get().setEntityList(list, Comparator.comparing(ParameterModel::getNameToLowerCase));
    }

    @Override
    public void setEntity(ParameterModel entity) {
        entity.setLoad(true);
        entity.setValue(!entity.getEntityList().isEmpty());
        if (Parameters.get().getEntity(entity.getId()) != null) {
            Parameters.get().replace(entity);
        } else {
            Parameters.get().addEntity(entity, Comparator.comparing(ParameterModel::getNameToLowerCase));
        }
    }

    @Override
    public void deleteEntity(int id) {
        Parameters.get().deleteEntity(id);
    }
}
