/**
 * Conference Manager Model.
 */

package model;

/**
 * This class represents a Recommendation that is submitted by a sub-chair.
 * 
 * @author David Burslem
 * @version 13 May 2013
 */
public class Recommendation {
	/**
	 * The reviewer of the paper.
	 */
	private User my_reviewer;
	
	/**
	 * The conference the paper is related to.
	 */
	private Conference my_conference;
	
	/**
	 * The paper the recommendation is related to.
	 */
	private Paper my_paper;
	
	/**
	 * The sub chair's recommendation, given in an integer value.
	 */
	private int my_recommendation;
	
	/**
	 * The sub chair's rational for giving the recommendation they did.
	 */
	private String my_rational;
	
	/**
	 * Constructs a new Recommendation.
	 */
	public Recommendation(final User the_reviewer, final Conference the_conference,
			final Paper the_paper, final int the_recommendation, final String the_rational) {
		my_reviewer = the_reviewer;
		my_conference = the_conference;
		my_recommendation = the_recommendation;
		my_rational = the_rational;
		my_paper = the_paper;
	}
	
	/**
	 * Returns the reviewer.
	 * 
	 * @return the my_reviewer
	 */
	public User getReviewer() {
		return my_reviewer;
	}
	
	/**
	 * Returns the conference.
	 * 
	 * @return the my_conference
	 */
	public Conference getConference() {
		return my_conference;
	}
	
	/**
	 * Returns the Recommendation.
	 * 
	 * @return the my_recommendation
	 */
	public int getRecommendation() {
		return my_recommendation;
	}

	/**
	 * @return the my_rational
	 */
	public String getRational() {
		return my_rational;
	}
	
	/**
	 * Returns the paper.
	 * @return The paper.
	 */
	public Paper getPaper() {
		return my_paper;
	}
	
	/**
	 * Sets the reviewer.
	 * 
	 * @param the_reviewer
	 */
	public void setReviewer(final User the_reviewer) {
		my_reviewer = the_reviewer;
	}
	
	/**
	 * Sets the Conference.
	 * 
	 * @param the_conference
	 */
	public void setConference(final Conference the_conference) {
		my_conference = the_conference;
	}
	
	/**
	 * Sets the recommendation.
	 * 
	 * @param the_recommendation
	 */
	public void setRecommendation(final int the_recommendation) {
		my_recommendation = the_recommendation;
	}
	
	/**
	 * Sets the rational.
	 * 
	 * @param the_rational
	 */
	public void setRational(final String the_rational) {
		my_rational = the_rational;
	}
	
	/**
	 * Set the paper.
	 * @param the_paper The paper.
	 */
	public void setPaper(final Paper the_paper) {
		my_paper = the_paper;
	}
}
