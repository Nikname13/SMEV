package Net;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;


public class Connector {



    private static HttpURLConnection connection(String uri, String method){

        try {
            URL url = new URL(uri);
            HttpURLConnection connect=(HttpURLConnection) url.openConnection();
            connect.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            //connect.setUseCaches(false);
            connect.setAllowUserInteraction(true);
            connect.setDoOutput(true);
            connect.setDoInput(true);
            connect.setRequestMethod(method);

            return connect;
        }catch (IOException ex){
            System.out.println("Exception connection "+ex);
        }
        return null;
    }

    private static String getJSON(HttpURLConnection connect){
        try {
            //int status = connect.getResponseCode();
            // System.out.println(status);
            System.out.println(" connect encoding ");
            BufferedReader br = new BufferedReader(new InputStreamReader(connect.getInputStream(),"UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            System.out.println("out "+sb.toString());
            return sb.toString();
        } catch (IOException ex) {
            System.out.println("Нет связи с сервером");
            System.out.println("Exception getJSON " + ex);
            return null;
        }
    }

    private static File getFile(HttpURLConnection connect, File file){
        try {
            ReadableByteChannel rbc = Channels.newChannel(connect.getInputStream());
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            long filePosition = 0;
            long transferedBytes = fileOutputStream.getChannel().transferFrom(rbc, filePosition, Long.MAX_VALUE);
            while(transferedBytes == Long.MAX_VALUE){
                filePosition += transferedBytes;
                transferedBytes = fileOutputStream.getChannel().transferFrom(rbc, filePosition, Long.MAX_VALUE);
            }
            rbc.close();
            fileOutputStream.close();
            return file;
        }catch (IOException ex){
            System.out.println(ex);
            return null;
        }
    }

    public static String get(String uri){
        System.out.println("GET "+uri);
        HttpURLConnection connect=connection(uri,"GET");

        if(connect!=null) {
                String json=getJSON(connect);
                return json;
            }
                connect.disconnect();
                return "";
        }

    public static File get(String uri, File file){
        HttpURLConnection connect=connection(uri,"GET");
            System.out.println("Content-disposition "+connect.getHeaderField("Content-disposition"));
            return getFile(connect,file);
    }

    public static String post(String uri, String json){
        System.out.println("POST "+json);
        HttpURLConnection connect=connection(uri,"POST");
        if(connect != null) {
            try {
                OutputStream os = connect.getOutputStream();
                os.write(json.getBytes("UTF-8"));
                os.close();
                String response = getJSON(connect);
                connect.disconnect();
                return response;
            } catch (IOException ex) {
                System.out.println("Exception POST " + ex);
            }
        }
        System.out.println("Нет связи с сервером");
            return null;
    }

    public static int delete(String uri){
        System.out.println("DELETE ");
        HttpURLConnection connect = connection(uri,"DELETE");
        if(connect != null) {
            try {
                int responseCode=connect.getResponseCode();
                System.out.println(responseCode+" "+connect.getResponseMessage());
                connect.disconnect();
                return responseCode;
            } catch (IOException ex) {
                System.out.println("Exception DELETE " + ex);
            }
        }
        return 0;
    }

    public static String put(String uri,String json){
        System.out.println("PUT "+json);
        HttpURLConnection connect=connection(uri,"PUT");
        if(connect != null){
            try {
                OutputStream os = connect.getOutputStream();
                os.write(json.getBytes("UTF-8"));
                os.close();
                String response = getJSON(connect);
                connect.disconnect();
                int responseCode=connect.getResponseCode();
                System.out.println(responseCode+" "+connect.getResponseMessage());
                return response;
            }catch (Exception ex){
                System.out.println("Exception PUT "+ex);
            }
        }
        return null;
    }

    static String CRLF = "\r\n";
    static String boundary = "------------------------" + Long.toHexString(System.currentTimeMillis());
    String charset = "UTF-8";

    public static String post(String uri, List<File> uploadFiles ) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection connect=(HttpURLConnection) url.openConnection();
        connect.setRequestMethod("POST");
        connect.setRequestProperty("Connection", "Keep-Alive");
        connect.setRequestProperty("Cache-Control", "no-cache");
        connect.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
        //connect.setUseCaches(false);
        //connect.setAllowUserInteraction(true);
        connect.setDoOutput(true);
        connect.setDoInput(true);

        DataOutputStream outputStream=new DataOutputStream(connect.getOutputStream());
        for(File file:uploadFiles) {
            outputStream.writeBytes("--" + boundary + CRLF);
            outputStream.writeBytes("Content-Disposition: form-data;name=\"documents\";filename=\"" + file.getName() + "\"" + CRLF);
            //outputStream.writeBytes("Content-Type: text/plane"+CRLF);
            outputStream.writeBytes(CRLF);
            byte[] bytes = Files.readAllBytes(file.toPath());
            outputStream.write(bytes);
            outputStream.writeBytes(CRLF);
        }
        outputStream.writeBytes("--"+boundary+"--"+CRLF);
        outputStream.flush();
        outputStream.close();
        System.out.println(connect.getResponseMessage());

        String response=getJSON(connect);
        connect.disconnect();
        return response;
    }



}
