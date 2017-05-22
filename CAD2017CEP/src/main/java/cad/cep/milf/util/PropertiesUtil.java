package cad.cep.milf.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {

	public static Properties loadProperty(String path) throws IOException{
		Properties prop = new Properties();
		InputStream stream =PropertiesUtil.class.getClassLoader().getResourceAsStream(path);
		prop.load(stream);
		return prop;
	}
}
