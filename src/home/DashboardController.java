package home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Created by Wasiq on 12/23/2018.
 */
public class DashboardController implements Initializable {
@FXML
Button back,floors,rooms,teams,event,change;
    @FXML
    Label totalstudent,totalfloor,totalroom,totalstaff;
    int c=1200,d=800,e=1600;



    @FXML
    private void back(){
        loadstage("/fxml/home.fxml",back,c,d);

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
        else if(actionEvent.getSource().equals(event)){
            loadstage("/fxml/event.fxml",event,c,d);
        }
        else if(actionEvent.getSource().equals(change)){
            loadstage("/fxml/changepass.fxml",change,c,d);
        }



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        change.setVisible(false);
        try {
            Connection con=DB.getConnection();
            ResultSet rs=con.createStatement().executeQuery("select count(*) from studenttbl");
            ResultSet rs2=con.createStatement().executeQuery("select count(*) from floortbl");
            ResultSet rs3=con.createStatement().executeQuery("select count(*) from roomtbl");
            ResultSet rs4=con.createStatement().executeQuery("select count(*) from stafftbl");

            int total=0;
            int ftotal=0;
            int rtotal=0;
            int stotal=0;
            while (rs.next()){
                 total=rs.getInt(1);
            }
            while(rs2.next()){
                ftotal=rs2.getInt(1);
            }
            while (rs3.next()){
                rtotal=rs3.getInt(1);
            }
            while(rs4.next()){
                stotal=rs4.getInt(1);
            }
            totalfloor.setText(""+ftotal);
            totalroom.setText(""+rtotal);
            totalstudent.setText(""+total);
            totalstaff.setText(""+stotal);
            con.close();



        }catch (Exception e){

        }



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
