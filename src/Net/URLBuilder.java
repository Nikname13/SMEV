package Net;

import java.util.ArrayList;
import java.util.List;

public class URLBuilder {
    private String mUrl;
    private List<String> mParams;
    private String URL="http://10.13.96.34:8080/Smev-server";

    public URLBuilder(){
        mUrl="";
        mParams=new ArrayList<>();
    }

    public URLBuilder(String url){
        this();
        mUrl=url;
    }

    public URLBuilder withParam(String name, String value){
        mParams.add(name+"="+value);
        return this;
    }

    public String build(){
        return URL+mUrl+"?"+String.join("&",mParams);
    }


}
