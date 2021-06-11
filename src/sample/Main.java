package sample;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        common.mainStage = primaryStage;
        loginController.init(primaryStage);
    }


    public static void main(String[] args) {
        try {
            directoryInitialize();
        } catch (IOException e) {
            e.printStackTrace();
        }

        launch(args);
    }



    public static void directoryInitialize() throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        if(os.startsWith("windows")){
            Files.createDirectories(Paths.get("C:\\.ospitality"));

            common.setWorkingDirectory("C:\\.ospitality\\");


        }else{
            Runtime.getRuntime().exec("mkdir "+common.getWorkingDirectory()+".ospitality").destroy();
            common.setWorkingDirectory(".ospitality/");
            Runtime.getRuntime().exec("touch "+common.getWorkingDirectory()+"dbUName").destroy();
            Runtime.getRuntime().exec("touch "+common.getWorkingDirectory()+"dbPass").destroy();
            Runtime.getRuntime().exec("touch "+common.getWorkingDirectory()+"ip").destroy();
        }
    }
}
