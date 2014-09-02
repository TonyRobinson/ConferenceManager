/**
 * Conference Manager Model.
 */

package model;

import java.util.List;

/**
 * This class represents a review that is submitted by a Reviewer.
 * 
 * @author David Burslem
 * @version 13 May 2013
 */
public class Review {
    /**
     * the comments are an integer value that represents what the reviewer rates
     * the paper. There are several questions that require an integer answer.
     */
    private List<Integer> my_comments;

    /**
     * The Reviewer of the paper.
     */
    private User my_reviewer;

    /**
     * The name of the paper.
     */
    private Paper my_paper;

    /**
     * The name of the conference.
     */
    private Conference my_conference;

    /**
     * A rating the reviewer gives the paper.
     */
    private int my_summary;

    /**
     * comments the reviewer can leave.
     */
    private String my_summaryComments;

    /**
     * Constructor.
     */
    public Review(final List<Integer> the_comments, final User the_reviewer,
            final Paper the_paper, final Conference the_conference,
            final int the_summary, final String the_summaryComments) {
        my_comments = the_comments;
        my_reviewer = the_reviewer;
        my_paper = the_paper;
        my_conference = the_conference;
        my_summary = the_summary;
        my_summaryComments = the_summaryComments;
    }

    /**
     * Returns a list of comments.
     * 
     * @return List<Integer>
     */
    public List<Integer> getComments() {
        return my_comments;
    }

    /**
     * Returns a the Reviewer.
     * 
     * @return User
     */
    public User getReviewer() {
        return my_reviewer;
    }

    /**
     * Returns the Paper.
     * 
     * @return Paper
     */
    public Paper getPaper() {
        return my_paper;
    }

    /**
     * Returns the Conference.
     * 
     * @return Conference
     */
    public Conference getConference() {
        return my_conference;
    }

    /**
     * Returns the summary.
     * 
     * @return int
     */
    public int getSummary() {
        return my_summary;
    }

    /**
     * Returns the summary comments.
     * 
     * @return String
     */
    public String getSummaryComments() {
        return my_summaryComments;
    }

    /**
     * Sets the comments.
     * 
     * @param the_comments
     */
    public void setComments(final List<Integer> the_comments) {
        my_comments = the_comments;
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
     * Sets the Paper.
     * 
     * @param the_paper
     */
    public void setPaper(final Paper the_paper) {
        my_paper = the_paper;
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
     * Sets the summary.
     * 
     * @param the_summary
     */
    public void setSummary(final int the_summary) {
        my_summary = the_summary;
    }

    /**
     * Sets the summary comments.
     * 
     * @param the_summaryComments
     */
    public void setSummaryComments(final String the_summaryComments) {
        my_summaryComments = the_summaryComments;
    }

}
