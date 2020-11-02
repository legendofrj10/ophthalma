package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(600);

        loginController.login(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
