package Presenter;

import Iteractor.IteractorWorker;
import Model.Department.DepartmentModel;
import Model.Department.Departments;
import Model.Worker.WorkerModel;
import Model.Worker.Workers;
import javafx.collections.ObservableList;

public class WorkerPresenter {

    private static WorkerModel mWorker;

    public static WorkerModel getWorker() {
        return mWorker;
    }

    public static void setWorker(WorkerModel mWorker) {
        WorkerPresenter.mWorker = mWorker;
    }

    public ObservableList<WorkerModel> getObservableWorkers(){
        return Workers.get().getEntityList();
    }

    public ObservableList<DepartmentModel> getObservableDepartment(){
        return Departments.get().getEntityList();
    }

    public void addWorker(String name, String post, DepartmentModel department){
        new IteractorWorker().addNew(new WorkerModel(0, name, post, department));
    }

    public void editWorker(String name, String post, DepartmentModel department){
        new IteractorWorker().edit(new WorkerModel(mWorker.getId(), name, post, department));
    }

    public void deleteWorker(int id){
        new IteractorWorker().delete(id);
    }

    public void update(){
        Workers.get().update();
    }

}
