package fr.tbr.iamcore.Luncher;



import java.util.List;

import fr.tbr.iamcore.config.AppConfig;
import fr.tbr.iamcore.dao.IdentityDAOInterface;
import fr.tbr.iamcore.dao.IdentityJdbcDAO;
import fr.tbr.iamcore.dao.IdentityXmlDAO;
import fr.tbr.iamcore.dao.exceptions.DaoUpdateException;
import fr.tbr.iamcore.datamodel.Identity;
import fr.tbr.iamcore.utils.Utils;

/**
 * class handling the Identity management request 
 *
 */
public class IamManager {
	
	private static IamManager iamManagerInst = null;
	int daoType= 2;//this take dafalut as databse
	IdentityDAOInterface identDAO;
	private IamManager(){
		 
		//iamManagerInst = new IamManager();
		daoType = AppConfig.getApplicationConfig().getAppDaoType();
		if( daoType == 1 )
		{
			identDAO = new IdentityXmlDAO();
		}
		else
		{
			identDAO = new IdentityJdbcDAO();
		}
	 }
	
	/**
	 *
	 * @param 
	 * @return IamManager
	 */
	public static IamManager getInstance() {
        if ( iamManagerInst == null ) {
        	iamManagerInst = new IamManager();
        }

        return iamManagerInst;
    }
	
	/**
	 *
	 * @param Identity
	 * @return null
	 */
	public void CreateUser( Identity iden)
	{
		iden.setUid(Utils.generateGUID());
		identDAO.create(iden);
	}
	
	/**
	 *
	 * @param Identity
	 * @return  List<Identity>
	 */
	public List<Identity> SearchUser( Identity iden)
	{
		return identDAO.search(iden);
	}
	
	/**
	 *
	 * @param Identity
	 * @return  null
	 */
	public void Update( Identity iden)
	{
		try {
			identDAO.update(iden);
		} catch (DaoUpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *
	 * @param Identity
	 * @return  null
	 */
	public void Delete( Identity iden)
	{
		identDAO.delete(iden);
	}
}
