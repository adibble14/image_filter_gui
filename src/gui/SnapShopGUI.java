

package gui;

import filters.*;
import image.PixelImage;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Andrew Dibble
 * @version Winter 2021
 * a SnapShopGui object creates the Gui platform to open an image and apply filters to the image
 */
public class SnapShopGUI extends JFrame {

    private static final JButton edgeDetect = new JButton("Edge Detect");  //creating the buttons for the north region
    private static final JButton edgeHighlight = new JButton("Edge Highlight");
    private static final JButton flipHorizontal = new JButton("Flip Horizontal");
    private static final JButton flipVertical = new JButton("Flip Vertical");
    private static final JButton grayScale = new JButton("Grayscale");
    private static final JButton soften = new JButton("Soften");

    //the buttons that are disabled at the start of the program
    JButton[] disabledButtons = {edgeDetect, edgeHighlight, flipHorizontal, flipVertical, grayScale, soften, saveAs, closeImage};

    private static final Icon openIcon = new ImageIcon("icons/open.gif"); //creating icons for images
    private static final Icon saveIcon = new ImageIcon("icons/save.gif");
    private static final Icon closeIcon = new ImageIcon("icons/close.gif");

    private static final JButton open = new JButton("Open...", openIcon);   //creating the buttons for the south region
    private static final JButton saveAs = new JButton("Save As...", saveIcon);
    private static final JButton closeImage = new JButton("Close Image", closeIcon);

    private static final JLabel imageLabel = new JLabel();  //where the image is held

    private static PixelImage image;  //the image
    private static File file;  //the file chosen

    public SnapShopGUI() {
        start();
    }

    /**
     * essentially the method of the class, calls other methods to ultimately create the GUI
     */
    public void start() {

        addButtons(this);  //adding the buttons

        JPanel center = new JPanel();
        center.setBackground(Color.LIGHT_GRAY);
        this.add(center); //the center panel
        center.setLayout(new GridBagLayout());
        center.add(imageLabel); //adding the label for the image

        addActionListeners();  //adding action listeners

    }

    /**
     * adds buttons to the gui
     *
     * @param gui the frame to which the buttons shall be added
     */
    public void addButtons(SnapShopGUI gui) {

        JPanel north = new JPanel();
        north.setBackground(Color.LIGHT_GRAY);
        gui.add(north, BorderLayout.NORTH);  //the north panel

        JPanel south = new JPanel();
        south.setBackground(Color.LIGHT_GRAY);
        gui.add(south, BorderLayout.SOUTH); //the south panel

        JButton[] effectButtons = {edgeDetect, edgeHighlight, flipHorizontal, flipVertical, grayScale, soften};
        for (JButton button : effectButtons) {    //adding buttons to northern region
            north.add(button);
        }

        south.add(open);
        south.add(saveAs);
        south.add(closeImage);  //adding buttons to the southern region

        for (JButton button : disabledButtons) {  //disabling all buttons but open
            button.setEnabled(false);
        }
    }

    /**
     * adds action listeners to all the buttons
     */
    public void addActionListeners() {
        JFrame frame = new JFrame();

        open.addActionListener(e ->   //action listener for open button which opens a File Chooser
                {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                    int returnVal = fileChooser.showOpenDialog(this);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        file = fileChooser.getSelectedFile();  //getting the selected file
                        try {  //seeing if the file is an image
                            image = PixelImage.load(file);
                            updateImage();

                            for (JButton button : disabledButtons) {
                                button.setEnabled(true);
                            }
                        } catch (IOException ex) {  //outputting an error message if not an image
                            JOptionPane.showMessageDialog(frame, "The file " + file + " did not contain an image");
                        }
                    }
                }
        );
        closeImage.addActionListener(e -> {
            imageLabel.setIcon(null);  //removing the image

            for (JButton button : disabledButtons) {  //disabling all buttons but open
                button.setEnabled(false);
            }

            this.setSize(800, 125);  //putting the GUI back to original size
        });

        saveAs.addActionListener(e -> {
            try {
                image.save(file);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Unable to save image");
            }
        });

        edgeDetect.addActionListener(e -> {new EdgeDetectFilter().filter(image);  updateImage();});  //action listeners for filter buttons
        edgeHighlight.addActionListener(e -> {new EdgeHighlightFilter().filter(image); updateImage();});
        flipHorizontal.addActionListener(e -> {new FlipHorizontalFilter().filter(image); updateImage();});
        flipVertical.addActionListener(e -> {new FlipVerticalFilter().filter(image); updateImage();});
        grayScale.addActionListener(e -> {new GrayscaleFilter().filter(image); updateImage();});
        soften.addActionListener(e -> {new SoftenFilter().filter(image);updateImage();});
    }

    /**
     * updates the image, called when user selected a new image, adds a filter to an image, or wants to close the image selected
     */
    public void updateImage() {  //updates the image
        ImageIcon icon = new ImageIcon(image);
        imageLabel.setIcon(icon);
        this.pack();
        this.setMinimumSize(new Dimension(800, 125));
    }
}