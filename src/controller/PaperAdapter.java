package controller;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Category;
import model.Paper;
import model.User;

/**
 * A paper adapter.
 * 
 * @author Tony Robinson
 * @version $Id: PaperAdapter.java 263 2013-06-09 00:46:12Z ajr42@uw.edu $
 */
public class PaperAdapter extends AbstractAdapter {
    /**
     * The name of the table.
     */
    private final String TABLE_NAME = "papers";
    
    /**
     * User adapter, used to get user ids and user objects.
     */
    private final UserAdapter my_user_adapter;
    
    /**
     * Statement to execute on the database.
     */
    private PreparedStatement my_prepared_statement;
    
    
    /**
     * Default constructor.
     */
    public PaperAdapter() {
        super();
    	my_user_adapter = new UserAdapter();
    }

    /**
     * Adds a paper to the database.
     * 
     * @param the_paper The paper to add.
     */
    public void addPaper(final Paper the_paper) {
        try {
            my_prepared_statement = getConnection()
                    .prepareStatement(
                            "INSERT INTO " + TABLE_NAME 
                            + " (Author, Title, Category, KeyWords,"
                            + "Abstract, FileName) VALUES (?, ?, ?, ?, ?, ?)");
            my_prepared_statement.setInt(1, my_user_adapter.getUserKey(the_paper.getAuthor()));
            my_prepared_statement.setString(2, the_paper.getTitle());
            my_prepared_statement.setString(3, the_paper.getCategory().getName());
            my_prepared_statement.setString(4, the_paper.getKeywords());
            my_prepared_statement.setString(5, the_paper.getAbstract());
            my_prepared_statement.setString(6, the_paper.getFile().getName());
            my_prepared_statement.execute();
            my_prepared_statement.close();
        } catch (SQLException e) {
            System.out
                    .println("ERROR: There was something wrong with the SQL syntax for addPaper()");
            System.out.println("ERROR DUMP: " + e);
        }
    }

    /**
     * Updates an existing paper given a new paper object.
     * NOTE: The author of a paper cannot be changed.
     * 
     * @param the_old_paper The paper to update.
     * @param the_new_paper The updated information.
     */
    public void updatePaper(final Paper the_old_paper, final Paper the_new_paper) {
    	int paper_key = getPaperKey(the_old_paper);
        try {
        	my_prepared_statement = getConnection().prepareStatement(
        			"UPDATE Papers SET Title = ?, Category = ?, " +
        			"KeyWords = ?, Abstract = ?, FileName = ? " +
        			"WHERE Paper = ?");
        	my_prepared_statement.setString(1, the_new_paper.getTitle());
        	my_prepared_statement.setString(2, the_new_paper.getCategory().getName());
        	my_prepared_statement.setString(3, the_new_paper.getKeywords());
        	my_prepared_statement.setString(4, the_new_paper.getAbstract());
        	my_prepared_statement.setString(5, the_new_paper.getFile().getName());
        	my_prepared_statement.setInt(6, paper_key);
        	my_prepared_statement.executeUpdate();
        	my_prepared_statement.close();
        } catch (SQLException e) {
        	System.out.println("ERROR: There was something wrong with the SQL syntax for updatePaper()");
        	System.out.println("ERROR DUMP: " + e);
        }
    }
    
    
    /**
     * Retrieves a paper from the database, builds a 
     * paper object and returns that paper.
     * 
     * @param the_paper_key The key of the paper to find
     * 
     * @return The paper object built from database.
     */
    public Paper getPaper(final int the_paper_key) {
        Paper paper = new Paper();

        try {
            final PreparedStatement statement = getConnection()
                    .prepareStatement(
                            "SELECT * FROM " + TABLE_NAME + " WHERE Paper = ?");
            statement.setInt(1, the_paper_key);
            ResultSet query_result = statement.executeQuery();

            final UserAdapter ua = new UserAdapter();

            paper.setAuthor(ua.getUser(Integer.parseInt(query_result
                    .getString("Author"))));
            paper.setTitle(query_result.getString("Title"));
            paper.setAbstract(query_result.getString("Abstract"));
            paper.setCategory(new Category(query_result.getString("Category")));
            paper.setFile(new File(query_result.getString("FileName"))); // Only Returns FileName, does not get file from disk yet.
            //paper.setFileName();
            paper.setKeywords(query_result.getString("KeyWords"));
            query_result.close();
            statement.close();
        } catch (final SQLException e) {
            System.out
                    .println("ERROR: there was something wrong in the SQL syntax for getPaper()");
            System.out.println("ERROR DUMP: " + e);
        }
        return paper;
    }
    
