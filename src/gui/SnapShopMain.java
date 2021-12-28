/*
 * TCSS 305 - Assignment 3: SnapShop
 */

package gui;

import javax.swing.*;

/**
 * Runs SnapShop by instantiating and starting the SnapShopGUI.
 * 
 * @author Marty Stepp
 * @author Daniel M. Zimmerman
 * @author Alan Fowler
 * @version Spring 2015
 */
public final class SnapShopMain {

    /**
     * Private constructor, to prevent instantiation of this class.
     */
    private SnapShopMain() {
        throw new IllegalStateException();
    }

    /**
     * The main method, invokes the SnapShop GUI. Command line arguments are
     * ignored.
     * 
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        var application = new SnapShopGUI();
        application.setTitle("TCSS 305 – Programming Assignment 3 (adibble)");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setSize(800, 125);
        application.setVisible(true);

    }
}
