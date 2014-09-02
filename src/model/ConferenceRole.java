/**
 * Conference Manager Model.
 */

package model;

/**
 * The Role for each user.
 * 
 * @author  David
 * @version $Id: ConferenceRole.java 177 2013-06-05 21:14:28Z ajr42@uw.edu $
 */
public class ConferenceRole {
    /**
     * The User this role applies to.
     */
	private User my_user;
	
	/**
	 * The Role the user has.
	 */
	private Role my_role;
	
	/**
	 * the conference that the role applies to a user for.
	 */
	private Conference my_conference;

	/**
	 * Constructs a new ConferenceRole.
	 */
	public ConferenceRole() {
		my_role = new Role();
		my_user = new User();
		my_conference = null;
	}
		
	/**
     * Constructs a new ConferenceRole.
     * 
     * @param the_role
     * @param the_user
	 */
	public ConferenceRole(final Role the_role, final User the_user, final Conference the_conference) {
		my_role = the_role;
		my_user = the_user;
		my_conference = the_conference;
	}
	
	/**
	 * Returns the role.
	 * 
	 * @return the my_role
	 */
	public Role getRole() {
		return my_role;
	}

	/**
	 * Sets the role
	 * 
	 * @param the_role the role to set
	 */
	public void setRole(final Role the_role) {
		my_role = the_role;
	}
	
	/**
	 * Returns the user.
	 * 
	 * @return the my_user
	 */
	public User getUser() {
		return my_user;
	}
	
	/**
	 * Sets the user.
	 * 
	 * @param the_user the user to set
	 */
	public void setUser(final User the_user) {
		my_user = the_user;
	}
	
	/**
	 * Gets the conference.
	 * @return the conference
	 */
	public Conference getConference() {
		return my_conference;
	}
	
	/**
	 * Sets a conference for a role
	 * @param the_conference the conference.
	 */
	public void setConference(Conference the_conference) {
		my_conference = the_conference;
	}
	
	/**
	 * toString()
	 * 
	 * @author Tristan Boucher
	 */
	public String toString()
	{
		return this.getRole().getName();
	}
	
}
