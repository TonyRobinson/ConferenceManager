package controller;

import java.sql.Connection;

/**
 * An abstract adapter.
 * 
 * @author  Tony Robinson
 * @version $Id: AbstractAdapter.java 224 2013-06-08 18:19:06Z ajr42@uw.edu $
 */
public abstract class AbstractAdapter {
    /**
     * Contains a connection to the database.
     */
    private static ConferenceDatabase my_database;
    
    /**
     * Constructs a new AbstractAdapter.
     */
    public AbstractAdapter() {
        if (null == my_database) {
            my_database = new ConferenceDatabase();
            my_database.initializeDriver();
        }
    }
    
    /**
     * Constructs a new AbstractAdapter.
     * 
     * @param the_database
     */
    public AbstractAdapter(final ConferenceDatabase the_database) {
        my_database = the_database;
    }
    
    /**
     * Sets the ConferenceDatabase.
     * 
     * @param the_database
     */
    public static void setConferenceDatabase(final ConferenceDatabase the_database) {
        my_database = the_database;
    }
    
    /**
     * Returns the database.
     * 
     * @return
     */
    public ConferenceDatabase getDatabase() {
        return my_database;
    }
    
    /**
     * Returns the connection.
     * 
     * @return
     */
    public Connection getConnection() {
        return my_database.getConnection();
    }
    
    /**
     * Opens a connection to the database.
     */
    public static void openConnection() {
    	my_database.createConnection();
    }

    /**
     * Closes a connection to the database.
     */
    public static void closeConnection() {
    	my_database.closeConnection();
    }
}
