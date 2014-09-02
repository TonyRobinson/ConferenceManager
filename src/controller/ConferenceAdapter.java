package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Conference;

/**
 * Provides connectivity to the database for conference objects.
 * 
 * @author  Tony Robinson
 * @version $Id: ConferenceAdapter.java 292 2013-06-09 22:43:51Z ajr42@uw.edu $
 */
public class ConferenceAdapter extends AbstractAdapter {
    /**
     * The name of the table.
     */
    private final String TABLE_NAME = "conferences";
    
	/**
	 * The statement to execute on the database
	 */
	protected PreparedStatement my_prepared_statement;
	
	/**
	 * The user adapter, used for retrieval of program chair key from database
	 */
	protected UserAdapter my_user_adapter;
	
	/**
	 * Default constructor
	 */
	public ConferenceAdapter() {
	    super();
		my_user_adapter = new UserAdapter();
	}
	
	/**
	 * Adds a conference to the database.
	 * @param the_conference
	 */
	public void addConference(final Conference the_conference) {
		final int program_chair_key = my_user_adapter.getUserKey(the_conference.getChair());
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"INSERT INTO " + TABLE_NAME + " " +
					"(ConferenceName, ProgramChair, StartDate, EndDate)VALUES (?,?,?,?)");
			my_prepared_statement.setString(1, the_conference.getName());
			my_prepared_statement.setInt(2, program_chair_key);
			my_prepared_statement.setLong(3, the_conference.getStartDate().getTime() / 1000);
			my_prepared_statement.setLong(4, the_conference.getEndDate().getTime() / 1000);
			my_prepared_statement.executeUpdate();
			my_prepared_statement.close();
		} catch (final SQLException e) {
			System.out.println("ERROR: There was something wrong with the INSERT on Conference");
			System.out.println("ERROR DUMP: " + e);
		}
	}
	
	/**
	 * Updates information on the old conference in the database given data in a new conference.
	 * @param the_old_conference The conference to update
	 * @param the_new_conference The updated conference
	 */
	public void updateConference(final Conference the_old_conference, Conference the_new_conference) {
		int program_chair_key = my_user_adapter.getUserKey(the_new_conference.getChair());
		int conference_key = getConferenceKey(the_old_conference);
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"UPDATE " + TABLE_NAME + " SET ConferenceName = ?, ProgramChair = ?, StartDate = ?, " +
					"EndDate = ? WHERE Conference = ?");
			my_prepared_statement.setString(1, the_new_conference.getName());
			my_prepared_statement.setInt(2, program_chair_key);
            my_prepared_statement.setLong(3, the_new_conference.getStartDate().getTime() / 1000);
            my_prepared_statement.setLong(4, the_new_conference.getEndDate().getTime() / 1000);
			my_prepared_statement.setInt(5, conference_key);
			my_prepared_statement.executeUpdate();
			my_prepared_statement.close();
		} catch (final SQLException e) {
			System.out.println("ERROR: There was something wrong with the UPDATE on Conference");
			System.out.println("ERROR DUMP: " + e);
		} 
	}
	/**
	 * Gets the Conference Key from the database.
	 * @param the_conference The conference to find a key for
	 * @return The conference key (unique identifier)
	 */
	public int getConferenceKey(final Conference the_conference) {
		int conference_key = 0;
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"SELECT Conference FROM " + TABLE_NAME + " WHERE ConferenceName = ?");
			my_prepared_statement.setString(1, the_conference.getName());
			ResultSet query_result = my_prepared_statement.executeQuery();
			if (query_result.next()) {
				conference_key = query_result.getInt("Conference");
			} else {
				conference_key = 0;
			}
			query_result.close();
			my_prepared_statement.close();
		} catch (final SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for getConferenceKey()");
			System.out.println("ERROR DUMP: " + e);
		}
		return conference_key;
	}
	
	/**
	 * Queries the database with a given conference to check for existence.
	 * @param the_conference The conference to find
	 * @return True if conference is found, false if conference is not found in database.
	 */
	public boolean doesConferenceExist(final Conference the_conference) {
		int conference_key = getConferenceKey(the_conference);
		boolean conference_exist = false;
		try {
			my_prepared_statement = getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE Conference = ?");
			my_prepared_statement.setInt(1, conference_key);
			ResultSet query_result = my_prepared_statement.executeQuery();
			conference_exist = (query_result.next())? true : false;
			query_result.close();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for doesConferenceExist()");
			System.out.println("ERROR DUMP: " + e);
		}
		return conference_exist;
	}
	
	/**
	 * Queries the database to retrieve all conferences from the conference table.
	 * @return A list of Conference objects from the database.
	 */
	public List<Conference> getAllConferences() {
		final List<Conference> conference_list = new ArrayList<Conference>();
		Conference temp_conference;
		try {
			my_prepared_statement = getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME);
			ResultSet query_result = my_prepared_statement.executeQuery();
			while (query_result.next()) {
				temp_conference = new Conference(
						query_result.getString("ConferenceName"),
						query_result.getDate("StartDate"),
						query_result.getDate("EndDate"),
						my_user_adapter.getUser(query_result.getInt("ProgramChair"))); //Gets a User object

				conference_list.add(temp_conference);
			}
			query_result.close();
			my_prepared_statement.close();
		} catch (final SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for SELECT from CONFERENCE");
			System.out.println("ERROR DUMP: " + e);
		} 
		return conference_list;
	}
	
	/**
	 * Retrieves a given Conference.
	 * 
	 * @param the_conference_key
	 * 
	 * @return Conference
	 * 
	 * @throws SQLException
	 */
	public Conference getConference(final int the_conference_key) throws SQLException {
       Conference c;
       my_prepared_statement = getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE Conference = ?");
       my_prepared_statement.setInt(1, the_conference_key);

       ResultSet query_result = my_prepared_statement.executeQuery();
       c = new Conference(
               query_result.getString("ConferenceName"),
               query_result.getDate("StartDate"),
               query_result.getDate("EndDate"),
               my_user_adapter.getUser(query_result.getInt("ProgramChair"))); //Gets a User object

       query_result.close();
       my_prepared_statement.close();
       
       return c;
	}
}
