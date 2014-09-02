package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JPanel;

import com.toedter.calendar.JDateChooser;

import model.Conference;
import model.User;
import controller.UserAdapter;

/**
 * ConferenceFormView is a GUI component. It deals with displaying to the user,
 * a easy to use graphical panel, that will be able to obtain and relay all
 * information involved in creating a conference via filling out the
 * ConferenceForm Client Artifact.
 * 
 * @author Tristan Boucher
 * @version V1.0 5/29/2013 3:13am
 */
public class ConferenceFormView extends AbstractView {
    /**
     * The constant for the number of fields in this view. Needed to define the
     * loops and such for GUI construction.
     */
    private static final int NUM_FIELDS = 5;

    /**
     * The title message for this view.
     */
    private static final String[] LABEL_TEXTS = { "Conference Form View" };

    /**
     * The list of strings that are used as the labels for all the text fields
     * in this view.
     */
    private static final String[] TEXT_FIELD_LABEL_TEXTS = {
            "Conference Name :", "Chair First Name :", "Chair Last Name :" };

    /**
     * An array of strings labeling all the date fields.
     */
    private static final String[] DATE_FIELD_LABEL_TEXTS = {
            "Begin Date :", "End Date :" };

    /**
     * The list of strings that are used as the labels for all the buttons in
     * this view.
     */
    private static final String[] BUTTON_TEXTS = { "Submit", "Cancel" };
    
    /**
     * Contains an array of date pickers.
     */
    private JDateChooser my_date_pickers[];

    /**
     * Default Constructor, creates a blank conference form view object.
     */
    public ConferenceFormView(final Controller the_controller) {
        super(createLabelsArray(LABEL_TEXTS), createFieldsArray(
                TEXT_FIELD_LABEL_TEXTS.length, TEXT_FIELD_LABEL_TEXTS),
                createButtonsArray(BUTTON_TEXTS), the_controller);
        my_date_pickers = new JDateChooser[DATE_FIELD_LABEL_TEXTS.length];
        buildPanel();

        my_buttons[0].addActionListener(new SubmitButtonListener());
        my_buttons[1].addActionListener(new CancelButtonListener());
    }

    /**
     * AutoFill Constructor recieves information for all fields in order and
     * automatically generates a view with all that info in the text fields.
     * 
     * @param the_conference_name
     *            The name of the conference.
     * @param the_start
     *            The start of the conference as a String object.
     * @param the_end
     *            The end of the conference as a String object.
     * @param the_chair_first
     *            The first name of the conference chair.
     * @param the_chair_last
     *            The last name of the conference chair.
     */
    public ConferenceFormView(final String the_conference_name,
            final String the_start, final String the_end,
            final String the_chair_first, final String the_chair_last,
            final Controller the_controller) {
        this(the_controller);

        String[] temp = { the_conference_name, the_start, the_end,
                the_chair_first, the_chair_last };

        for (int i = 0; i < my_text_fields.length; i++) {
            my_text_fields[i].setText(temp[i]);
        }
        
        
    }

    
    /**
     * Constructs a new ConferenceFormView.
     * 
     * @param the_conference
     * @param the_controller
     */
    public ConferenceFormView(final Conference the_conference, 
            final Controller the_controller) {
        super(createLabelsArray(LABEL_TEXTS), createFieldsArray(
                TEXT_FIELD_LABEL_TEXTS.length, TEXT_FIELD_LABEL_TEXTS),
                createButtonsArray(BUTTON_TEXTS), the_controller);

        buildPanel();
        
        String[] temp = { the_conference.getName(),
                the_conference.getChair().getFirstName(),
                the_conference.getChair().getLastName() };
        
        for (int i = 0; i < my_text_fields.length; i++) {
            my_text_fields[i].setText(temp[i]);
        }

        my_buttons[0].addActionListener(new SubmitButtonListener());
        my_buttons[1].addActionListener(new CancelButtonListener());
    }
    
    /**
     * Builds the Panel
     */
    private void buildPanel() {
        getPanel().add(my_labels[0], BorderLayout.NORTH);

        final JPanel input_panel = new JPanel();
        input_panel.setLayout(new GridLayout(NUM_FIELDS, 0));
        for (int i = 0; i < my_text_fields.length; i++) {
            input_panel.add(my_text_fields[i]);
        }

        for (int i = 0; i < my_text_fields.length; i++) {
            input_panel.add(my_text_fields[i]);
        }
        
        for (int i = 0; i < DATE_FIELD_LABEL_TEXTS.length; i++ ) {
            my_date_pickers[i] = new JDateChooser();
            input_panel.add(my_date_pickers[i]);
            addBorder(my_date_pickers[i], DATE_FIELD_LABEL_TEXTS[i]);
        }

        
        
        getPanel().add(input_panel, BorderLayout.CENTER);

        final JPanel button_panel = new JPanel();
        for (int i = 0; i < my_buttons.length; i++) {
            button_panel.add(my_buttons[i]);
        }

        getPanel().add(button_panel, BorderLayout.SOUTH);

    }


    /**
     * Overridden retrieveInfo is designed as a generic way 
     * to easily access all information that is encompassed 
     * in a view.
     * 
     * This returns a List of Strings which is the text inside
     * each of the text boxes in order from top to bottom
     * and left to right. (normal english reading style)
     */
    public Conference retrieveConferenceInfo() {
    	return new Conference(my_text_fields[0].getText().trim(),
				my_date_pickers[0].getDate(), 
				my_date_pickers[1].getDate(),
				new User(my_text_fields[1].getText().trim(),
				my_text_fields[2].getText().trim(), 
				null)
        		);
    }

    /**
     * This listener notifies the Observers to create a new account by returning
     * null as the information about it's notification.
     * 
     * @author Tristan Boucher
     * 
     */
    private class SubmitButtonListener implements ActionListener {

        /**
         * The method that notifies the observer on an action.
         * 
         * @param the_event
         *            the event that was caused.
         */
        public void actionPerformed(final ActionEvent the_event) {
            getController().postCreateConference(retrieveConferenceInfo());
        }
    }

    /**
     * This listener notifies the Observers to create a new account by returning
     * null as the information about it's notification.
     * 
     * @author Tristan Boucher
     * 
     */
    private class CancelButtonListener implements ActionListener {

        /**
         * The method that notifies the observer on an action.
         * 
         * @param the_event
         *            the event that was caused.
         */
        public void actionPerformed(final ActionEvent the_event) {
            getController().postCancel();
        }
    }

    /**
     * Overridden Does nothing.
     */
	@Override
	public ArrayList<String> retrieveInfo() {
		// TODO Auto-generated method stub
		return null;
	}
}
