

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;

/*
 * FileChooserDemo.java uses these files:
 *   images/Open16.gif
 *   images/Save16.gif
 */

public class FileChooserWaction extends JPanel
                             implements ActionListener {
    static private final String newline = "\n";
    JButton openButton, saveButton;
    JTextArea log;
    JFileChooser fc;

    public FileChooserWaction() {
        super(new BorderLayout());

        //Create the log first, because the action listeners
        //need to refer to it.
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        //Create a file chooser
        fc = new JFileChooser();

        //Uncomment one of the following lines to try a different
        //file selection mode.  The first allows just directories
        //to be selected (and, at least in the Java look and feel,
        //shown).  The second allows both files and directories
        //to be selected.  If you leave these lines commented out,
        //then the default mode (FILES_ONLY) will be used.
        //
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        //Create the open button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        openButton = new JButton("Open a File...",
//                                 createImageIcon("images/Open16.gif"));
        							createImageIcon("images/open.gif"));
        openButton.addActionListener(this);

        //Create the save button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        saveButton = new JButton("Save a File...",
//                                 createImageIcon("images/Save16.gif"));
									createImageIcon("images/save.gif"));
        saveButton.addActionListener(this);

        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);

        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {

        //Handle open button action.
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(FileChooserWaction.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would open the file.
                File ThisFile ; 
                String filename = "" ; 
                String output = "" ;
                try 
                {
                	List files = getFileListing( file );

                	//print out all file names, and display the order of File.compareTo
                	Iterator<File> filesIter = files.iterator();
                	while( filesIter.hasNext() ){

                		// System.out.println( "--" + filesIter.next() + "--" );
                	ThisFile = filesIter.next() ;
                	// System.out.println( aaa );
                	filename = ThisFile.getName() ;
                	output = output.concat(filename) ;
                	output = output.concat("\n") ;
                		
                	}
                }
               
                catch (FileNotFoundException error) {
                    //do something clever with the exception
                    System.out.println(error.getMessage());
                }
//								not sure if required                
//            	finally {
//            	System.out.println("file not found exception") ;
//            	}

//                log.append("Opening: " + file.getName() + "." + newline);            
                
                //
                // prints the output in the created window
                //
                
                log.append(output + newline); 


            
                
            } else {
                log.append("Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());

        //Handle save button action.
        } else if (e.getSource() == saveButton) {
            int returnVal = fc.showSaveDialog(FileChooserWaction.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would save the file.
                log.append("Saving: " + file.getName() + "." + newline);
            } else {
                log.append("Save command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        }
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = FileChooserWaction.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("FileChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new FileChooserWaction());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
            }
        });
    }
    
    
    static public List getFileListing( File aStartingDir ) throws FileNotFoundException{
        validateDirectory(aStartingDir);
        List result = new ArrayList();

        File[] filesAndDirs = aStartingDir.listFiles();
        List filesDirs = Arrays.asList(filesAndDirs);
        Iterator filesIter = filesDirs.iterator();
        File file = null;
        while ( filesIter.hasNext() ) {
          file = (File)filesIter.next();
          result.add(file); //always add, even if directory
          if (!file.isFile()) {
            //must be a directory
            //recursive call!
            List deeperList = getFileListing(file);
            result.addAll(deeperList);
          }

        }
        Collections.sort(result);
        return result;
      }

      /**
      * Directory is valid if it exists, does not represent a file, and can be read.
      */
      static private void validateDirectory (File aDirectory) throws FileNotFoundException {
        if (aDirectory == null) {
          throw new IllegalArgumentException("Directory should not be null.");
        }
        if (!aDirectory.exists()) {
          throw new FileNotFoundException("Directory does not exist: " + aDirectory);
        }
        if (!aDirectory.isDirectory()) {
          throw new IllegalArgumentException("Is not a directory: " + aDirectory);
        }
        if (!aDirectory.canRead()) {
          throw new IllegalArgumentException("Directory cannot be read: " + aDirectory);
        }
      }
}
