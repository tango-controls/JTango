package fr.soleil.tangoweb.data;

/**
 * A TangoWebBean's Attribute
 * Could contains a TangoWebBean 
 * @author BARBA-ROSSA
 *
 */
public class TangoWebBeanAttribute implements java.io.Serializable
{
	/**
	 * The name of the attribute in the bean. 
	 */
	private String m_strName = null;
	/**
	 * If the type of the attribute is a bean, this value contains the id to find it.
	 */
	private String m_strBeanId = null;
	/**
	 * The bean object.
	 */
	private TangoWebBean m_strBean = null;
	
	/**
	 * Default constructor.
	 *
	 */
	public TangoWebBeanAttribute()
	{
	}

	/**
	 * Constructor for TangoWebAttribute
	 * @param strName : The name of the attribute in the bean.
	 * @param strBeanId : If the type of the attribute is a bean, this value contains the id to find it
	 */
	public TangoWebBeanAttribute(String strName, String strBeanId)
	{
		m_strName = strName;
		m_strBeanId = strBeanId;	
	}

	/**
	 * Return the bean
	 * @return TangoWebBean
	 */
	public TangoWebBean getBean() {
		return m_strBean;
	}
	
	/**
	 * Set the bean value
	 * @param bean
	 */
	public void setBean(TangoWebBean bean) {
		m_strBean = bean;
	}
	
	/**
	 * Get the bean id (coresponding to a TangoWebBean).
	 * @return String
	 */
	public String getBeanId() {
		return m_strBeanId;
	}
	
	/**
	 * Change the bean id value
	 * @param beanId
	 */
	public void setBeanId(String beanId) {
		m_strBeanId = beanId;
	}
	
	/**
	 * Get the name of the attribute
	 * @return String
	 */
	public String getName() {
		return m_strName;
	}
	
	/**
	 * change the name of the attribute
	 * @param name
	 */
	public void setName(String name) {
		m_strName = name;
	}
}
