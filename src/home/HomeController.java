package home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Wasiq on 12/23/2018.
 */
public class HomeController implements Initializable {

@FXML
    Button dashboard,students,employees,details,logout;

    @FXML
    private void go(ActionEvent actionEvent){
        if(actionEvent.getSource().equals(dashboard)){
            loadstage("/fxml/dashboard.fxml",dashboard,1200,800);
        }
        else if(actionEvent.getSource().equals(students)){
            loadstage("/fxml/students.fxml",students,1200,800);
        }
        else if(actionEvent.getSource().equals(employees)){
            loadstage("/fxml/employees.fxml",employees,1200,800);
        }
        else if(actionEvent.getSource().equals(details)){
            loadstage("/fxml/details.fxml",details,1200,800);
        }
        else if(actionEvent.getSource().equals(logout)){
            loadstage("/fxml/login.fxml",logout,1043,579);
        }



    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    private void loadstage(String fxml,Button btn ,int c,int b) {
        try{
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Hostel Managment");
        primaryStage.setScene(new Scene(root, c, b));
            primaryStage.resizableProperty().setValue(Boolean.FALSE);


        primaryStage.show();
        primaryStage= (Stage) btn.getScene().getWindow();
     primaryStage.close();
    } catch (IOException e) {

    }
    }

}
