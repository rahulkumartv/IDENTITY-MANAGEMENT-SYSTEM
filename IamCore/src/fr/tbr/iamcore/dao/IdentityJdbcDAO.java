/**
 * 
 */
package fr.tbr.iamcore.dao;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.tbr.iamcore.config.AppConfig;
import fr.tbr.iamcore.dao.exceptions.DaoUpdateException;
import fr.tbr.iamcore.datamodel.Identity;

/**
 *Class helps to connect derby database and do SQL operation for accessing and retrieving identity information 
 *
 */
public class IdentityJdbcDAO implements IdentityDAOInterface {

	private Connection connection;

	public IdentityJdbcDAO() {
		try {
			this.connection = getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *function helps to create Identity to database
	 * 
	 *@param Identity
	 *@return void
	 */
	@Override
	public void create(Identity identity) {
		try {
			if( !isTableExist("IDENTITIES"))
			{
				String sqlCreate = "CREATE TABLE IDENTITIES"+
									"(ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"+
									"DISPLAY_NAME VARCHAR(255),"+
									"EMAIL_ADDRESS VARCHAR(255),"+
									"BIRTHDATE DATE,"+
									"UID VARCHAR(25))";
				Statement stmt;
				try {
					stmt = connection.createStatement();
					stmt.executeUpdate(sqlCreate);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String query = "insert into IDENTITIES (\"DISPLAY_NAME\", \"EMAIL_ADDRESS\", \"BIRTHDATE\", \"UID\") values(?, ?, ?, ?)";

		try {
			PreparedStatement stmt = this.connection.prepareStatement(query);
			stmt.setString(1, identity.getDisplayName());
			stmt.setString(2, identity.getEmail());
			stmt.setDate(3, new java.sql.Date(identity.getBirthDate().getTime()));
			stmt.setString(4, identity.getUid());
			stmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *function reading all the entries in database identity table
	 * 
	 *@param null
	 *@return List<Identity>
	 */
	@Override
	public List<Identity> readAll() {
		List<Identity> identities = new ArrayList<Identity>();

		try {

			PreparedStatement prepareStatement = this.connection.prepareStatement("select * from IDENTITIES");
			ResultSet rs = prepareStatement.executeQuery();
			while (rs.next()) {
				String displayName = rs.getString("DISPLAY_NAME");
				String email = rs.getString("EMAIL_ADDRESS");
				Date date = rs.getDate("BIRTHDATE");
				String uid = rs.getString("UID");

				Identity identity = new Identity(uid, email, displayName);
				identity.setBirthDate(date);
				identities.add(identity);
			}

		} catch (Exception e) {
			System.out.println(e);

		}
		return identities;

	}

	/**
	 *function handles the connection to databse driver
	 * 
	 *@param null
	 *@return Connection
	 */
	private static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(AppConfig.getApplicationConfig().getAppDbDAOConf().get("classpath"));
		
		String username =AppConfig.getApplicationConfig().getAppDbDAOConf().get("username");
		String password =AppConfig.getApplicationConfig().getAppDbDAOConf().get("password");
		String driver  = AppConfig.getApplicationConfig().getAppDbDAOConf().get("driver");
		String host  = AppConfig.getApplicationConfig().getAppDbDAOConf().get("host");
		String port  = AppConfig.getApplicationConfig().getAppDbDAOConf().get("port");
		String folder  = AppConfig.getApplicationConfig().getAppDbDAOConf().get("folder");
		String name  = AppConfig.getApplicationConfig().getAppDbDAOConf().get("name");
		//Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/IAMDataBase;create=true", username, password);
		String connStr = driver + "://" +host+":"+ port + "/" + folder + "/" + name +";create=true";
		Connection connection = null ;
		//Connection connection = null;
		try
		{
			connection = DriverManager.getConnection( connStr, username, password);
		}
		catch( Exception e)
		{
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 *function handles the release of database connection
	 * 
	 *@param null
	 *@return null
	 */
	public void close() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *facilitate the identity search in identity table
	 * 
	 *@param Identity
	 *@return List<Identity>
	 */
	@Override
	public List<Identity> search(Identity criteria) {
		List<Identity> identities = new ArrayList<Identity>();

		try {

			PreparedStatement prepareStatement = this.connection.prepareStatement("select * from IDENTITIES where IDENTITIES.EMAIL_ADDRESS = ? and	IDENTITIES.DISPLAY_NAME = ?");
			prepareStatement.setString(1, criteria.getEmail());
			prepareStatement.setString(2, criteria.getDisplayName());
			ResultSet rs = prepareStatement.executeQuery();
			while (rs.next()) {
				String displayName = rs.getString("DISPLAY_NAME");
				String email = rs.getString("EMAIL_ADDRESS");
				Date date = rs.getDate("BIRTHDATE");
				String uid = rs.getString("UID");

				Identity identity = new Identity(uid, email, displayName);
				identity.setBirthDate(date);
				identities.add(identity);
			}

		} catch (Exception e) {
			System.out.println(e);

		}
		return identities;
	}

	/**
	 *facilitate the identity updation to identity table
	 * 
	 *@param Identity
	 *@return void
	 */
	@Override
	public void update(Identity identity) throws DaoUpdateException {
		// TODO Auto-generated method stub
		try {
			PreparedStatement prepareStatement = this.connection.prepareStatement("select * from IDENTITIES where IDENTITIES.UID = ?",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			prepareStatement.setString(1, identity.getUid());
			ResultSet rs = prepareStatement.executeQuery();
			if (rs.next()) { // or maybe while rs.next()
			    rs.updateString("DISPLAY_NAME", identity.getDisplayName());
			    rs.updateString("EMAIL_ADDRESS", identity.getEmail());
			    rs.updateDate("BIRTHDATE", new java.sql.Date(identity.getBirthDate().getTime()));
			    rs.updateRow();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *facilitate the identity entry deletion from identity table
	 * 
	 *@param Identity
	 *@return void
	 */
	@Override
	public void delete(Identity identity) {
		try {
			PreparedStatement prepareStatement = this.connection.prepareStatement("select * from IDENTITIES where IDENTITIES.UID = ?",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);			
			prepareStatement.setString(1, identity.getUid());
			ResultSet rs = prepareStatement.executeQuery();
			if (rs.next()) { // or maybe while rs.next()
			   
			    rs.deleteRow();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	
	private boolean isTableExist(String tablename) throws SQLException {
		// TODO Auto-generated method stub
		boolean bRet = false;
		DatabaseMetaData dbmd = connection.getMetaData();
		ResultSet rs = dbmd.getTables(null, null, tablename.toUpperCase(),null);
		if(rs.next())
		{
			bRet = true;
		}		
		return bRet;
	}
}
