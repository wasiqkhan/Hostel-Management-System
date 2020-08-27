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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Floor;
import model.Team;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Created by Wasiq on 12/28/2018.
 */
public class TeamController implements Initializable {
    @FXML
    Button back,floors,rooms,dashboard,event,change;
    @FXML
    TableView<Team> cantienetbl;
    @FXML
    TableColumn<Team,String > cantfirst;
    @FXML
    TableColumn<Team,String > cantlast;
    @FXML
    TableColumn<Team,String > cantteam;
    @FXML
    TableView<Team> janitortbl;
    @FXML
    TableColumn<Team,String > jfirst;
    @FXML
    TableColumn<Team,String > jlast;
    @FXML
    TableColumn<Team,String > jteam;

    @FXML
    TableView<Team> carpentertbl;
    @FXML
    TableColumn<Team,String > carpfirst;
    @FXML
    TableColumn<Team,String > carplast;
    @FXML
    TableColumn<Team,String > carpteam;
    @FXML
    TableView<Team> electbl;
    @FXML
    TableColumn<Team,String > efirst;
    @FXML
    TableColumn<Team,String > elast;
    @FXML
    TableColumn<Team,String > eteam;
    @FXML
    TableView<Team> messtbl;
    @FXML
    TableColumn<Team,String > mfirst;
    @FXML
    TableColumn<Team,String > mlast;
    @FXML
    TableColumn<Team,String > mteam;
    @FXML
    TableView<Team> guardtbl;
    @FXML
    TableColumn<Team,String > gfirst;
    @FXML
    TableColumn<Team,String > glast;
    @FXML
    TableColumn<Team,String > gteam;

    ObservableList<Team> guarddata= FXCollections.observableArrayList();
    ObservableList<Team> messdata= FXCollections.observableArrayList();
    ObservableList<Team> elecdata= FXCollections.observableArrayList();
    ObservableList<Team> janitordata= FXCollections.observableArrayList();
    ObservableList<Team> carpdata= FXCollections.observableArrayList();
    ObservableList<Team> cantdata= FXCollections.observableArrayList();

    @FXML
    private void back(){
        loadstage("/fxml/home.fxml",back);

    }
    @FXML
    private void go(ActionEvent actionEvent){
        if(actionEvent.getSource().equals(floors)){
            loadstage("/fxml/floor.fxml",floors);
        }
        else if(actionEvent.getSource().equals(rooms)){
            loadstage("/fxml/room.fxml",rooms);
        }
        else if(actionEvent.getSource().equals(dashboard)){
            loadstage("/fxml/dashboard.fxml",dashboard);
        }
        else if(actionEvent.getSource().equals(event)){
            loadstage("/fxml/event.fxml",event);
        }
        else if(actionEvent.getSource().equals(change)){
            loadstage("/fxml/changepass.fxml",change);
        }



    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        change.setVisible(false);
        try{
            Connection con=DB.getConnection();
            ResultSet rs=con.createStatement().executeQuery("select stfname,stlname,ttype from stafftbl where ttype='Mess'");
            ResultSet rs1=con.createStatement().executeQuery("select stfname,stlname,ttype from stafftbl where ttype='Security Guard'");
            ResultSet rs2=con.createStatement().executeQuery("select stfname,stlname,ttype from stafftbl where ttype='Janitor'");
            ResultSet rs3=con.createStatement().executeQuery("select stfname,stlname,ttype from stafftbl where ttype='Electricians'");
            ResultSet rs4=con.createStatement().executeQuery("select stfname,stlname,ttype from stafftbl where ttype='Carpenters'");
            ResultSet rs5=con.createStatement().executeQuery("select stfname,stlname,ttype from stafftbl where ttype='Cantiene'");
            while (rs.next()){
                messdata.add(new Team(rs.getString("stfname"), rs.getString("stlname"), rs.getString("ttype")));

            }
            while (rs1.next()){
                guarddata.add(new Team(rs1.getString("stfname"), rs1.getString("stlname"), rs1.getString("ttype")));

            }
            while (rs2.next()){
                janitordata.add(new Team(rs2.getString("stfname"), rs2.getString("stlname"), rs2.getString("ttype")));

            }
            while (rs3.next()){
                elecdata.add(new Team(rs3.getString("stfname"), rs3.getString("stlname"), rs3.getString("ttype")));

            }
            while (rs4.next()){
                carpdata.add(new Team(rs4.getString("stfname"), rs4.getString("stlname"), rs4.getString("ttype")));

            }
            while (rs5.next()){
                cantdata.add(new Team(rs5.getString("stfname"), rs5.getString("stlname"), rs5.getString("ttype")));

            }

        }catch (Exception e){
            System.out.println(e);
        }
        mfirst.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        mlast.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        mteam.setCellValueFactory(new PropertyValueFactory<>("team"));
        gfirst.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        glast.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        gteam.setCellValueFactory(new PropertyValueFactory<>("team"));
        jfirst.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        jlast.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        jteam.setCellValueFactory(new PropertyValueFactory<>("team"));
        efirst.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        elast.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        eteam.setCellValueFactory(new PropertyValueFactory<>("team"));
        carpfirst.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        carplast.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        carpteam.setCellValueFactory(new PropertyValueFactory<>("team"));
        cantfirst.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        cantlast.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        cantteam.setCellValueFactory(new PropertyValueFactory<>("team"));

        messtbl.setItems(messdata);
        guardtbl.setItems(guarddata);
        janitortbl.setItems(janitordata);
        electbl.setItems(elecdata);
        carpentertbl.setItems(carpdata);
        cantienetbl.setItems(cantdata);







    }
    private void loadstage(String fxml,Button btn) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Hostel Managment");
            primaryStage.setScene(new Scene(root, 1200, 800));
            primaryStage.resizableProperty().setValue(Boolean.FALSE);




            primaryStage.show();
            primaryStage= (Stage) btn.getScene().getWindow();
            primaryStage.close();
        } catch (IOException e) {

        }
    }
}
