package net.sf.taverna.t2.activities.stilts.utils;

import java.security.Permission;

/**
 * Checks the status of the a Stilts Run.
 * 
 * Extends the version in AstroTaverna by applying any existing Security Manager
 * @author Julian Garrido and Christian Brenninkmeijer
 * @since    19 May 2011
 */
public class NoExitSecurityManager extends SecurityManager {
    
    private final SecurityManager previousManager;
    
    public NoExitSecurityManager(){
        previousManager = System.getSecurityManager();
    }
    
    @Override
    public void checkPermission(Permission perm) {
        if (previousManager != null){
            previousManager.checkPermission(perm);
        }
    }

    @Override
    public void checkExit(int status)
    {
      /* Don't allow exit with any status code 1 . */
      //status == 0: no error
      //status != 0: error 
        if(status != 0){
            throw new SecurityException("Service exited with " + status);
        }
    }
}
