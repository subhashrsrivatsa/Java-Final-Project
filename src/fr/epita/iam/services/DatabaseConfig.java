/**
 * 
 */
package fr.epita.iam.services;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author subhash
 *
 */
public class DatabaseConfig {
	static  Connection connection =null;

    public static Connection getConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:myProject.db");
        }
        catch (Exception e){
           System.out.print("Exception:" +e);
        }
        return connection;
    }
}
