package org.infobip.mplatform.help.ejb.business;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.naming.InitialContext;

import org.infobip.mplatform.help.spi.business.MSRepoManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(mappedName = MSRepoManager.MAPPED_NAME)
@Remote(MSRepoManager.class)
public class MSRepoManagerBean implements MSRepoManager {

	final static Logger log = LoggerFactory.getLogger(MSRepoManagerBean.class);

	@Override
	public void test() {
		log.info("MSRepoManagerBean.test called!!!");
		
		Session sess = null;
		Repository repository = null;
/*		try {
		    InitialContext initialContext = new InitialContext();
		    String jndiName = "jndi:jcr/local?repositoryName=repository";
		    repository = (Repository)initialContext.lookup(jndiName);
		    sess = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
		 
		    // Do something interesting with the Session ...
		    log.info(sess.getRootNode().getPrimaryNodeType().getName());
		} catch (Exception ex) {
		    ex.printStackTrace();
		} finally {
		    if (sess != null) sess.logout();
		}
*/		
		try {
			InitialContext initialContext = new InitialContext();
			repository = (Repository)initialContext.lookup("java:jcr/local");
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
