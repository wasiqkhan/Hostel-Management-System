package home;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    Button close,login;
    @FXML
    TextField username;
    @FXML
    PasswordField password;

    @FXML
    private void handle(javafx.event.ActionEvent actionEvent){
        if(actionEvent.getSource().equals(close)){
            System.exit(0);
        }
        else if(actionEvent.getSource().equals(login)){
             try{
              // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
              //String url="jdbc:sqlserver://localhost:1434;databaseName=hosteldb;integratedSecurity=true";
              //Connection con= DriverManager.getConnection(url);
              Connection con=DB.getConnection();
              String sql="select * from logintbl where name=? and password=?";

              PreparedStatement pst=con.prepareStatement(sql);

              pst.setString(1,username.getText());
              pst.setString(2,password.getText());
              ResultSet rs=pst.executeQuery();
              if(rs.next()){
                  try {
                      Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
                      Stage primaryStage = new Stage();
                      primaryStage.setTitle("Hostel Managment");
                      primaryStage.setScene(new Scene(root, 1200, 800));
                      primaryStage.resizableProperty().setValue(Boolean.FALSE);



                      primaryStage.show();
                      primaryStage= (Stage) login.getScene().getWindow();
                      primaryStage.close();
                  } catch (IOException e) {

                  }

              }
              else {
                  Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect Username or Password, Try again", ButtonType.OK);
                  alert.showAndWait();
                  if (alert.getResult() == ButtonType.OK) {
                      alert.close();
                      username.setText("");
                      password.setText("");

                  }
              }
              con.close();

          }catch (Exception e){

          }
//            try{
//
//                        Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
//                        Stage primaryStage = new Stage();
//                        primaryStage.setTitle("Hostel Managment");
//                        primaryStage.setScene(new Scene(root, 1200, 800));
//                        primaryStage.resizableProperty().setValue(Boolean.FALSE);
//
//
//
//                        primaryStage.show();
//                        primaryStage= (Stage) login.getScene().getWindow();
//                        primaryStage.close();
//                    } catch (IOException e) {
//
//                    }
//
//                }
//                else {
//                    Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect Username or Password, Try again", ButtonType.OK);
//                    alert.showAndWait();
//                    if (alert.getResult() == ButtonType.OK) {
//                        alert.close();
//                        username.setText("");
//                        password.setText("");
//
//                    }
//                }


        }

            }




private void enterlogin(){
    password.setOnKeyPressed(event ->{
      if(event.getCode()== KeyCode.ENTER){
          try{
              // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
              //String url="jdbc:sqlserver://localhost:1434;databaseName=hosteldb;integratedSecurity=true";
              //Connection con= DriverManager.getConnection(url);
              Connection con=DB.getConnection();
              String sql="select * from logintbl where name=? and password=?";

              PreparedStatement pst=con.prepareStatement(sql);

              pst.setString(1,username.getText());
              pst.setString(2,password.getText());
              ResultSet rs=pst.executeQuery();
              if(rs.next()){
                  try {
                      Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
                      Stage primaryStage = new Stage();
                      primaryStage.setTitle("Hostel Managment");
                      primaryStage.setScene(new Scene(root, 1200, 800));
                      primaryStage.resizableProperty().setValue(Boolean.FALSE);



                      primaryStage.show();
                      primaryStage= (Stage) login.getScene().getWindow();
                      primaryStage.close();
                  } catch (IOException e) {

                  }

              }
              else {
                  Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect Username or Password, Try again", ButtonType.OK);
                  alert.showAndWait();
                  if (alert.getResult() == ButtonType.OK) {
                      alert.close();
                      username.setText("");
                      password.setText("");

                  }
              }
              con.close();

          }catch (Exception e){

          }
      }
    });
}


    @Override
    public void initialize(URL location, ResourceBundle resources) {
      //  enterlogin();
      //handle();

    }
}
