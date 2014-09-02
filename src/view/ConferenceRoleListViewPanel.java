package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;

import model.Conference;
import model.Paper;
import model.Role;

/**
 * The Conference Role List View Panel.
 * 
 * @author  Tristan, Andrew.
 * @version $Id: ConferenceRoleListViewPanel.java 288 2013-06-09 21:04:32Z tristb90@uw.edu $
 */
public class ConferenceRoleListViewPanel extends AbstractView {
    /**
     * The label for conference selection.
     */
    private static String CONFERENCE_LABEL = "Choose a Conference : ";

    /**
     * The label for role picking.
     */
    private static String ROLE_LABEL = "Choose a Role : ";

    /**
     * A list of conferences.
     */
    private JComboBox<String> my_conferences_list;
    
    /**
     * The current conference selected.
     */
    private Conference my_conference;

    /**
     * A list of roles.
     */
    private JComboBox<String> my_roles_list;
    
    /**
     * The array of conferences.
     */
    private Conference[] my_conferences;
    
    /**
     * The array of roles.
     */
    private Role[] my_roles;


    /**
     * Constructs a new conference role list view panel.
     */
    public ConferenceRoleListViewPanel(final Controller the_controller) {
        super(the_controller);
    	
    	my_conferences_list = new JComboBox<String>();
        my_roles_list = new JComboBox<String>();
        
        buildPanel();
    }

    /**
     * Constructs a new conference role list view panel.
     * 
     * @param the_conferences The array of conferences that will
     * populate the combo boxes.
     */
    public ConferenceRoleListViewPanel(final Conference[] the_conferences, 
    		final Controller the_controller) {
    	super(the_controller);
    	
    	my_conferences = the_conferences;
    	
    	String[] the_conference_titles = new String[the_conferences.length];
    	for(int i = 0; i < the_conferences.length; i++)
    	{
    		the_conference_titles[i] = the_conferences[i].getName();
    	}
    	
    	my_conferences_list = new JComboBox<String>(the_conference_titles);
        my_roles_list = new JComboBox<String>();

        buildPanel();

        my_conferences_list.setEnabled(true);
    }

    /**
     * Constructs a new conference role list view panel.
     * @param the_conferences
     * @param the_conference
     * @param the_roles
     * @param the_role
     * @param the_controller
     */
    public ConferenceRoleListViewPanel(final Conference[] the_conferences,
            final Conference the_conference,
    		final Role[] the_roles,
            final Controller the_controller) {
    	super(the_controller);
    	
    	my_conferences = the_conferences;
    	my_conference = the_conference;
    	my_roles = the_roles;
    	
    	String[] the_conference_titles = new String[the_conferences.length];
    	for(int i = 0; i < the_conferences.length; i++)
    	{
    		the_conference_titles[i] = the_conferences[i].getName();
    	}
        my_conferences_list = new JComboBox<String>(the_conference_titles);
        my_conferences_list.setSelectedItem(my_conference.getName());
        
        
        String[] the_role_names = new String[the_roles.length];
    	for(int i = 0; i < the_role_names.length; i++)
    	{
    		the_role_names[i] = the_roles[i].getName();
    	}
        my_roles_list = new JComboBox<String>(the_role_names);

        buildPanel();
        
        

        setEnabled(true);
    }
    
    /**
     * Builds the Panel.
     */
    public void buildPanel() {
        getPanel().setVisible(true);
        getPanel().setBackground(Color.cyan.darker());

        my_conferences_list.addActionListener(new ConferenceBoxListener());
        my_roles_list.addActionListener(new RoleBoxListener());

        my_conferences_list.setEnabled(false);
        my_roles_list.setEnabled(false);

        addBorder(my_conferences_list, CONFERENCE_LABEL);
        addBorder(my_roles_list, ROLE_LABEL);

        my_conferences_list.setPreferredSize(new Dimension(200, 50));

        getPanel().add(my_conferences_list, BorderLayout.NORTH);
        getPanel().add(my_roles_list, BorderLayout.SOUTH);
    }

    /**
     * Sets the JComboBoxes enabled.
     * 
     * @param the_choice
     */
    public void setEnabled(boolean the_choice) {
        my_conferences_list.setEnabled(the_choice);
        my_roles_list.setEnabled(the_choice);
    }

    /**
	 * Retrieves the Paper object associated with the chosen title.
	 */
	private Conference retrieveConference(String the_title)
	{
		Conference to_return = null;
		for(int i = 0; i < my_conferences.length; i++)
		{
			if(the_title.trim().equalsIgnoreCase(my_conferences[i].getName().trim()))
			{
				to_return = my_conferences[i];
			}
		}
		return to_return;
	}
	
	/**
	 * Retrieves the Paper object associated with the chosen title.
	 */
	private Role retrieveRole(String the_role)
	{
		Role to_return = null;
		for(int i = 0; i < my_roles.length; i++)
		{
			if(the_role.trim().equalsIgnoreCase(my_roles[i].getName().trim()))
			{
				to_return = my_roles[i];
			}
		}
		return to_return;
	}
    
    /**
     * Listens for a conference to be selected from the box to enable the 
     * other box for the roles you have in that conference.
     * 
     * @author Tristan Boucher
     * 
     */
    private class ConferenceBoxListener implements ActionListener {

        /**
         * The method that notifies the observer on an action.
         * 
         * @param the_event
         *            The event that was caused.
         */
        public void actionPerformed(final ActionEvent the_event) {
        	if (the_event.getSource() != null) {
                my_roles_list.setEnabled(true);
                getController().postConferenceSelected(retrieveConference(
                		(String)my_conferences_list.getSelectedItem()));
            }
        }
    }

    /**
     * This listener happens when a role is selected from the 
     * combo box. It sends the selections to the controller
     * to decide what view to show next.
     * 
     * @author Tristan Boucher
     * 
     */
    private class RoleBoxListener implements ActionListener {

        /**
         * The method that notifies the observer on an action.
         * 
         * @param the_event
         *            the event that was caused.
         */
        public void actionPerformed(final ActionEvent the_event) {
            getController().postRoleSelected(retrieveRole(
            		(String) my_roles_list.getSelectedItem()));
        }
    }

	@Override
	public ArrayList<String> retrieveInfo() {
		return null;
	}

}
