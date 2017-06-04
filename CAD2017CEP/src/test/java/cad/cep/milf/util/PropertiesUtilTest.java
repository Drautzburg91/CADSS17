package cad.cep.milf.util;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

public class PropertiesUtilTest {

	@Test
	public void test() throws IOException {
		Properties prop = PropertiesUtil.loadProperty("db.properties");
		assertTrue(prop != null);

	}

}
