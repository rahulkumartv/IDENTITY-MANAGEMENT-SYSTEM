package fr.tbr.iamcore.config.datamodel;

import java.util.HashMap;
import java.util.Map;

/**
 *Class for stroring the configuartion information
 * 
 */
public class configurations {

	private int daoType;
	private Map<String, String> xmlDAOConfMAp = new HashMap<String, String>();
	private Map<String, String> dbDAOConfMAp = new HashMap<String, String>();
	public configurations()
	{		
	}
	
	/**
	 *return Map for DAO type from configuration file
	 * 
	 *@param null
	 *@return int
	 */
	public int getDaoType() {
		return daoType;
	}
	
	/**
	 *Setting DAO type
	 * 
	 *@param int
	 *@return void
	 */
	public void setDaoType(int daoType) {
		this.daoType = daoType;
	}
	
	/**
	 *return Map for xml configuration
	 * 
	 *@param null
	 *@return Map<String, String>
	 */
	public Map<String, String> getXmlDAOConfMAp() {
		return xmlDAOConfMAp;
	}
	
	/**
	 *setting xml DAO configuration
	 * 
	 *@param Map<String, String>
	 *@return void
	 */
	public void setXmlDAOConfMAp(Map<String, String> xmlDAOConfMAp) {
		this.xmlDAOConfMAp = xmlDAOConfMAp;
	}
	
	/**
	 *return Map for DB configuration
	 * 
	 *@param null
	 *@return Map<String, String>
	 */
	public Map<String, String> getDbDAOConfMAp() {
		return dbDAOConfMAp;
	}
	
	/**
	 *setting DB DAO configuration
	 * 
	 *@param Map<String, String>
	 *@return void
	 */
	public void setDbDAOConfMAp(Map<String, String> dbDAOConfMAp) {
		this.dbDAOConfMAp = dbDAOConfMAp;
	}
}
