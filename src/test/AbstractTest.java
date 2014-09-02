/**
 * JUnit tests.
 */
package test;

import org.junit.BeforeClass;
import controller.AbstractAdapter;
import controller.ConferenceDatabase;

/**
 * Contains an Abstract Test.
 * 
 * @author  Andrew Sorensen
 * @version $Id: AbstractTest.java 146 2013-06-04 16:05:39Z andrewx@uw.edu $
 */
public class AbstractTest {
    /**
     * Sets up the database connection.
     */
    @BeforeClass
    public static void oneTimeSetUp()
    {
        ConferenceDatabase db = new ConferenceDatabase();
        db.initializeDriver();
        AbstractAdapter.setConferenceDatabase(db);
    }

}
