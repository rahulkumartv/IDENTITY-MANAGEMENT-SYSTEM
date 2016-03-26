package fr.tbr.iamcore.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.bcel.internal.generic.CPInstruction;

import fr.tbr.iamcore.config.datamodel.configurations;

/**
 *Appconfig is a single ton class which will handling the application configuration
 *from c://iamcore//configApp.xml path
 */
public class AppConfig {

	 private static AppConfig appConfgInst = null;
	 private configurations configDetails;
	private AppConfig() {
		// TODO Auto-generated constructor stub
		configDetails = new configurations();
		InitializeConfiguration();
	}
	
	/**
	 * this function handling the initialization of AppConfig by reading configuration XML file
	 * 
	 *@param null
	 *@return void
	 */
	private void InitializeConfiguration() {
		// TODO Auto-generated method stub
		Document document=null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			document = db.parse(new File(Settings.CONFG_PATH));
			
			Element elemConfig = document.getDocumentElement();
			NodeList daotypeList = elemConfig.getElementsByTagName("daotype");
			Element daotype = (Element) daotypeList.item(0);
			configDetails.setDaoType(Integer.parseInt(daotype.getTextContent()));
			
			Map<String, String> xmlDAOConfMAp = new HashMap<String, String>();
			Map<String, String> dbDAOConfMAp = new HashMap<String, String>();
			Node daoXml = elemConfig.getElementsByTagName("xmldao").item(0);
			NodeList daoxmlList = daoXml.getChildNodes();
			
			for (int i = 0; i < daoxmlList.getLength(); i++)
			{
				Node currNode = daoxmlList.item(i);
				if(currNode.getNodeType() == Node.ELEMENT_NODE )
				{
					Element elem = (Element) currNode;
					xmlDAOConfMAp.put(elem.getTagName(),elem.getTextContent());
					configDetails.setXmlDAOConfMAp(xmlDAOConfMAp);
				}
				
			}
			
			Node daoDb = elemConfig.getElementsByTagName("dbdao").item(0);
			NodeList daoDbList = daoDb.getChildNodes();
			for (int i = 0; i < daoDbList.getLength(); i++)
			{
				Node currNode = daoDbList.item(i);
				if(currNode.getNodeType() == Node.ELEMENT_NODE )
				{
					Element elem = (Element) currNode;
					dbDAOConfMAp.put(elem.getTagName(),elem.getTextContent());
					configDetails.setDbDAOConfMAp(dbDAOConfMAp);
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO handle exception
		}finally{
			if (document != null){
				document.getDocumentElement();
			}
		}
	}

	/**
	 * return single ton instance of AppConfig class
	 * 
	 *@param null
	 *@return AppConfig
	 */
	public static AppConfig getApplicationConfig()
	{
		if ( appConfgInst == null ) {
			appConfgInst = new AppConfig();
        }

        return appConfgInst;
	}
	
	/**
	 * return Map for database configuration
	 * 
	 *@param null
	 *@return Map<String, String>
	 */
	public Map<String, String> getAppDbDAOConf() {
		return configDetails.getDbDAOConfMAp();
	}
	
	/**
	 * return Map for xml configuration
	 * 
	 *@param null
	 *@return Map<String, String>
	 */
	public Map<String, String> getAppXmlDAOConf() {
		return configDetails.getXmlDAOConfMAp();
	}
	
	/**
	 * return DAO type from configuration file
	 * 
	 *@param null
	 *@return int 
	 */
	public int getAppDaoType() {
		return configDetails.getDaoType();
	}
}
