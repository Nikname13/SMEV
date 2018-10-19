package Presenter;

import Iteractor.IteractorParameter;
import Model.Parameter.ParameterModel;
import Model.Parameter.Parameters;
import Model.Parameter.SelectedParameterShell;
import Model.Parameter.ValueParameterModel;
import Service.IUpdateData;
import Service.IUpdateUI;
import Service.ListenersService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public ObservableList<SelectedParameterShell> getParameterList(ObservableList<ParameterModel> listExcluding){
        ObservableList<SelectedParameterShell> list=FXCollections.observableArrayList();
        if(listExcluding!=null){
            boolean flag=false;
            for(ParameterModel parameter: Parameters.get().getObsEntityList()){
                for(ParameterModel parameterExcluding:listExcluding){
                    if(parameter.getId()==parameterExcluding.getId()){
                        flag=true;
                        break;
                    }
                }
                if(!flag){
                    list.add(new SelectedParameterShell(parameter));
                }
                flag=false;
            }
            return list;
        }else{
            for(ParameterModel parameter:Parameters.get().getObsEntityList()){
                list.add(new SelectedParameterShell(parameter));
            }
            return list;
        }
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

    public void editParameter(ParameterModel parameter) {
        new IteractorParameter().edit(parameter);
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
