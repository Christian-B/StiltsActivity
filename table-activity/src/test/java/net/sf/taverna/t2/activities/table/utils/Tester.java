/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.table.utils;
 
import java.io.IOException;

import net.sf.taverna.t2.activities.table.utils.StiltsRunner;
import net.sf.taverna.t2.activities.table.utils.StreamRerouter;

public class Tester
{
 
    public static void test(String[] parameters) throws IOException, InterruptedException
    {
        long past = System.currentTimeMillis();
            
        System.out.println("Ready:");
        StreamRerouter  rerouter = new StreamRerouter();
        StiltsRunner runner = new StiltsRunner(rerouter, parameters);
//        StiltsRunner1 runner = new StiltsRunner1(parameters);
            
        Thread runnerThread = new Thread(runner);
        Thread rerouterThread = new Thread(rerouter);
            
        rerouterThread.start();
        runnerThread.start();
        runnerThread.join();
        if (rerouterThread.isAlive()){
            System.out.println("reouter to finish");
            rerouterThread.join();
        }   
        
        System.out.println("Slits run: " + rerouter.getRunStatus());
        System.out.println("ERROR:");
        System.out.println(rerouter.getSavedErr());
 //       System.out.println("OUT:");
 //       System.out.println(rerouter.getSavedOut());
        long now = System.currentTimeMillis();
        System.out.println("Time taken: " + (now-past) + " ms");
    }

    public static void main(String... args) throws IOException, InterruptedException
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
        
        test(parameters);
        test(parameters);
        parameters[4] = "ifmt1=csv";
        test(parameters);
        parameters[4] = "ifmt1=tst";
        test(parameters);
    }
}