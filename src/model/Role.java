/**
 * 
 */
package model;

/**
 * Represents a possible user role
 * 
 * @author David
 * @version $Id: Role.java 226 2013-06-08 18:31:59Z andrewx@uw.edu $
 */
public class Role {
    /**
     * The name of the role.
     */
    private String my_name;

    /**
     * Constructs a new Role (with name = Author).
     */
    public Role() {
        setName("Author");
    }

    /**
     * Constructs a new Role.
     * 
     * @param the_name
     *            the name of the role.
     */
    public Role(final String the_name) {
        setName(the_name);
    }

    /**
     * Returns the name of the role.
     * 
     * @return the my_name
     */
    public String getName() {
        return my_name;
    }

    /**
     * Sets the name of the role.
     * 
     * @param the_name
     */
    public void setName(final String the_name) {
        my_name = the_name;
    }
    
    /**
     * Returns a string representing this object.
     * 
     * @return string
     */
    @Override
    public String toString()
    {
        return this.getName();
    }

    /**
     * Returns whether or not the Roles are equal.
     * 
     * @return returns true if they are equal otherwise it will return false.
     * @param the_category
     */
    @Override
    public boolean equals(final Object the_role) {
        boolean result = false;

        if (the_role instanceof Role) {
            if (((Role)the_role).getName().equals(this.getName())) {
                result = true;
            }
        }

        return result;
    }
}
