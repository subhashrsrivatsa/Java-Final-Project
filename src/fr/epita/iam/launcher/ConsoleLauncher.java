/**
 * 
 */
package fr.epita.iam.launcher;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.datamodel.User;
import fr.epita.iam.services.DatabaseConfig;
import fr.epita.iam.services.FileIdentityDAO;
import fr.epita.iam.services.UserDAO;

/**
 * @author subhash
 *
 */
public class ConsoleLauncher {
	
	private static FileIdentityDAO dao;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Hello, welcome to the IAM application by SRIVATSA_Subhash");
		System.out.println("********************************************************");
		Scanner scanner = new Scanner(System.in);
		dao = new FileIdentityDAO();

		//authentication
		System.out.println("For your AUTHENTICATION");
		System.out.println("Please enter your login");
		String login = scanner.nextLine();
		System.out.println("Please enter your password");
		String password = scanner.nextLine();

		if (!authenticate(login, password)) {
			System.out.println("Please type Correct Username / Password");
			exit(0);
		}else
		while(true) {
			System.out.println("***************************************************");

			String answer = menu(scanner);

			switch (answer) {
				case "a":
					createIdentity(scanner);
					break;
				case "b":
					modifyIdentity(scanner);
					break;
				case "c":
					deleteIdentity(scanner);
					break;
				case "d":
					listIdentity(scanner);
					break;
				case "e":{
					System.out.println("You have chosen to EXIT");
					System.out.println("Thanks for using IAM CORE SUBHASH");
					System.exit(0);
					break;
				}
				default:
					System.out.println("This option is not recognized (" + answer + ")");
					break;
			}
			System.out.println("---------------------------------------------------");
		}
	}

	private static void exit(int i) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param scanner
	 */
	private static void createIdentity(Scanner scanner) throws Exception {
		System.out.println("You've selected : Identity CREATION");
		System.out.println("Please enter the UID");
		String uid = scanner.nextLine();
		System.out.println("Please enter the Identity display name");
		String displayName = scanner.nextLine();
		System.out.println("Please enter the Identity email");
		String email = scanner.nextLine();
		//System.out.println("Please enter the Date of Birth");
		//Date birthDate = new Date();
		
		Identity newIdentity = new Identity(uid, displayName, email);

		if (dao.writeIdentity(newIdentity))
			System.out.println("You successfully created this identity :\n" + newIdentity);
		else
			System.out.println("Some INPUT ERROR occurred / Cannot CREATE \n");
		
	}


	private static void modifyIdentity(Scanner scanner) throws Exception {
		System.out.println("You've selected : IDENTITY MODIFY ");
		System.out.println("Please enter the UID");
		String uid = scanner.nextLine();
		System.out.println("Please enter the new IDENTITY display name to MODIFY");
		String displayName = scanner.nextLine();
		System.out.println("Please enter the new IDENTITY EMAIL");
		String email = scanner.nextLine();
		//System.out.println("Please enter the new Date of Birth");
		//Date birthDate = scanner.nextLine();
		Identity newIdentity = new Identity(uid, displayName, email);
		Connection connection = DatabaseConfig.getConnection();

		if (dao.updateIdentities(connection, newIdentity))
			System.out.println("You successfully UPDATED this identity");
		else
			System.out.println("Some INPUT Error / Data Cannot be MODIFIED");

	}

	private static void deleteIdentity(Scanner scanner) throws Exception {
		System.out.println("You've selected : IDENTITY DELETE");
		System.out.println("Please enter the UID");
		String uid = scanner.nextLine();
		Identity newIdentity = new Identity(uid,null,null);
		Connection connection = DatabaseConfig.getConnection();
		if (dao.deleteIdentities(connection, newIdentity))
			System.out.println("You successfully deleted this identity :\n" + newIdentity);
		else
			System.out.println("Cannot be DELETED / The List is EMPTY");

	}

	
	private static void listIdentity(Scanner scanner) throws Exception {
		System.out.println("You've selected : LIST ");
		Connection connection = DatabaseConfig.getConnection();
		List<Identity> identities = FileIdentityDAO.listAllIdentities(connection);
		System.out.println("Here is the list" + identities);
		

	}


	/**
	 * @param scanner
	 * @return
	 */
	private static String menu(Scanner scanner) {
		System.out.println("You're AUTHENTICATED");
		System.out.println("Here are the actions you can perform :");
		System.out.println("a. Create an IDENTITY");
		System.out.println("b. Modify an IDENTITY");
		System.out.println("c. Delete an IDENTITY");
		System.out.println("d. List IDENTITIES");
		System.out.println("e. EXIT");
		System.out.println("your choice (a|b|c|d|e) ? : ");
		String answer = scanner.nextLine();
		return answer;
	}

	/**
	 * @param login
	 * @param password
	 */
	private static boolean authenticate(String login, String password){
		User user = new User();
		user.setUserName(login);
		user.setPassword(password);

		UserDAO userDAO = new UserDAO();

		boolean validateStatus= userDAO.validateUser(user);
		return validateStatus;
	}
	

}
