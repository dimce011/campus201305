package org.infobip.mpayments.help.repo.rest;

import javax.ejb.Stateless;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.infobip.mpayments.help.repo.rest.vo.TestResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class RestHelpRepoService implements RestHelpRepo {
	
	static final Logger logger = LoggerFactory.getLogger(RestHelpRepoService.class);
	
	@Override
	public Response test(HttpServletRequest request) {

		
		TestResponseVO responseVO = new TestResponseVO();
		
		if (logger.isDebugEnabled()) {
			logger.debug("Test Called.");
		}
	
		Session sess = null;
		Repository repository = null;
		boolean error = false;
		try {
			InitialContext initialContext = new InitialContext();
			repository = (Repository)initialContext.lookup("java:jcr/local");
			sess = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
		    
		    // Do something interesting with the Session ...
		    logger.info("name: {}", sess.getRootNode().getName());
		    logger.info("type name: {}", sess.getRootNode().getPrimaryNodeType().getName());
		    logger.info("path: {}", sess.getRootNode().getPath());
		    
		    Node node = sess.getNode("/");
		    logger.info("node: {}", node==null?null:node);
		    
		    node.setProperty("name", "root");
		    node.addNode("help", "nt:folder");
		    
		    node = sess.getNode("/help");
		    logger.info("node: {}", node==null?null:node);
		    
		    node = sess.getNode("/");
		    
		    logger.info("name: {}", node==null?null:node.getName());
		    logger.info("node: {}", node==null?null:node);
		    
		} catch (Exception ex) {
			error = true;
			responseVO.setErrorMessage("Internal service error!");
		    ex.printStackTrace();
		} finally {
		    if (sess != null) sess.logout();
		}

		if (!error) {
			return Response.status(Response.Status.OK).entity(responseVO).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseVO).build();
		}
	}

}
