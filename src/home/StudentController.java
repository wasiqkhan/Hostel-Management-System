package home;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Student;

import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.Image;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * Created by Wasiq on 12/23/2018.
 */
public class StudentController implements Initializable {
    String filename=null;
    byte[] pimage=null;
    Image image;
    FileInputStream fis;

    File file;
    javafx.scene.image.Image image1;
    javafx.scene.image.Image image2;
    @FXML
    ImageView imagev;
    @FXML
    Button bac,adds,save,update,delete,choose;
    @FXML
    TextField fname,lname,reg,dep,city,state,phone,room,search;


    @FXML
    TableView<Student> tbdata;
    @FXML
    TableColumn<Student,Integer> studentid;

    @FXML
    TableColumn<Student,String > firstname;

    @FXML
    TableColumn<Student,String > lastname;
    @FXML
    ComboBox box;

    ObservableList<String> mainlist=FXCollections.observableArrayList("Architecure","Computer Science","Economics","Humanities","Management","Meteorology","Bio Sciences","Electrical","Mathematics","Physics");
    ObservableList<Student> stdata=FXCollections.observableArrayList();

    ObservableList<Student> observableList= FXCollections.observableArrayList();
    @FXML
    private void back(){
       loadstage("/fxml/home.fxml",bac);

    }
    @FXML
    private void chose(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/students.fxml"));
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
    @FXML
    private void updatedata(){
        fname.setEditable(true);
        lname.setEditable(true);
        reg.setEditable(true);
        dep.setEditable(true);
        city.setEditable(true);
        state.setEditable(true);
        phone.setEditable(true);
        room.setEditable(true);
        save.setVisible(true);
        box.setVisible(true);
        choose.setVisible(true);
    }
    private void editable(TextField [] ar) {
        for (int i = 0; i < ar.length; i++) {
            ar[i].setEditable(false);
        }
    }
    @FXML
    private void savedata() throws SQLException, ClassNotFoundException {
        Student student=tbdata.getItems().get(tbdata.getSelectionModel().getSelectedIndex());
        int id=student.getId();
        TextField [] arr={fname,lname,reg,dep,phone,city,state,room};
        String riid=room.getText();
        int nid=Integer.parseInt(riid);
        int roomcap=getroomcap(nid);
        int assignedstudents=getroomassigned(nid);
        if(roomcap<=assignedstudents){
            Alert fill = new Alert(Alert.AlertType.ERROR, "No More Capacity in Room "+nid, ButtonType.OK);
            fill.showAndWait();
            if (fill.getResult() == ButtonType.OK) {
                fill.close();
            }
        }
        else {


            try {
                Connection con = DB.getConnection();
                String quer = "update studenttbl set sfname=?,slname=?,sreg=?,sdep=?,scity=?,sstate=?,sphone=?,rid=?,images=? where sid=" + id + "";
                PreparedStatement pst = con.prepareStatement(quer);
                pst.setString(1, fname.getText());
                pst.setString(2, lname.getText());
                pst.setString(3, reg.getText());
                String dep;
                dep = box.getValue().toString();

                pst.setString(4, dep);
                pst.setString(5, city.getText());
                pst.setString(6, state.getText());
                pst.setString(7, phone.getText());
                String rid = room.getText();
                int nrid = Integer.parseInt(rid);
                pst.setInt(8, nrid);
                fis = new FileInputStream(file);
                pst.setBinaryStream(9, (InputStream) fis, (int) file.length());
                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Record Successfully Updated", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    save.setVisible(false);
                    editable(arr);
                    box.setVisible(false);
                    refresh(observableList, tbdata, studentid, firstname, lastname);


                    alert.close();
                }
                con.close();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "An Error Occured Please Try Again" + e, ButtonType.OK);
                System.out.print(e);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    alert.close();
                }

            }
        }
    }
    @FXML
      private void delete(){
        Student student=tbdata.getItems().get(tbdata.getSelectionModel().getSelectedIndex());
        int id=student.getId();
        Alert confrim=new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to Delete",ButtonType.YES,ButtonType.NO);
        confrim.showAndWait();
        if(confrim.getResult()==ButtonType.YES){
            try{

                Connection con=DB.getConnection();
                String q="delete from studenttbl where sid="+id+"";
                PreparedStatement pst=con.prepareStatement(q);
                pst.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Record Successfully Deleted", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    refresh(observableList,tbdata,studentid,firstname,lastname);


                    alert.close();
                }
                con.close();

            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "An Error Occured Please Try Again"+e, ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    alert.close();
                }

            }
            confrim.close();
        }
        if(confrim.getResult()==ButtonType.NO){
            confrim.close();
        }

    }
    @FXML
    private void gotoadd(){
        loadstage("/fxml/addstudent.fxml", adds);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fname.setEditable(false);
        lname.setEditable(false);
        reg.setEditable(false);
        dep.setEditable(false);
        city.setEditable(false);
        state.setEditable(false);
        phone.setEditable(false);
        room.setEditable(false);
        save.setVisible(false);
        choose.setVisible(false);



        try{
            Connection con=DB.getConnection();
            ResultSet rs=con.createStatement().executeQuery("select sid,sfname,slname from studenttbl");
            while(rs.next()){
                observableList.add(new Student(rs.getInt("sid"),rs.getString("sfname"),rs.getString("slname")));

            }


        }catch (Exception e){

        }

        studentid.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        lastname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        tbdata.setItems(observableList);
        box.setValue("Architecture");
        box.setItems(mainlist);
        box.setVisible(false);
        gettofields();
        getto();
        searchdata();


    }
    private void refresh(ObservableList<Student> list,TableView<Student> tbl,TableColumn<Student,Integer> id,TableColumn<Student,String > first,TableColumn<Student,String> last){
        list.clear();
        try{
            Connection con=DB.getConnection();
            ResultSet rs=con.createStatement().executeQuery("select sid,sfname,slname from studenttbl");
            while(rs.next()){
                list.add(new Student(rs.getInt("sid"),rs.getString("sfname"),rs.getString("slname")));

            }


        }catch (Exception e){

        }
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        first.setCellValueFactory(new PropertyValueFactory<>("fname"));
        last.setCellValueFactory(new PropertyValueFactory<>("lname"));
        tbl.setItems(list);

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
    private void gettofields(){
        tbdata.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Student student = tbdata.getItems().get(tbdata.getSelectionModel().getSelectedIndex());

                int a = student.getId();
                try {

                    Connection con = DB.getConnection();
                    ResultSet rss = con.createStatement().executeQuery("select * from studenttbl where sid=" + a + "");
                    while (rss.next()) {
                        fname.setText(rss.getString("sfname"));
                        lname.setText(rss.getString("slname"));
                        reg.setText(rss.getString("sreg"));
                        dep.setText(rss.getString("sdep"));
                        city.setText(rss.getString("scity"));
                        state.setText(rss.getString("sstate"));
                        phone.setText(rss.getString("sphone"));
                        room.setText("" + rss.getInt("rid"));
                        InputStream inputStream = rss.getBinaryStream("images");
                        OutputStream outputStream = new FileOutputStream(new File("photo.jpg"));
                        byte[] content = new byte[1024];
                        int size = 0;
                        while ((size = inputStream.read(content)) != -1) {
                            outputStream.write(content, 0, size);
                        }
                        // outputStream.close();
                        // inputStream.close();
                        image2 = new javafx.scene.image.Image("file:photo.jpg", 174, 192, true, true);
                        imagev.setImage(image2);
                        imagev.setPreserveRatio(true);

                        //   stdata.add(new Student(a,rss.getString("sfname"),rss.getString("slname"),rss.getString("sreg"),rss.getString("sdep"),rss.getString("scity"),rss.getString("sstate"),rss.getString("sphone"),rss.getInt("rid")));
                    }

                } catch (FileNotFoundException e) {

                } catch (Exception e) {

                }


            }
        });
    }
    private void getto(){
        tbdata.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {


                Student student = tbdata.getItems().get(tbdata.getSelectionModel().getSelectedIndex());

                int a = student.getId();
                try {

                    Connection con = DB.getConnection();
                    ResultSet rss = con.createStatement().executeQuery("select * from studenttbl where sid=" + a + "");
                    while (rss.next()) {
                        fname.setText(rss.getString("sfname"));
                        lname.setText(rss.getString("slname"));
                        reg.setText(rss.getString("sreg"));
                        dep.setText(rss.getString("sdep"));
                        city.setText(rss.getString("scity"));
                        state.setText(rss.getString("sstate"));
                        phone.setText(rss.getString("sphone"));
                        room.setText("" + rss.getInt("rid"));
                        InputStream inputStream = rss.getBinaryStream("images");
                        OutputStream outputStream = new FileOutputStream(new File("photo.jpg"));
                        byte[] content = new byte[1024];
                        int size = 0;
                        while ((size = inputStream.read(content)) != -1) {
                            outputStream.write(content, 0, size);
                        }
                        // outputStream.close();
                        // inputStream.close();
                        image2 = new javafx.scene.image.Image("file:photo.jpg", 174, 192, true, true);
                        imagev.setImage(image2);
                        imagev.setPreserveRatio(true);

                        //   stdata.add(new Student(a,rss.getString("sfname"),rss.getString("slname"),rss.getString("sreg"),rss.getString("sdep"),rss.getString("scity"),rss.getString("sstate"),rss.getString("sphone"),rss.getInt("rid")));
                    }

                } catch (FileNotFoundException e) {

                } catch (Exception e) {

                }
            }

        });
    }
    private void searchdata(){
        FilteredList<Student> filteredList=new FilteredList<>(observableList, event -> true);
        search.setOnKeyReleased(event -> {
            search.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(studen -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowercase = newValue.toLowerCase();


                    if (studen.getFname().toLowerCase().contains(lowercase)) {
                        return true;
                    } else if (studen.getLname().toLowerCase().contains(lowercase)) {
                        return true;
                    }


                    return false;
                });
            });
            SortedList<Student> sortedList = new SortedList<Student>(filteredList);
            sortedList.comparatorProperty().bind(tbdata.comparatorProperty());
            tbdata.setItems(sortedList);
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
