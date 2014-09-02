package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The object that will serve as the admin view for this program.
 * 
 * @author Tristan Boucher
 * @version 6/8/2013
 *
 */
public class AdminView extends AbstractView{

	/**
	 * The text for the text field borders.
	 */
	private static String LABEL_TEXTS = "You must create a conference";
	
	/**
	 * The text for the buttons.
	 */
	private static String[] BUTTON_TEXTS = {"Create A New Conference"};
	
	/**
	 * No-arg Constructor creates the button.
	 * Builds the panel.
	 */
	public AdminView(final Controller the_controller) {
		super(null, null, 
				createButtonsArray(BUTTON_TEXTS), 
				the_controller);
		buildPanel();
	}
	
	/**
	 * Builds the panel.
	 */
	public void buildPanel()
	{
		addBorder(my_buttons[0], LABEL_TEXTS);
		my_buttons[0].addActionListener(new CreateConferenceButtonListener());
		getPanel().add(my_buttons[0]);
	}
	
	/**
	 * Listens for the create a conference button to be pushed.
	 * 
	 * @author Tristan Boucher
	 *
	 */
	private class CreateConferenceButtonListener implements ActionListener
	{
	    /**
	     * {@inheritDoc}
	     */
		@Override
		public void actionPerformed(final ActionEvent arg0) {
		    getController().setConferenceFormVisible();
		}
	}

	/**
	 * Overridden. Does nothing. Not used.
	 */
	@Override
	public ArrayList<String> retrieveInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
