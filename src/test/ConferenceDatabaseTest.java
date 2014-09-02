package test;

import controller.ConferenceDatabase;

public class ConferenceDatabaseTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Leave following lines commented unless a new database needs to be created. Delete the ConferenceManager.db file first before running or it will not change.
	
		ConferenceDatabase my_database = new ConferenceDatabase();
		my_database.initializeDriver();
		my_database.createConnection();
		my_database.createDatabase();
		my_database.closeConnection();
				
	}

}
