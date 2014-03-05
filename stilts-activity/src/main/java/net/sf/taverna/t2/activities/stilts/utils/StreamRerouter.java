package net.sf.taverna.t2.activities.stilts.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.StringBufferInputStream;

public class StreamRerouter implements Runnable
{
//    private final BufferedReader outReader;
    private final BufferedReader errReader;
//    private final StringBuilder outRecord;
    private final StringBuilder errRecord;
    
//    private final PrintStream originalSystemOut;
    private final PrintStream originalSystemErr;
    private final InputStream originalSystemIn;
    private RunStatus runStatus = RunStatus.RUNNING;
    
    public StreamRerouter() throws IOException {
//        PipedInputStream receiveOutPipe = new PipedInputStream(10);
//        PipedOutputStream redirectOutPipe = new PipedOutputStream(receiveOutPipe);
//        originalSystemOut = System.out;
//        System.setOut(new PrintStream(redirectOutPipe));
//        outRecord = new StringBuilder();
//        outReader = new BufferedReader( new InputStreamReader( receiveOutPipe ));

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

/*    private void closeOut(){
        System.setOut(originalSystemOut);
        try {
            checkOut();
        } catch (Exception ex) {
            //ignore
        } finally {
        }        
        try {
            outReader.close();
        } catch (Exception ex) {
            //ignore
        }
    }
 */   
    private void closeErr(){
        System.setErr(originalSystemErr);
        try {
            checkErr();
        } catch (Exception ex) {
            //ignore
        } finally {
        }        
        try {
            errReader.close();
        } catch (Exception ex) {
            //ignore
        }
    }

    public synchronized void reset(RunStatus newStatus) {
        runStatus = newStatus;
//        closeOut();
        closeErr();
        System.setIn(originalSystemIn);
    }
  
//    public String getSavedOut(){
//        return outRecord.toString();
//    }
    
    public String getSavedErr(){
        return errRecord.toString();
    }

    private synchronized void checkErr() throws IOException{
        if(runStatus == RunStatus.RUNNING){
            while (errReader.ready()){        
                String temp= errReader.readLine();
                errRecord.append(temp);
                errRecord.append(System.lineSeparator());
                originalSystemErr.println(temp);
            }
        }
    }
    
/*    private void checkOut() throws IOException{
        System.out.println("waiting");
/*        while (outReader.ready()){        
            String temp=outReader.readLine();
            outRecord.append(temp);
            outRecord.append(System.lineSeparator());
            originalSystemOut.println("out: " + temp);
        }
    }
*/
    private synchronized void checkIn() throws IOException{
        if(runStatus == RunStatus.RUNNING){
            if (originalSystemIn.available() > 0){
                System.setIn(originalSystemIn);
            }
       }
    }

    @Override
    public void run(){
        try {
            while(runStatus == RunStatus.RUNNING){
                checkIn();
 //               checkOut();
                checkErr();
                Thread.sleep(100);
            }
        }
        catch (Exception e){
           e.printStackTrace();
           reset(RunStatus.ERROR);
        }       
    }

    /**
     * @return the runStatus
     */
    public RunStatus getRunStatus() {
        return runStatus;
    }
}