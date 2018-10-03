package Iteractor;

import Model.Post.PostModel;
import Model.Post.Posts;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class IteractorPost extends GenericIteractor<PostModel> {

    private static String sURI = "/post_servlet";

    public IteractorPost() {
        super(sURI, PostModel.class, new TypeToken<ArrayList<PostModel>>() {
        }.getType());
    }

    @Override
    public void setList(ObservableList<PostModel> list) {
        Collections.sort(list, Comparator.comparing(PostModel::getNameToLowerCase));
        Posts.get().setEntityList(list);
    }

    @Override
    public void setEntity(PostModel entity) {
        if (Posts.get().getEntity(entity.getId()) != null) {
            Posts.get().replace(entity);
        } else {
            Posts.get().addEntity(entity);
        }
    }
}
