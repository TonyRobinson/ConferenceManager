/**
 * Conference Manager Model.
 */

package model;

import java.util.Date;

/**
 * This class represents a Conference.
 * 
 * @author David Burslem, Tristan Boucher
 * @version 19 May 2013
 */
public class Conference {
	/**
	 * the conference name.
	 */
	private String my_name;
	
	/**
	 * the day the paper must be submitted.
	 */
	private Date my_paperSubmitDate;
	
	/**
	 * the first day of the conference.
	 */
	private Date my_startDate;
	
	/**
	 * the day the decision on for a paper must be made.
	 */
	private Date my_decisionDate;
	
	/**
	 * the day the review for a paper must be submitted.
	 */
	private Date my_reviewDate;
	
	/**
	 * the day the recommendation for a paper must be submitted.
	 */
	private Date my_recommendDate;
	
	/**
	 * the last day of the conference.
	 */
	private Date my_endDate;
	
	/**
	 * the chair that is in charge of the conference.
	 */
	private User my_chair;

	/**
	 * Constructor that only requires a name to create
	 * a conference.
	 * 
	 * @param my_name The name for the conference to be
	 * created.
	 */
	public Conference(final String the_name)
	{
		my_name = the_name;
	}
	
	/**
	 * @param my_name
	 * @param my_paperSubmitDate
	 * @param my_startDate
	 * @param my_decisionDate
	 * @param my_reviewDate
	 * @param my_recommendDate
	 * @param my_endDate
	 * @param my_chair
	 */
	public Conference(final String my_name, final User my_chair, 
	        final Date my_startDate, final Date my_endDate, 
	        final Date my_paperSubmitDate, final Date my_reviewDate,
	        final Date my_recommendDate, final Date my_decisionDate ) {
		this.my_name = my_name;
		this.my_paperSubmitDate = my_paperSubmitDate;
		this.my_startDate = my_startDate;
		this.my_decisionDate = my_decisionDate;
		this.my_reviewDate = my_reviewDate;
		this.my_recommendDate = my_recommendDate;
		this.my_endDate = my_endDate;
		this.my_chair = my_chair;
	}

	/**
	 * @param the_deadlines
	 * @param the_startDate
	 * @param the_endDate
	 * @param the_chair
	 */
	public Conference(final Date the_startDate,
			final Date the_endDate, final User the_chair) {
		my_name = "";
		my_startDate = the_startDate;
		my_endDate = the_endDate;
		my_chair = the_chair;
	}
	
	/**
	 * @param the_deadlines
	 * @param the_startDate
	 * @param the_endDate
	 * @param the_chair
	 */
	public Conference(final String the_conference_name,final Date the_startDate,
			final Date the_endDate, final User the_chair) {
		my_name = the_conference_name;
		my_startDate = the_startDate;
		my_endDate = the_endDate;
		my_chair = the_chair;
	}
	
	/**
	 * gets the name of the conference.
	 * 
	 * @return the conference name
	 */
	public String getName() {
		return my_name;
	}
	
	/**
	 * sets the name of the conference.
	 * 
	 * @param the_name the name of the conference.
	 */
	public void setName(final String the_name) {
		my_name = the_name;
	}
	
	/**
	 * gets the first day of the conference.
	 * 
	 * @return the my_startDate
	 */
	public Date getStartDate() {
		return my_startDate;
	}

	/**
	 * sets the first day of the conference.
	 * 
	 * @param the_startDate
	 *            the start date.
	 */
	public void setStartDate(final Date the_startDate) {
		my_startDate = the_startDate;
	}

	/**
	 * gets the last day of the conference.
	 * 
	 * @return the my_endDate
	 */
	public Date getEndDate() {
		return my_endDate;
	}

	/**
	 * Sets the last day of the conference.
	 * 
	 * @param the_endDate
	 *            the last day of the conference.
	 */
	public void setEndDate(final Date the_endDate) {
		my_endDate = the_endDate;
	}

	/**
	 * gets the chair.
	 * 
	 * @return the my_chair
	 */
	public User getChair() {
		return my_chair;
	}

	/**
	 * sets the chair.
	 * 
	 * @param the_chair
	 *            the chair.
	 */
	public void setChair(final User the_chair) {
		my_chair = the_chair;
	}


	/**
	 * Returns the paper submission date.
	 * 
	 * @return the my_paperSubmitDate
	 */
	public Date getPaperSubmitDate() {
		return my_paperSubmitDate;
	}

	/**
	 * Sets the paper submission date.
	 * 
	 * @param my_paperSubmitDate the my_paperSubmitDate to set
	 */
	public void setPaperSubmitDate(final Date my_paperSubmitDate) {
		this.my_paperSubmitDate = my_paperSubmitDate;
	}

	/**
	 * Returns the decision date.
	 * 
	 * @return the my_decisionDate
	 */
	public Date getDecisionDate() {
		return my_decisionDate;
	}

	/**
	 * Sets the decision date.
	 * 
	 * @param my_decisionDate the my_decisionDate to set
	 */
	public void setDecisionDate(final Date my_decisionDate) {
		this.my_decisionDate = my_decisionDate;
	}

	/**
	 * Returns the review date.
	 * 
	 * @return the my_reviewDate
	 */
	public Date getReviewDate() {
		return my_reviewDate;
	}

	/**
	 * Sets the review date.
	 * 
	 * @param my_reviewDate the my_reviewDate to set
	 */
	public void setReviewDate(final Date my_reviewDate) {
		this.my_reviewDate = my_reviewDate;
	}

	/**
	 * Returns the recommend date.
	 * 
	 * @return the my_recommendDate
	 */
	public Date getRecommendDate() {
		return my_recommendDate;
	}

	/**
	 * Sets the recommend date.
	 * 
	 * @param my_recommendDate the my_recommendDate to set
	 */
	public void setRecommendDate(final Date my_recommendDate) {
		this.my_recommendDate = my_recommendDate;
	}

	/**
	 * toString()
	 * @author Tristan Boucher
	 */
	@Override
	public String toString()
	{
		return getName() + "-" + 
				this.getChair().getFirstName() + " " + 
				this.getChair().getLastName();
	}
}
