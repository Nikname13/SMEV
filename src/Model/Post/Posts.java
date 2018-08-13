package Model.Post;

import Model.GenericList;

public class Posts extends GenericList<PostModel> {

    private static Posts sPosts;

    public static Posts get() {
        if (sPosts == null) {
            sPosts = new Posts();
        }
        return sPosts;
    }

    @Override
    public void update() {
        clear();
    }

}
