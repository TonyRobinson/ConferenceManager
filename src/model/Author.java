/**
 * Conference Manager Model.
 */

package model;

import java.util.List;

/**
 * Contains an Author.
 * 
 * @author 	Andrew Sorensen
 * @version $Id: Author.java 91 2013-06-02 02:45:55Z andrewx@uw.edu $
 */
public interface Author extends UserInterface {
	/**
	 * Returns a list of papers.
	 * 
	 * @return	List<Paper>
	 */
	public List<Paper> getPapers();
	
	/**
	 * Adds a paper.
	 * 
	 * @param 	the_paper	the paper to add.
	 */
	public void addPaper(final Paper the_paper);
	
	/**
	 * Removes a paper.
	 * 
	 * @param 	the_paper	the paper to remove.
	 */
	public void removePaper(final Paper the_paper);
}
