/**
 * Conference Manager Model.
 */

package model;

/**
 * This class specifies a category for papers.
 * 
 * @author David Burslem
 * @version 13 May 2013
 */
public class Category {
	/**
	 * The name of the category.
	 */
	private String my_name;

	/**
	 * Constructs a new category.
	 */
	public Category() {
		setName("Science Conference");
	}

	/**
	 * Constructs a new Category.
	 * 
	 * @param the_name the name of the category.
	 */
	public Category(final String the_name) {
		setName(the_name);
	}

	/**
	 * Returns the name.
	 * 
	 * @return the my_name
	 */
	public String getName() {
		return my_name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param the_name the name to set.
	 */
	public void setName(final String my_name) {
		this.my_name = my_name;
	}

	/**
	 * @return returns true if they are equal otherwise it will return false.
	 * @param the_category
	 */
	public boolean equals(final Category the_category) {
		boolean result = false;
		if (this.equals(the_category)) {
			result = true;
		}
		return result;
	}
}
