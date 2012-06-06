/**
 * 
 */
package fr.soleil.tango.clientapi.attribute;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceAttribute;

/**
 * @author ABEILLE
 * 
 */
public interface IInserter {
    void insert(final DeviceAttribute da, final Object value) throws DevFailed;

    /**
     * For Images
     * 
     * @param value
     * @param dimX
     * @param dimY
     * @param da
     * @throws NumberFormatException
     */
    void insert(final DeviceAttribute da, final Object value, final int dimX, final int dimY) throws DevFailed;
}
