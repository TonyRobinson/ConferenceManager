package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Conference;
import model.ConferenceRole;
import model.Role;
import model.User;

/**
 * Provides connectivity to the database for the ConferenceRoleAdapter table.
 * 
 * @author Tony Robinson
 * @version $Id: ConferencePaperAdapter.java 289 2013-06-10 11:32:09Z ajr42@uw.edu $
 */
public class ConferenceRoleAdapter extends AbstractAdapter {
	
	/**
	 * The statement to execute on the database
	 */
	protected PreparedStatement my_prepared_statement;
	
	/**
	 * The conference adapter to get information on conferences
	 */
	protected ConferenceAdapter my_conference_adapter;
	
	/**
	 * The user adapter to get information on users
	 */
	protected UserAdapter my_user_adapter;
	
	/**
	 * The foreign key of a user.
	 */
	protected int user_key;
	
	/**
	 * The foreign key of a conference
	 */
	protected int conference_key;
	
	/**
	 * Default constructor
	 */
	public ConferenceRoleAdapter() {
	    super();
		my_user_adapter = new UserAdapter();
		my_conference_adapter = new ConferenceAdapter();
	}
	
	
	/**
	 * T
	 */
	public void setRole(final ConferenceRole the_conference_role) {
		if (doesRoleExist(the_conference_role)) {
			updateRole(the_conference_role);
		} else {
			addRole(the_conference_role);
		}
	}
	
	/**
	 * Inserts a record into the ConferenceRole table in the database
	 * @param the_user The user to tie this role to.
	 * @param the_conference The conference to tie this role to.
	 * @param the_role The role tied to the user and conference.
	 */
	private void addRole(final ConferenceRole the_conference_role) {
		user_key = my_user_adapter.getUserKey(the_conference_role.getUser());
		conference_key = my_conference_adapter.getConferenceKey(the_conference_role.getConference());
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"INSERT INTO ConferenceRoles (User, Conference, Role) VALUES (?,?,?)");
			my_prepared_statement.setInt(1, user_key);
			my_prepared_statement.setInt(2, conference_key);
			my_prepared_statement.setString(3, the_conference_role.getRole().getName());
			my_prepared_statement.executeUpdate();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: There was something wrong with addRole()");
			System.out.println("ERROR DUMP: " + e);
		}	
	}
	
	/**
	 * Changes the role a user has in the database
	 * @param the_user The user tied to the role
	 * @param the_conference The conference tied to the role
	 * @param the_role A new role the user should be given for a conference
	 */
	private void updateRole(final ConferenceRole the_conference_role) {
		user_key = my_user_adapter.getUserKey(the_conference_role.getUser());
		conference_key = my_conference_adapter.getConferenceKey(the_conference_role.getConference());
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"UPDATE ConferenceRoles SET Role = ? WHERE User = ? AND Conference = ?");
			my_prepared_statement.setString(1, the_conference_role.getRole().getName());
			my_prepared_statement.setInt(2, user_key);
			my_prepared_statement.setInt(3, conference_key);
			my_prepared_statement.executeUpdate();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: There was something wrong with updateRole()");
			System.out.println("ERROR DUMP: " + e);
		}	
	}
	
	public List<Role> getRoles(final User the_user, final Conference the_conference) {
		List<Role> role_list = new ArrayList<Role>();
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"SELECT * From ConferenceRoles WHERE User = ? AND Conference = ?");
			my_prepared_statement.setInt(1, my_user_adapter.getUserKey(the_user));
			my_prepared_statement.setInt(2, my_conference_adapter.getConferenceKey(the_conference));
			ResultSet query_result = my_prepared_statement.executeQuery();
			while (query_result.next()) {
				role_list.add(new Role(query_result.getString("Role")));
			}
			query_result.close();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: There was something wrong with getRoles()");
			System.out.println("ERROR DUMP: " + e);
		}
		return role_list;
		
	}
	
	/**
	 * Retrieves the primary key of a role given the user and conference.
	 * @param the_user The user tied to a role.
	 * @param the_conference The conference tied to a role.
	 * @return The role of a user in a given conference.
	 */
	public int getRoleKey(User the_user, Conference the_conference) {
		int role_key = 0;
		user_key = my_user_adapter.getUserKey(the_user);
		conference_key = my_conference_adapter.getConferenceKey(the_conference);
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"SELECT ConferenceRole FROM ConferenceRoles WHERE User = ? AND Conference = ?");
			my_prepared_statement.setInt(1, user_key);
			my_prepared_statement.setInt(2, conference_key);
			ResultSet query_result = my_prepared_statement.executeQuery();
			role_key = query_result.getInt("ConferenceRole");
			query_result.close();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: There was something wrong with the SELECT on ConferenceRole");
			System.out.println("ERROR DUMP: " + e);
		}
		return role_key;
	}
	
	/**
	 * Checks if a role exists so we are not inserting multiple roles for the same user.
	 * @param the_conference_role The ConferenceRole object
	 * @return True if the role exists, false otherwise.
	 */
	public boolean doesRoleExist(ConferenceRole the_conference_role) {
		boolean role_exist = false;
		try {
			my_prepared_statement = getConnection().prepareStatement("SELECT * FROM ConferenceRoles WHERE User = ? AND Conference = ?");
			my_prepared_statement.setInt(1, my_user_adapter.getUserKey(the_conference_role.getUser()));
			my_prepared_statement.setInt(2, my_conference_adapter.getConferenceKey(the_conference_role.getConference()));
			ResultSet query_result = my_prepared_statement.executeQuery();
			role_exist = (query_result.next())? true : false;
			query_result.close();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for doesRoleExist()");
			System.out.println("ERROR DUMP: " + e);
		}
		return role_exist;
	}
	
}
