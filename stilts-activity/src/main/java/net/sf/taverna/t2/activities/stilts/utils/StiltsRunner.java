/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.stilts.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.starlink.ttools.Stilts;

/**
 *
 * @author christian
 */  
public class StiltsRunner implements Runnable
{
    private final StreamRerouter consumer;
    private final String[] parameters;
    
    public StiltsRunner(StreamRerouter dataConsumer, String[] stiltsParameters){
        consumer = dataConsumer;
        parameters = stiltsParameters;
    }
        
    @Override
    public void run() {
        SecurityManager securityBackup = System.getSecurityManager();
        try {
            System.setSecurityManager(new NoExitSecurityManager());
            Stilts.main(parameters);
        } catch (Exception ex){
            //do nothing
        } finally {
            System.setSecurityManager(securityBackup);
            consumer.reset();
            System.out.println("out:");
            System.out.println(consumer.getSavedOut());
            System.out.println("err:");
            System.out.println(consumer.getSavedErr());
        }
    }
}
