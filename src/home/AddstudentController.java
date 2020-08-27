package home;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Room;

import java.awt.*;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Created by Wasiq on 12/25/2018.
 */
public class AddstudentController implements Initializable {

    String filename=null;
    byte[] pimage=null;
    Image image;
    FileInputStream fis;
    File file;
    javafx.scene.image.Image image1;
    @FXML
    Button add,cancel,choose;
    @FXML
    TextField fnametxt,lnametxt,regtxt,citytxt,statetxt,roomtxt,phonetxt;
    @FXML
    ImageView imagev;
    @FXML
    TableView<Room> availtbl;
    @FXML
    TableColumn<Room,Integer> roomno;
    @FXML
    TableColumn<Room,Integer> space;

    @FXML
    ComboBox box;
    ObservableList<String> mainlist= FXCollections.observableArrayList("Architecure","Computer Science","Economics","Humanities","Management","Meteorology","Bio Sciences","Electrical","Mathematics","Physics");
    ObservableList<Room> roomdata= FXCollections.observableArrayList();

   @FXML
   private void chose(){
       try {
           Parent root = FXMLLoader.load(getClass().getResource("/fxml/addstudent.fxml"));
           Stage stage = new Stage();
           stage.setTitle("Hostel Managment");
           stage.setScene(new Scene(root, 1200, 800));
           FileChooser fileChooser=new FileChooser();

         file= fileChooser.showOpenDialog(stage);

           if(file!=null)
           filename=file.getAbsolutePath();

           image1=new javafx.scene.image.Image(file.toURI().toString(),174,192,true,true);
           imagev.setImage(image1);
          // imagev=new ImageView(image1);
           imagev.setPreserveRatio(true);


       }catch (Exception e){

       }




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

    @FXML
    private void addstudent(){
        String riid = roomtxt.getText();
        int nid = Integer.parseInt(riid);
        int roomcap=getroomcap(nid);
        int assignedstudents=getroomassigned(nid);
        if (fnametxt.getText().isEmpty() || lnametxt.getText().isEmpty() || regtxt.getText().isEmpty() || citytxt
                .getText().isEmpty() || statetxt.getText().isEmpty() || phonetxt.getText().isEmpty() || roomtxt.getText().isEmpty() || box.getValue().toString().isEmpty()||file==null ) {
            Alert fill = new Alert(Alert.AlertType.ERROR, "Please Fill all the Fields", ButtonType.OK);
            fill.showAndWait();
            if (fill.getResult() == ButtonType.OK) {
                fill.close();
            }
        }
        else if(roomcap<=assignedstudents){
            Alert fill = new Alert(Alert.AlertType.ERROR, "No More Capacity in Room "+nid, ButtonType.OK);
            fill.showAndWait();
            if (fill.getResult() == ButtonType.OK) {
                fill.close();
            }
        }
        else {


            try {
                String rid = roomtxt.getText();
                int nrid = Integer.parseInt(rid);
                Connection con = DB.getConnection();
                String query = "insert into studenttbl (sfname,slname,sreg,sdep,scity,sstate,sphone,rid,images) values(?,?,?,?,?,?,?,?,?)";

                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, fnametxt.getText());
                pst.setString(2, lnametxt.getText());
                pst.setString(3, regtxt.getText());
                String dep;
                dep = box.getValue().toString();

                pst.setString(4, dep);
                pst.setString(5, citytxt.getText());
                pst.setString(6, statetxt.getText());
                pst.setString(7, phonetxt.getText());
                pst.setInt(8, nrid);
                fis = new FileInputStream(file);
                pst.setBinaryStream(9, (InputStream) fis, (int) file.length());


                pst.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Record Successfully Inserted", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    TextField[] ar = {fnametxt, lnametxt, regtxt, citytxt, statetxt, phonetxt, roomtxt};
                    reset(ar);
                    loadstage("/fxml/students.fxml",add);
                    alert.close();
                }
                con.close();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "An Error Occured Please Try Again" + e, ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    alert.close();
                }
            }

        }
    }
    @FXML
    private void cancel(){
        loadstage("/fxml/students.fxml",cancel);

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
    private void reset(TextField [] arr){
        for(int i=0;i<arr.length;i++){
            arr[i].setText("");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        box.setValue("Architecture");
        box.setItems(mainlist);
        try{
            Connection con=DB.getConnection();
            ResultSet rs=con.createStatement().executeQuery("select rid, rcap-(select count(*) from studenttbl where studenttbl.rid=roomtbl.rid)as avail  from roomtbl where rcap>(select count(*) as a from studenttbl where studenttbl.rid=roomtbl.rid )");


            while (rs.next()){
                roomdata.add(new Room(rs.getInt("rid"),rs.getInt("avail")));

            }


        }catch (Exception e){
            System.out.println(e);
        }
        roomno.setCellValueFactory(new PropertyValueFactory<>("id"));
        space.setCellValueFactory(new PropertyValueFactory<>("cap"));
        availtbl.setItems(roomdata);






    }
}
