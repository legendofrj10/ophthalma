package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.loginController;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);

        loginController.login(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
