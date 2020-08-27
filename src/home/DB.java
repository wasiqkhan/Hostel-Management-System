package home;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Created by Wasiq on 12/25/2018.
 */
public class DB {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {


            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
           // Class.forName("sun.jdbc.obdc.JdbcOdbcDriver");
            //Connection con=DriverManager.getConnection("jdbc:odbc:hosteldb");
            String url="jdbc:sqlserver://localhost:1433;databaseName=hosteldb;integratedSecurity=true;";

           Connection con= DriverManager.getConnection(url);
            return con;


    }
}
