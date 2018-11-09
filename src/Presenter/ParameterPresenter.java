package Presenter;

import Iteractor.IteractorParameter;
import Model.Parameter.ParameterModel;
import Model.Parameter.Parameters;
import Model.Parameter.SelectedParameterShell;
import Model.Parameter.ValueParameterModel;
import Service.IUpdateUI;
import Service.ListenersService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ParameterPresenter extends BasePresenter {

    private static ParameterModel sParameterModel;
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

    public ParameterModel getParameter() {
        return sParameterModel;
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

    public void setParameter(ParameterModel parameter) {
        sParameterModel = parameter;
        setSelectedObject(parameter);
    }

    public void addParameter(String name) {
        new IteractorParameter().addNew(new ParameterModel(0, name));
        ListenersService.get().updateControl(ParameterModel.class);
    }

    private void printList(ObservableList<ValueParameterModel> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getName());
        }
    }

    public void editParameter(ParameterModel parameter) {
        ListenersService.get().updateData(new IteractorParameter().edit(parameter));
    }

    @Override
    public void delete() {
        if (getSelectedObject() != null) {
            if (getSelectedObject().equals(sParameterModel)) {
                if (new IteractorParameter().delete(sParameterModel.getId())) {
                    ListenersService.get().updateControl(ParameterModel.class);
                }
            }
        }
    }
}
