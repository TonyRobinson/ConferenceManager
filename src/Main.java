import view.Controller;
import controller.AbstractAdapter;
import controller.ConferenceDatabase;

/**
 * Contains the Main class.
 * 
 * @author Andrew Sorensen
 * @version $Id: Main.java 223 2013-06-08 18:18:06Z andrewx@uw.edu $
 */
public class Main {
    /**
     * The main method.
     * 
     * @param args the command line arguments.
     */
    public static void main(final String[] args) {
        ConferenceDatabase db = new ConferenceDatabase();
        db.initializeDriver();
        AbstractAdapter.setConferenceDatabase(db);
        AbstractAdapter.openConnection();

        // display the GUI.
        final Controller test = new Controller();
        test.setLoginVisible();

    }
}
