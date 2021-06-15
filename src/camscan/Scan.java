package camscan;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Scanner;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.swing.*;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;
import java.text.SimpleDateFormat; 

public class Scan {

    JFrame frame ;
    String[] options = {"Copy to Clipboard","Save to File","Ok"};

    public void scanFile(String img_file_name,boolean capturedImage){

        frame = new JFrame();

        Tesseract tesseract = new Tesseract();
        try {
            tesseract.setDatapath("C:\\Users\\Tejasvp\\Downloads\\Compressed\\Tess4J-3.4.8-src\\Tess4J\\tessdata");
            String inputFileName = capturedImage ? "images/" + img_file_name +".jpg" : img_file_name ;
            String text
                    = tesseract.doOCR(new File( img_file_name ));

            int option  = JOptionPane.showOptionDialog(frame,
            	    text,
            	    	    "Text",
            	    	    JOptionPane.YES_OPTION,
            	    	    JOptionPane.YES_NO_CANCEL_OPTION,
            	    	    null,
            	    	    options,
            	    	    options[2]);
            switch(option) {
            case 0:
            	StringSelection stringSelection = new StringSelection(text);
            	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            	clipboard.setContents(stringSelection, null);
            	JOptionPane.showMessageDialog(frame, "Text Copied to Clipboard");
            	break;
            case 1:
            	try {
					final String filename = "text/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".txt";
            		  FileOutputStream outputStream = new FileOutputStream(filename);
            		    byte[] strToBytes = text.getBytes();
            		    outputStream.write(strToBytes);
            		    outputStream.close();
            	      JOptionPane.showMessageDialog(frame, "Text Copied to File : " + filename);
            	} catch (IOException e) {
            	      
            	      e.printStackTrace();
            	}
            	break;
            default:
            	
            }
            

        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }
}