package net.sf.taverna.t2.activities.stilts.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Utils mainly copied from Astro Taverna
 * @author Julian Garrido and Christian Brenninkmeijer
 * @since    19 May 2011
 */
public class MyUtils {
    
    public static String ordinal(int i) {
        String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
        default:
            return i + sufixes[i % 10];
        }
    }
    
     public static String readFileAsString(String filePath) throws java.io.IOException{
        byte[] buffer = new byte[(int) new File(filePath).length()];
        BufferedInputStream f = null;
        try {
            f = new BufferedInputStream(new FileInputStream(filePath));
            f.read(buffer);
        } finally {
            if (f != null) try { f.close(); } catch (IOException ignored) { }
        }
        return new String(buffer);
    }
	
    public static File writeStringAsTmpFile(String content) throws java.io.IOException{
	    
        File file = File.createTempFile("astro", null);
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();
	    
        return file;
    }
	
    public static void writeStringToAFile(String filepath, String content) throws IOException{
        BufferedWriter out = new BufferedWriter(new FileWriter(filepath));
        out.write(content);
        out.close();
    }
	
    public static String convertStreamToString(InputStream is)
        throws IOException {
        // To convert the InputStream to String we use the
        // Reader.read(char[] buffer) method. We iterate until the
        // Reader return -1 which means there's no more data to
        // read. We use the StringWriter class to produce the string.
        //
        if (is != null) {
            Writer writer = new StringWriter();
 
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {       
            return "";
        }
    }
		
}
