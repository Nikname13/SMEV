package Iteractor;

import Model.Parameter.ParameterModel;
import Model.Parameter.Parameters;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class IteractorParameter extends GenericIteractor<ParameterModel> {

    private static String sURI = "/parameter_servlet";

    public IteractorParameter() {
        super(sURI, ParameterModel.class, new TypeToken<ArrayList<ParameterModel>>() {}.getType());
    }
    @Override
    public void setList(ObservableList<ParameterModel> list) {
        Collections.sort(list, Comparator.comparing(ParameterModel::getNameToLowerCase));
        Parameters.get().setEntityList(list);
    }

    @Override
    public void setEntity(ParameterModel entity) {
        entity.setLoad(true);
        entity.setValue(!entity.getEntityList().isEmpty());
        if (Parameters.get().getEntity(entity.getId()) != null) {
            Parameters.get().replace(entity);
        } else {
            Parameters.get().addEntity(entity);
        }
    }

    @Override
    public void deleteEntity(int id) {
        Parameters.get().deleteEntity(id);
    }
}
