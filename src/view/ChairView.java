package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Paper;
import model.User;

/**
 * The view that a conference chair can use
 * to 
 * @author Tristan Boucher
 * @version 6/8/2013
 *
 */
public class ChairView extends AbstractView
{

	/**
	 * The JList that will hold the
	 * Papers to be shown.
	 */
	private JList<String> my_papers_list;
	
	/**
	 * The JScrollPane that houses the 
	 * JList.
	 */
	private JScrollPane my_scroll_pane;
	
	/**
	 * The lookup table for the list of papers.
	 */
	private Paper[] my_papers;

	/**
	 * The label for the chair's decision buttons.
	 */
	private static final String[] BUTTON_TEXTS = 
		{"Accept", "Reject"};

	/**
	 * The Button Listeners in this view.
	 */
	private final ActionListener[] BUTTON_LISTENERS =
		{new AcceptButtonListener(), new RejectButtonListener()};

	/**
	 * Constructs a basic blank view.
	 * @param the_controller The controller involved with this
	 * view.
	 */
	public ChairView(final Controller the_controller)
	{
		super(null, null, 
				createButtonsArray(BUTTON_TEXTS),
				the_controller);
		my_papers_list = new JList<String>();
		my_papers_list.addListSelectionListener(new PaperListListener());
		my_papers_list.setBackground(Color.CYAN.darker());
		my_scroll_pane = new JScrollPane(my_papers_list);
		addBorder(my_scroll_pane, "Select a Paper");
		buildPanel();
		my_scroll_pane.setVisible(true);
	}

	/**
	 * Autofill Constructor fills out the list of papers for the
	 * chair to see.
	 * 
	 * @param the_papers The array of papers recieved to display.
	 * @param the_controller The controller involved with this
	 * view.
	 */
	public ChairView(final Paper[] the_papers, 
			final Controller the_controller)
	{
		super(null, null, 
				createButtonsArray(BUTTON_TEXTS),
				the_controller);
		
		my_papers = the_papers;
		
		String[] paper_titles = new String[the_papers.length];
		for(int i = 0; i < paper_titles.length; i++)
		{
			paper_titles[i] = the_papers[i].getTitle();
		}
		
		my_papers_list = new JList<String>(paper_titles);
		my_papers_list.addListSelectionListener(new PaperListListener());
		my_papers_list.setBackground(Color.CYAN.darker());
		my_scroll_pane = new JScrollPane(my_papers_list);
		addBorder(my_scroll_pane, "Select a Paper");
		buildPanel();
		my_scroll_pane.setVisible(true);
	}

	/**
	 * Builds the Panel
	 */
	private void buildPanel()
	{
		getPanel().add(my_scroll_pane, BorderLayout.CENTER);

		final JPanel button_panel = new JPanel();
		for(int i = 0; i < my_buttons.length; i++)
		{
			button_panel.add(my_buttons[i]);
			my_buttons[i].addActionListener(BUTTON_LISTENERS[i]);
		}

		getPanel().add(button_panel, BorderLayout.SOUTH);
		
		enableButtons(false);
	}

	/**
	 * Enables the buttons
	 */
	private void enableButtons(boolean the_choice)
	{
		for(int i = 0; i < my_buttons.length; i++)
		{
			my_buttons[i].setEnabled(the_choice);
		}
	}
	
	/**
	 * Overridden. Not Used.
	 */
	@Override
	public ArrayList<String> retrieveInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Retrieves the Paper object associated with the chosen title.
	 */
	private Paper retrievePaper(final String the_title)
	{
		Paper to_return = null;
		for(int i = 0; i < my_papers.length; i++)
		{
			if(the_title.trim().equalsIgnoreCase(my_papers[i].getTitle().trim()))
			{
				to_return = my_papers[i];
			}
		}
		return to_return;
	}
	
	/**
	 * This listener notifies the Observers to create a new account
	 * by returning "Accept" as the information about it's notification.
	 * 
	 * @author Tristan Boucher
	 *
	 */
	private class AcceptButtonListener implements ActionListener 
	{

		/**
		 * The method that notifies the observer on an action.
		 * 
		 * @param the_event the event that was caused.
		 */
		public void actionPerformed(final ActionEvent the_event) 
		{
			getController().postChairAccept(retrievePaper(my_papers_list.getSelectedValue().trim()), true);
		}
	}

	/**
	 * This listener notifies the Observers to create a new account
	 * by returning "Reject" as the information about it's notification.
	 * 
	 * @author Tristan Boucher
	 *
	 */
	private class RejectButtonListener implements ActionListener 
	{

		/**
		 * The method that notifies the observer on an action.
		 * 
		 * @param the_event the event that was caused.
		 */
		public void actionPerformed(final ActionEvent the_event) 
		{
            getController().postChairAccept(retrievePaper(my_papers_list.getSelectedValue().trim()), false);
		}
	}

	/**
	 * This listener notifies the Observers to create a new account
	 * by returning The Paper object selected as the information 
	 * about it's notification.
	 * 
	 * @author Tristan Boucher
	 *
	 */
	private class PaperListListener implements ListSelectionListener 
	{

		/**
		 * The method that notifies the observer on an action.
		 * 
		 * @param the_event the event that was caused.
		 */
		public void valueChanged(final ListSelectionEvent the_event) 
		{
			if(the_event.getSource() != null)
			if(((JList<String>)the_event.getSource()).getSelectedValue() != null)
			{
				enableButtons(true);
			}
		}
	}

}
