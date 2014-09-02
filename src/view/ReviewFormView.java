package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import model.Paper;

public class ReviewFormView extends AbstractView{

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 1L;

	private static final int NUM_FIELDS = 8;

	/**
	 * The titles in this form.
	 */
	private static final String[] TITLE_TEXTS= 
		{"Instructions to Reviewers: ", 
		"Confidential Comments to the Subprogram Chair ",
		"Comments to the Author: ",
		"Summary Rating: ",
		"Summary Comments: "};

	/**
	 * The questions for the radio buttons.
	 */
	private static final String[] RADIO_BUTTON_QUESTIONS =
		{"1. Can the content be directly applied by classroom " +
				"instructors or curriculum designers?",
				"2. Does the work appeal to a broad readership interested " +
						"in engineering education or is it narlwly " +
						"specialized?",
						"3. Does the work address a significant problem?",
						"4. Does the author build upon relevant references " +
								"and bodies of knowledge?",
								"5. If a teaching intervention is reported, is it " +
										"adequaely evaluated in terms of its impact " +
										"on learning in actual use?" +
										"6. Does the author use methods appropriate to the " +
										"goals, both for the instructional intervention " +
										"and the evaluation of impact on learning?",
										"7. Did the author provide sufficient detail to replicate " +
												"and evaluate?",
												"8. Is the paper clearly and carefully written?",
												"9. Does the paper adhere to accepted standards of style," +
		"usage, and composition?"};
	/**
	 * The radio button texts for this view.
	 */
	private static final String[] RADIO_BUTTON_TEXTS = 
		{"[5] Excellent", "[4] Very Good", "[3] Acceptable",
		"[2] Weak", "[1] Unacceptable"};
	/**
	 * The radio buttons for this view.
	 */
	private static final JRadioButton[] RADIO_BUTTONS =
			createRadioButtonArray(RADIO_BUTTON_TEXTS);
	/**
	 * The radio button groups for this view.
	 */
	private static final ButtonGroup[] BUTTON_GROUPS = 
			createButtonGroupArray(RADIO_BUTTONS);
	/**
	 * The label for the Create Account Button.
	 */
	private static final String[] BUTTON_TEXTS = 
		{"Submit", "Cancel"};
	
	/**
	 * The Paper that this review is for.
	 */
	private Paper my_paper;
	
	/**
	 * Constructor default takes just a paper and controller.
	 * @param the_controller
	 */
	public ReviewFormView(final Paper the_paper, final Controller the_controller)
	{
		super(the_controller);

		my_paper = the_paper;
		
		buildPanel();

		my_buttons[1].addActionListener(new SubmitButtonListener());
		my_buttons[2].addActionListener(new CancelButtonListener());
	}
	
	/**
	 * AutoFill Constructor
	 */
	
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

	private static JRadioButton[] createRadioButtonArray(String[] the_labels)
	{
		JRadioButton[] radio_buttons = 
				new JRadioButton[RADIO_BUTTON_TEXTS.length];
		for(int i = 0; i < RADIO_BUTTON_QUESTIONS.length; i++)
		{
			for(int j = 0; j < radio_buttons.length; j++)
			{
				radio_buttons[j] = 
						new JRadioButton(RADIO_BUTTON_TEXTS[j], false);
				radio_buttons[j].setBackground(Color.CYAN.darker());
			}
		}
		return radio_buttons;
	}

	private static ButtonGroup[] createButtonGroupArray(JRadioButton[] the_buttons)
	{
		ButtonGroup[] button_groups = 
				new ButtonGroup[RADIO_BUTTON_QUESTIONS.length];


		int j = 0;
		do{
			for(int i = 0; i < the_buttons.length / button_groups.length; 
					i++, j++)
			{
				button_groups[i].add(the_buttons[j]);
			}
		}while(j < RADIO_BUTTONS.length);


		return button_groups;
	}


	@Override
	public ArrayList<String> retrieveInfo() {
		ArrayList<String> packet = new ArrayList<String>();

		return packet;
	}

	/**
	 * This listener notifies the Observers to create a new account
	 * by returning null as the information about it's notification.
	 * 
	 * @author Tristan Boucher
	 *
	 */
	private class SubmitButtonListener implements ActionListener 
	{

		/**
		 * The method that notifies the observer on an action.
		 * 
		 * @param the_event the event that was caused.
		 */
		public void actionPerformed(final ActionEvent the_event) 
		{
			ArrayList<String> info = retrieveInfo();

			for(int i = 0; i < info.size(); i++)
			{
				if(info.get(i) == null)
				{
					JOptionPane.showMessageDialog(null,
							"Form incomplete. Please enter valid " +
							"information in all fields.");
					return;
				}
			}
			//getController().postReviewSubmission(my_paper, review);
		}
	}

	/**
	 * This listener notifies the Observers to create a new account
	 * by returning null as the information about it's notification.
	 * 
	 * @author Tristan Boucher
	 *
	 */
	private class CancelButtonListener implements ActionListener 
	{

		/**
		 * The method that notifies the observer on an action.
		 * 
		 * @param the_event the event that was caused.
		 */
		public void actionPerformed(final ActionEvent the_event) 
		{
			my_controller.postCancel();
		}
	}

}
