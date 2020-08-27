package home;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    Image logo,user,lock;

    @FXML
    Button close,login;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/login.fxml"));
        primaryStage.setTitle("Hostel Managment");
        primaryStage.setScene(new Scene(root, 1043, 579));
        primaryStage.initStyle(StageStyle.UNDECORATED);


        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);



    }

    }

