package Model.Post;

import Model.AbstractModel;

public class PostModel extends AbstractModel {

    public PostModel(int id, String name) {
        super(id, name);
    }

    public PostModel() {
    }

    @Override
    public AbstractModel<?> create(String name) {
        return new PostModel(-1, name);
    }
}
