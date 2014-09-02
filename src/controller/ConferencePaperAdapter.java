package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Conference;
import model.Paper;
import model.User;

/**
 * Contains a ConferencePaper Adapter.
 * 
 * @author  Tony Robinson
 * @version $Id: ConferencePaperAdapter.java 289 2013-06-09 21:57:03Z ajr42@uw.edu $
 */
public class ConferencePaperAdapter extends AbstractAdapter{
    /**
     * The name of the table.
     */
    private final String TABLE_NAME = "conferencepapers";
    
	/**
	 * The sql statement to execute on the database
	 */
	private PreparedStatement my_prepared_statement;
	
	/**
	 * The conference adapter, retrieves conference information (keys)
	 */
	private ConferenceAdapter my_conference_adapter;
	
	/**
	 * The paper adapters, retrieves paper information (keys)
	 */
	private PaperAdapter my_paper_adapter;
	
	/**
	 * Default constructor
	 */
	public ConferencePaperAdapter() {
	    super();
		my_conference_adapter = new ConferenceAdapter();
		my_paper_adapter = new PaperAdapter();
	}
	
	/**
	 * Adds a paper to a given conference
	 * @param the_paper The paper to add to a conference
	 * @param the_conference The conference to add the paper to.
	 */
	public void addConferencePaper(final Paper the_paper, 
			final Conference the_conference, final int the_decision) {
		int paper_key = my_paper_adapter.getPaperKey(the_paper);
		int conference_key = my_conference_adapter.getConferenceKey(the_conference);
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"INSERT INTO " + TABLE_NAME + " (Paper, Conference, Decision) VALUES (?,?,?)");
			my_prepared_statement.setInt(1, paper_key);
			my_prepared_statement.setInt(2, conference_key);
			my_prepared_statement.setInt(3, 0);
			my_prepared_statement.executeUpdate();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for addConferencePaper()");
			System.out.println("ERROR DUMP: " + e);
		}
	}
	
	/**
	 * Un-submits a paper from a conference.
	 * @param the_paper The paper to un-submit.
	 * @param the_conference The conference to un-submit from.
	 */
	public void removeConferencePaper(final Paper the_paper, final Conference the_conference) {
		final int conference_paper_key = getConferencePaperKey(the_conference, the_paper);
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"DELETE FROM ConferencePapers WHERE ConferencePaper = ?");
			my_prepared_statement.setInt(1, conference_paper_key);
			my_prepared_statement.executeUpdate();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for removeConferencePaper()");
			System.out.println("ERROR DUMP: " + e);
		}
	}
	
	/**
	 * Allows the decision of a conference paper to be updated.
	 * @param the_paper The paper to update the decision on.
	 * @param the_conference The conference the paper is submitted to.
	 * @param the_decision The decision on that paper.
	 */
	public void updateDecision(final Paper the_paper, final Conference the_conference, final int the_decision) {
		final int conference_paper_key = getConferencePaperKey(the_conference, the_paper);
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"UPDATE " + TABLE_NAME + " SET Decision = ? WHERE ConferencePaper = ?");
			my_prepared_statement.setInt(1, the_decision);
			my_prepared_statement.setInt(2, conference_paper_key);
			my_prepared_statement.executeUpdate();
			my_prepared_statement.close();
			} catch (SQLException e) {
				System.out.println("ERROR: there was something wrong in the SQL syntax for updateDecision()");
				System.out.println("ERROR DUMP: " + e);
			}
	}
	
	/**
	 * Returns a list of papers from a given conference.
	 * @param the_conference The conference to get a list of papers from.
	 * @return A list of papers from the given conference.
	 */
	public List<Paper> getConferencePapers(final Conference the_conference) {
		final int conference_key = my_conference_adapter.getConferenceKey(the_conference);
		ArrayList<Paper> conference_papers = new ArrayList<Paper>();
		try {
			my_prepared_statement = getConnection().prepareStatement( 
					"SELECT Paper FROM " + TABLE_NAME + " WHERE Conference = ?");
			my_prepared_statement.setInt(1, conference_key);
			ResultSet query_result = my_prepared_statement.executeQuery();
			while (query_result.next()) {
				conference_papers.add(my_paper_adapter.getPaper(query_result.getInt("Paper")));
			}
			query_result.close();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for getConferencePapers()");
			System.out.println("ERROR DUMP: " + e);
		}
		//SELECT p.* from papers p LEFT OUTER JOIN conferencepapers cp on p.Paper = cp.Paper Where Conference=1
		return conference_papers; //return stub
	}
	
	/**
	 * Retrieves the primary key for a conference paper record from the database.
	 * @param the_conference The conference that a paper was submitted to.
	 * @param the_paper The paper submitted
	 * @return The primary key of this conference paper.
	 */
	public int getConferencePaperKey(final Conference the_conference, final Paper the_paper) {
		int conference_paper_key = 0;
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"SELECT ConferencePaper FROM " + TABLE_NAME + " " +
					"WHERE Paper = ? AND Conference = ?");
			my_prepared_statement.setInt(1,my_paper_adapter.getPaperKey(the_paper));
			my_prepared_statement.setInt(2, my_conference_adapter.getConferenceKey(the_conference));
			ResultSet query_result = my_prepared_statement.executeQuery();
			conference_paper_key = query_result.getInt("ConferencePaper");
			query_result.close();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for getPaperKey()");
			System.out.println("ERROR DUMP: " + e);
		}
		return conference_paper_key;
	}
	
	/**
	 * Checks if a paper is submitted to a certain conference
	 * @param the_conference The conference to look in for a paper.
	 * @param the_paper The paper that was submitted
	 * @return True if paper exists in conference, false otherwise.
	 */
	public boolean doesConferencePaperExist(final Conference the_conference, final Paper the_paper) {
		int conference_paper_key = getConferencePaperKey(the_conference, the_paper);
		boolean conference_paper_exist = false;
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"SELECT * FROM " + TABLE_NAME + " WHERE ConferencePaper = ?");
			my_prepared_statement.setInt(1, conference_paper_key);
			ResultSet query_result = my_prepared_statement.executeQuery();
			conference_paper_exist = (query_result.next())? true : false;
			query_result.close();
			my_prepared_statement.close();
		} catch (SQLException e){
			System.out.println("ERROR: there was something wrong in the SQL syntax for doesConferencePaperExist()");
			System.out.println("ERROR DUMP: " + e);
		}
		return conference_paper_exist;
	}
	
    /**
     * Counts the number of papers a user has submitted to a conference.
     * 
     * @param the_conference The conference to look in for a paper.
     * @param the_user the user that submitted the paper.
     * 
     * @return int a count of papers.
     * 
     * @throws SQLException 
     */
    public int countConferencePapers(final Conference the_conference, final User the_user) throws SQLException {
        int num_rows;
        UserAdapter ua = new UserAdapter();
        
        my_prepared_statement = getConnection().prepareStatement(
                "SELECT COUNT(DISTINCT p.paper) FROM papers p JOIN " + TABLE_NAME + " ON p.Paper = cp.Paper WHERE Author = ?");
        my_prepared_statement.setInt(1, ua.getUserKey(the_user));
        ResultSet query_result = my_prepared_statement.executeQuery();
        num_rows = query_result.getInt(1);
        query_result.close();
        my_prepared_statement.close();
        return num_rows;
    }
}
