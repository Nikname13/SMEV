package Presenter;

import Iteractor.IteractorWorker;
import Model.Department.DepartmentModel;
import Model.Department.Departments;
import Model.Worker.WorkerModel;
import Model.Worker.Workers;
import Service.IUpdateData;
import Service.UpdateService;
import javafx.collections.ObservableList;

public class WorkerPresenter extends BasePresenter implements IUpdateData {

    private static WorkerModel sWorkerModel;
    private static WorkerPresenter sWorkerPresenter;

    private WorkerPresenter() {
        UpdateService.get().addListenerData(this);
    }

    public static WorkerPresenter get() {
        if (sWorkerPresenter == null) sWorkerPresenter = new WorkerPresenter();
        return sWorkerPresenter;
    }

    public WorkerModel getWorkerModel() {
        return sWorkerModel;
    }

    public void setWorkerModel(WorkerModel worker) {
        sWorkerModel = worker;
    }

    public ObservableList<WorkerModel> getObservableWorkers(){
        return Workers.get().getEntityList();
    }

    public ObservableList<DepartmentModel> getObservableDepartment(){
        return Departments.get().getEntityList();
    }

    public void addWorker(String name, String post, DepartmentModel department){
        UpdateService.get().updateData(new IteractorWorker().addNew(new WorkerModel(0, name, post, department)));
        UpdateService.get().updateControl(WorkerModel.class);
    }

    public void editWorker(String name, String post, DepartmentModel department){
        new IteractorWorker().edit(new WorkerModel(sWorkerModel.getId(), name, post, department));
    }

    public void deleteWorker(int id){
        new IteractorWorker().delete(id);
    }

    @Override
    public void update(Object equipment) {

    }

    @Override
    public void delete() {
        if (getSelectedObject() != null) {
            if (getSelectedObject().equals(sWorkerModel)) {
                System.out.println("delete " + sWorkerModel.getDepartmentModel().getId() + " hashCode " + sWorkerModel.hashCode());
                new IteractorWorker().delete(sWorkerModel);
                UpdateService.get().updateControl(WorkerModel.class);
            }
        }
    }
}
