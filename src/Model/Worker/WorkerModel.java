package Model.Worker;

import Model.AbstractModel;
import Model.Department.DepartmentModel;
import Model.Post.PostModel;

public class WorkerModel extends AbstractModel {

    private DepartmentModel mDepartmentModel;
    private PostModel mPost;

    public WorkerModel(int id, String name, PostModel post, DepartmentModel department) {
        super(id,name);
        mPost = post;
        setDepartmentModel(department);
    }

    public WorkerModel(int id, String name) {
        super(id, name);
    }

    public WorkerModel() {
    }

    @Override
    public AbstractModel<?> create(String name) {
        return new WorkerModel(-1, name);
    }

    public DepartmentModel getDepartmentModel() {
        return mDepartmentModel;
    }

    public void setDepartmentModel(DepartmentModel departmentModel) {
        mDepartmentModel = departmentModel;
    }

    public PostModel getPost() {
        return mPost;
    }

    public void setPost(PostModel post) {
        mPost = post;
    }
}
