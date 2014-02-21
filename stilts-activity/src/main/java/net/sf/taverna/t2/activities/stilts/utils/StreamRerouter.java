/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.stilts.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.StringBufferInputStream;

public class StreamRerouter  
{
    private final BufferedReader outReader;
    private final BufferedReader errReader;
    private final StringBuilder outRecord;
    private final StringBuilder errRecord;
    
    private final PrintStream originalSystemOut;
    private final PrintStream originalSystemErr;
    private final InputStream originalSystemIn;
    private boolean running = true;
    private RunStatus runStatus = RunStatus.RUNNING;
    
    public StreamRerouter() throws IOException {
        PipedInputStream receiveOutPipe = new PipedInputStream(10);
        PipedOutputStream redirectOutPipe = new PipedOutputStream(receiveOutPipe);
        originalSystemOut = System.out;
        System.setOut(new PrintStream(redirectOutPipe));
        outRecord = new StringBuilder();
        outReader = new BufferedReader( new InputStreamReader( receiveOutPipe ));

        PipedInputStream receiveErrPipe = new PipedInputStream(10);
        PipedOutputStream redirectErrPipe = new PipedOutputStream(receiveErrPipe);
        originalSystemErr = System.err;
        System.setErr(new PrintStream(redirectErrPipe));
        errRecord = new StringBuilder();
        errReader = new BufferedReader( new InputStreamReader( receiveErrPipe ));

        originalSystemIn = System.in;
        InputStream testInput;
        testInput = new StringBufferInputStream("");
        System.setIn(testInput);
    }
 
    public void reset(RunStatus newStatus) {
        runStatus = newStatus;
        running = false;            
        try {
            checkOut();
        } catch (Exception ex) {
            //ignore
        } finally {
            System.setOut(originalSystemOut);
        }
        try {
            checkErr();
        } catch (IOException ex) {
            //ignore
        } finally {
            System.setErr(originalSystemErr);
        }
        System.setIn(originalSystemIn);
    }
  
    public String getSavedOut(){
        return outRecord.toString();
    }
    
    public String getSavedErr(){
        return errRecord.toString();
    }

    private void checkErr() throws IOException{
        while (errReader.ready()){        
            String temp= errReader.readLine();
            errRecord.append(temp);
            errRecord.append(System.lineSeparator());
            originalSystemErr.println("err: " + temp);
        }
    }
    
    private void checkOut() throws IOException{
        while (outReader.ready()){        
            String temp=outReader.readLine();
            outRecord.append(temp);
            outRecord.append(System.lineSeparator());
            originalSystemOut.println("out: " + temp);
        }
    }

    private void checkIn() throws IOException{
        if (originalSystemIn.available() > 0){
            System.setIn(originalSystemIn);
        }
    }

    public void listen()
    {
        try
        {
            while(running){
                checkIn();
                checkOut();
                checkErr();
                Thread.sleep(100);
            }
        }
        catch (Exception e){
           //do nothing
        } finally {
            System.setOut(originalSystemOut);
            System.setErr(originalSystemErr);
            System.setIn(originalSystemIn);
        }
    }

    /**
     * @return the runStatus
     */
    public RunStatus getRunStatus() {
        return runStatus;
    }
}