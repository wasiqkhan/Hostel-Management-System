package home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Created by Wasiq on 12/29/2018.
 */
public class PasswordController implements Initializable {
    @FXML
    Button back,floors,rooms,teams,dashboard,event,changepass;
    @FXML
    PasswordField oldtxt,newtxt;
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
        else if(actionEvent.getSource().equals(event)){
            loadstage("/fxml/changepass.fxml",event,c,d);
        }



    }
    @FXML
    private String  getold(){
        String old=null;
        try{
            Connection connection=DB.getConnection();
            ResultSet rs=connection.createStatement().executeQuery("select password from logintbl where id=1");
            while (rs.next()){
                old=rs.getString("password");
            }

        }catch (Exception e){
            System.out.println(old);
        }
        return old;
    }

    @FXML
    private void change(){
        String old=getold();

        if(oldtxt.getText().isEmpty()||newtxt.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Fill all the Fields", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                alert.close();
            }
        }


        else {
            try{
                String oldd=oldtxt.getText();
                int id=1;
                Connection con=DB.getConnection();
                String query="update logintbl set password=? where id=" +id+"";
                PreparedStatement pst=con.prepareStatement(query);
                pst.setString(1,newtxt.getText());
                pst.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Password Successfully Changed", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    loadstage("/fxml/home.fxml",changepass,1200,800);
                    alert.close();
                }
                con.close();

            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "An Error Occured Please Try Again" + e, ButtonType.OK);
                System.out.print(e);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    alert.close();
                }
            }
        }



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
