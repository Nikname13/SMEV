package Presenter;

import Iteractor.IteractorParameter;
import Model.Parameter.ParameterModel;
import Model.Parameter.ValueParameterModel;
import Service.IUpdateData;
import Service.IUpdateUI;
import Service.ListenersService;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ParameterPresenter extends BasePresenter implements IUpdateData {

    private static ParameterModel mParameter;
    private static IUpdateUI updateListener;
    private static ParameterPresenter sParameterPresenter;

    public static ParameterPresenter get(){
        if(sParameterPresenter ==null){
            sParameterPresenter =new ParameterPresenter();
        }
        return sParameterPresenter;
    }

    private ParameterPresenter(){
        ListenersService.get().addListenerData(this);
    }

    public void setParameter(Object parameter) {
        mParameter = (ParameterModel) parameter;
    }

    public void addListener(IUpdateUI listener) {
        updateListener = listener;
    }

    public ParameterModel getParameter() {
        return mParameter;
    }

    public void addParameter(String name) {
        new IteractorParameter().addNew(new ParameterModel(0, name));
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
    }

    @Override
    void loadEntity(int id) {

    }

    @Override
    public void update(Object equipment) {

    }

    @Override
    public void delete() {

    }
}
