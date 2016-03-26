package fr.tbr.iamcore.tests.config;

import java.util.Map;

import fr.tbr.iamcore.config.AppConfig;

public class TestConfiguration {
	public static void main(String[] args) throws Exception{
		int n =10;
		Map<String, String> list = AppConfig.getApplicationConfig().getAppDbDAOConf();
		Map<String, String> list2 = AppConfig.getApplicationConfig().getAppXmlDAOConf();
		for (Map.Entry<String,String> entry : list.entrySet()) {
		    
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
			
		}
		
		for (Map.Entry<String,String> entry : list2.entrySet()) {
		    
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
			
		}
			
		}

}
