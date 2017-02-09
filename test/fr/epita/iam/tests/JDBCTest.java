/**
 * 
 */
package fr.epita.iam.tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import fr.epita.iam.datamodel.User;

/**
 * @author subhash
 *
 */
public class JDBCTest {
	
	
	
	public static void main(String[] args) throws SQLException {
		Connection connection = null;
		try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:myProject.db");
        }
        catch (Exception e){
           System.out.print("Exception:" +e);
        }
		clean(connection);
		setup(connection);

		User user = new User();
		user.setUserName("subhash");
		user.setPassword("subhash");

		boolean check = insertUser(connection, user);

		connection.close();

		System.out.print("Created user :"+check);

	}

	private static boolean clean(Connection connection){
		String dropTableIdentity = "DROP TABLE IF EXISTS IDENTITIES";
		String dropTableUser = "DROP TABLE IF EXISTS USERS";
		PreparedStatement pstmt_insert = null;
		boolean success1 = false ;
		boolean success2 =false;
		try {
			pstmt_insert = connection.prepareStatement(dropTableIdentity);
			success1= pstmt_insert.execute();

			pstmt_insert = connection.prepareStatement(dropTableUser);
			success2 = pstmt_insert.execute();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return success1&& success2;
	}

	private static boolean setup(Connection connection) throws SQLException {
		String createTableIdentity =
				"create table IDENTITIES (" +
						"IDENTITIES_UID varchar(10), " +
						"IDENTITIES_DISPLAYNAME varchar(20), " +
						"IDENTITIES_EMAIL varchar(20), " +
						"IDENTITIES_BIRTHDATE   date, " +
						"PRIMARY KEY( IDENTITIES_UID)) ";
		String createTableUsers =
				"create table USERS (" +
						"USERNAME varchar(20), " +
						"PASSWORD varchar(20), " +
						"PRIMARY KEY( USERNAME)) ";
		PreparedStatement pstmt_insert = connection.prepareStatement(createTableIdentity);
		int success1 = pstmt_insert.executeUpdate();

		pstmt_insert = connection.prepareStatement(createTableUsers);
		int success2 = pstmt_insert.executeUpdate();

		return success1>0 && success2>0;

	}

	private static boolean insertUser(Connection connection, User user) throws SQLException {
		String insertStatement =
				"insert into USERS (USERNAME, PASSWORD) "
						+ "values(?, ?)";
		PreparedStatement pstmt_insert = connection.prepareStatement(insertStatement);
		pstmt_insert.setString(1, user.getUserName());
		pstmt_insert.setString(2, user.getPassword());
		int success = pstmt_insert.executeUpdate();
		return success>0;
	}
}
