package net.sf.taverna.t2.activities.stilts.utils;

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
        System.out.println("security was " + System.getSecurityManager());
        System.setProperty("votable.strict", "false");
        SecurityManager securityBackup = System.getSecurityManager();
        try {
            System.setSecurityManager(new NoExitSecurityManager());
        System.out.println("main start");
            Stilts.main(parameters);
        System.out.println("main out");
            consumer.reset(RunStatus.SUCCESS);
        System.out.println("success");
       } catch (Exception ex){
            consumer.reset(RunStatus.FAILED);
       } finally {
            System.setSecurityManager(securityBackup);
            System.out.println("security reset " + System.getSecurityManager());
       }
       System.out.println("Strinlts runner run ending");
    }
}
