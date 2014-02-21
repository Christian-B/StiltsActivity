/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.stilts.utils;
 
import java.io.IOException;

public class Tester
{
 
    public static void main(String... args) throws IOException
    {
        String[] parameters = new String[8];
        parameters[0] = "tcatn";
        parameters[1] = "nin=2";
        parameters[2] = "ofmt=ascii";
        parameters[3] = "out=C:\\temp\\test.txt";
        parameters[4] = "ifmt1=tst";
        parameters[5] = "in1=C:\\temp\\test.tst";
        parameters[6] = "ifmt2=csv";
        parameters[7] = "in2=C:\\temp\\test.csv";
        long past = System.currentTimeMillis();
            
        StreamRerouter rerouter = new StreamRerouter();
        StiltsRunner runner = new StiltsRunner(rerouter, parameters);
            
        Thread runnerThread = new Thread(runner);
            
        runnerThread.start();
        rerouter.listen();
                       
        long now = System.currentTimeMillis();
        System.out.println("Time taken: " + (now-past) + " ms");
    }
}