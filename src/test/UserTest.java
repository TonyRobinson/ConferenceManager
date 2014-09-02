package test;

import static org.junit.Assert.*;

import model.Paper;
import model.Review;
import model.User;

import org.junit.Before;
import org.junit.Test;

/**
 * Contains a unit test for User.
 * 
 * @author Andrew Sorensen
 * @version $Id: UserTest.java 268 2013-06-09 06:08:46Z tristb90@uw.edu $
 */
public class UserTest {
	/**
	 * Contains the first name.
	 */
	protected String my_firstName;

	/**
	 * Contains the last name.
	 */
	protected String my_lastName;

	/**
	 * The user being tested.
	 */
	private User my_user;

	/**
	 * The paper to test with.
	 */
	private Paper my_paper;

	/**
	 * The review to test with.
	 */
	private Review my_review;

	/**
	 * Sets up the environment.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		my_user = new User();
		my_lastName = "Pickle";
		my_firstName = "Summer";
		//my_paper = new Paper("good title", my_user, null, "totally abstract");
		//my_review = new Review(null, my_user, "blah", "blah", "blah", 0, "blah");
	}

	/**
	 * Tests the user's name.
	 */
	@Test
	public void testUserName() {
		
		assertSame("first name should be the default", "Default",
				my_user.getFirstName());
		assertSame("last name should be the default", "McDefault",
				my_user.getLastName());
		
		my_user.setFirstName(my_firstName);
		assertSame("first name should be "+my_firstName, my_firstName,
				my_user.getFirstName());
		
		my_user.setLastName(my_lastName);
		assertSame("last name should be "+my_lastName, my_lastName,
				my_user.getLastName());
	}
	
	/**
	 * Tests getting a list of reviews.
	 */
	@Test
	public void testGetLists() {
		assertSame("list should exist and have a size of 0", 0,
				my_user.getReviews().size());
		assertSame("list should exist and have a size of 0", 0,
				my_user.getPapers().size());
	}
	
	/**
	 * Tests adding a paper to the list.
	 */
	@Test
	public void testAddToLists() {
		my_user.addReview(my_review);
		assertSame("should have size of 1 with the new review", my_review,
				my_user.getReviews().get(0));
		assertSame("list should exist and have a size of 1", 1,
				my_user.getReviews().size());
		
		my_user.addPaper(my_paper);
		assertSame("should have size of 1 with the new paper", my_paper,
				my_user.getPapers().get(0));
		assertSame("list should exist and have a size of 1", 1,
				my_user.getPapers().size());
	}
	
	/**
	 * Tests removing a paper from the list.
	 */
	@Test
	public void testRemoveFromList() {
		my_user.addPaper(my_paper);
		assertSame("should have size of 1 with the new paper", my_paper,
				my_user.getPapers().get(0));
		assertSame("list should exist and have a size of 1", 1,
				my_user.getPapers().size());
		
		my_user.removePaper(my_paper);
		assertSame("list should exist and have a size of 0", 0,
				my_user.getPapers().size());
	}

}
