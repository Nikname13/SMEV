package Net;

import Model.Parameter.ParameterModel;
import Model.Parameter.ValueParameterModel;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Deserializer<T> implements JsonDeserializer<T> {

    private String mKey;

    public Deserializer(String key) {
        mKey = key;
    }

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        System.out.println("jsonElement="+jsonElement+"type="+type+"context="+jsonDeserializationContext);
        JsonElement content=jsonElement.getAsJsonObject().getAsJsonArray(mKey);
        System.out.println("Gson deserialize"+content);
        return new Gson().fromJson(content,type);
    }
}
