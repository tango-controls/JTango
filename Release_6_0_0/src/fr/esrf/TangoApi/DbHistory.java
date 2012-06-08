package fr.esrf.TangoApi;

/**
 *	Class Description:
 *	This class manage data object for Tango databse history access.
 */
public class DbHistory implements java.io.Serializable {

  private String propName; // Property name
  private String attName;  // Attribute name (Not used for device properties)
  private String value;    // Property value
  private String date;     // Update date
  private boolean deleted; // Deleted flag

  /**
   * Constructs a property.
   * @param propName Property name
   * @param date Update date (in MySQL format)
   * @param value Property value
   */
  DbHistory(String propName,String date,String[] value) {

    this.propName = propName;
    this.date = formatMySQLDate(date);
    this.value = formatValue(value);
    deleted = (value.length==0);

  }

  /**
   * Constructs an attribute property.
   * @param attName Attribute name
   * @param propName Property name
   * @param date Update date (in MySQL format)
   * @param value Property value
   */
  DbHistory(String attName,String propName,String date,String[] value) {

    this(propName,date,value);
    this.attName = attName;

  }

  /**
   * Returns property name.
   */
  public String getName() {
    return propName;
  }

  /**
   * Return attribute name. Used when retrieving attribute property.
   */
  public String getAttributeName() {
    return attName;
  }

  /**
   * Returns the value.
   */
  public String getValue() {
    return value;
  }

  /**
   * Returns the update date.
   */
  public String getDate() {
    return date;
  }

  /**
   * Return true if the property is deleted.
   */
  public boolean isDeleted() {
    return deleted;
  }

  /**
   * Format the value in one string by adding "\n" after each string.
   * @param value value to convert.
   */
  private String formatValue(String[] value) {

    String ret = "";
    for(int i=0;i<value.length;i++) {
      ret += value[i];
      if(i<value.length-1) ret+="\n";
    }
    return ret;

  }

  /**
   * @param date MySQL date
   * @return Date in DD/MM/YYYY hh:mm:ss format
   */
  private String formatMySQLDate(String date) {

    // Handle MySQL date formating
    if( date.indexOf("-")!=-1 )
      return date.substring(8,10) + "/" + date.substring(5,7) + "/" + date.substring(0,4) + " " +
             date.substring(11,13) + ":" + date.substring(14,16) + ":" + date.substring(17,19);
    else
      return date.substring(6,8) + "/" + date.substring(4,6) + "/" + date.substring(0,4) + " " +
             date.substring(8,10) + ":" + date.substring(10,12) + ":" + date.substring(12,14);
    
  }

}
