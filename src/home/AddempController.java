package home;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

/**
 * Created by Wasiq on 12/25/2018.
 */
public class AddempController implements Initializable {


    @FXML
    TextField fnametxt,lnametxt,citytxt,statetxt,emailtxt,phonetxt,salarytxt;
    @FXML
    ComboBox teambox;
    @FXML
    Button add,cancel;
    ObservableList<String> mainlist= FXCollections.observableArrayList("Mess","Cantiene","Electricians","Carpenters","Janitor","Security Guard");

    @FXML
    private void cancel(){
        loadstage("/fxml/employees.fxml",cancel);
    }
    @FXML
    private void addstaff(){
        if(fnametxt.getText().isEmpty() || lnametxt.getText().isEmpty() || emailtxt.getText().isEmpty() || citytxt
                .getText().isEmpty() || statetxt.getText().isEmpty() || phonetxt.getText().isEmpty() || salarytxt.getText().isEmpty() || teambox.getValue().toString().isEmpty()){
            Alert fill = new Alert(Alert.AlertType.ERROR, "Please Fill all the Fields", ButtonType.OK);
            fill.showAndWait();
            if (fill.getResult() == ButtonType.OK) {
                fill.close();
            }
        }
else {


            try {


                Connection con = DB.getConnection();
                String query = "insert into stafftbl (stfname,stlname,stsalary,stcity,ststate,stemail,stphone,ttype) values(?,?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, fnametxt.getText());
                pst.setString(2, lnametxt.getText());
                pst.setString(3, salarytxt.getText());
                String team;
                team = teambox.getValue().toString();
                pst.setString(4, citytxt.getText());
                pst.setString(5, statetxt.getText());
                pst.setString(6, emailtxt.getText());
                pst.setString(7, phonetxt.getText());
                pst.setString(8, team);

                pst.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Record Successfully Inserted", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    TextField[] ar =  {fnametxt, lnametxt, emailtxt, citytxt, statetxt, phonetxt, salarytxt};
                    reset(ar);
                    alert.close();
                }
                con.close();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "An Error Occured Please Try Again" + e, ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    loadstage("/fxml/employees.fxml",add);
                    alert.close();
                }
            }
        }
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
        teambox.setValue("Mess");
        teambox.setItems(mainlist);

    }
}
