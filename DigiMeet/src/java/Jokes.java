
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author enver
 */
@ManagedBean
@RequestScoped
public class Jokes {
    
    public String fetchJoke() throws SQLException
    {
        String returnString="error";
        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/DigiMeet");
        PreparedStatement pst = conn.prepareStatement("Select JOKE from JOKES where id=?");
        Random rand = new Random();
        Integer rand_int1 = rand.nextInt(5)+1;
        pst.setString(1, rand_int1.toString());
        ResultSet rs = pst.executeQuery();
        while(rs.next())
        {
            returnString = rs.getString("JOKE");
            break;
        }
        conn.close();
        pst.close();
        return returnString;
    }
}
