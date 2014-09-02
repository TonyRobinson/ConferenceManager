/**
 * Conference Manager Model.
 */

package model;

import java.util.List;

/**
 * Contains a Reviewer.
 * 
 * @author 	Andrew Sorensen
 * @version $Id: Reviewer.java 91 2013-06-02 02:45:55Z andrewx@uw.edu $
 */
public interface Reviewer extends UserInterface {
	/**
	 * Returns a list of reviews.
	 * 
	 * @return	List<Review>
	 */
	public List<Review> getReviews();
	
	/**
	 * Adds a a review.
	 * 
	 * @param 	the_review	the review to add.
	 */
	public void addReview(final Review the_review);
	
	// TODO should reviews be able to be removed?
}
