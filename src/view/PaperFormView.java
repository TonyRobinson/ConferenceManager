package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import model.Category;
import model.Conference;
import model.Paper;
import model.User;

/**
 * The PaperFormView.
 * 
 * @author  Tristan, Andrew
 * @version $Id: PaperFormView.java 268 2013-06-09 06:08:46Z tristb90@uw.edu $
 */
public class PaperFormView extends AbstractView
{
	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The number of fields.
	 */
	private static final int NUM_FIELDS = 7;

	/**
	 * The width of the area.
	 */
	private static final int AREA_WIDTH = 20;

	/**
	 * The height of the area.
	 */
	private static final int AREA_HEIGHT = NUM_FIELDS;
	
	/**
	 * The specialized messages for this view.
	 */
	private static final String[] LABEL_TEXTS = 
		{"Paper: Submission", "Abstract  (100 words max) : "};

	/**
	 * The labels for the text fields.
	 */
	private static final String[] FIELD_LABEL_TEXTS = 
		{"Your First Name : ", "Your Last Name : ", "Your Email : ", 
		 "Paper's Title : ", "Category : ", "Search Keywords : ", 
		 "Paper File Directory : "};

	/**
	 * The label for the Create Account Button.
	 */
	private static final String[] BUTTON_TEXTS = 
		{"Choose Paper File", "Submit", "Cancel"};

	/**
	 * The scroll pane for the abstract.
	 */
	private JScrollPane my_scroll_pane;

	/**
	 * The area for the abstract.
	 */
	private JTextArea my_abstract_area;
	
	/**
	 * Constructs a new PaperFormView.
	 * @param the_controller
	 */
	public PaperFormView(final Controller the_controller)
	{
		super(createLabelsArray(LABEL_TEXTS),
				createFieldsArray(NUM_FIELDS, FIELD_LABEL_TEXTS),
				createButtonsArray(BUTTON_TEXTS), the_controller);

		buildPanel();

		buildTextAreaPanel();

		my_buttons[0].addActionListener(new PaperFileButtonListener());
		my_buttons[1].addActionListener(new SubmitButtonListener());
		my_buttons[2].addActionListener(new CancelButtonListener());
	}

    /**
     * Constructs a new PaperFormView.
     * 
     * @param the_controller
     */
	public PaperFormView(final String the_author_first, 
			final String the_author_last,final String the_email,
			final String the_conference, final String the_title,
			final String the_category, final String the_keywords,
			final String the_file_path, final String the_abstract,
			final Controller the_controller)
	{
		this(the_controller);	

		String[] temp = {the_author_first, the_author_last, the_email, 
				the_conference, the_title, the_category, the_keywords, 
				the_file_path};

		for(int i = 0; i < my_text_fields.length; i++)
		{
			my_text_fields[i].setText(temp[i].trim());
		}

		my_abstract_area.setText(the_abstract);
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

	/**
	 * Creates the panel that houses the text area for the abstract.
	 */
	private void buildTextAreaPanel()
	{
		JPanel text_area_panel = new JPanel();
		text_area_panel.setLayout(new BorderLayout());

		my_abstract_area = new JTextArea(AREA_HEIGHT, AREA_WIDTH);
		my_scroll_pane = new JScrollPane(my_abstract_area);

		my_scroll_pane.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLUE, 2, true),
				LABEL_TEXTS[LABEL_TEXTS.length-1], 
				SwingConstants.CENTER, SwingConstants.CENTER, 
				new Font("Veranda", Font.ITALIC, 16), Color.GREEN.darker().darker()));

		text_area_panel.add(my_scroll_pane, BorderLayout.CENTER);

		getPanel().add(text_area_panel, BorderLayout.EAST);
	}

	/**
	 * Returns the text inside the abstract text area.
	 * @return The string that is in the text area.
	 */
	public String getAbstract()
	{
		return my_abstract_area.getText().trim();
	}

	@Override
	public ArrayList<String> retrieveInfo() {
		ArrayList<String> packet = new ArrayList<String>();

		for(int i = 0; i < my_text_fields.length; i++)
		{
			packet.add(my_text_fields[i].getText().trim());
		}

		packet.add(my_abstract_area.getText().trim());

		return packet;
	}

	/**
	 * This listener notifies the controller when a paper has 
	 * been submitted.
	 * 
	 * @author Tristan Boucher
	 *
	 */
	private class SubmitButtonListener implements ActionListener 
	{
		/**
		 * The method that notifies the controller on an action.
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
			
			final Paper paper = new Paper(info.get(3),
					new User(info.get(0), info.get(1), info.get(2)),
					new Category(info.get(4)), 
					info.get(5),
					my_abstract_area.getText().trim(), 
					new File(info.get(6)));
			
			getController().postPaperSubmission(paper);
		}
	}

	/**
	 * This Listener notifies the Controller that the cancel button
	 * was pressed.
	 * 
	 * @author Tristan Boucher
	 *
	 */
	private class CancelButtonListener implements ActionListener 
	{
		/**
		 * The method that notifies the controller on an action.
		 * 
		 * @param the_event the event that was caused.
		 */
		public void actionPerformed(final ActionEvent the_event) 
		{
			my_controller.postCancel();
		}
	}

	/**
	 * This listener pulls up a JFileChooser in order to choose 
	 * the paper submission file for upload.
	 * 
	 * @author Tristan Boucher
	 *
	 */
	private class PaperFileButtonListener implements ActionListener 
	{

		/**
		 * The method that notifies the controller on an action.
		 * 
		 * @param the_event the event that was caused.
		 */
		public void actionPerformed(final ActionEvent the_event) 
		{
			JFileChooser jfc = new JFileChooser();
			int result = jfc.showOpenDialog(getPanel());

			if(result == JFileChooser.APPROVE_OPTION)
			{
				my_text_fields[6].setText(jfc.getSelectedFile().getPath());
			}
		}
	}

}
