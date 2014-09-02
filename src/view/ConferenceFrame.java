package view;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Will be the frame that swaps panels in and out of it.
 * This process of coordinating all of the swapping and displaying
 * of all the gui components is dealt with by the Controller class.
 * 
 * @author Tristan Boucher
 * @version 5/22/2013
 *
 */
public class ConferenceFrame extends JFrame
{
    /**
     * The window title.a
     * Andrew Sorensen
     */
    private final String WINDOW_TITLE = "Group 2 Conference Manager";
    
    /**
     * Constructs a new ConferenceFrame.
     */
	public ConferenceFrame()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(600, 400));
		setTitle(WINDOW_TITLE);
		this.setLocation(500, 200);
	}
}
