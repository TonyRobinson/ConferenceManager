/**
 * Conference Manager Model.
 */

package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains a User.
 * 
 * @author  Andrew Sorensen
 * @version $Id: User.java 94 2013-06-03 07:57:27Z tristb90@uw.edu $
 */
public class User implements Author, Chair, Subchair, Reviewer {
	/**
	 * Contains the first name.
	 */
	protected String my_firstName;

	/**
	 * Contains the last name.
	 */
	protected String my_lastName;
	
	/**
	 * Contains the email address.
	 */
	protected String my_email;
	
	/**
	 * Contains the reviews.
	 */
	private List<Review> my_reviews;
	
	/**
	 * Contains the papers.
	 */
	private List<Paper> my_papers;
	
	/**
	 * Contains the chair.
	 */
	private Chair my_chair;

	/**
	 * Constructor.
	 * 
	 * @author David
	 */
	public User(final String the_firstName, final String the_lastName, 
			final String the_email, final List<Review> the_reviews, 
			final List<Paper> the_papers, final Chair the_chair) {
		my_firstName = the_firstName;
		my_lastName = the_lastName;
		my_email = the_email;
		my_reviews = the_reviews;
		my_papers = the_papers;
		my_chair = the_chair;
	}
	
	/**
	 * Constructor.
	 * 
	 * @author Tony
	 */
	public User(final String the_first_name, final String the_last_name,
			final String the_email) {
		my_firstName = the_first_name;
		my_lastName = the_last_name;
		my_email = the_email;
		my_reviews = new ArrayList<Review>();
		my_papers = new ArrayList<Paper>();
	}
	
	/**
	 * Constructor.
	 * 
	 * @author David
	 */
	public User() {
		my_firstName = "Default";
		my_lastName = "McDefault";
		my_email = "dmcd@uw.edu";
		my_reviews = new ArrayList<Review>();
		my_papers = new ArrayList<Paper>();

	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public String getFirstName() {
		return this.my_firstName;
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public void setFirstName(final String the_name) {
		this.my_firstName = the_name;
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public String getLastName() {
		return this.my_lastName;
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	public void setLastName(final String the_name) {
		this.my_lastName = the_name;
	}

	/**
	 * Returns e-mail address of user.
	 * @return E-Mail address of user.
	 */
	public String getEmail() {
		return this.my_email;
	}
	
	/**
	 * Changes the e-mail of a user.
	 * @param the_email The new e-mail to be set.
	 */
	public void setEmail(final String the_email) {
		this.my_email = the_email;
	}
	/**
	 * @{inheritDoc}
	 * @author David
	 */
	@Override
	public List<Review> getReviews() {
		return my_reviews;
	}

	/**
	 * @{inheritDoc}
	 * @author David
	 */
	@Override
	public void addReview(Review the_review) {
		my_reviews.add(the_review);

	}

	/**
	 * @{inheritDoc}
	 * @author David
	 */
	@Override
	public List<Paper> getPapers() {
		return my_papers;
	}

	/**
	 * @{inheritDoc}
	 * @author David
	 */
	@Override
	public void addPaper(Paper the_paper) {
		my_papers.add(the_paper);
	}

	/**
	 * @{inheritDoc}
	 * @author David
	 */
	@Override
	public void removePaper(Paper the_paper) {
		my_papers.remove(the_paper);
	}

	/**
	 * @{inheritDoc}
	 * @author David
	 */
	@Override
	public Chair getChair() {
		return my_chair;
	}

	/**
	 * @{inheritDoc}
	 * @author David
	 */
	@Override
	public void setChair(Chair the_chair) {
		my_chair = the_chair;
	}
}
