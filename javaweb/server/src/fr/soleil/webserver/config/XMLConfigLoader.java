package fr.soleil.webserver.config;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import fr.soleil.tangoweb.data.TangoWebApplicationRules;
import fr.soleil.tangoweb.data.TangoWebBean;
import fr.soleil.tangoweb.data.TangoWebBeanAttribute;
import fr.soleil.tangoweb.data.TangoWebBeanList;
import fr.soleil.tangoweb.data.TangoWebRewriteRules;

/**
 * Load the serialization parameters
 * @author BARBA-ROSSA
 *
 */
public class XMLConfigLoader 
{

	public static TangoWebBeanList loadSerializationConfig(String xmlfileName) throws Exception
	{
		// we load the xml file
		org.jdom.Document document = loadXMLFile(xmlfileName);
	    return buildTangWebList(document);
	}

	public static TangoWebRewriteRules loadRulesConfig(String xmlfileName) throws Exception 
	{
		// we load the xml file
		org.jdom.Document document = loadXMLFile(xmlfileName);
		return getTangoWebRewriteRules(document);
	}
	
	
	public static TangoWebApplicationRules loadApplicationRulesConfig(String xmlfileName) throws Exception 
	{
		// we load the xml file
		org.jdom.Document document = loadXMLFile(xmlfileName); 
		return getTangoWebApplicationRules( document);
	}
	
	/**
	 * We load the config xml file  
	 * @param xmlfileName
	 * @return Document
	 * @throws Exception
	 */
	private static Document loadXMLFile(String xmlfileName) throws Exception
	{
		// On crée une instance de SAXBuilder
		SAXBuilder sxb = new SAXBuilder();
		try
		{
		    //On crée un nouveau document JDOM avec en argument le fichier XML
			//Le parsing est terminé ;)
			//InputStream stream = XMLConfigLoader.class.getResourceAsStream(xmlfileName);
			URL uri = XMLConfigLoader.class.getClassLoader().getResource(xmlfileName);
		    return sxb.build(new File(uri.toURI()));
		   }
		catch(Exception e)
		{
			System.err.println("FATAL ERROR : We can't load the XML Config file :" + xmlfileName);
			System.err.println("FATAL ERROR : " + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * We build the list of TangoWebBean
	 * @param document
	 * @return TangoWebBeanList
	 */
	private static TangoWebBeanList buildTangWebList(Document document)
	{
		Element racine = document.getRootElement();

		Element beanNode = racine.getChild("beans");
		// we get the list of beans
		List<Element> beans = beanNode.getChildren();
		Element bean = null;
		Element tempElt = null;
		// 
		TangoWebBeanList beanList = new TangoWebBeanList();
		TangoWebBean webBean = null;
		
		// for each bean we get the content
		for(int i=0; i < beans.size();i++)
		{
			bean = beans.get(i);
			webBean = new TangoWebBean();
			// we get the id
			webBean.setBeanID(bean.getAttributeValue("id"));
			// we get the class name
			tempElt = bean.getChild("className");
			if(tempElt != null)
				webBean.setClassName(tempElt.getText());

			// we get the store object boolean
			tempElt = bean.getChild("storeObject");
			if(tempElt != null)
				webBean.setStoreObject(new Boolean(tempElt.getText()));

			// we get the value which indicate if we store the object into a optimized storage 
			tempElt = bean.getChild("optimizedStorage");
			if(tempElt != null)
				webBean.setOptimizedStorage(new Boolean(tempElt.getText()));
			
			
			// we get the remove all attribute boolean
			tempElt = bean.getChild("removeAllAttributes");
			if(tempElt != null)
				webBean.setRemoveAllAttribute(new Boolean(tempElt.getText()));
			
			//List<String> m_removeAttributes;
			tempElt = bean.getChild("removeAttributes");
			if(tempElt != null)
				webBean.setRemoveAttributes(getRemoveAttributesList(tempElt));
			
			tempElt = bean.getChild("attributes");
			if(tempElt != null)
				webBean.setAttributes(getAttributesList(tempElt));

			beanList.addBean(webBean);
		}
		return beanList;
	}
	
	private static TangoWebRewriteRules getTangoWebRewriteRules(Document document)
	{
		Element racine = document.getRootElement();
		Element ruleNode = racine.getChild("rewriterules");
		TangoWebRewriteRules ruleList = getTangoWebRewriteRules(ruleNode);
		return ruleList;
	}
	
	private static TangoWebRewriteRules getTangoWebRewriteRules(Element ruleNode)
	{
		TangoWebRewriteRules ruleList = new TangoWebRewriteRules();
		List<Element> rules = ruleNode.getChildren();		
		Element rule = null;
		Element tmpelt = null;
		String tempSrc = null;
		String tempDest = null;
		// for each bean we get the content
		for(int i=0; i < rules.size();i++)
		{
			rule = rules.get(i);
			// we get the src
			tmpelt = rule.getChild("src");
			tempSrc = tmpelt.getText();
			tmpelt = rule.getChild("dest");
			tempDest = tmpelt.getText();
			ruleList.addRules(tempSrc, tempDest);
		}		
		return ruleList;		
	}
	
	private static TangoWebApplicationRules getTangoWebApplicationRules(Document document)
	{
		TangoWebApplicationRules rulesList = new TangoWebApplicationRules();
		Element racine = document.getRootElement();
		Element applicationRulesNode = racine.getChild("applicationRules");
		List<Element> applications = applicationRulesNode.getChildren();		
		TangoWebRewriteRules rules = null;
		Element application = null;
		Element tmpElt = null;
		String tempAppli = null;
		
		// for each bean we get the content
		for(int i=0; i < applications.size();i++)
		{
			application = applications.get(i);
			// we get the name
			tmpElt = application.getChild("name");
			tempAppli = tmpElt.getText();
			// we get the rules
			tmpElt =  application.getChild("rewriterules");
			rules = getTangoWebRewriteRules(tmpElt);
			// we add the rules list
			rulesList.addTangoWebRewriteRules(tempAppli, rules);
		}		
		return rulesList;
	}
	
	private static List<String> getRemoveAttributesList(Element removeAttributes)
	{
		ArrayList<String> list = new ArrayList<String>(); 
		
		List<Element> beans = removeAttributes.getChildren();
		Element bean = null;
		// for each bean we get the content
		for(int i=0; i < beans.size();i++)
		{
			bean = beans.get(i);
			list.add(bean.getAttributeValue("name"));
		}
		return list;
	}
	
	private static List<TangoWebBeanAttribute> getAttributesList(Element attributes)
	{
		ArrayList<TangoWebBeanAttribute> list = new ArrayList<TangoWebBeanAttribute>(); 
		
		List<Element> beans = attributes.getChildren();
		Element bean = null;
		TangoWebBeanAttribute tempBeanAttr = null;
		// for each bean we get the content
		for(int i=0; i < beans.size();i++)
		{
			bean = beans.get(i);
			tempBeanAttr = new TangoWebBeanAttribute();	
			tempBeanAttr.setName(bean.getAttributeValue("name"));
			tempBeanAttr.setBeanId(bean.getAttributeValue("id"));
			list.add(tempBeanAttr);
		}
		return list;
	}	
}
