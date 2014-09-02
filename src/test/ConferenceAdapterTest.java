package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import model.Conference;
import model.Role;
import model.User;
import controller.AbstractAdapter;
import controller.ConferenceAdapter;
import controller.ConferenceRoleAdapter;
import controller.UserAdapter;

public class ConferenceAdapterTest extends AbstractAdapter {

	ConferenceAdapter my_conference_adapter;
	UserAdapter my_user_adapter;
	User my_user;
	Conference my_conference;
	
	@Before
	public void setUp() {
		my_conference_adapter = new ConferenceAdapter();
		my_user_adapter = new UserAdapter();
		my_user = new User();
		//my_conference = new Conference();
	}

	/**
	 * Inserts a conference to the database and checks if it exists.
	 */
	@Test
	public void testConferenceExistence() {
		
	}

}
