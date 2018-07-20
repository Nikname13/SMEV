package Presenter;

import Iteractor.IteractorParameter;
import Iteractor.IteractorValueParameter;
import Model.Parameter.ParameterModel;
import Model.Parameter.Parameters;
import Model.Parameter.ValueParameterModel;
import Service.IUpdateUI;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ParametersPresenter  {

    private static ParameterModel mParameter;
    private static IUpdateUI updateListener;

    public void setParameter(Object parameter) {
        this.mParameter = (ParameterModel) parameter;
    }

    public void addListener(IUpdateUI listener) {
        updateListener = listener;
    }

    public ParameterModel getParameter() {
        return mParameter;
    }

    public ObservableList<ParameterModel> getObservableParameters() {
        return Parameters.get().getEntityList();
    }

    public void addParameter(String name, boolean isValue, List<Object> valuesParameter) {
        System.out.println("Name= " + name + "\nisValue= " + isValue + "\nValues=" + valuesParameter);
        if (isValue) {
            List<ValueParameterModel> values = new ArrayList<>();
            for (int i = 0; i < valuesParameter.size(); i++) {
                values.add(new ValueParameterModel(0, (String) valuesParameter.get(i)));
            }
            new IteractorParameter().addNew(new ParameterModel(0, name, values, isValue));
        } else new IteractorParameter().addNew(new ParameterModel(0, name, null, isValue));
    }

    private void printList(ObservableList<ValueParameterModel> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getName());
        }
    }

    public void editParameter(String name, boolean isValue, List<Object> editValue, List<Object> deleteValue) {
        //mParameter.setLoad(false);
        if (isValue) {
            List<ValueParameterModel> values = new ArrayList<>();
            if (mParameter.isValue()) {
                System.out.println("getValueList!=null");
/*                for (int i = 0; i < editValue.size(); i++) {
                    values.add((ValueParameterModel) editValue.get(i));
                    //mParameter.getValuesList().remove(values.get(i));
                }*/
                values=mParameter.getEntityList();
               for (int i = 0; i < deleteValue.size(); i++) {
                    values.remove(deleteValue.get(i));
                }
                for(ValueParameterModel value: values){
                    System.out.println(value.getName());
                }
            } else {
                for (int i = 0; i < editValue.size(); i++) {
                    values.add(new ValueParameterModel(0, (String) editValue.get(i)));
                }
            }
            new IteractorParameter().edit(new ParameterModel(mParameter.getId(), name, values, isValue));
        }else {
            new IteractorParameter().edit(new ParameterModel(mParameter.getId(), name, null, isValue));
        }
        System.out.println("editParameter");
        update();
    }

    public void deleteParameters(int id) {
        new IteractorParameter().delete(id);
        update();
    }

    public void deleteValueParameter(Set<Integer> idList) {
        new IteractorValueParameter().delete(idList);
        for(Integer id:idList) {
            mParameter.deleteEntity(id);
        }

    }

    public void update() {
        Parameters.get().update();
    }
}
