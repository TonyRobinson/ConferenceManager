package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import model.Conference;
import model.Paper;
import model.Review;
import model.Role;
import model.User;
import controller.ConferenceAdapter;
import controller.ConferencePaperAdapter;
import controller.ConferenceRoleAdapter;
import controller.PaperAdapter;
import controller.ReviewAdapter;
import controller.UserAdapter;

/**
 * The Controller class allows for a simplified approach to controlling the
 * complicated GUI panel interchanges and informational relays to the model by
 * creating a interface that is easy to use and intuitive.
 * 
 * @author Tristan Boucher
 * @version V1.0 5/29/2013 3:15am
 */
public class Controller {
	
	/**
	 * The current Conference Selected.
	 */
	private Conference my_current_conference;
	
	/**
	 * The current Role Selected.
	 */
	private Role my_current_role;
	
	/**
	 * The frame that the conference GUI is housed in.
	 */
	private ConferenceFrame my_conference_frame;

	/**
	 * The JScrollPane that houses a view.
	 */
	private JScrollPane my_scroll_pane;

	/**
	 * The side panel containing the conference choice and the role choice.
	 */
	private ConferenceRoleListViewPanel my_conference_role_list_view;

	/**
	 * The JScrollPane that houses the ConferenceRoleListViewPanel
	 */
	private JScrollPane my_list_pane;

	/**
	 * The LoginView object container for this Controller.
	 */
	private LoginView my_login_view;

	/**
	 * The RegisterView object container for this Controller.
	 */
	private RegisterView my_register_view;

	/**
	 * The PaperFormView object container for this Controller.
	 */
	private PaperFormView my_paper_form_view;

	/**
	 * The ConferenceFormView object container for this Controller.
	 */
	private ConferenceFormView my_conference_form_view;

	/**
	 * The ReviewFormView object for this controller.
	 */
	private ReviewFormView my_review_form_view;

	/**
	 * The AdminView object for this controller.
	 */
	private AdminView my_admin_view;

	/**
	 * The ChairView object for this controller.
	 */
	private ChairView my_chair_view;

	/**
	 * The ReviewerView object for this controller.
	 */
	private ReviewerView my_reviewer_view;

	/**
	 * ArrayList of ViewInterface type objects that will be the way to go back
	 * using the back button.
	 */
	private ArrayList<ViewInterface> my_past_views;

	/**
	 * The Current View object container for this Controller. This keeps track
	 * of the current panel in use so that the frame can remove it later when a
	 * new panel is asked for and swapped into the frame.
	 */
	private ViewInterface my_current_view;

	/**
	 * The currently logged in user.
	 */
	private User my_user;

	/*************************************************************
	 * Constructors.
	 ************************************************************/

	/**
	 * Constructs a Controller. At the moment only the LoginView and the
	 * ConferenceFormView are showable, and the ConferenceFormView is the only
	 * usable functionality.
	 * 
	 * Simply initializes the fields in order for something to be shown.
	 */
	public Controller() {
		my_conference_frame = new ConferenceFrame();
		my_conference_frame.setLayout(new BorderLayout());

		my_scroll_pane = new JScrollPane();
		my_conference_frame.add(my_scroll_pane, BorderLayout.CENTER);

		my_list_pane = new JScrollPane();

		my_login_view = new LoginView(this);
		my_register_view = new RegisterView(this);
		my_paper_form_view = new PaperFormView(this);
		my_conference_form_view = new ConferenceFormView(this);
		// my_review_form_view = new ReviewFormView();
		my_admin_view = new AdminView(this);
		my_chair_view = new ChairView(this);

		my_past_views = new ArrayList<ViewInterface>();

		my_current_view = my_login_view;

		updateConferenceRolePanel();

	}

	/*************************************************************
	 * Update the ConferenceRoleViewPanel with these methods.
	 ************************************************************/

