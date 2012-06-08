/*	Synchrotron Soleil 
 *  
 *   File          :  DoNothingLogger.java
 *  
 *   Project       :  javaapi
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  29 juin 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: DoNothingLogger.java,v 
 *
 */
 /*
 * Created on 29 juin 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingTools.Diary;

import java.io.IOException;

public class DoNothingLogger implements ILogger {

    public DoNothingLogger() {
        super();
        // TODO Auto-generated constructor stub
    }

    public int getTraceLevel() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setTraceLevel(int level) {
        // TODO Auto-generated method stub

    }

    public void trace(int level, Object o) {
        // TODO Auto-generated method stub

    }

    public void initDiaryWriter(String path, String archiver)
            throws IOException {
        // TODO Auto-generated method stub

    }

    public void close() {
        // TODO Auto-generated method stub

    }

    public int getTraceLevel(String level_s) {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setTraceLevel(String string) {
        // TODO Auto-generated method stub
        
    }

}
