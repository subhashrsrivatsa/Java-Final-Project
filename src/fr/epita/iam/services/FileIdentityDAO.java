/**
 * 
 */
package fr.epita.iam.services;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;

/**
 * @author subhash
 *
 */
public class FileIdentityDAO {
	private PrintWriter printer;
	private Scanner scanner;

	/**
	 * @throws IOException
	 * 
	 */
	public FileIdentityDAO() throws IOException {
		File file = new File("tests.txt");
		if (!file.exists()) {
			System.out.println("the file does not exist");
			file.createNewFile();
		}

		this.setPrinter(new PrintWriter(file));
		this.scanner = new Scanner(file);
	}

	/**
	 * @param identity
	 */
	public boolean writeIdentity(Identity identity) throws  Exception{
		Connection connection = DatabaseConfig.getConnection();
		boolean status = insertIdentity(connection, identity);
		return status;
	}



	public List<Identity> readAll() {
		List<Identity> results = new ArrayList<>();
		while (this.scanner.hasNext()) {
			// First line : delimiter
			this.scanner.nextLine();
			String uid = this.scanner.nextLine();
			String displayName = this.scanner.nextLine();
			String email = this.scanner.nextLine();
			//Date birthDate = this.scanner.nextLine();
			// Last line : delimiter
			this.scanner.nextLine();
			Identity readIdentity = new Identity(uid, displayName, email);
			results.add(readIdentity);
					
		}
		return results;
		
	
	}


	private static boolean insertIdentity(Connection connection, Identity identity) throws SQLException {
		String insertStatement =
				"insert into IDENTITIES (IDENTITIES_UID, IDENTITIES_DISPLAYNAME, IDENTITIES_EMAIL) "
						+ "values(?, ?, ?)";
		PreparedStatement pstmt_insert = connection.prepareStatement(insertStatement);
		pstmt_insert.setString(1, identity.getUid());
		pstmt_insert.setString(2, identity.getDisplayName());
		pstmt_insert.setString(3, identity.getEmail());
		//Date now = new Date();
		//pstmt_insert.setDate(3,new java.sql.Date(now.getTime()));

		int count = pstmt_insert.executeUpdate();
		return count>0;
	}

	/**
	 * @param connection
	 * @throws SQLException
	 */
	public static List<Identity>  listAllIdentities(Connection connection) throws SQLException {
		List<Identity> identities = new ArrayList<Identity>();

		PreparedStatement pstmt_select = connection.prepareStatement("select * from IDENTITIES");
		ResultSet rs = pstmt_select.executeQuery();
		while (rs.next()){
			String uid = String.valueOf(rs.getString("IDENTITIES_UID"));
			String displayName = rs.getString("IDENTITIES_DISPLAYNAME");
			String email = rs.getString("IDENTITIES_EMAIL");
			//Date birthDate = rs.getDate("IDENTITIES_BIRTHDATE");
			Identity identity = new Identity(uid, displayName, email);
			identities.add(identity);
		}
		return identities;
	}

	/**
	 * @param connection
	 * @throws SQLException
	 */
	public boolean deleteIdentities(Connection connection, Identity identity) throws SQLException {
		new ArrayList<Identity>();

		PreparedStatement pstmt_select = connection.prepareStatement("DELETE from IDENTITIES where IDENTITIES_UID=?");
		pstmt_select.setString(1, identity.getUid());
		int count = pstmt_select.executeUpdate();
		return count>0;
	}

	/**
	 * @param connection
	 * @throws SQLException
	 */
	public boolean  updateIdentities(Connection connection, Identity identity)  {
		new ArrayList<Identity>();

		PreparedStatement pstmt_select = null;
		int count=0;
		try {
			pstmt_select = connection.prepareStatement("UPDATE IDENTITIES SET IDENTITIES_DISPLAYNAME=?,IDENTITIES_EMAIL=? WHERE IDENTITIES_UID=?");
			pstmt_select.setString(1, identity.getDisplayName());
			pstmt_select.setString(2, identity.getEmail());
			pstmt_select.setString(3, identity.getUid());
			count = pstmt_select.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count>0;
	}

	/**
	 * @return the printer
	 */
	public PrintWriter getPrinter() {
		return printer;
	}

	/**
	 * @param printer the printer to set
	 */
	public void setPrinter(PrintWriter printer) {
		this.printer = printer;
	}
}
