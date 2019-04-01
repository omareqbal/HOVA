/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Source;
import java.sql.*;
/**
 *
 * @author vishal
 */
public class Connect {
    public static  Connection returnConnection(){
        Connection conn=null;
        String JDBC_DRIVER="com.mysql.jdbc.Driver";
        String DB_URL="jdbc:mysql://localhost:3306/dbmslab";
        try{
            Class.forName(JDBC_DRIVER);
            conn=DriverManager.getConnection(DB_URL,"root","vishal");
            
            return conn;
        }
        catch(Exception e){
            System.out.println(e);
            return conn;
        }
    }
}
