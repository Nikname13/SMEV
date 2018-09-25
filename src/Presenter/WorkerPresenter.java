package Presenter;

import Iteractor.IteractorPost;
import Iteractor.IteractorWorker;
import Model.Department.DepartmentModel;
import Model.Post.PostModel;
import Model.Worker.WorkerModel;
import Service.IUpdateData;
import Service.ListenersService;

public class WorkerPresenter extends BasePresenter implements IUpdateData {

    private static WorkerModel sWorkerModel;
    private static WorkerPresenter sWorkerPresenter;

    private WorkerPresenter() {
        ListenersService.get().addListenerData(this);
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

    public void addWorker(String name, PostModel post, DepartmentModel department) {
        if (post.getId() == 0) {
            post = addPost(post);
        }
        ListenersService.get().updateData(new IteractorWorker().addNew(new WorkerModel(0, name, post, department)));
        ListenersService.get().updateControl(WorkerModel.class);
    }

    public void editWorker(String name, PostModel post, DepartmentModel department) {
        if (post.getId() == 0) {
            post = addPost(post);
        }
        ListenersService.get().updateData(sWorkerModel);
        ListenersService.get().updateData(new IteractorWorker().edit(new WorkerModel(sWorkerModel.getId(), name, post, department)));
        ListenersService.get().updateControl(WorkerModel.class);
    }

    public PostModel addPost(PostModel post) {
        return new IteractorPost().addNew(post);
    }

    public void deleteWorker(int id) {
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
                ListenersService.get().updateControl(WorkerModel.class);
            }
        }
    }

    @Override
    void loadEntity(int id) {

    }
}
