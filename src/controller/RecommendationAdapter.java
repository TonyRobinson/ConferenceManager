package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Conference;
import model.Paper;
import model.Recommendation;

/**
 * Provides database connectivity and functionality for the recommendation table.
 * 
 * @author Tony Robinson
 * @version $Id: PaperAdapter.java 263 2013-06-10 9:46:23Z ajr42@uw.edu $
 */
public class RecommendationAdapter extends AbstractAdapter {
	
	/**
	 * Holds the statement to execute on the database.
	 */
	private PreparedStatement my_prepared_statement;
	
	/**
	 * Conference Paper adapter, retrieves ConferencePaper key
	 */
	private ConferencePaperAdapter my_conference_paper_adapter;
	
	private UserAdapter my_user_adapter;

	/**
	 * Default constructor
	 */
	public RecommendationAdapter() {
	    super();
		my_conference_paper_adapter = new ConferencePaperAdapter();
		my_user_adapter = new UserAdapter();
	}
	
	/**
	 * Inserts a recommendation to the database for a given on a given conference.
	 * @param the_recommendation The recommendation object to insert.
	 * @param the_paper The paper that the recommendation is for.
	 * @param the_conference The conference that the paper is submitted to.
	 */
	public void addRecommendation(final Recommendation the_recommendation) {
		int conference_paper_key = my_conference_paper_adapter
				.getConferencePaperKey(the_recommendation.getConference(), the_recommendation.getPaper());
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"INSERT INTO Recommendations (Recommender, ConferencePaper, " +
					"RecommendationInt, Rationale) VALUES (?, ?, ?, ?)");
			my_prepared_statement.setInt(1, my_user_adapter.getUserKey(the_recommendation.getReviewer()));
			my_prepared_statement.setInt(2, conference_paper_key);
			my_prepared_statement.setInt(3, the_recommendation.getRecommendation());
			my_prepared_statement.setString(4, the_recommendation.getRational());
			my_prepared_statement.executeUpdate();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for addReccomendation()");
			System.out.println("ERROR DUMP: " + e);
		}
	}
	
	/**
	 * Deletes a recommendation from the database for a given on a given conference.
	 * NOT YET IMPLEMENTED, DO NOT USE.
	 * @param the_recommendation The recommendation object to insert.
	 * @param the_paper The paper that the recommendation is for.
	 * @param the_conference The conference the paper is submitted to.
	 */
	public void removeRecommendation(final Recommendation the_recommendation) {
		//TO-DO finish
	}
	
	/**
	 * Retrieves the key of a recommendation, 
	 * @param the_recommendation The recommendation object to insert.
	 * @param the_paper The paper that the recommendation is for.
	 * @param the_conference The conference the paper is submitted to.
	 * @return The primary key of the recommendation.
	 */
	public int getRecommendationKey(final Recommendation the_recommendation) {
		int recommendation_key = 0;
		final int conference_paper_key = my_conference_paper_adapter
				.getConferencePaperKey(the_recommendation.getConference(), the_recommendation.getPaper());
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"SELECT Recommendation FROM Recommendations WHERE " +
					"ConferencePaper = ? AND Recommender = ? AND " +
					"RecommendationInt = ? AND Rationale =?");
			my_prepared_statement.setInt(1, conference_paper_key);
			my_prepared_statement.setInt(2, my_user_adapter.getUserKey(the_recommendation.getReviewer()));
			my_prepared_statement.setInt(3, the_recommendation.getRecommendation());
			my_prepared_statement.setString(4, the_recommendation.getRational());
			ResultSet query_result = my_prepared_statement.executeQuery();
			recommendation_key = query_result.getInt("Recommendation");
			query_result.close();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for getReccomendationKey()");
			System.out.println("ERROR DUMP: " + e);
		}
		return recommendation_key;
	}
	
	/**
	 * Retrieves a list of recommendations for a paper on a given conference.
	 * @param the_paper The paper to get a list of recommendations for.
	 * @param the_conference The conference the paper is submitted to.
	 * @return List of recommendations for a paper in a conference.
	 */
	public List<Recommendation> getRecommendations(final Paper the_paper, final Conference the_conference) {
		ArrayList<Recommendation> recommendation_list = new ArrayList<Recommendation>();
		int conference_paper_key = my_conference_paper_adapter
				.getConferencePaperKey(the_conference, the_paper);
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"SELECT * FROM Recommendations WHERE ConferencePaper = ?");
			my_prepared_statement.setInt(1, conference_paper_key);
			ResultSet query_result = my_prepared_statement.executeQuery();
			while (query_result.next()) {
				recommendation_list.add(new Recommendation(
						my_user_adapter.getUser(query_result.getInt("Recommender")),
						the_conference, //hotfix for complex logic to retrieve conference object
						the_paper, //hotfix for complex logic to retrieve paper object
						query_result.getInt("RecommendationInt"),
						query_result.getString("Rationale")));
			}
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for getReccomendations()");
			System.out.println("ERROR DUMP: " + e);
		}
		
		return recommendation_list;
	}
	
	/**
	 * Checks if a recommendation exists in the database.
	 * @param the_recommendation The recommendation to check for.
	 * @return True if recommendation exists, false otherwise.
	 */
	public boolean doesRecommendationExist(final Recommendation the_recommendation) {
		final int recommendation_key = getRecommendationKey(the_recommendation);
		boolean recommendation_exist = false;
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"SELECT * FROM Recommendations WHERE Recommendation = ?");
			my_prepared_statement.setInt(1, recommendation_key);
			ResultSet query_result = my_prepared_statement.executeQuery();
			recommendation_exist = (query_result.next())? true : false;
			query_result.close();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for doesRecommendationExist()");
			System.out.println("ERROR DUMP: " + e);
		}
		
		return recommendation_exist;
	}
}
