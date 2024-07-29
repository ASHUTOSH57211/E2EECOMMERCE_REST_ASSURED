package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ProjectConfigurations {
	
	public static String LoadProperties(String key) throws IOException {
		
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//configurations//config.properties");
		Properties prop = new Properties();
		String value = null;
		prop.load(fis);
		value= prop.getProperty(key);
		
		
		
		
		
		return value;
		
	}

}
