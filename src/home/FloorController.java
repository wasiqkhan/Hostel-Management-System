package home;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Floor;
import model.Staff;
import model.Student;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Created by Wasiq on 12/28/2018.
 */
public class FloorController implements Initializable {
    @FXML
    Button back,dashboard,rooms,teams,event,change;
    @FXML
    TableView<Floor> basement;
    @FXML
    TableView<Floor> firstfloor;
    @FXML
    TableView<Floor> secondfloor;
    @FXML
    TableView<Floor> thirdfloor;
    @FXML
    TableColumn<Floor,Integer> broom;
    @FXML
    TableColumn<Floor,Integer> bcap;
    @FXML
    TableColumn<Floor,String > bfloor;


    @FXML
    TableColumn<Floor,Integer> froom;
    @FXML
    TableColumn<Floor,Integer> fcap;
    @FXML
    TableColumn<Floor,String > ffloor;


    @FXML
    TableColumn<Floor,Integer> sroom;
    @FXML
    TableColumn<Floor,Integer> scap;
    @FXML
    TableColumn<Floor,String > sfloor;


    @FXML
    TableColumn<Floor,Integer> troom;
    @FXML
    TableColumn<Floor,Integer> tcap;
    @FXML
    TableColumn<Floor,String > tfloor;

    ObservableList<Floor> basedata= FXCollections.observableArrayList();
    ObservableList<Floor> firstdata= FXCollections.observableArrayList();
    ObservableList<Floor> seconddata= FXCollections.observableArrayList();
    ObservableList<Floor> thirddata= FXCollections.observableArrayList();
    int c=1200,d=800,e=1600;












    @FXML
    private void back(){
        loadstage("/fxml/home.fxml",back,c,d);

    }
    @FXML
    private void go(ActionEvent actionEvent){
        if(actionEvent.getSource().equals(dashboard)){
            loadstage("/fxml/dashboard.fxml",dashboard,c,d);
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
        try{
            Connection con=DB.getConnection();
            ResultSet rs=con.createStatement().executeQuery("select fname,rid,rcap from floortbl inner join roomtbl on floortbl.fid=roomtbl.fid where fname='Basement'");
            ResultSet rs1=con.createStatement().executeQuery("select fname,rid,rcap from floortbl inner join roomtbl on floortbl.fid=roomtbl.fid where fname='First'");
            ResultSet rs2=con.createStatement().executeQuery("select fname,rid,rcap from floortbl inner join roomtbl on floortbl.fid=roomtbl.fid where fname='Second'");
            ResultSet rs3=con.createStatement().executeQuery("select fname,rid,rcap from floortbl inner join roomtbl on floortbl.fid=roomtbl.fid where fname='Third'");

            while (rs.next()){
                basedata.add(new Floor(rs.getInt("rid"), rs.getString("fname"), rs.getInt("rcap")));

            }
            while (rs1.next()){
                firstdata.add(new Floor(rs1.getInt("rid"), rs1.getString("fname"), rs1.getInt("rcap")));

            }
            while (rs2.next()){
                seconddata.add(new Floor(rs2.getInt("rid"), rs2.getString("fname"), rs2.getInt("rcap")));

            }
            while (rs3.next()){
                thirddata.add(new Floor(rs3.getInt("rid"), rs3.getString("fname"), rs3.getInt("rcap")));

            }

        }catch (Exception e){
                        System.out.println(e);
        }
        broom.setCellValueFactory(new PropertyValueFactory<>("id"));
        bfloor.setCellValueFactory(new PropertyValueFactory<>("name"));
        bcap.setCellValueFactory(new PropertyValueFactory<>("cap"));
       froom.setCellValueFactory(new PropertyValueFactory<>("id"));
        ffloor.setCellValueFactory(new PropertyValueFactory<>("name"));
        fcap.setCellValueFactory(new PropertyValueFactory<>("cap"));
        sroom.setCellValueFactory(new PropertyValueFactory<>("id"));
        sfloor.setCellValueFactory(new PropertyValueFactory<>("name"));
        scap.setCellValueFactory(new PropertyValueFactory<>("cap"));
        troom.setCellValueFactory(new PropertyValueFactory<>("id"));
        tfloor.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcap.setCellValueFactory(new PropertyValueFactory<>("cap"));
        basement.setItems(basedata);
        firstfloor.setItems(firstdata);
        secondfloor.setItems(seconddata);
        thirdfloor.setItems(thirddata);



    }
    private void loadstage(String fxml,Button btn,int a,int b) {
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
