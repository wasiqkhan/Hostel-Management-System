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
 * Created by Wasiq on 12/28/2018.
 */
public class EventController implements Initializable {

    @FXML
    Button back,floors,rooms,teams,dashboard,change;
    int c=1200,d=800,e=1600;

    @FXML
    private void back(){
        loadstage("/fxml/home.fxml",back ,c,d);

    }
    @FXML
    private void go(ActionEvent actionEvent){
        if(actionEvent.getSource().equals(floors)){
            loadstage("/fxml/floor.fxml",floors,c,d);
        }
        else if(actionEvent.getSource().equals(rooms)){
            loadstage("/fxml/room.fxml",rooms,c,d);
        }
        else if(actionEvent.getSource().equals(teams)){
            loadstage("/fxml/team.fxml",teams,e,d);
        }
        else if(actionEvent.getSource().equals(dashboard)){
            loadstage("/fxml/dashboard.fxml",dashboard,c,d);
        }
        else if(actionEvent.getSource().equals(change)){
            loadstage("/fxml/changepass.fxml",change,c,d);
        }



    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        change.setVisible(false);
    }
    private void loadstage(String fxml,Button btn ,int a,int b) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Hostel Managment");
            primaryStage.setScene(new Scene(root, a, b));
            primaryStage.resizableProperty().setValue(Boolean.FALSE);




            primaryStage.show();
            primaryStage= (Stage) btn.getScene().getWindow();
            primaryStage.close();
        } catch (IOException e) {

        }
    }
}
