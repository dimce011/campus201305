package org.infobip.mpayments.help.repo.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import javax.ejb.Stateless;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.naming.InitialContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class RestAPIService implements RestAPI {
	static final Logger logger = LoggerFactory.getLogger(RestHelpRepoService.class);
	@Override
	public String getPage(@QueryParam("app") String app, @QueryParam("topic") String topic,
			@QueryParam("reseller") String reseller, @QueryParam("language") String language) {
		// TODO Auto-generated method stub

		if (logger.isDebugEnabled()) {
			logger.debug("Test Called.");
		}

		Session session = null;
		Repository repository = null;
		boolean error = false;
		File f = null;
		try {
			InitialContext initialContext = new InitialContext();
			repository = (Repository) initialContext.lookup("java:jcr/local");
			session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));

			System.out.println("ovde");
			
			
			//Node en = session.getNode("/help[3]/pp/service/1/en");
			Node target = session.getNode("/help[3]/"+app+"/"+topic+"/"+reseller+"/"+language+"");
			
			logger.info("name: {}", session.getNode("/help[3]/pp/service/1/en"));
			ispisiSvuDecu(session.getNode("/"));
			//Node content = en.addNode("jcr:content", "nt:resource");
			Node content = target.getNode("jcr:content");
			InputStream input = content.getProperty("jcr:data").getBinary().getStream();

			f = new File("template.html");
			OutputStream output = new FileOutputStream(f);

			byte[] buffer = new byte[input.available()];
			while (input.read(buffer) != -1) {
				output.write(buffer);
				buffer = new byte[input.available() + 1];

			}
			output.write('\n');
			output.flush();
			output.close();
			input.close();

		} catch (Exception ex) {
			error = true;

			ex.printStackTrace();
		} finally {
			if (session != null)
				session.logout();
		}

		if (!error) {
			try {
				return readFile(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return "error";
		}
		return "error";
	}
	
	private String readFile(File file) throws IOException {

		   // File file = new File(pathname);
		    StringBuilder fileContents = new StringBuilder((int)file.length());
		    Scanner scanner = new Scanner(file);
		    String lineSeparator = System.getProperty("line.separator");

		    try {
		        while(scanner.hasNextLine()) {        
		            fileContents.append(scanner.nextLine() + lineSeparator);
		        }
		        return fileContents.toString();
		    } finally {
		        scanner.close();
		    }
	}

	private void ispisiSvuDecu(Node node) throws RepositoryException {
		if (node.hasNodes()) {
			NodeIterator it = node.getNodes();
			while (it.hasNext()) {
				Node dete = it.nextNode();
				logger.info(dete.getPath());
				// System.out.println(dete.getPath());
				ispisiSvuDecu(dete);
			}
		}
	}

	@Override
	public String getPageById(@PathParam("id") String id){
		// TODO Auto-generated method stub

		if (logger.isDebugEnabled()) {
			logger.debug("Test Called.");
		}

		Session session = null;
		Repository repository = null;
		boolean error = false;
		File f = null;
		try {
			InitialContext initialContext = new InitialContext();
			repository = (Repository) initialContext.lookup("java:jcr/local");
			session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));

			System.out.println("ovde");
			
			
			Node en = session.getNode("/help[3]/pp/service/1/en");
		    //Node target = session.getNode("/help[3]/"+app+"/"+topic+"/"+reseller+"/"+language+"");
			Node target = session.getNodeByIdentifier(en.getIdentifier());
			
			logger.info("name: {}", session.getNode("/help[3]/pp/service/1/en"));
			ispisiSvuDecu(session.getNode("/"));
			//Node content = en.addNode("jcr:content", "nt:resource");
			Node content = target.getNode("jcr:content");
			InputStream input = content.getProperty("jcr:data").getBinary().getStream();

			f = new File("template.html");
			OutputStream output = new FileOutputStream(f);

			byte[] buffer = new byte[input.available()];
			while (input.read(buffer) != -1) {
				output.write(buffer);
				buffer = new byte[input.available() + 1];

			}
			output.write('\n');
			output.flush();
			output.close();
			input.close();

		} catch (Exception ex) {
			error = true;

			ex.printStackTrace();
		} finally {
			if (session != null)
				session.logout();
		}

		if (!error) {
			try {
				return readFile(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return "error";
		}
		return "error";
	}


}
