package fr.tbr.iamcore.tests.xml;

import java.text.SimpleDateFormat;

import fr.tbr.iamcore.dao.IdentityXmlDAO;
import fr.tbr.iamcore.datamodel.Identity;

public class TestXml {
	public static void main(String[] args) throws Exception{
		IdentityXmlDAO xmldao = new IdentityXmlDAO();
		Identity iden = new Identity();
		iden.setDisplayName("Rahul");
		iden.setEmail("ssdddd@gmail.com");
		iden.setUid("98746541");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		iden.setBirthDate(simpleDateFormat.parse("28/02/1987"));
		//xmldao.delete(iden);
		xmldao.create(iden);
		
	}
}
