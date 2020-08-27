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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Staff;
import model.Student;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Created by Wasiq on 12/23/2018.
 */
public class EmployeeController implements Initializable {

    @FXML
    Button ba,addm,save,update,delete;
    @FXML
    TextField fname,lname,salary,city,state,email,phone,team,search;
    @FXML
    ComboBox teambox;
    @FXML
    TableView<Staff> tbdata;
    @FXML
    TableColumn<Staff,Integer> staffid;
    @FXML
    TableColumn<Staff,String > firstname;
    @FXML
    TableColumn<Staff,String> lastname;
    ObservableList<String> mainlist= FXCollections.observableArrayList("Mess", "Cantiene", "Electricians", "Carpenters", "Janitor","Security Guard");
    ObservableList<Staff> observableList= FXCollections.observableArrayList();

    @FXML
    private void back(){
        loadstage("/fxml/home.fxml",ba);
    }
    @FXML
    private void gotoaddm(){
        loadstage("/fxml/addemployee.fxml", addm);
    }

    @FXML
    private void updatedata(){
        fname.setEditable(true);
        lname.setEditable(true);
        salary.setEditable(true);
        email.setEditable(true);
        city.setEditable(true);
        state.setEditable(true);
        phone.setEditable(true);
        save.setVisible(true);
        teambox.setVisible(true);

    }
    private void editable(TextField [] ar){
        for(int i=0;i<ar.length;i++){
            ar[i].setEditable(false);
        }

    }
    @FXML
    private void savedata(){
        Staff staff=tbdata.getItems().get(tbdata.getSelectionModel().getSelectedIndex());
        TextField [] arr={fname,lname,salary,city,state,phone,email,team};
        int id=staff.getId();
        try{
            Connection con=DB.getConnection();
            String quer="update stafftbl set stfname=?,stlname=?,stsalary=?,stcity=?,ststate=?,stemail=?,stphone=?,ttype=? where stid="+id+"";
            PreparedStatement pst=con.prepareStatement(quer);
            pst.setString(1, fname.getText());
            pst.setString(2,lname.getText());
            pst.setString(3,salary.getText());
            String team;
            team=teambox.getValue().toString();

            pst.setString(4, city.getText());
            pst.setString(5,state.getText());
            pst.setString(6, email.getText());
            pst.setString(7,phone.getText());
            pst.setString(8, team);

            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Record Successfully Updated", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {

                save.setVisible(false);
                editable(arr);
                teambox.setVisible(false);
                refresh(observableList,tbdata,staffid,firstname,lastname);


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

    }
    @FXML
    private void delete(){
        Staff staff=tbdata.getItems().get(tbdata.getSelectionModel().getSelectedIndex());
        int id=staff.getId();
        Alert confrim=new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to Delete",ButtonType.YES,ButtonType.NO);
        confrim.showAndWait();
        if(confrim.getResult()==ButtonType.YES){
            try{

                Connection con=DB.getConnection();
                String q="delete from stafftbl where stid="+id+"";
                PreparedStatement pst=con.prepareStatement(q);
                pst.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Record Successfully Deleted", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    refresh(observableList,tbdata,staffid,firstname,lastname);


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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fname.setEditable(false);
        lname.setEditable(false);
        salary.setEditable(false);
        city.setEditable(false);
        state.setEditable(false);
        phone.setEditable(false);
        email.setEditable(false);
        save.setVisible(false);
       teambox.setValue("Mess");
        teambox.setItems(mainlist);
        teambox.setVisible(false);


        try{
            Connection con=DB.getConnection();
            ResultSet rs=con.createStatement().executeQuery("select stid,stfname,stlname from stafftbl");
            while(rs.next()){
                observableList.add(new Staff(rs.getInt("stid"),rs.getString("stfname"),rs.getString("stlname")));

            }

        }catch (Exception e){

        }
        staffid.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        lastname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        tbdata.setItems(observableList);
        gettofield();
        getto();
        searchdata();


    }
    private void refresh(ObservableList<Staff> list,TableView<Staff> tbl,TableColumn<Staff,Integer> id,TableColumn<Staff,String > first,TableColumn<Staff,String> last){
        list.clear();
        try{
            Connection con=DB.getConnection();
            ResultSet rs=con.createStatement().executeQuery("select stid,stfname,stlname from stafftbl");
            while(rs.next()){
                list.add(new Staff(rs.getInt("stid"),rs.getString("stfname"),rs.getString("stlname")));

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
    private void gettofield(){
        tbdata.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Staff staff=tbdata.getItems().get(tbdata.getSelectionModel().getSelectedIndex());
                int id=staff.getId();
                try{
                    Connection con=DB.getConnection();

                    ResultSet rss=con.createStatement().executeQuery("select * from stafftbl where stid="+id+"");
                    while(rss.next()){
                        fname.setText(rss.getString("stfname"));
                        lname.setText(rss.getString("stlname"));
                        salary.setText(rss.getString("stsalary"));
                        city.setText(rss.getString("stcity"));
                        state.setText(rss.getString("ststate"));
                        phone.setText(rss.getString("stphone"));
                        email.setText(rss.getString("stemail"));
                        team.setText(rss.getString("ttype"));
                    }



                }catch (Exception e){

                }
            }
        });
    }
    private void getto(){
        tbdata.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                Staff staff = tbdata.getItems().get(tbdata.getSelectionModel().getSelectedIndex());
                int id = staff.getId();
                try {
                    Connection con = DB.getConnection();

                    ResultSet rss = con.createStatement().executeQuery("select * from stafftbl where stid=" + id + "");
                    while (rss.next()) {
                        fname.setText(rss.getString("stfname"));
                        lname.setText(rss.getString("stlname"));
                        salary.setText(rss.getString("stsalary"));
                        city.setText(rss.getString("stcity"));
                        state.setText(rss.getString("ststate"));
                        phone.setText(rss.getString("stphone"));
                        email.setText(rss.getString("stemail"));
                        team.setText(rss.getString("ttype"));
                    }


                } catch (Exception e) {

                }
            }

        });
    }
    private void searchdata(){
        FilteredList<Staff> filteredList=new FilteredList<>(observableList, event -> true);
        search.setOnKeyReleased(event -> {
            search.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate( staff->{
                    if(newValue==null||newValue.isEmpty()){
                        return true;
                    }
                    String lowercase=newValue.toLowerCase();


                    if(staff.getFname().toLowerCase().contains(lowercase)){
                        return true;
                    }
                    else if(staff.getLname().toLowerCase().contains(lowercase)){
                        return true;
                    }


                    return false;
                });
            });
            SortedList<Staff> sortedList=new SortedList<Staff>(filteredList);
            sortedList.comparatorProperty().bind(tbdata.comparatorProperty());
            tbdata.setItems(sortedList);
        });





    }
}
