package org.infobip.mpayments.help.repo.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class RestAPIService implements RestAPI {

	static final Logger logger = LoggerFactory.getLogger(RestHelpRepoService.class);

	@Override
	public Response getParagraph(@PathParam("app") String app, @PathParam("topic") String topic,
			@PathParam("parID") String parID, @QueryParam("reseller") String reseller,
			@QueryParam("language") String language) {
		Session session = null;
		Repository repository = null;
		boolean error = false;
		OutputStream output = null;
		InputStream input = null;
		String noSuchID = "No such ID!";
		String toProcess = null;
		StringBuffer result = new StringBuffer();
		try {
			InitialContext initialContext = new InitialContext();
			repository = (Repository) initialContext.lookup("java:jcr/local");
			session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));

			logger.info("name: {}",
					session.getNode("/help/" + app + "/" + topic + "/" + reseller + "/" + language + ""));
			Node target = session.getNode("/help/" + app + "/" + topic + "/" + reseller + "/" + language + "");

			Node content = target.getNode("jcr:content");
			input = content.getProperty("jcr:data").getBinary().getStream();
			toProcess = RestAPIService.getStringFromInputStream(input).toString();

		} catch (Exception ex) {
			error = true;
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.logout();
			closeStreams(input, output);
		}
		if (!error) {
			Document doc = Jsoup.parse(toProcess);
			Elements divs = doc.getElementsByTag("div");
			for (Element elem : divs) {
				if (elem.id().equals(parID)) {
					// if (elem.className().equals(parID)) {
					result.append(elem.toString());
				}
			}
			if ("".equals(result.toString())) {
				return Response.status(Response.Status.OK).entity(noSuchID).build();
			} else {
				return Response.status(Response.Status.OK).entity(result.toString()).build();
			}
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("error").build();
		}
	}

	// @Override
	// public Response getPage(@QueryParam("app") String app,
	// @QueryParam("topic") String topic,
	// @QueryParam("reseller") String reseller, @QueryParam("language") String
	// language) {
	// Session session = null;
	// Repository repository = null;
	// boolean error = false;
	// File file = null;
	// InputStream input = null;
	// OutputStream output = null;
	// try {
	// InitialContext initialContext = new InitialContext();
	// repository = (Repository) initialContext.lookup("java:jcr/local");
	// session = repository.login(new SimpleCredentials("admin",
	// "admin".toCharArray()));
	//
	// System.out.println("ovde");
	//
	// Node target = session.getNode("/help/" + app + "/" + topic + "/" +
	// reseller + "/" + language + "");
	//
	// Node content = target.getNode("jcr:content");
	// input = content.getProperty("jcr:data").getBinary().getStream();
	//
	// file = new File("template.html");
	// output = new FileOutputStream(file);
	//
	// byte[] buffer = new byte[input.available()];
	// while (input.read(buffer) != -1) {
	// output.write(buffer);
	// buffer = new byte[input.available() + 1];
	// }
	// output.write('\n');
	// output.flush();
	// } catch (Exception ex) {
	// error = true;
	// ex.printStackTrace();
	// } finally {
	// if (session != null)
	// session.logout();
	// closeStreams(input, output);
	// }
	//
	// if (!error) {
	// try {
	// return
	// Response.status(Response.Status.OK).entity(readFile(file)).build();
	// } catch (IOException e) {
	// return
	// Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("error").build();
	// }
	// } else {
	// return
	// Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("error").build();
	// }
	// }

	@Override
	public Response getPageById(@PathParam("id") String id) {
		Session session = null;
		Repository repository = null;
		boolean error = false;
		InputStream input = null;
		OutputStream output = null;
		String result = null;
		try {
			InitialContext initialContext = new InitialContext();
			repository = (Repository) initialContext.lookup("java:jcr/local");
			session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));

			Node en = session.getNode("/help/pp/service/1/en");
			Node target = session.getNodeByIdentifier(en.getIdentifier());

			printChildren(session.getNode("/"));
			Node content = target.getNode("jcr:content");
			input = content.getProperty("jcr:data").getBinary().getStream();
			result = RestAPIService.getStringFromInputStream(input).toString();
			
		} catch (Exception ex) {
			error = true;
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.logout();
			closeStreams(input, output);
		}

		if (!error) {
			return Response.status(Response.Status.OK).entity(result).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("error").build();
		}
	}

	@Override
	public Response getDoc(@PathParam("rPath") String rPath) {
		Session session = null;
		Repository repository = null;
		boolean error = false;
		InputStream input = null;
		OutputStream output = null;
		String result = null;
		try {
			InitialContext initialContext = new InitialContext();
			repository = (Repository) initialContext.lookup("java:jcr/local");
			session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));

			System.out.println("rpath " + rPath);
			Node target = session.getNode("/" + rPath);
			input = target.getProperty("jcr:data").getBinary().getStream();
			result = RestAPIService.getStringFromInputStream(input).toString();

		} catch (Exception ex) {
			error = true;
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.logout();
			closeStreams(input, output);
		}

		if (!error) {
			return Response.status(Response.Status.OK).entity(result).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("error").build();
		}
	}
	
	private static StringBuilder getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb;
	}

	private void closeStreams(InputStream input, OutputStream output) {
		if (input != null)
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (output != null)
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	private String readFile(File file) throws IOException {
		StringBuilder fileContents = new StringBuilder((int) file.length());
		Scanner scanner = new Scanner(file);
		String lineSeparator = System.getProperty("line.separator");
		try {
			while (scanner.hasNextLine()) {
				fileContents.append(scanner.nextLine() + lineSeparator);
			}
			return fileContents.toString();
		} finally {
			scanner.close();
		}
	}

	private void printChildren(Node node) throws RepositoryException {
		if (node.hasNodes()) {
			NodeIterator it = node.getNodes();
			while (it.hasNext()) {
				Node child = it.nextNode();
				printChildren(child);
			}
		}
	}

}
