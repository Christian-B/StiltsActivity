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
        SecurityManager securityBackup = System.getSecurityManager();
        try {
            System.setSecurityManager(new NoExitSecurityManager());
            Stilts.main(parameters);
            consumer.reset(RunStatus.SUCCESS);
        } catch (Exception ex){
            consumer.reset(RunStatus.FAILED);
        } finally {
            System.setSecurityManager(securityBackup);
        }
    }
}
