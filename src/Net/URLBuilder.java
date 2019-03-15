package Net;

import java.util.ArrayList;
import java.util.List;

public class URLBuilder {
    private String mUrl;
    private List<String> mParams;
    private static String sURL;

    public URLBuilder(){
        mUrl="";
        mParams=new ArrayList<>();
    }

    public URLBuilder(String url){
        this();
        mUrl=url;
    }

    public void setURL(String URL) {
        sURL = URL;
    }

    public URLBuilder withParam(Object name, Object value) {
        if (name != null && value != null)
        mParams.add(name+"="+value);
        return this;
    }

    public String build(){
        return sURL +mUrl+"?"+String.join("&",mParams);
    }


}
