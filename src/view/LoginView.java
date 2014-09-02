package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.User;

/**
 * The object that will serve as the login nexus for the
 * program. This is a gui.
 * 
 * @author Tristan Boucher
 * @version 5/19/2013
 *
 */
public class LoginView extends AbstractView
{
	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The number of fields for this view that are displayed.
	 */
	private static final int NUM_FIELDS = 2;

	/**
	 * The welcome message for this view.
	 */
	private static final String[] LABEL_TEXTS = 
		{"Welcome to the Conference Manager!"};
	
	/**
	 * The label for the text fields.
	 */
	private static final String[] FIELD_LABEL_TEXTS = 
		{"First Name :", "Last Name :"};

	/**
	 * The labels for the buttons
	 */
	private static final String[] BUTTON_TEXTS = 
		{"Create Account", "Login"};

	/**
	 * The array of listeners for the buttons. 
	 */
	private final ActionListener[] BUTTON_LISTENERS =
		{new CreateAccountButtonListener(), new LoginButtonListener()};
	
	/**
	 * Default Constructor LoginView initiates the fields and build's the
	 * panel through a helper method.
	 */
	public LoginView(Controller the_controller)
	{
		super(createLabelsArray(LABEL_TEXTS),
			  createFieldsArray(NUM_FIELDS, FIELD_LABEL_TEXTS),
			  createButtonsArray(BUTTON_TEXTS), the_controller);

		for(int i = 0; i < BUTTON_LISTENERS.length; i++)
		{
			my_buttons[i].addActionListener(BUTTON_LISTENERS[i]);
		}
		
		buildPanel();
	}

	/**
	 * Overloaded Constructor simply fills in the username passed as the
	 * argument. 
	 * 
	 * @param the_first_name, final String the_last_name Used for auto-fills 
	 * the username field. 
	 */
	public LoginView(final String the_first_name, 
			final String the_last_name, 
			final Controller the_controller)
	{
		this(the_controller);
		my_text_fields[0].setText(the_first_name);
		my_text_fields[1].setText(the_last_name);
	}

	/**
	 * Builds the panel: adds buttons, fields, and labels.
	 * All ready for display.
	 */
	private void buildPanel()
	{
		getPanel().add(my_labels[0], BorderLayout.NORTH);
		
		final JPanel input_panel = new JPanel();
		input_panel.setLayout(new GridLayout(NUM_FIELDS, 0));
		for(int i = 0; i < my_text_fields.length; i++)
		{
			input_panel.add(my_text_fields[i]);
		}

		getPanel().add(input_panel, BorderLayout.CENTER);

		final JPanel button_panel = new JPanel();
		for(int i = 0; i < my_buttons.length; i++)
		{
			button_panel.add(my_buttons[i]);
		}

		getPanel().add(button_panel, BorderLayout.SOUTH);
		
	}

	//Accessor Methods.
	@Override
	public ArrayList<String> retrieveInfo() {
		ArrayList<String> packet = new ArrayList<String>();
		
		for(int i = 0; i < my_text_fields.length; i++)
		{
			packet.add(my_text_fields[i].getText().trim());
		}
		
		return packet;
	}
	
	/**
	 * Calls the proper action in the controller class. 
	 * 
	 * @author Tristan Boucher
	 *
	 */
	private class LoginButtonListener implements ActionListener
	{

		/**
		 * The method that notifies the observer on an action.
		 * 
		 * @param the_event The event that was caused.
		 */
		public void actionPerformed(final ActionEvent the_event) 
		{
			ArrayList<String> info = retrieveInfo();

            final User user = new User(info.get(0), info.get(1), null);
            getController().postLogin(user);
		}
	}

	/**
	 * Notifies the controller to show a create account view.
	 *  
	 * @author Tristan Boucher
	 *
	 */
	private class CreateAccountButtonListener implements ActionListener 
	{

		/**
		 * The method that notifies the observer on an action.
		 * 
		 * @param the_event the event that was caused.
		 */
		public void actionPerformed(final ActionEvent the_event) 
		{
            getController().setRegisterFormVisible();
		}
	}

	
}
