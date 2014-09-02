package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

/**
 * The abstract view.
 * 
 * @author  Tristan, Andrew
 * @version $Id: AbstractView.java 268 2013-06-09 06:08:46Z tristb90@uw.edu $
 */
public abstract class AbstractView  extends Observable implements ViewInterface 
{
	private final static int FIELD_LENGTH = 20;
	private final BorderLayout MY_LAYOUT = new BorderLayout();
	protected JPanel my_panel;

	protected JLabel[] my_labels;
	protected JLabel[] my_field_labels;
	protected JTextField[] my_text_fields;
	protected JButton[] my_buttons;
	protected ActionListener[] my_button_listeners;

	/**
	 * The controller.
	 */
	protected Controller my_controller;

	/**
	 * No-arg Constructor creates a panel and a layout and sets the panel
	 * invisible for the time being.
	 */
	public AbstractView(final Controller the_controller)
	{
		my_panel = new JPanel();
		my_panel.setLayout(MY_LAYOUT);
		setViewVisible(false);

		my_controller = the_controller;
	}

	/**
	 * Constructor sets all commonly used components.
	 * 
	 * @param the_labels The title and special labels
	 * @param the_field_labels
	 * @param the_text_fields
	 * @param the_buttons
	 */
	public AbstractView(final JLabel[] the_labels, 
			final JTextField[] the_text_fields, 
			final JButton[] the_buttons, 
			final Controller the_controller)
	{
		this(the_controller);

		my_labels = the_labels;
		my_text_fields = the_text_fields;
		my_buttons = the_buttons;

	}

	/**
	 * Sets the controller.
	 * 
	 * @param the_controller
	 */
	public void setController(final Controller the_controller) {
		this.my_controller = the_controller;
	}

	/**
	 * Returns the controller.
	 * 
	 * @return Controller.
	 */
	protected Controller getController() {
		return this.my_controller;
	}

	/**
	 * Sets the panel visible or invisible using the_choice.
	 * If true, the panel is visible, it is hidden otherwise.
	 * 
	 * @param the_choice The boolean choice for visibility.
	 */
	public void setViewVisible(final boolean the_choice)
	{
		getPanel().setVisible(the_choice);
	}

	/**
	 * Returns this view's panel. 
	 * 
	 * @return my_panel The panel that contains this view.
	 */
	public JPanel getPanel()
	{
		return my_panel;
	}

	/**
	 * This static factory method recieves an array of Strings
	 * and from that list, generates a list of JLabels following
	 * the look and feel of this gui. Only extending classes may
	 * use this as to keep the general look and feel consistant.
	 * 
	 * @param the_messages The text used in the JLabels construction.
	 * @return a JLabel array constructed from the strings passed.
	 */
	protected static JLabel[] createLabelsArray(final String[] the_messages)
	{
		JLabel[] labels = new JLabel[the_messages.length];

		for(int i = 0; i < the_messages.length; i++)
		{
			labels[i] = new JLabel(the_messages[i]);
			labels[i].setFont(new Font("Times New Roman", Font.BOLD, 20));
			labels[i].setForeground(Color.BLUE);
		}

		return labels;
	}
	
	/**
	 * Creates an array of JTextFields with the array of strings that is passed
	 * and uses those strings as titled borders to surround the text box and
	 * contribute to the look and feel.
	 * @param the_n The number of fields. Useful in increasing or decreasing number
	 * of fields easilly. (initially thought to allow each field to show up only 
	 * after the last had been completed by increasing the field count. but 
	 * that idea never came to fruition.)
	 * 
	 * @param the_labels The titles for the titled borders. descriptions of the 
	 * fields.
	 * @return An array of JTextFields; each wrapped in a descriptive border.
	 */
	protected static JTextField[] createFieldsArray(final int the_n, 
			final String[] the_labels)
	{
		JTextField[] text_fields = new JTextField[the_n];

		for(int i = 0; i < the_n; i++)
		{
			text_fields[i] = new JTextField(FIELD_LENGTH);
			addBorder(text_fields[i], the_labels[i]);
			text_fields[i].setPreferredSize(new Dimension(0,45));
			text_fields[i].setHorizontalAlignment(SwingConstants.CENTER);
			text_fields[i].setBackground(Color.CYAN.darker());
		}

		return text_fields;
	}

	/**
	 * The JChooser objects used to insert dates into the system.
	 * 
	 * @param the_n The number of components.
	 * @param the_labels The labels for the border.
	 * @return An array of JDateChooser objects.
	 */
	protected static JDateChooser[] createDateFieldsArray(
			final int the_n,
			final String[] the_labels) {
		JDateChooser[] date_fields = new JDateChooser[the_n];

		for (int i = 0; i < the_n; i++) {
			date_fields[i] = new JDateChooser();
			addBorder(date_fields[i], the_labels[i]);
			date_fields[i].setPreferredSize(new Dimension(0, 45));
			date_fields[i].setBackground(Color.CYAN.darker());
		}

		return date_fields;
	}

	/**
	 * Creates an array of JTextFields filled with the given
	 * text. This gui is mostly a factory type and therefore
	 * in order to have auto-fill constructors this was helpful.
	 * 
	 * @param the_n The number of Components to make.(easy
	 * to restrict and change.)
	 * @param the_fill The text that will be inserted into the
	 * fields in the array.
	 * @param the_labels The strings that are used in the borders.
	 * @return A JTextField array consistent with the look and feel.
	 */
	protected static JTextField[] createFieldsArray(final int the_n,
			final String[] the_fill,
			final String[] the_labels)
	{
		JTextField[] text_fields = new JTextField[the_n];

		for(int i = 0; i < the_n; i++)
		{
			text_fields[i] = new JTextField(FIELD_LENGTH);
			text_fields[i].setText(the_fill[i]);

		}

		return text_fields;
	}

	/**
	 * Creates a n array of buttons consistent with the look and feel.
	 * 
	 * @param the_messages The text to create the buttons with.
	 * @return An array of JButtons consitent with the look and feel.
	 */
	protected  static JButton[] createButtonsArray(final String[] the_messages) 
	{
		JButton[] buttons = new JButton[the_messages.length];

		for(int i = 0; i < the_messages.length; i++)
		{
			buttons[i] = new JButton(the_messages[i]);
			buttons[i].setBackground(Color.CYAN.darker());
		}

		return buttons;
	}

	/**
	 * The cornerstone of the look of this gui. This tags every JComponent
	 * that is created with a titled border that describes what it is and
	 * makes it cool.
	 * @param the_field The text fields that are to be bordered.
	 * @param the_string The text used in the titled borders for the 
	 * description.
	 */
	protected static void addBorder(final JComponent the_field, 
			final String the_string)
	{
		the_field.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLUE, 2, true),
				the_string, SwingConstants.LEFT, SwingConstants.LEFT, 
				new Font("Veranda", Font.ITALIC, 16), Color.GREEN.darker().darker()));
	}
}
