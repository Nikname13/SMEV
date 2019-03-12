package Presenter;

import Iteractor.IteractorPost;
import Iteractor.IteractorWorker;
import Model.Department.DepartmentModel;
import Model.Post.PostModel;
import Model.Worker.WorkerModel;
import Service.ListenersService;

public class WorkerPresenter extends BasePresenter {

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
        setSelectedObject(worker);
    }

    public void addWorker(String name, PostModel post, DepartmentModel department) {
        if (post.getId() == -1
                ) {
            post = addPost(post);
        }
        new IteractorWorker().addNew(new WorkerModel(0, name, post, department));
        ListenersService.get().updateControl(WorkerModel.class);
        // ListenersService.get().updateData();
        //  ListenersService.get().updateControl(WorkerModel.class);
    }

    public void editWorker(String name, PostModel post, DepartmentModel department) {
        if (post.getId() == 0) {
            post = addPost(post);
        }
        new IteractorWorker().edit(new WorkerModel(sWorkerModel.getId(), name, post, department));
        ListenersService.get().refreshControl(WorkerModel.class);
        //ListenersService.get().updateData(sWorkerModel);
        // ListenersService.get().updateData();
        // ListenersService.get().updateControl(WorkerModel.class);
    }

    public PostModel addPost(PostModel post) {
        return new IteractorPost().addNew(post);
    }

    @Override
    public void delete() {
        if (getSelectedObject() != null) {
            if (getSelectedObject().equals(sWorkerModel)) {
                if (new IteractorWorker().delete(sWorkerModel)) {
                    ListenersService.get().updateControl(WorkerModel.class);
                }
            }
        }
    }

}
