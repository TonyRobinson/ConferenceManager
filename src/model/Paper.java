/**
 * Conference Manager Model.
 */

package model;

import java.io.File;

/**
 * This class represents a paper that is submitted by an author.
 * 
 * @author David Burslem, Tony Robinson
 * @version 13 May 2013
 */
public class Paper {
    /**
     * The title of the paper.
     */
    private String my_title;

    /**
     * The author of the paper.
     */
    private User my_author;

    /**
     * the Category that the paper would be submitted in relation to the
     * conference.
     */
    private Category my_category;

    /**
     * the abstract field of the paper.
     */
    private String my_abstract;

    /**
     * The search keywords submitted with the paper.
     */
	private String my_keywords;
	
	/**
	 * The file that contains the paper.
	 */
	private File my_file;

    /**
     * Constructs a new paper.
     */
    public Paper() {
        
    }

    /**
     * Constructs a new paper.
     */
    public Paper(final String the_title, final User the_author,
            final Category the_category, final String the_abstract,
            final File the_file) {
        my_title = the_title;
        my_author = the_author;
        my_category = the_category;
        my_abstract = the_abstract;
        my_file = the_file;
    }
    
    /**
     * Overloaded Constructor for Paper
     */
    public Paper(final String the_title, final User the_author,
    			final Category the_category, final String the_keywords,
    			final String the_abstract, final File the_file)
    {
    	my_title = the_title;
    	my_author = the_author;
    	my_category = the_category;
    	my_keywords = the_keywords;
    	my_abstract = the_abstract;
    	my_file = the_file;
    }

    /**
     * Returns the title.
     */
    public String getTitle() {
        return my_title;
    }

    /**
     * Returns the author.
     */
    public User getAuthor() {
        return my_author;
    }

    /**
     * Returns the Category.
     */
    public Category getCategory() {
        return my_category;
    }

    /**
     * Returns the abstract.
     */
    public String getAbstract() {
        return my_abstract;
    }
    
    /**
     * Returns the keywords
     */
    public String getKeywords()
    {
    	return my_keywords;
    }
    
    /**
     * Returns the file object for the paper.
     * @return the file object containing the actual paper.
     */
    public File getFile()
    {
    	return my_file;
    }
    
    /**
     * Sets the title.
     */
    public void setTitle(final String the_title) {
        this.my_title = the_title;
    }

    /**
     * Sets the author.
     */
    public void setAuthor(final User the_author) {
        my_author = the_author;
    }

    /**
     * Sets the Category.
     */
    public void setCategory(final Category the_category) {
        my_category = the_category;
    }

    /**
     * Sets the abstract.
     */
    public void setAbstract(final String the_abstract) {
        my_abstract = the_abstract;
    }
    
    /**
     * Sets the file name.
     * @param the_file_name The file name of the paper.
     */
    public void setFile(final File the_file) {
    	my_file = the_file;
    }
    
    /**
     * Sets the key words.
     * @param the_keywords The keywords of the paper.
     */
    public void setKeywords(final String the_keywords) {
    	my_keywords = the_keywords;
    }

}