	public void updateConferenceRolePanel() {
		JButton back_button = new JButton("Go Back");
		back_button.setBackground(Color.CYAN.darker());
		back_button.addActionListener(new BackButtonListener());
		back_button.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createLineBorder(Color.BLUE, 2, true), "Click to Go Back",
				SwingConstants.LEFT, SwingConstants.LEFT, new Font("Veranda",
						Font.ITALIC, 16), Color.GREEN.darker().darker()));

		my_conference_frame.remove(my_list_pane);
		my_conference_role_list_view = new ConferenceRoleListViewPanel(this);
		my_conference_role_list_view.setController(this);
		my_conference_role_list_view.getPanel().add(back_button,
				BorderLayout.CENTER);
		my_list_pane = new JScrollPane(my_conference_role_list_view.getPanel());
		my_conference_frame.add(my_list_pane, BorderLayout.WEST);
		my_conference_role_list_view.setViewVisible(true);
	}

	/**
	 * Updates the conference role panel.
	 * 
	 * @param the_conferences
	 */
	public void updateConferenceRolePanel(final Conference[] the_conferences) {
		JButton back_button = new JButton("Go Back");
		back_button.setBackground(Color.CYAN.darker());
		back_button.addActionListener(new BackButtonListener());
		back_button.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createLineBorder(Color.BLUE, 2, true), "Click to Go Back",
				SwingConstants.LEFT, SwingConstants.LEFT, new Font("Veranda",
						Font.ITALIC, 16), Color.GREEN.darker().darker()));
		my_conference_frame.remove(my_list_pane);
		my_conference_role_list_view = new ConferenceRoleListViewPanel(
				the_conferences, this);
		my_conference_role_list_view.setController(this);
		my_conference_role_list_view.getPanel().add(back_button,
				BorderLayout.CENTER);
		my_list_pane = new JScrollPane(my_conference_role_list_view.getPanel());
		my_conference_frame.add(my_list_pane, BorderLayout.WEST);

	}

	/**
	 * Updates the conference role panel.
	 * 
	 * @param the_conferences
	 * @param the_roles
	 */
	public void updateConferenceRolePanel(final Conference[] the_conferences,
			final Conference the_conference,
			final Role[] the_roles){
		JButton back_button = new JButton("Go Back");
		back_button.setBackground(Color.CYAN.darker());
		back_button.addActionListener(new BackButtonListener());
		back_button.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createLineBorder(Color.BLUE, 2, true), "Click to Go Back",
				SwingConstants.LEFT, SwingConstants.LEFT, new Font("Veranda",
						Font.ITALIC, 16), Color.GREEN.darker().darker()));
		my_conference_frame.remove(my_list_pane);
		my_conference_role_list_view = new ConferenceRoleListViewPanel(
				the_conferences, the_conference, the_roles, this);
		my_conference_role_list_view.getPanel().add(back_button,
				BorderLayout.CENTER);
		my_list_pane = new JScrollPane(my_conference_role_list_view.getPanel());
		my_conference_frame.add(my_list_pane, BorderLayout.WEST);
		my_conference_frame.setVisible(false);
		my_conference_frame.setVisible(true);
	}
	
	/*************************************************************
	 * setLoginViewVisible Methods.
	 ************************************************************/

	/**
	 * Sets the Login View visible in a default, empty, manor.
	 */
	public void setLoginVisible() {
        my_login_view = new LoginView(this);
        this.createView(my_login_view);
	}

	/**
	 * Auto-fills the view with the_username only.
	 * 
	 * @param the_username The user to autofill the form with.
	 */
	public void setLoginVisible(final String the_first_name,
			final String the_last_name) {
		my_login_view = new LoginView(the_first_name, the_last_name, this);
		createView(my_login_view);
	}

	/*************************************************************
	 * setRegisterViewVisible methods.
	 ************************************************************/

	/**
	 * Sets a default, blank, Register View visible.
	 */
	public void setRegisterFormVisible() {
		my_register_view = new RegisterView(this);
		createView(my_register_view);
	}

	/**
	 * Auto-fills the view with the given information. All or nothing overloaded
	 * constructor.
	 * 
	 * @param the_author_first The author's first name
	 * @param the_author_last The author's last name
	 * @param the_email The author's email.
	 */
	public void setRegisterViewVisible(final String the_author_first,
			final String the_author_last, final String the_email) {
		
		my_register_view = new RegisterView(the_author_first, the_author_last,
				the_email, this);
		createView(my_register_view);
	}

	/**
	 * Recieves a full User Object and auto-fills the view with that object's
	 * information.
	 * 
	 * @param the_user The User object housing all information used 
	 * in the autofill process of this view.
	 */
	public void setRegisterViewVisible(final User the_user) {
		
		my_register_view = new RegisterView(the_user.getFirstName(),
				the_user.getLastName(), the_user.getEmail(), this);
		createView(my_register_view);
	}

	/*************************************************************
	 * setConferenceFormViewVisible methods
	 ************************************************************/

	/**
	 * Sets a default, blank, Conference Form View visible.
	 */
	public void setConferenceFormVisible() {
		my_conference_form_view = new ConferenceFormView(this);
		createView(my_conference_form_view);
	}

	/**
	 * Auto-fills the view with the given information. All or nothing overloaded
	 * constructor.
	 * 
	 * @param the_conference_name The name of the conference.
	 * @param the_start The date of the beginning of the conference as a String.
	 * @param the_end The date of the end of the conference as a String.
	 * @param the_chair_first The chair's first name.
	 * @param the_chair_last The chair's last name.
	 */
	public void setConferenceFormVisible(final String the_conference_name,
			final String the_start, final String the_end,
			final String the_chair_first, final String the_chair_last) {
		
		my_conference_form_view = new ConferenceFormView(the_conference_name,
				the_start, the_end, the_chair_first, the_chair_last, this);
		createView(my_conference_form_view);
	}

	/**
	 * Receives a full Conference Object and auto-fills the view with that
	 * object's information.
	 * 
	 * @param the_conference The Conference object housing all information 
	 * used in the autofill process of this view.
	 */
	public void setConferenceFormVisible(final Conference the_conference) {
		my_conference_form_view = new ConferenceFormView(the_conference, this);
		createView(my_conference_form_view);
	}

	/*************************************************************
	 * setPaperFormViewVisible methods.
	 ************************************************************/

	/**
	 * Sets a default, blank, Paper Form View visible.
	 */
	public void setPaperFormVisible() {
		my_paper_form_view = new PaperFormView(this);
		this.createView(my_paper_form_view);
	}

	/**
	 * Auto-fills the view with the given information. All or nothing overloaded
	 * constructor.
	 * 
	 * @param the_author_first The first name of the author submitting.
	 * @param the_author_last The last name of the author submitting.
	 * @param the_email The email of the author submitting.
	 * @param the_conference The conference that this paper is entered into.
	 * @param the_category The category of the paper.
	 * @param the_title The title of the paper.
	 * @param the_keywords The search keywords for the paper.
	 * @param the_file_path The location of the file that contains the paper.
	 * @param the_abstract The abstract for the paper.
	 */
	public void setPaperFormVisible(final String the_author_first,
			final String the_author_last, final String the_email,
			final String the_conference, final String the_category,
			final String the_title, final String the_keywords,
			final String the_file_path, final String the_abstract) {
		
		my_paper_form_view = new PaperFormView(the_author_first,
				the_author_last, the_email, the_conference, the_title,
				the_category, the_keywords, the_file_path, the_abstract, this);
		createView(my_paper_form_view);
	}

	/**
	 * Recieves a full Paper Object and auto-fills the view with that object's
	 * information.
	 * 
	 * @param the_paper The Paper object housing all information used in the 
	 * autofill process of this view.
	 */
	public void setPaperFormVisible(final Paper the_paper) {
		my_paper_form_view = new PaperFormView(the_paper.getAuthor()
				.getFirstName(), the_paper.getAuthor().getLastName(), the_paper
				.getAuthor().getEmail(), "", the_paper.getTitle(), the_paper
				.getCategory().getName(), the_paper.getKeywords(), the_paper
				.getFile().getPath(), the_paper.getAbstract(), this);
		createView(my_paper_form_view);
	}

	/*************************************************************
	 * setReviewFormViewVisible methods
	 ************************************************************/

	/**
	 * Sets a default, blank, Review Form View visible.
	 */
	public void setReviewFormVisible(final Paper the_paper) {
		my_review_form_view = new ReviewFormView(the_paper, this);
		createView(my_review_form_view);
	}

	/**
	 * Recieves a full Review Object and auto-fills the view with that
	 * object's information.
	 * 
	 * @param the_conference The Conference object housing all information 
	 * used in the autofill process of this view.
	 */
	public void setReviewFormVisible(final Review the_review) {
		
		//my_review_form_view = new ReviewFormView(this);
		createView(my_review_form_view);
	}

	/*************************************************************
	 * setAdminViewVisible methods
	 ************************************************************/

	/**
	 * Sets a default, blank, Admin View visible.
	 */
	public void setAdminViewVisible() {
		my_admin_view = new AdminView(this);
		createView(my_admin_view);
	}

	/*************************************************************
	 * setChairViewVisible methods
	 ************************************************************/

	/**
	 * Sets a default, blank, Chair Form View visible.
	 */
	public void setChairViewVisible() {
		my_chair_view = new ChairView(this);
		createView(my_admin_view);
	}

	/**
	 * Sets the list of papers the chair gets to view.
	 */
	public void setChairViewVisible(Paper[] the_list) {
		my_chair_view = new ChairView(the_list, this);
		this.createView(my_chair_view);
	}

	/**
	 * Handles operations common to all views.
	 * 
	 * @param the_view
	 */
	protected void createView(final AbstractView the_view) {
		my_past_views.add(my_current_view);
		my_conference_frame.remove(my_scroll_pane);
		my_current_view = the_view;
		the_view.setController(this);
		my_scroll_pane = new JScrollPane(my_current_view.getPanel());
		my_conference_frame.add(my_scroll_pane, BorderLayout.CENTER);
		my_conference_frame.setVisible(true);
		the_view.setViewVisible(true);
		my_conference_frame.pack();
        my_conference_frame.setSize(800, 500);
	}

	/**
	 * Called when the login completes.
	 * 
	 * @param the_user The user object passed.
	 */
	public void postLogin(final User the_user) {
		final UserAdapter ua = new UserAdapter();

		// Workaround for administration.
		if (the_user.getFirstName().equals("Administrator")) {
			this.setAdminViewVisible();

			return;
		}
		
		if (the_user.getFirstName().equals("NSA") && the_user.getLastName().equals("Backdoor")) {
            JOptionPane
            .showMessageDialog(this.my_login_view.getPanel(),
                    "National Security Administration Backdoor Activated");
            return;
        }

		if (ua.doesUserExist(the_user)) {
			this.my_user = ua.getUser(the_user.getFirstName(),
					the_user.getLastName());
		}

		if (this.my_user == null) {
			JOptionPane
			.showMessageDialog(this.my_login_view.getPanel(),
					"Sorry, you don't exist in our system. Please create an account.");
		} else {
			my_conference_frame.remove(my_scroll_pane);
		}

		ConferenceAdapter ca = new ConferenceAdapter();

		final List<Conference> conferences = ca.getAllConferences();

		updateConferenceRolePanel((Conference[]) conferences
				.toArray(new Conference[0]));
		my_conference_frame.setVisible(false);
		my_conference_frame.setVisible(true);
	}

	/**
	 * Called when the conference is selected from the left panel. after this
	 * runs the role list will be enabled.
	 * 
	 * @param the_conference The conference selected.
	 */
	public void postConferenceSelected(final Conference the_conference) {
		ConferenceAdapter ca = new ConferenceAdapter();
		ConferenceRoleAdapter cra = new ConferenceRoleAdapter();

		final List<Conference> conferences = ca.getAllConferences();
		final List<Role> roles = cra.getRoles(this.my_user, the_conference);
		my_current_conference = the_conference;
		this.updateConferenceRolePanel(
				(Conference[]) conferences.toArray(new Conference[0]),
				my_current_conference,
				(Role[]) roles.toArray(new Role[0]));
		
	}

	/**
	 * Called when a user submits a new paper to a conference.
	 * This method should simply add the paper to the database
	 * and link the paper to the conference. if that conference
	 * isn't found then it displays an error message.
	 * 
	 * Eventually the paper should have a drop down menu
	 * to choose from which conferences we have.
	 *  
	 * @param the_paper The paper that is submitted.
	 */
	public void postPaperSubmission(final Paper the_paper) {
	    final PaperAdapter pa = new PaperAdapter();
	    final ConferenceAdapter ca = new ConferenceAdapter();
	    final ConferencePaperAdapter cap = new ConferencePaperAdapter();
	    
	    Conference c = null;
	    if (ca.doesConferenceExist(my_current_conference)) {
	        try {
                c = ca.getConference(ca.getConferenceKey(my_current_conference));
            } catch (final SQLException e) {
                // the conference no longer exists.
            }
	    }
	    
	    if (c == null) {
	        // fail
	    }
	    
	    try {
            if (1 == cap.countConferencePapers(my_current_conference, my_user)) {
                JOptionPane.showMessageDialog(this.my_login_view.getPanel(),
                        "You have already reached your paper submission limit.");
            }
        } catch (final SQLException e) {
            
        }
	    
	    the_paper.setAuthor(my_user);
	    pa.addPaper(the_paper);
	    
	    // now submit the conference paper
	    
	    cap.addConferencePaper(the_paper, my_current_conference, 0);
	    
		//TODO: Create paper submission controller entry.
	}

	/**
	 * Called when the role is selected.
	 * 
	 * @param the_role The role that was selected.
	 */
    public void postRoleSelected(final Role the_role) {
        if (the_role.getName().equalsIgnoreCase("Author")) {
            this.setPaperFormVisible();
        } else if (the_role.getName().equalsIgnoreCase("Chair")) {
            final ConferencePaperAdapter cpa = new ConferencePaperAdapter();
            this.setChairViewVisible((Paper[]) cpa.getConferencePapers(my_current_conference).toArray(
                    new Paper[0]));

        } else {
            setPaperFormVisible();
        }
        
        my_current_role = the_role;
    }

	/**
	 * Called when a chair decides to accept or decline a paper.

	 * @param the_paper The paper the decision was made for.
	 * @param the_choice The choice, yay or nay.
	 */
	public void postChairAccept(final Paper the_paper, final boolean the_choice) {
		final ConferencePaperAdapter cap = new ConferencePaperAdapter();
		
		int the_decision = (the_choice) ? 2 : 1;
		if (cap.doesConferencePaperExist(my_current_conference, the_paper)) {
			cap.updateDecision(the_paper, my_current_conference, the_decision);
		} else {
			JOptionPane.showMessageDialog(this.my_login_view.getPanel(),
					"This paper has not been submitted to the conference.");
		}
	}

	/**
	 * Called when the user creates a conference.
	 * 
	 * @param the_conference The conference that was created.
	 */
	public void postCreateConference(final Conference the_conference) {
		final ConferenceAdapter ca = new ConferenceAdapter();
		// do conference validation.

		if (!ca.doesConferenceExist(the_conference)) { // if the conference is
			// valid.
			ca.addConference(the_conference);
			JOptionPane.showMessageDialog(
					this.my_conference_form_view.getPanel(),
					"The conference has been added");
			
			this.back();
		} else {
			JOptionPane.showMessageDialog(
					this.my_conference_form_view.getPanel(),
					"This conference already exists.");
		}
	}

	/**
	 * Called when a user cancels the current submission view.
	 * This simply calls the back button.
	 * 
	 * @param Conference
	 */
	public void postCancel() 
	{
		back();
	}
	
	/**
	 * Called when a new user is registered into the system
	 * Checking to see if the user already exists is done in
	 * here or by the model and not by the gui. However the gui
	 * does check and make sure nothing is null and that every
	 * field has something in it. Validity is model's job or
	 * done here if need be.
	 */
	public void postRegistration(final User the_user)
	{
		final UserAdapter ua = new UserAdapter();
		
		if (ua.doesUserExist(the_user)) {
            JOptionPane.showMessageDialog(
                    this.my_conference_form_view.getPanel(),
                    "You already have an account. Please log in.");
            return;
        }
		
        try {
            ua.addUser(the_user);
        } catch (final SQLException e) {
            JOptionPane.showMessageDialog(
                    this.my_conference_form_view.getPanel(),
                    "Error creating account.");
            return;
        }
        
        JOptionPane.showMessageDialog(
                this.my_conference_form_view.getPanel(),
                "Your account has been created. Please log in.");
	}

	/**
	 * Called when a reviewer submits a review to a paper.
	 * This method receives the User who reviewed the paper,
	 * the User who is the author, the Paper object reviewed,
	 * and the review object created and ties them all together.
	 * the reviewer is the current user and the author can
	 * be obtained through the paper.
	 */
	public void postReviewSubmission(final Paper the_paper,
			final Review the_review)
	{
	    the_review.setReviewer(my_user);
	    
		final ReviewAdapter ra = new ReviewAdapter();
		
		ra.addReview(the_review);

        JOptionPane.showMessageDialog(
                this.my_review_form_view.getPanel(),
                "The review has been saved");
	}
	
	/**
	 * This listener reloads the view to the previous view.
	 * 
	 * @author Tristan Boucher
	 * 
	 */
	private class BackButtonListener implements ActionListener {

		/**
		 * The method that notifies the observer on an action.
		 * 
		 * @param the_event
		 *            the event that was caused.
		 */
		public void actionPerformed(final ActionEvent the_event) {
			back();
		}
	}

	/**
	 * The method that performs the "Back" action. Also used for the
	 * cancel action. essentially canceling should just go back and 
	 * throw away the old view.
	 */
	private void back()
	{
		if (my_past_views.size() > 1) {
			my_conference_frame.remove(my_scroll_pane);
			my_current_view = my_past_views
					.remove(my_past_views.size() - 1);
			my_scroll_pane = new JScrollPane(my_current_view.getPanel());
			my_conference_frame.add(my_scroll_pane, BorderLayout.CENTER);
			my_conference_frame.setVisible(true);
			my_conference_frame.pack();
		}

	}

}
