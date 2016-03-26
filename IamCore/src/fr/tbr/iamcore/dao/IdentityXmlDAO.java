package fr.tbr.iamcore.dao;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.tbr.iamcore.config.AppConfig;
import fr.tbr.iamcore.dao.exceptions.DaoUpdateException;
import fr.tbr.iamcore.datamodel.Identity;
import fr.tbr.iamcore.services.match.Matcher;
import fr.tbr.iamcore.services.match.impl.ContainsIdentityMatcher;

/**
 *Class helps to accessing and retrieving identity information from xml file
 *
 */
public class IdentityXmlDAO implements IdentityDAOInterface {

	Document document;
	
	private Matcher<Identity> activeMatchingStrategy = new ContainsIdentityMatcher();

	public IdentityXmlDAO() {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			document = db.parse(new File(AppConfig.getApplicationConfig().getAppXmlDAOConf().get("path")));
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
	 *function reading all the entries in identities xml
	 * 
	 *@param null
	 *@return List<Identity>
	 */
	public List<Identity> readAll() {

		//This is creating an anonymous implementation of the Matcher interface and 
		//instantiating it at the same time
		return internalSearch(null, new Matcher<Identity>(){
			public boolean match(Identity criteria, Identity toBeMatched) {
				return true;
			}
		});
	}

	/**
	 *function will search all the entries from identities xml
	 * 
	 *@param Identity
	 *@return List<Identity>
	 */
	public List<Identity> search(Identity criteria) {
			return internalSearch(criteria, activeMatchingStrategy);
	}

	
	private List<Identity> internalSearch(Identity criteria, Matcher<Identity> identityMatcher){
		ArrayList<Identity> resultList = new ArrayList<Identity>();
		NodeList identitiesList = document.getElementsByTagName("identity");
		int length = identitiesList.getLength();
		for (int i = 0; i < length; i++) {
			Element identity = (Element) identitiesList.item(i);
			Identity identityInstance = readIdentityFromXmlElement(identity);
			if(identityMatcher.match(criteria, identityInstance)){
				resultList.add(identityInstance);
			}
		}

		return resultList;
		
	}

	private Identity readIdentityFromXmlElement(Element identity){
		NodeList properties = identity.getElementsByTagName("property");
		Identity identityInstance = new Identity();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		int propertiesLength = properties.getLength();
		for (int j = 0; j < propertiesLength; j++) {
			Element property = (Element) properties.item(j);
			String attribute = property.getAttribute("name");
			String value = property.getTextContent().trim();
			switch (attribute) {
			case "displayName":
				identityInstance.setDisplayName(value);
				break;
			case "email":
				identityInstance.setEmail(value);
				break;

			case "guid":
				identityInstance.setUid(value);
				break;
				
			case "birthDate":
				try {
					Date parsedDate = simpleDateFormat.parse(value);
					identityInstance.setBirthDate(parsedDate);
				} catch (ParseException e) {
					// TODO Check if the birthDate should provoke the cancellation of the current identity reading
					e.printStackTrace();
				}
				break;
			}
		}
		return identityInstance;
	}
	
	/**
	 *function will create entries for identities in xml
	 * 
	 *@param Identity
	 *@return void
	 */
	@Override
	public void create(Identity identity) {
		// TODO Auto-generated method stub
		 Element rootIden =(Element)document.createElement("identity"); 
		 document.getDocumentElement().appendChild(rootIden);
		 Element propertyElm = document.createElement("property");
		 Attr Attrbute = document.createAttribute("name");
		 Attrbute.setValue("displayName");
		 propertyElm.setAttributeNode(Attrbute);
		 propertyElm.setTextContent(identity.getDisplayName());
		 rootIden.appendChild(propertyElm);
		 
		 propertyElm = document.createElement("property");
		 Attrbute = document.createAttribute("name");
		 Attrbute.setValue("email");
		 propertyElm.setAttributeNode(Attrbute);		 
		 propertyElm.setTextContent(identity.getEmail());
		 rootIden.appendChild(propertyElm);
		 
		 propertyElm = document.createElement("property");
		 Attrbute = document.createAttribute("name");
		 Attrbute.setValue("guid");
		 propertyElm.setAttributeNode(Attrbute);		 
		 propertyElm.setTextContent(identity.getUid());
		 rootIden.appendChild(propertyElm);
		 
		 propertyElm = document.createElement("property");
		 Attrbute = document.createAttribute("name");
		 Attrbute.setValue("birthDate");
		 propertyElm.setAttributeNode(Attrbute);
		 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		 propertyElm.setTextContent(df.format(identity.getBirthDate()));
		 rootIden.appendChild(propertyElm);
		 WritetoXml();
	}

	
	/**
	 * function will update entries for identities in xml
	 * 
	 * @param Identity
	 * @return void
	 */
	@Override
	public void update(Identity identity) throws DaoUpdateException {
		
		Node ElemNode = getMatchingNode(identity);
		if( ElemNode!= null)
		{			
			Element identNode = (Element) ElemNode;
			NodeList properties = identNode.getElementsByTagName("property");
			int propertiesLength = properties.getLength();
			for (int j = 0; j < propertiesLength; j++) {
				Element property = (Element) properties.item(j);
				String attribute = property.getAttribute("name");
				switch (attribute) {
				case "displayName":
					property.setTextContent(identity.getDisplayName());
					break;
				case "email":
					property.setTextContent(identity.getEmail());
					break;
					
				case "birthDate":
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					property.setTextContent(df.format(identity.getBirthDate()));
					
					break;
				}
			}
			WritetoXml();
		}
		

	}
	
	private void WritetoXml()
	{
		TransformerFactory tFactory =  TransformerFactory.newInstance();
		Transformer transformer=null;
		try {
			transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(AppConfig.getApplicationConfig().getAppXmlDAOConf().get("path"));
		try {
			transformer.transform(source, result);
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
	}
	
	private Node getMatchingNode(Identity identity){
		NodeList identitiesList = document.getElementsByTagName("identity");
		int length = identitiesList.getLength();
		boolean bIdenFound = false;
		Node ElemNode = null;
		for (int i = 0; i < length; i++) {
			ElemNode = identitiesList.item(i);
			Element idenItem = (Element)ElemNode;
			NodeList properties = idenItem.getElementsByTagName("property");
			int propertiesLength = properties.getLength();
			for (int j = 0; j < propertiesLength; j++) {
				Element property = (Element) properties.item(j);
				String attribute = property.getAttribute("name");
				String value = property.getTextContent().trim();
				
				if( attribute.equals("guid") && value.equals(identity.getUid()) )
				{
					bIdenFound = true;
					break;
				}
			}
			
			if( bIdenFound)
			{
				break;
			}
			
		}
		return ElemNode;
	}

	/**
	 *function will delete entries for identities in xml
	 * 
	 *@param Identity
	 *@return void
	 */
	@Override
	public void delete(Identity identity) {
		// TODO Auto-generated method stub
		Node ElemNode = getMatchingNode(identity);
		if( ElemNode!= null)
		{
			Node rootElem = document.getDocumentElement();
			rootElem.removeChild(ElemNode);
			WritetoXml();
		}
	}

	/**
	 *function will close for resuorces for xamldao
	 * 
	 *@param null
	 *@return void
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
