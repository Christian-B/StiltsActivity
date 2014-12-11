package net.sf.taverna.t2.activities.stilts.utils;

import uk.ac.starlink.ttools.Stilts;

/**
 * Wrapper to run stilts in its own thread, informing the consumer when it is done.
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
        
    public void run() {
        System.setProperty("votable.strict", "false");
        SecurityManager securityBackup = System.getSecurityManager();
        try {
            System.setSecurityManager(new NoExitSecurityManager());
            Stilts.main(parameters);
            consumer.reset(RunStatus.SUCCESS);
       } catch (Exception ex){
           ex.printStackTrace();
            consumer.reset(RunStatus.FAILED);
       } finally {
            System.setSecurityManager(securityBackup);
       }
    }
}
