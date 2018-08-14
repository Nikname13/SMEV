package Model.Post;

import Iteractor.IteractorPost;
import Model.GenericList;

public class Posts extends GenericList<PostModel> {

    private static Posts sPosts;

    public static Posts get() {
        if (sPosts == null) {
            sPosts = new Posts();
            new IteractorPost().loadData();
        }
        return sPosts;
    }

    @Override
    public void update() {
        clear();
        new IteractorPost().loadData();
    }

    @Override
    public void replace(PostModel entity) {
        PostModel postModel = Posts.get().getEntity(entity.getId());
        postModel.setName(entity.getName());
    }
}
