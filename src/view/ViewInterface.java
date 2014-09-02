package view;

import java.awt.Component;
import java.util.ArrayList;

/**
 * The interface for all views.
 * 
 * @author Tristan Boucher
 * @version 6/8/2013
 *
 */
public interface ViewInterface {
	
	/**
	 * Sets the view visible or not according to the_choice.
	 * @param the_choice
	 */
	public void setViewVisible(boolean the_choice);
	
	/**
	 * The generic information retrieval system that returns
	 * an arraylist of strings.
	 * @return an array list of strings containing an organized
	 * representation of what information was entered into the 
	 * view.
	 */
	public ArrayList<String> retrieveInfo();

	/**
	 * Returns the top-most panel used in the creation of this
	 * view.
	 * @return This view's containing panel.
	 */
	public Component getPanel();
}
