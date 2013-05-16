package org.infobip.mplatform.help.it.business;

import java.util.Map;

import javax.jcr.Repository;
import javax.jcr.RepositoryFactory;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.naming.InitialContext;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModeShapeRepositoryIT  {

	static final Logger log = LoggerFactory.getLogger(ModeShapeRepositoryIT.class);

	private InitialContext initialContext = null;
	
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	
    }

	@Before
	public void setUp() throws Exception {
		initialContext = new InitialContext();
	}

	@After
	public void tearDown() throws Exception {
		
	}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    
    }

	@Test
	@Ignore
	public void testOnce() {

		Session sess = null;
		Repository repository = null;

		try {
		    repository = (Repository)initialContext.lookup("jndi:jcr/local?repositoryName=repository");
		    sess = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
		 
		    // Do something interesting with the Session ...
		    log.info(sess.getRootNode().getPrimaryNodeType().getName());
		} catch (Exception ex) {
		    ex.printStackTrace();
		} finally {
		    if (sess != null) sess.logout();
		}
		
		try {
            Map<String, String> parameters = java.util.Collections.singletonMap("org.modeshape.jcr.URL", "jndi:jcr/local?repositoryName=repository");
            for (RepositoryFactory factory : java.util.ServiceLoader.load(RepositoryFactory.class)) {
                repository = factory.getRepository(parameters);
                if (repository != null) break;
            }
		    sess = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
			 
		    // Do something interesting with the Session ...
		    log.info(sess.getRootNode().getPrimaryNodeType().getName());

		} catch (Exception ex) {
		    ex.printStackTrace();
		} finally {
		    if (sess != null) sess.logout();
		}

	}
	
}
