package home;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Floor;
import model.Room;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Wasiq on 12/28/2018.
 */
public class RoomController implements Initializable {
    @FXML
    Button back,floors,dashboard,teams,event,change;
    @FXML
    TextField st1,st2,st3,st4,avail;
    @FXML
    TableView<Room> roomtbl;
    @FXML
    TableColumn<Room,Integer> roomno;
    @FXML
    TableColumn<Room,Integer> roomcap;
    @FXML
    TableColumn<Room,String > roomfloor;
    ObservableList<Room> roomdata= FXCollections.observableArrayList();
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
        else if(actionEvent.getSource().equals(dashboard)){
            loadstage("/fxml/dashboard.fxml",dashboard,c,d);
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
        st1.setEditable(false);
        st2.setEditable(false);
        st3.setEditable(false);
        st4.setEditable(false);
        avail.setEditable(false);



        try{
            Connection con=DB.getConnection();
            ResultSet rs=con.createStatement().executeQuery("select rid,rcap,fname from floortbl inner join roomtbl on floortbl.fid=roomtbl.fid ");


            while (rs.next()){
                roomdata.add(new Room(rs.getInt("rid"), rs.getInt("rcap"), rs.getString("fname")));

            }


        }catch (Exception e){
            System.out.println(e);
        }
        roomno.setCellValueFactory(new PropertyValueFactory<>("id"));
        roomcap.setCellValueFactory(new PropertyValueFactory<>("cap"));
        roomfloor.setCellValueFactory(new PropertyValueFactory<>("floor"));
        roomtbl.setItems(roomdata);
        gettofields();




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
    private void gettofields(){
        roomtbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Room room = roomtbl.getItems().get(roomtbl.getSelectionModel().getSelectedIndex());
                int id = room.getId();
                int rcap=getroomcap(id);
                int assigned=getroomassigned(id);
                int available=rcap-assigned;
                avail.setText(""+available);
                List<String> list = new ArrayList<String>();
                try {
                    Connection connection = DB.getConnection();
                    ResultSet rs = connection.createStatement().executeQuery("select sfname from roomtbl inner join studenttbl on roomtbl.rid=studenttbl.rid where roomtbl.rid=" + id + "");
                    while (rs.next()) {

                        list.add(rs.getString("sfname"));


                    }
                    st1.setText("");
                    st2.setText("");
                    st3.setText("");
                    st4.setText("");
                 //   avail.setText("");
                    if (list.get(0) != null) {
                        st1.setText(list.get(0));
                    }

                    if (list.get(1) != null) {
                        st2.setText(list.get(1));
                    }

                    if (list.get(2) != null ) {
                        st3.setText(list.get(2));
                    }

                    if (list.get(3) != null) {
                        st4.setText(list.get(3));
                    }



                } catch (Exception e) {

                }
            }
        });
    }
    private int getroomcap(int a){
        int roomcap=0;

        try{
            Connection connection=DB.getConnection();
            ResultSet rs = connection.createStatement().executeQuery("select rcap from roomtbl where rid=" +a+"");



            while (rs.next()) {
                roomcap = rs.getInt(1);

            }




        }catch (Exception e){

        }
        return roomcap;
    }
    private int getroomassigned(int a){
        int assigengstudents = 0;
        try {
            Connection con=DB.getConnection();
            ResultSet rs1 = con.createStatement().executeQuery("select count(*) from studenttbl where rid=" +a+"");

            while (rs1.next()) {
                assigengstudents = rs1.getInt(1);
            }
        }catch (Exception e){

        }


        return assigengstudents;
    }
}
