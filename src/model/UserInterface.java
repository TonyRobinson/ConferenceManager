/**
 * Conference Manager Model.
 */

package model;

/**
 * Contains a UserInterface.
 * 
 * @author 	Andrew Sorensen
 * @version $Id: UserInterface.java 93 2013-06-02 02:48:40Z andrewx@uw.edu $
 */
public interface UserInterface {

	/**
	 * Returns the first name.
	 * 
	 * @return 	String
	 */
	public String getFirstName();
	
	/**
	 * Sets the first name.
	 * 
	 * @param 	the_name	the name to set.
	 */
	public void setFirstName(final String the_name);

	/**
	 * Returns the last name.
	 * 
	 * @return String
	 */
	public String getLastName();
	
	/**
	 * Sets the last name.
	 * 
	 * @param 	the_name 	the name to set.
	 */
	public void setLastName(final String the_name);
}