    /**
     * Retrieves a list of papers given a user.
     * @param the_user The user to get a list of papers for.
     */
    public List<Paper> getPapers(final User the_user) {
    	ArrayList<Paper> paper_list = new ArrayList<Paper>();
    	final int user_key = my_user_adapter.getUserKey(the_user);
    	try {
    		my_prepared_statement = getConnection().prepareStatement(
    				"SELECT * FROM Papers WHERE Author = ?");
    		my_prepared_statement.setInt(1, user_key);
    		ResultSet query_result = my_prepared_statement.executeQuery();
    		while (query_result.next()) {
    			paper_list.add(new Paper (
    					query_result.getString("Title"),
    					my_user_adapter.getUser(user_key),
    					new Category(query_result.getString("Category")),
    					query_result.getString("KeyWords"),
    					query_result.getString("Abstract"),
    					new File(query_result.getString("FileName"))));
    		}
    		query_result.close();
    		my_prepared_statement.close();
    	} catch (SQLException e) {
    		System.out.println("ERROR: there was something wrong in the SQL syntax for getPapers()");
    System.out.println("ERROR DUMP: " + e);
    	}
    	return paper_list;
    }
    
    /**
     * Retrieves a list of papers given a user.
     */
    public List<Paper> getPapers() {
        ArrayList<Paper> paper_list = new ArrayList<Paper>();
        try {
            my_prepared_statement = getConnection().prepareStatement(
                    "SELECT * FROM Papers");
            ResultSet query_result = my_prepared_statement.executeQuery();
            while (query_result.next()) {
                paper_list.add(new Paper (
                        query_result.getString("Title"),
                        my_user_adapter.getUser(query_result.getInt("Author")),
                        new Category(query_result.getString("Category")),
                        query_result.getString("KeyWords"),
                        query_result.getString("Abstract"),
                        new File(query_result.getString("FileName"))));
            }
            query_result.close();
            my_prepared_statement.close();
        } catch (SQLException e) {
            System.out.println("ERROR: there was something wrong in the SQL syntax for getPapers()");
    System.out.println("ERROR DUMP: " + e);
        }
        return paper_list;
    }

    /**
     * Retrieves the primary key of a given paper.
     * @param the_paper The paper to find a primary key for.
     * @return The primary key of the paper.
     */
    public int getPaperKey(final Paper the_paper) {
    	int paper_key = 0;
    	try {
    		my_prepared_statement = getConnection().prepareStatement(
    				"SELECT Paper FROM " + TABLE_NAME + " WHERE Author = ? AND " +
    						"Title = ? AND Category = ?AND KeyWords = ? AND " +
    						"Abstract = ? AND FileName = ?");
    		my_prepared_statement.setInt(1, my_user_adapter.getUserKey(the_paper.getAuthor()));
    		my_prepared_statement.setString(2, the_paper.getTitle());
    		my_prepared_statement.setString(3, the_paper.getCategory().getName());
    		my_prepared_statement.setString(4, the_paper.getKeywords());
    		my_prepared_statement.setString(5, the_paper.getAbstract());
    		my_prepared_statement.setString(6, the_paper.getFile().getName());
    		ResultSet query_result = my_prepared_statement.executeQuery();
    		paper_key = query_result.getInt("Paper");
    		query_result.close();
    		my_prepared_statement.close();
    				
    	} catch (SQLException e) {
    		System.out.println("ERROR: there was something wrong in the SQL syntax for getPaperKey()");
    		System.out.println("ERROR DUMP: " + e);
    	}
    	return paper_key;
    }
    /**
     * Deletes a paper from the database.
     *  
     * @param the_paper The paper to delete.
     */
    public void deletePaper(final Paper the_paper) {
        int paper_key = getPaperKey(the_paper);
        try {
        	my_prepared_statement = getConnection().prepareStatement(
        			"DELETE FROM Papers WHERE Paper = ?");
        	my_prepared_statement.setInt(1, paper_key);
        	my_prepared_statement.executeUpdate();
        	my_prepared_statement.close();
        } catch (SQLException e) {
        	System.out.println("ERROR: there was something wrong in the SQL syntax for deletePaper()");
    		System.out.println("ERROR DUMP: " + e);
        }
    }
    
    /**
     * Checks to see if a given paper exists in the database.
     * @param the_paper The paper to check for
     * @return True if the paper is found, false otherwise.
     */
    public boolean doesPaperExist (final Paper the_paper) {
    	final int paper_key = getPaperKey(the_paper);
    	boolean paper_exist = false;
    	try {
    		my_prepared_statement = getConnection().prepareStatement(
    				"SELECT * FROM " + TABLE_NAME + " WHERE Paper = ?");
    		my_prepared_statement.setInt(1, paper_key);
    		ResultSet query_result = my_prepared_statement.executeQuery();
    		paper_exist = (query_result.next())? true : false;
    		query_result.close();
    		my_prepared_statement.close();
    	} catch (SQLException e) {
    		System.out.println("ERROR: there was something wrong in the SQL syntax for doesPaperExist()");
    		System.out.println("ERROR DUMP: " + e);
    	}
    	return paper_exist;
    }
}
