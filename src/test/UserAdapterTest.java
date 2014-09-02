package test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.AbstractAdapter;
import controller.UserAdapter;
import model.User;

/**
 * JUnit testing for the User Table. Database must be cleared for all tests to
 * complete properly.
 * 
 * @author Tony
 */
public class UserAdapterTest {
    /**
     * The user adapter
     */
    protected UserAdapter my_user_adapter;

    /**
     * A default user
     */
    protected User my_default_user;

    /**
     * A initialized user
     */
    protected User my_init_user;

    /**
     * A user for updating
     */
    protected User my_update_user;

    /**
     * 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        my_user_adapter = new UserAdapter();
        my_default_user = new User();
        my_init_user = new User("Joe", "Shmoe", "JoSh@uw.edu");
        my_update_user = new User("Sally", "Smith", "SS@Titanic.com");
        AbstractAdapter.openConnection();
    }

    @After
    public void tearDown() {
        AbstractAdapter.closeConnection();
    }

    /**
     * Tests addUser() and doesUserExist()
     */
    @Test
    public void testUserExistence() {
        boolean throw_exception = false;
        try {
            my_user_adapter.addUser(my_default_user);
        } catch (final SQLException e) {
            throw_exception = true;
        }

        assertFalse("addUser() threw an exception", throw_exception);
        throw_exception = false;

        try {
            my_user_adapter.addUser(my_init_user);
        } catch (final SQLException e) {
            throw_exception = true;
        }
        assertFalse("addUser() threw an exception", throw_exception);

        assertTrue("Default user should exist",
                my_user_adapter.doesUserExist(my_default_user));
        assertTrue("Initialized user should exist",
                my_user_adapter.doesUserExist(my_init_user));
    }

    /**
     * Tests updateUser(), getUserKey() and getUser(). If getUserKey or getUser
     * failed, then assertEquals should fail as well.
     */
    @Test
    public void testUpdateUser() {
        my_user_adapter.updateUser(my_default_user, my_update_user);
        User temp_new_user = my_user_adapter.getUser(my_user_adapter
                .getUserKey(my_update_user)); // Get user object by getting key
                                              // of new user.
        assertEquals("The default user should be updated to Sally",
                temp_new_user.getFirstName(), my_update_user.getFirstName());
        assertEquals("The default user should be updated to Smith",
                temp_new_user.getLastName(), my_update_user.getLastName());
        assertEquals("The default user should be updated to SS@Titanic.com",
                temp_new_user.getEmail(), my_update_user.getEmail());
    }
}
