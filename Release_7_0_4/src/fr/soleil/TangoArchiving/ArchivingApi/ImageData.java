/*	Synchrotron Soleil 
 *  
 *   File          :  ImageData.java
 *  
 *   Project       :  javaapi_IMAGES
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  25 avr. 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: ImageData.java,v 
 *
 */
 /*
 * Created on 25 avr. 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingApi;

import java.sql.Timestamp;

import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;

/**
 * 
 * @author CLAISSE 
 */
public class ImageData 
{
    private Timestamp date;
    private int dimX;
    private int dimY;
    private String name;
    private boolean isHistoric;
    private DataBaseApi database;
    
    private double [][] value;
    
    /**
     * 
     */
    public ImageData(DataBaseApi _database) 
    {
        super();
        this.database = _database;
    }

    /**
     * @return Returns the date.
     */
    public Timestamp getDate() {
        return date;
    }
    /**
     * @param date The date to set.
     */
    public void setDate(Timestamp date) {
        this.date = date;
    }
    /**
     * @return Returns the dimX.
     */
    public int getDimX() {
        return dimX;
    }
    /**
     * @param dimX The dimX to set.
     */
    public void setDimX(int dimX) {
        this.dimX = dimX;
    }
    /**
     * @return Returns the dimY.
     */
    public int getDimY() {
        return dimY;
    }
    /**
     * @param dimY The dimY to set.
     */
    public void setDimY(int dimY) {
        this.dimY = dimY;
    }
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return Returns the isHistoric.
     */
    public boolean isHistoric() {
        return isHistoric;
    }
    /**
     * @param isHistoric The isHistoric to set.
     */
    public void setHistoric(boolean isHistoric) {
        this.isHistoric = isHistoric;
    }

    /**
     * @throws ArchivingException 
     * 
     */
    public void load() throws ArchivingException 
    {
        double [][] _value = database.getAttImageDataForDate ( this.name , this.date.toString () );
        this.value = _value;
    }

    /**
     * @return Returns the value.
     */
    public double[][] getValue() {
        return value;
    }

    /**
     * @param value The value to set.
     */
    public void setValue(double[][] value) {
        this.value = value;
    }
}
