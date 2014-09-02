package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

/**
 * Provides connectivity to the conference manager database in order to insert, update and delete users.
 * @author  Tony Robinson
 * @version $Id: UserAdapter.java 246 2013-06-08 21:39:10Z ajr42@uw.edu $
 */
public class UserAdapter extends AbstractAdapter{
    /**
     * The name of the table.
     */
    private final String TABLE_NAME = "users";

	/**
	 * The prepared database statement to be executed. 
	 */
	protected PreparedStatement my_prepared_statement;
	
	/**
	 * Constructs a new User Adapter.
	 */
	public UserAdapter() {
	    super();
	}
	
	/**
	 * Adds a user to the system.
	 * 
	 * @param the_user The user to add to the system.
	 * 
	 * @throws SQLException
	 */
	public void addUser(final User the_user) throws SQLException {
		my_prepared_statement = getConnection().prepareStatement(
				"INSERT INTO " + TABLE_NAME + " (FirstName, LastName, Email) VALUES (?,?,?)");
		my_prepared_statement.setString(1, the_user.getFirstName());
		my_prepared_statement.setString(2, the_user.getLastName());
		my_prepared_statement.setString(3, the_user.getEmail());
		my_prepared_statement.execute();
		my_prepared_statement.close();
	}

	/**
	 * Updates a users information in the database given a new user object.
	 * @param the_oldUser The user to update.
	 * @param the_newUser The updated user information.
	 */
	public void updateUser(final User the_old_user, final User the_new_user) {
		int user_key = getUserKey(the_old_user);
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"UPDATE " + TABLE_NAME + " SET FirstName = ?, LastName = ?, Email = ? WHERE User = ?");
			my_prepared_statement.setString(1, the_new_user.getFirstName());
			my_prepared_statement.setString(2, the_new_user.getLastName());
			my_prepared_statement.setString(3, the_new_user.getEmail());
			my_prepared_statement.setInt(4, user_key);
			my_prepared_statement.executeUpdate();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: There was something wrong in the SQL syntax for updateUser()");
			System.out.println("ERROR DUMP: " + e.toString());
		}	
	}
	
	/**
	 * Finds the key of a given user in the database.
	 * @param the_user The user object used to find the key.
	 * @return the user's key.
	 */
	public int getUserKey(final User the_user) {
	    int user_key = 0;
	    if (the_user == null) {
	        throw new IllegalArgumentException("The user cannot be null");
	    }
        
		try {
			my_prepared_statement = getConnection().prepareStatement(
					 "SELECT User FROM " + TABLE_NAME + " WHERE FirstName = ? AND LastName = ?");
			my_prepared_statement.setString(1, the_user.getFirstName());
			my_prepared_statement.setString(2, the_user.getLastName());
			ResultSet query_result = my_prepared_statement.executeQuery();
			while (query_result.next()) {
				user_key = query_result.getInt("User");
			}
			
			query_result.close();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for getUserKey()");
			System.out.println("ERROR DUMP: " + e);
		}
		return user_key;
	}
	
	/**
	 * Overloaded
	 * Finds the key of a given users first and last name in the database.
	 * @param the_user The user object used to find the key.
	 * @return the user's key.
	 */
	public int getUserKey(final String the_first_name, final String the_last_name) {
		int user_key = 0;
		try {
			my_prepared_statement = getConnection().prepareStatement(
					 "SELECT User FROM " + TABLE_NAME + " WHERE FirstName = ? AND LastName = ?");
			my_prepared_statement.setString(1, the_first_name);
			my_prepared_statement.setString(2, the_last_name);
			ResultSet query_result = my_prepared_statement.executeQuery();
			while (query_result.next()) {
				user_key = query_result.getInt("User");
			}
			
			query_result.close();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for getUserKey()");
			System.out.println("ERROR DUMP: " + e);
		}
		return user_key;
	}
	
	/**
	 * Checks the database to see if a certain user exists.
	 * @param the_user The user to find in the database
	 * @return Returns true if the user is found, false otherwise.
	 */
	public boolean doesUserExist(final User the_user) {
		boolean user_exist = false;
		try {
			my_prepared_statement = getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE FirstName = ? AND LastName = ?");
			my_prepared_statement.setString(1, the_user.getFirstName());
			my_prepared_statement.setString(2, the_user.getLastName());
			ResultSet query_result = my_prepared_statement.executeQuery();
			user_exist = (query_result.next())? true : false;
			query_result.close();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for doesUserExist()");
			System.out.println("ERROR DUMP: " + e);
		}
		return user_exist;
	}
	
	/**
	 * Overloaded
	 * Checks the database to see if a certain user exists given their first and last name.
	 * @param the_user The user to find in the database
	 * @return Returns true if the user is found, false otherwise.
	 */
	public boolean doesUserExist(final String the_first_name, final String the_last_name) {
		boolean user_exist = false;
		try {
			my_prepared_statement = getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE FirstName = ? AND LastName = ?");
			my_prepared_statement.setString(1, the_first_name);
			my_prepared_statement.setString(2, the_last_name);
			ResultSet query_result = my_prepared_statement.executeQuery();
			user_exist = (query_result.next())? true : false;
			query_result.close();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for doesUserExist()");
			System.out.println("ERROR DUMP: " + e);
		}
		return user_exist;
	}

	/**
	 * Retrieves information from the database on a user and constructs a user object.
	 * @param the_user_key The primary key of the user to find (have to determine primary key first)
	 * @return A user object of that user's record.
	 */
	public User getUser(final int the_user_key) {
		User temp_user = null;
		try {
			my_prepared_statement = getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE User = ?");
			my_prepared_statement.setInt(1, the_user_key);
			ResultSet query_result = my_prepared_statement.executeQuery();
			temp_user = new User(query_result.getString("FirstName"), query_result.getString("LastName"), query_result.getString("Email"));
			query_result.close();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for getUser()");
			System.out.println("ERROR DUMP: " + e);
		}
		return temp_user;
	}
	
	/**
	 * Retrieves a user record from the database, given their first and last name
	 * @param the_first_name The user's first name.
	 * @param the_last_name The user's last name.
	 * @return The user object retrieved.
	 */
	public User getUser(final String the_first_name, final String the_last_name) {
		User temp_user = null;
		try {
			my_prepared_statement = getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE FirstName = ? AND LastName = ?");
			my_prepared_statement.setString(1, the_first_name);
			my_prepared_statement.setString(2, the_last_name);
			ResultSet query_result = my_prepared_statement.executeQuery();
			temp_user = new User(query_result.getString("FirstName"), query_result.getString("LastName"), query_result.getString("Email"));
			query_result.close();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for getUser()");
			System.out.println("ERROR DUMP: " + e);
		}
		return temp_user;
	}
}
