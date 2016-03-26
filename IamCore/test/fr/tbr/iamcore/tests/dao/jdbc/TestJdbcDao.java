package fr.tbr.iamcore.tests.dao.jdbc;

import java.util.Date;
import java.util.List;

import fr.tbr.iamcore.dao.IdentityJdbcDAO;
import fr.tbr.iamcore.dao.exceptions.DaoUpdateException;
import fr.tbr.iamcore.datamodel.Identity;

public class TestJdbcDao {
	
	
	
	public static void main(String[] args){
		testDelete();
		
	}

	private static void testReadAll() {
		IdentityJdbcDAO dao = new IdentityJdbcDAO();
		List<Identity> identities = dao.readAll();
		
		System.out.println(identities);
		
		
		dao.close();
	}

	private static void testSearch() {
		IdentityJdbcDAO dao = new IdentityJdbcDAO();
		List<Identity> identities = dao.search(new Identity(null, "thomas.broussard@gmail.com", "Thomas Broussard"));
		
		System.out.println(identities);
		identities = dao.search(new Identity(null, "blabla","Thomas Broussard"));
		
		System.out.println(identities);
		dao.close();
	}
	
	
	private static void testCreate() {
		IdentityJdbcDAO dao = new IdentityJdbcDAO();
		Identity identity = new Identity("123654789", "sample@gmail.com", "Rahul Kumar");
		identity.setBirthDate(new Date());
		dao.create(identity);
	
		
		dao.close();
	}
	private static void testUpdate() {
		IdentityJdbcDAO dao = new IdentityJdbcDAO();
		Identity identity = new Identity("123654789", "sdddd.decayeux@gmail.com", "ddddd Decayeux");
		identity.setBirthDate(new Date());
		try {
			dao.update(identity);
		} catch (DaoUpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		List<Identity> identities = dao.search(identity);
		
		System.out.println(identities);
		dao.close();
	}
	private static void testDelete() {
		IdentityJdbcDAO dao = new IdentityJdbcDAO();
		Identity identity = new Identity("123654789", "sdddd.decayeux@gmail.com", "ddddd Decayeux");
		identity.setBirthDate(new Date());
		dao.delete(identity);
	
		List<Identity> identities = dao.search(identity);
		
		System.out.println(identities);
		dao.close();
	}
}
