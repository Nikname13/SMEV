package UI;

import Net.URLBuilder;
import Service.ErrorService;
import Service.IErrorMessage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.ErrorManager;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws IOException {
        createConfig();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("example.fxml"));
        Parent root = loader.load();
        primaryStage.setWidth(1300);
        primaryStage.setHeight(1000);
        primaryStage.setMaximized(true);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createConfig() throws IOException {
        File conf=new File("config_smevmv.txt");
        if(!conf.exists()) conf.createNewFile();
        BufferedReader br = null;
        FileReader fr = null;
        StringBuilder sb = new StringBuilder();
        try{
            fr = new FileReader(conf.getName());
            br = new BufferedReader(fr);
            String sCurrentLine;
            while((sCurrentLine=br.readLine())!=null){
                sb.append(sCurrentLine);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
            new URLBuilder().setURL(sb.toString());
    }

    public static void main(String[] args) {
        launch(args);
    }

}
