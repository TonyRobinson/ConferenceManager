package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import model.Conference;
import model.Paper;
import model.Review;

/**
 * Contains a Review Adapter.
 * 
 * @author  Tony Robinson
 * @version $Id: ReviewAdapter.java 224 2013-06-08 18:19:06Z ajr42@uw.edu $
 */
public class ReviewAdapter extends AbstractAdapter {
    /**
     * The name of the table.
     */
    private final String TABLE_NAME = "reviews";
    
	/**
	 * The statement to be executed on the database.
	 */
	private PreparedStatement my_prepared_statement;
	
	/**
	 * The conference paper adapter, used to get primary key of ConferenceAdapter
	 */
	private ConferencePaperAdapter my_conference_paper_adapter;
	
	/**
	 * The user adapter, used to get the primary key of a user
	 */
	private UserAdapter my_user_adapter;
	
	/**
	 * Default constructor.
	 */
	public ReviewAdapter() {
	    super();
		my_conference_paper_adapter = new ConferencePaperAdapter();
	}
	
	/**
	 * Adds a review to the database.
	 * @param the_review The review to add.
	 */
	public void addReview(Review the_review) {
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"INSERT INTO " + TABLE_NAME +
					"(Reviewer, ConferencePaper, Comments, Summary, SummaryComments) " +
					"VALUES (?, ?, ?, ?, ?)");
			my_prepared_statement.setInt(1, my_user_adapter.getUserKey(the_review.getReviewer()));
			my_prepared_statement.setInt(2, my_conference_paper_adapter.
					getConferencePaperKey(the_review.getConference(), the_review.getPaper()));
			my_prepared_statement.setString(3, commentListToString(the_review));
			my_prepared_statement.setInt(4, the_review.getSummary());
			my_prepared_statement.setString(5, the_review.getSummaryComments());
			my_prepared_statement.executeUpdate();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for addReview()");
			System.out.println("ERROR DUMP: " + e);
		}
	}
	
	/**
	 * Retrieves a list of reviews from the database for a paper and conference.
	 * @param the_paper The paper the reviews are for.
	 * @param the_conference The conference the paper is submitted to.
	 * @return A list of reviews for a paper submitted to a conference.
	 */
	public List<Review> getReviews(Paper the_paper, Conference the_conference) {
		List<Review> review_list = new ArrayList<Review>();
		int conference_paper_key = 
				my_conference_paper_adapter.getConferencePaperKey(the_conference, the_paper);
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"SELECT * FROM " + TABLE_NAME + " WHERE ConferencePaper = ?");
			my_prepared_statement.setInt(1, conference_paper_key);
			ResultSet query_result = my_prepared_statement.executeQuery();
			while (query_result.next()) {
				review_list.add(new Review(
						commentStringToList(query_result.getString("Comments")),
						my_user_adapter.getUser(query_result.getInt("Reviewer")),
						the_paper,
						the_conference,
						query_result.getInt("Summary"),
						query_result.getString("SummaryContents")));				
			}
			query_result.close();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for getReviews");
			System.out.println("ERROR DUMP: " + e);
		}
		return review_list;
	}

	/**
	 * Retrieves the primary key for a review.
	 * @param the_review The review to find.
	 */
	public int getReviewKey(Review the_review) {
		int review_key = 0;
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"SELECT Review FROM " + TABLE_NAME + " WHERE Reviewer = ? " +
					"AND ConferencePaper = ? AND Comments = ? " +
					"AND Summary = ? AND SummaryComments = ? ");
			my_prepared_statement.setInt(1, my_user_adapter.getUserKey(the_review.getReviewer()));
			my_prepared_statement.setInt(2, my_conference_paper_adapter
					.getConferencePaperKey(the_review.getConference(), the_review.getPaper()));
			my_prepared_statement.setString(3, commentListToString(the_review));
			my_prepared_statement.setInt(4, the_review.getSummary());
			my_prepared_statement.setString(5, the_review.getSummaryComments());
			ResultSet query_result = my_prepared_statement.executeQuery();
			review_key = query_result.getInt("Review");
			query_result.close();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for getReviewKey()");
			System.out.println("ERROR DUMP: " + e);
		}
		return review_key;
	}
	
	/**
	 * Checks if a review exists in the database.
	 * @param the_review The review object to look for.
	 * @return True if the review exists, false otherwise.
	 */
	public boolean doesReviewExist(Review the_review) {
		int review_key = getReviewKey(the_review);
		boolean review_exist = false;
		try {
			my_prepared_statement = getConnection().prepareStatement(
					"SELECT * FROM " + TABLE_NAME + " WHERE Review = ?");
			my_prepared_statement.setInt(1, review_key);
			ResultSet query_result = my_prepared_statement.executeQuery();
			review_exist = (query_result.next())? true : false;
			query_result.close();
			my_prepared_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: there was something wrong in the SQL syntax for doesReviewExist");
			System.out.println("ERROR DUMP: " + e);
		}
		return review_exist;
	}
	
	/**
	 * Converts a List of integers for comments to a comma delimited string.
	 * @param the_review The review object
	 * @return A string of integer comments. Example: "1,5,2,4,3"
	 */
	private String commentListToString(Review the_review) {
		List<Integer> comment_list = (ArrayList<Integer>)the_review.getComments();
		StringBuilder comment_string_builder = new StringBuilder();
		for (int i = 0; i < comment_list.size(); i++) {
			comment_string_builder.append(comment_list.get(i));
			comment_string_builder.append(",");
		}
		return comment_string_builder.toString();
	}
	
	/**
	 * Converts a comma delimited string of comments to a list of integers.
	 * @param the_comments The comma delimited string of comments. Example: "1,5,2,4,3".
	 * @return A list of integer comments.
	 */
	private List<Integer> commentStringToList(String the_comments) {
		StringTokenizer comment_tokens = new StringTokenizer(the_comments, ",");
		List<Integer> comment_list = new ArrayList<Integer>();
		while (comment_tokens.hasMoreTokens()) {
			comment_list.add(Integer.parseInt(comment_tokens.nextToken()));
		}
		return comment_list;
	}
}
