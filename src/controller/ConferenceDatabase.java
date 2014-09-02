package controller;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

/**
 * This class handles the creation of the database and makes sure it can be accessed.
 * If for any reason the database needs to be re-initialized, this class can be called
 * in the main after the ConferenceDatabase.db file is deleted within the project. This
 * process could be made better and can explore possibilities in the future.
 * @author  Tony Robinson
 * @version $Id: ConferenceDatabase.java 291 2013-06-09 22:35:34Z ajr42@uw.edu $
 */
public class ConferenceDatabase {
	/**
	 * The name of the SQLite JDBC Driver
	 */
	private static final String DRIVER_NAME = "org.sqlite.JDBC";
	
	/**
	 * Name of the database (filename)
	 */
	private String my_databaseName;
	
	/**
	 * Type of the database specified
	 */
	private String my_databaseType;
	
	/**
	 * Type and name of the database (previous two fields concatenated)
	 */
	private String my_url;
	
	/**
	 * Timeout (in seconds) for any sql statement to complete.
	 */
	private int my_timeout;
	
	/**
	 * Object that hold connection information for the database.
	 */
	private Connection my_connection;
	
	/**
	 * Object that allows execution of string SQL statements to the database.
	 */
	private Statement my_statement;

	/**
	 * Constructor for database creation object.
	 */
	public ConferenceDatabase() {
		my_databaseName = "var/ConferenceManager.db";
		my_databaseType = "jdbc:sqlite";
		my_url = my_databaseType + ":" + my_databaseName;
		my_timeout = 30;
	}
	
	/**
	 * Ensures that the sqlite jdbc driver is loaded.
	 */
	public void initializeDriver() {
		try {
			Driver my_sqlDriver = (Driver)Class.forName(DRIVER_NAME).newInstance();
			DriverManager.registerDriver(my_sqlDriver);
		} catch (Exception e) {
			System.out.println("ERROR: Database driver [" + DRIVER_NAME + "] could not be loaded.");
			System.out.println("ERROR DUMP: " + e.toString());
		}
	}
	
	/**
	 * Creates a connection to the database and returns the statement object to be used.
	 * @return The statement object for sql execution.
	 * 
	 * TODO make this just create a connection, and not a statement as well.
	 */
	public void createConnection() {
		try {
			this.my_connection = DriverManager.getConnection(my_url);
			this.my_statement = my_connection.createStatement();
		} catch (SQLException e) {
			System.out.println("ERROR: Cannot Create Connection");
			System.out.println("ERROR DUMP: " + e.toString());
		}
	}
	
	/**
	 * Closes connection to database.
	 */
	public void closeConnection() {
		try {
			this.my_connection.close();
			this.my_statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: Could not close connection");
		}
	}
	
	/**
	 * Executes SQL statements to build the tables and relations for the database.
	 */
	public void createDatabase() {
		String createUserTable = "CREATE TABLE Users (User INTEGER PRIMARY KEY ASC, FirstName TEXT, LastName TEXT, Email TEXT)";
		String createPaperTable = "CREATE TABLE Papers (Paper INTEGER PRIMARY KEY ASC, Author INTEGER, Title TEXT, Category TEXT, KeyWords TEXT, Abstract TEXT, FileName TEXT, FOREIGN KEY (Author) REFERENCES Users(User))";
		String createConferenceTable = "CREATE TABLE Conferences (Conference INTEGER PRIMARY KEY ASC, ConferenceName TEXT, ProgramChair INTEGER, StartDate NUMERIC, EndDate NUMERIC, FOREIGN KEY (ProgramChair) REFERENCES Users(User))";
		String createConferencePaperTable = "CREATE TABLE ConferencePapers (ConferencePaper INTEGER PRIMARY KEY ASC, Paper INTEGER, Conference INTEGER, Decision INTEGER, FOREIGN KEY (Paper) REFERENCES Papers(Paper), FOREIGN KEY (Conference) REFERENCES Conferences(Conference))";
		String createRecommendationTable = "CREATE TABLE Recommendations (Recommendation INTEGER PRIMARY KEY ASC, Recommender INTEGER, ConferencePaper INTEGER, RecommendationInt INTEGER, Rationale TEXT, FOREIGN KEY (Recommender) REFERENCES Users(User), FOREIGN KEY (ConferencePaper) REFERENCES ConferencePapers(ConferencePaper))";
		String createReviewTable = "CREATE TABLE Reviews (Review INTEGER PRIMARY KEY ASC, Reviewer INTEGER, ConferencePaper INTEGER, Comments TEXT, Summary INTEGER, SummaryComments TEXT, FOREIGN KEY (Reviewer) REFERENCES Users(User), FOREIGN KEY (ConferencePaper) REFERENCES ConferencePapers(ConferencePaper))";
		String createConferenceRolesTable = "CREATE TABLE ConferenceRoles (ConferenceRoles INTEGER PRIMARY KEY ASC, User INTEGER, Conference INTEGER, Role TEXT, FOREIGN KEY(User) REFERENCES Users(User), FOREIGN KEY(Conference) REFERENCES Conference(Conference))";		
		String createSubchairReviewerTable = "CREATE TABLE SubchairReviewers (SubchairReviewers INTEGER PRIMARY KEY ASC, Subchair INTEGER, Reviewer INTEGER, FOREIGN KEY (Subchair) REFERENCES Users(User), FOREIGN KEY (Reviewer) REFERENCES Users(User))";
		
		try {
			my_statement.setQueryTimeout(my_timeout);
			my_statement.execute(createUserTable);
			my_statement.execute(createPaperTable);
			my_statement.execute(createConferenceTable);
			my_statement.execute(createConferencePaperTable);
			my_statement.execute(createRecommendationTable);
			my_statement.execute(createReviewTable);
			my_statement.execute(createConferenceRolesTable);
			my_statement.execute(createSubchairReviewerTable);
		} catch (SQLException e) {
			System.out.println("ERROR: Something is wrong in the SQLite Syntax");
			System.out.println("ERROR DUMP: " + e.toString());
		}
	}
	
	public Statement getStatement() {
		return my_statement;
	}

	/**
	 * Returns the database connection.
	 * 
	 * @return	Connection
	 */
	public Connection getConnection() {
		return this.my_connection;
	}

}
