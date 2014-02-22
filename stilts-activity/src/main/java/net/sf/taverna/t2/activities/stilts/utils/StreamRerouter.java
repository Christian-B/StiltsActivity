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

        System.out.println(System.err);
        PipedInputStream receiveErrPipe = new PipedInputStream(10);
        PipedOutputStream redirectErrPipe = new PipedOutputStream(receiveErrPipe);
        originalSystemErr = System.err;
        System.setErr(new PrintStream(redirectErrPipe));
        errRecord = new StringBuilder();
        errReader = new BufferedReader( new InputStreamReader( receiveErrPipe ));
        System.out.println(System.err);

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

    public void reset(RunStatus newStatus) {
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

    private void checkErr() throws IOException{
        while (errReader.ready()){        
            String temp= errReader.readLine();
            errRecord.append(temp);
            errRecord.append(System.lineSeparator());
            originalSystemErr.println("err: " + temp);
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
    private void checkIn() throws IOException{
        if (originalSystemIn.available() > 0){
            System.setIn(originalSystemIn);
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
           reset(RunStatus.ERROR);
        }       
        System.out.println("Rerouter run ending");
        System.out.println(System.err);
    }

    /**
     * @return the runStatus
     */
    public RunStatus getRunStatus() {
        return runStatus;
    }
}