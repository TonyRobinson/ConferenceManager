package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import model.Conference;
import model.User;

import org.junit.Before;
import org.junit.Test;

/**
 * Contains a unit test for Conference.
 * 
 * @author David Burslem
 * @version $Id: ConferenceTest.java 9 2013-05-19 bursld@uw.edu $
 */
public class ConferenceTest {

	
	private Date my_startDate;
	private Date my_endDate;
	private User my_chair;
	private Conference my_con;
	private Date my_date;
	private Calendar cal;
	
	@Before
	public void setUp() throws Exception {
		
		my_chair = new User();
		my_startDate = new Date();
		my_endDate = new Date();
		my_con = new Conference( my_startDate, my_endDate,
				my_chair);
		my_date = new Date();
		cal = Calendar.getInstance();
		cal.setTime(my_date);
		cal.set(Calendar.DAY_OF_MONTH, 7);
		cal.set(Calendar.MONTH, 7);
		cal.set(Calendar.YEAR, 2013);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		my_date.setTime(cal.getTimeInMillis());
		
	}

	/**
	 * making sure everything is assigned correctly in the constructor.
	 */
	@Test
	public void testConference() {
		assertSame("end date should be " + my_endDate, my_endDate,
				my_con.getEndDate());
		assertSame("start date should be " + my_startDate, my_startDate,
				my_con.getStartDate());
		assertSame("chair should be " + my_chair, my_chair, my_con.getChair());
		
	}

	/**
	 * Test the setEndDate() method and make sure all other data stays the same
	 * as expected.
	 */
	@Test
	public void testsetEndDate() {
		my_con.setEndDate(my_date);
		assertSame("end date should be " + my_endDate, my_date,
				my_con.getEndDate());
		assertSame("start date should be " + my_startDate, my_startDate,
				my_con.getStartDate());
		assertSame("chair should be " + my_chair, my_chair, my_con.getChair());
		
	}

	/**
	 * Test the setStartDate() method and make sure all other data stays the
	 * same as expected.
	 */
	@Test
	public void testsetStarDate() {
		my_con.setStartDate(my_date);
		assertSame("end date should be " + my_endDate, my_endDate,
				my_con.getEndDate());
		assertSame("start date should be " + my_startDate, my_date,
				my_con.getStartDate());
		assertSame("chair should be " + my_chair, my_chair, my_con.getChair());
		
	}

	

}
