package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.User;

/**
 * The RegisterView.
 * 
 * @author  Tristan, Andrew
 * @version $Id: RegisterView.java 269 2013-06-09 06:17:11Z tristb90@uw.edu $
 */
public class RegisterView extends AbstractView {
	/**
	 * The number of fields.
	 */
	private static final int NUM_FIELDS = 3;

	/**
	 * The welcome message for this view.
	 */
	private static final String[] LABEL_TEXTS = 
		{ "Registration of new user" };

	/**
	 * The Labels for the text fields in the view.
	 */
	private static final String[] FIELD_LABEL_TEXTS = 
		{ "First Name : ",
		"Last Name : ", 
		"Email : " };

	/**
	 * The label for the Create Account Button and 
	 * cancel button.
	 */
	private static final String[] BUTTON_TEXTS = 
		{ "Create", "Cancel" };

	/**
	 * The action Listeners for all the buttons in the view.
	 */
	private final ActionListener[] BUTTON_LISTENERS = 
		{new CreateButtonListener(), 
			new CancelButtonListener() };

	/**
	 * Default Constructor RegisterView initiates the fields and build's the
	 * panel through a helper method.
	 */
	public RegisterView(Controller the_controller) {
		super(createLabelsArray(LABEL_TEXTS), createFieldsArray(NUM_FIELDS,
				FIELD_LABEL_TEXTS), createButtonsArray(BUTTON_TEXTS),
				the_controller);

		for (int i = 0; i < BUTTON_LISTENERS.length; i++) {
			my_buttons[i].addActionListener(BUTTON_LISTENERS[i]);
		}

		buildPanel();
	}

	/**
	 * Overloaded Constructor simply fills in the strings passed as the
	 * arguments.
	 * 
	 * @param the_first_name, final String the_last_name Used for 
	 * auto-fills the username field.
	 */
	public RegisterView(final String the_first_name,
			final String the_last_name, 
			final String the_email,
			final Controller the_controller) {
		this(the_controller);
		my_text_fields[0].setText(the_first_name);
		my_text_fields[1].setText(the_last_name);
		my_text_fields[2].setText(the_email);
	}

	/**
	 * Builds the panel: adds buttons, fields, and labels. All ready for
	 * display.
	 */
	private void buildPanel() {
		getPanel().add(my_labels[0], BorderLayout.NORTH);

		final JPanel input_panel = new JPanel();
		input_panel.setLayout(new GridLayout(NUM_FIELDS, 0));
		for (int i = 0; i < my_text_fields.length; i++) {
			input_panel.add(my_text_fields[i]);
		}

		getPanel().add(input_panel, BorderLayout.CENTER);

		final JPanel button_panel = new JPanel();
		for (int i = 0; i < my_buttons.length; i++) {
			button_panel.add(my_buttons[i]);
		}

		getPanel().add(button_panel, BorderLayout.SOUTH);

	}

	@Override
	public ArrayList<String> retrieveInfo() {
		ArrayList<String> packet = new ArrayList<String>();

		for (int i = 0; i < my_text_fields.length; i++) {
			packet.add(my_text_fields[i].getText().trim());
		}

		return packet;

	}

	/**
	 * This listener notifies the Controller to create a new account by returning
	 * null as the information about it's notification.
	 * 
	 * @author Tristan Boucher
	 * 
	 */
	private class CreateButtonListener implements ActionListener {

		/**
		 * The method that notifies the Controller on an action.
		 * 
		 * @param the_event
		 *            the event that was caused.
		 */
		public void actionPerformed(final ActionEvent the_event) {
			ArrayList<String> info = retrieveInfo();

			for (int i = 0; i < info.size(); i++) {
				if (info.get(i) == null || info.get(i).length() == 0) {
					JOptionPane.showMessageDialog(null,
							"Form incomplete. Please enter valid "
									+ "information in all fields.");
					return;
				}
			}

			final User user = new User(info.get(0), info.get(1), info.get(2));

			getController().postRegistration(user);
		}
	}

	/**
	 * This listener notifies the Controller to create a new account by returning
	 * null as the information about it's notification.
	 * 
	 * @author Tristan Boucher
	 * 
	 */
	private class CancelButtonListener implements ActionListener {

		/**
		 * The method that notifies the Controller on an action.
		 * 
		 * @param the_event
		 *            the event that was caused.
		 */
		public void actionPerformed(final ActionEvent the_event) {
			my_controller.postCancel();
		}
	}
}
