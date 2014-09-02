package model;

/**
 * Contains a Subchair.
 * 
 * @author 	Andrew Sorensen
 * @version $Id: Subchair.java 12 2013-05-18 05:53:11Z andrewx@uw.edu $
 */
public interface Subchair extends UserInterface {
	/**
	 * Returns the the chair.
	 * 
	 * @return Chair
	 */
	public Chair getChair();
	
	/**
	 * Sets the Chair.
	 * 
	 * @param 	the_chair	the chair to set.
	 */
	public void setChair(final Chair the_chair);
}
