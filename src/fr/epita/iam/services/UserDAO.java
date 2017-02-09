/**
 * 
 */
package fr.epita.iam.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import fr.epita.iam.datamodel.User;

/**
 * @author subhash
 *
 */
public class UserDAO {
		public boolean validateUser(User user) {
        Connection connection= DatabaseConfig.getConnection();
        new ArrayList<User>();
        String userName="";
        String password="";
        PreparedStatement pstmt_select = null;
        try {
            pstmt_select = connection.prepareStatement("select * from USERS where USERNAME=? and PASSWORD=?");
            pstmt_select.setString(1, user.getUserName());
            pstmt_select.setString(2, user.getPassword());

            ResultSet rs = pstmt_select.executeQuery();
            while (rs.next()){
                userName = rs.getString("userName");
                password = rs.getString("userName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(userName.equals(user.getUserName())&& password.equals(user.getPassword()))
            return true;
        else
            return false;
    }
}
