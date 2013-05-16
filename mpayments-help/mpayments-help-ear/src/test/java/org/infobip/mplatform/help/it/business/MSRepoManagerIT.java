package org.infobip.mplatform.help.it.business;

import javax.naming.InitialContext;

import org.infobip.mplatform.help.spi.business.MSRepoManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MSRepoManagerIT  {

	static final Logger log = LoggerFactory.getLogger(MSRepoManagerIT.class);

	private static MSRepoManager msRepoManager = null;
	private InitialContext initialContext = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	
    }

	@Before
	public void setUp() throws Exception {
/*		Properties properties = new Properties();
	    properties.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
	    properties.put("java.naming.factory.url.pkgs", "org.jboss.naming rg.jnp.interfaces");
	    properties.setProperty(Context.PROVIDER_URL, "localhost:1099");
		
	    initialContext = new InitialContext(properties);
*/
		initialContext = new InitialContext();
		try {
			msRepoManager = (MSRepoManager) initialContext.lookup(MSRepoManager.MAPPED_NAME);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new Exception("Could not lookup MSRepoManager!");
		}
	}

	@After
	public void tearDown() throws Exception {
		msRepoManager = null;
	}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    
    }

	@Test
	public void testOnce() {
		msRepoManager.test();
	}
	
}
