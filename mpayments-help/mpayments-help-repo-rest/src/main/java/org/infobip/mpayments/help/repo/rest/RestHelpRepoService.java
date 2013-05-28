package org.infobip.mpayments.help.repo.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.jcr.AccessDeniedException;
import javax.jcr.Binary;
import javax.jcr.InvalidItemStateException;
import javax.jcr.ItemExistsException;
import javax.jcr.LoginException;
import javax.jcr.NamespaceRegistry;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.NodeTypeManager;
import javax.jcr.version.VersionException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.infobip.mpayments.help.dto.DocumentCvor;
import org.infobip.mpayments.help.dto.DocumentCvorWrapper;
import org.infobip.mpayments.help.dto.DocumentNode;
import org.infobip.mpayments.help.freemarker.FreeMarker;
import org.infobip.mpayments.help.repo.rest.vo.TestResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class RestHelpRepoService implements RestHelpRepo {

	static final Logger logger = LoggerFactory.getLogger(RestHelpRepoService.class);
	private static ObjectMapper jsonMapper = new ObjectMapper();

	Session session = null;
	Repository repository = null;
	InitialContext initialContext;

	@Override
	/**
	 * @param action (String "delete" - deletes help tree from repository,
	 * 				  String "create" - creates help tree with template file in repository,
	 * 				  otherwise prints info about repository)
	 * 
	 */
	public Response test(HttpServletRequest request, String action) {

		TestResponseVO responseVO = new TestResponseVO();

		if (logger.isDebugEnabled()) {
			logger.debug("Test Called.");
		}
		boolean error = false;
		try {
			openSession();
			
			if ("delete".equals(action)) {
				deleteHelpTree("/help");
			} else if ("create".equals(action)) {
				createHelpTree();
			}
			
			ispisiSvuDecu(session.getNode("/"));

			session.save();

			// pravljenje namespace-a
			// NamespaceRegistry registry =
			// session.getWorkspace().getNamespaceRegistry();
			//
			// List<String> list = Arrays.asList(registry.getPrefixes());
			// if (!list.contains("my")) {
			// registry.registerNamespace("my", "com.infobip.jcr.my");
			// }
			//
			// NodeTypeManager manager =
			// session.getWorkspace().getNodeTypeManager();

			// pravljenje novog tipa cvora sa novim propertijima

			// NamespaceRegistry nsReg =
			// (NamespaceRegistry)session.getWorkspace().getNamespaceRegistry();
			// nsReg.registerNamespace("my","com.infobip.jcr.my");
			//
			// NodeTypeTemplate nodeType = manager.createNodeTypeTemplate();
			// nodeType.setMixin(true);
			// nodeType.setName("my:metaPageData");
			// nodeType.setQueryable(true);
			//
			// nodeType.setDeclaredSuperTypeNames(new String[]{"mix:title"});
			// System.out.println("ovde1");
			//
			// PropertyDefinitionTemplate propertyDef =
			// manager.createPropertyDefinitionTemplate();
			// propertyDef.setName("my:lang");
			// propertyDef.setMultiple(false);
			// propertyDef.setRequiredType(PropertyType.STRING);
			// propertyDef.setOnParentVersion(OnParentVersionAction.COPY);
			// propertyDef.setProtected(false);
			// System.out.println("ovde2");
			//
			// PropertyDefinitionTemplate propertyDef2 =
			// manager.createPropertyDefinitionTemplate();
			// propertyDef2.setName("my:reseller");
			// propertyDef2.setMultiple(false);
			// propertyDef2.setRequiredType(PropertyType.STRING);
			// propertyDef2.setOnParentVersion(OnParentVersionAction.COPY);
			// propertyDef2.setProtected(false);
			// System.out.println("ovde2");
			//
			// nodeType.getPropertyDefinitionTemplates().add(propertyDef);
			// nodeType.getPropertyDefinitionTemplates().add(propertyDef2);
			// System.out.println("ovde3");
			// manager.registerNodeType(nodeType, true);
			//
			// ispisiSvuDecu(session.getNode("/"));

			// upit za trazenje cvora
			// Workspace ws = session.getWorkspace();
			// QueryManager qm = ws.getQueryManager();
			// // Query query = qm.createQuery(reseller,lang);
			// // Query query =
			// qm.createQuery("SELECT * FROM [my:metaPageData]", Query.XPATH);
			// Query query =
			// qm.createQuery("SELECT * FROM [mix:title]  WHERE [my:lang] = 'en'",
			// Query.JCR_SQL2);
			//
			// QueryResult res = query.execute();
			//
			// NodeIterator it = res.getNodes();
			//
			// System.out.println("ovde88 " + it.hasNext());
			//
			// while (it.hasNext()) {
			// Node n = it.nextNode();
			//
			// System.out.println("query " + n.getPath()); // Check out the
			// // current node
			//
			// }

		} catch (Exception ex) {
			error = true;
			responseVO.setErrorMessage("Internal service error!");
			ex.printStackTrace();
		} finally {
			if (session != null)
				closeSession();
		}

		if (!error) {
			return Response.status(Response.Status.OK).entity(responseVO).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseVO).build();
		}
	}

	private void createHelpTree() {
		try {
			Node root = session.getRootNode();
			Node help = root.addNode("help", NodeType.NT_FOLDER);

			Node pp = help.addNode("pp", NodeType.NT_FOLDER);
			Node cs = help.addNode("cs", NodeType.NT_FOLDER);
			Node wd = help.addNode("wd", NodeType.NT_FOLDER);
			Node fi = help.addNode("fi", NodeType.NT_FOLDER);
			Node ami = help.addNode("ami", NodeType.NT_FOLDER);

			Node service = pp.addNode("service", NodeType.NT_FOLDER);
			Node tran = pp.addNode("tran", NodeType.NT_FOLDER);
			Node sals = pp.addNode("sals", NodeType.NT_FOLDER);
			Node resel = pp.addNode("resel", NodeType.NT_FOLDER);

			Node one = service.addNode("one", NodeType.NT_FILE);
			Node content = one.addNode("jcr:content", "nt:resource");
			one.addMixin("my:metaPageData");

			one.setProperty("my:lang", "en");
			one.setProperty("my:reseller", "1");
			
			/**
			 * add template file
			 */
			File f = new File("template.ftl");
			InputStream stream = new BufferedInputStream(new FileInputStream(f));
			Binary binary = session.getValueFactory().createBinary(stream);
			content.setProperty("jcr:data", binary);
			
		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void deleteHelpTree(String helpPath) {
		try {
			session.getNode(helpPath).remove();
			ispisiSvuDecu(session.getNode("/"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String sayHtmlHello() {
		return "<html> " + "<title>" + "Hello guys" + "</title>" + "<body><h1>" + "Hello guys" + "</body></h1>"
				+ "</html> ";
	}

	@Override
	public String getDocument(String language, String test) {

		Map<String, Object> inputMap = new HashMap<String, Object>();

		inputMap.put("telefon", "011-222-333");
		inputMap.put("adresa", "address");
		inputMap.put("firma", language);
		inputMap.put("test", test);

		// FreeMarker fm = new FreeMarker();
		// String s = fm.process(inputMap);
		return "";
	}

	private void ispisiSvuDecu(Node node) throws RepositoryException {
		if (node.hasNodes()) {
			NodeIterator it = node.getNodes();
			while (it.hasNext()) {
				Node dete = it.nextNode();
				logger.info(dete.getPath());
				ispisiSvuDecu(dete);
			}
		}
	}

	public DocumentNode createTree(Node node) {
		DocumentNode dn = null;
		try {
			String[] niz = node.getPath().split("/");
			dn = new DocumentNode(node.getIdentifier(), node.getName(), niz[1].toUpperCase(), node.getPrimaryNodeType()
					.getName(), node.getParent().getPath(), node.getPath());

			if (node.hasNodes()) {
				for (NodeIterator nodeIterator = node.getNodes(); nodeIterator.hasNext();) {
					Node subNode = nodeIterator.nextNode();
					dn.addChild(createTree(subNode));
				}
			}
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dn;
	}

	@Override
	public Response getJSON() {

		String response = null;
		DocumentNode help = null;
		try {
			openSession();
			ispisiSvuDecu(session.getNode("/help"));
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			help = createTree(session.getNode("/help"));
		} catch (PathNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		// DocumentNode tran = new DocumentNode();
		// DocumentNode service = new DocumentNode();
		// DocumentNode pp = new DocumentNode();
		// DocumentNode cs = new DocumentNode();
		// DocumentNode help = new DocumentNode();
		//
		// cs.setCategory("HELP");
		// cs.setKey("1666-sssss-ddddd-33333");
		// cs.setSelfPath("/help/cs");
		// cs.setTitle("CS");
		// cs.setType("nt:FOLDER");
		//
		// service.setCategory("HELP");
		// service.setKey("1666-ddddd-ddddd-33333");
		// service.setSelfPath("/help/cs/service");
		// service.setTitle("service");
		// service.setType("nt:FOLDER");
		//
		// tran.setCategory("HELP");
		// tran.setKey("1666-ddddd-99999-33333");
		// tran.setSelfPath("/help/cs/tran");
		// tran.setTitle("tran");
		// tran.setType("nt:FOLDER");
		//
		// pp.setCategory("HELP");
		// pp.setKey("1111-sssss-ddddd-33333");
		// pp.setSelfPath("/help/pp");
		// pp.setTitle("PP");
		// pp.setType("nt:FOLDER");
		//
		// help.setCategory("Help");
		// help.setKey("1111-sssss-ddddd-22222");
		// help.setParent(null);
		// help.setSelfPath("/help");
		// help.setTitle("Help");
		// help.setType("nt:FOLDER");
		//
		// cs.setParent(help.getSelfPath());
		// pp.setParent(help.getSelfPath());
		// service.setParent(pp.getSelfPath());
		// tran.setParent(pp.getSelfPath());
		// pp.addChild(service);
		// pp.addChild(tran);
		// help.addChild(pp);
		// help.addChild(cs);
		try {
			response = jsonMapper.defaultPrettyPrintingWriter().writeValueAsString(help);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			if (session != null)
				closeSession();
		}

		return Response.status(Response.Status.OK).entity(response).build();
	}

	@Override
	public Response getOneLevelJSON(@PathParam("nodePath") String nodePath) {
		openSession();
		String response = null;
		Node node = null;
		try {
			node = session.getNode("/" + nodePath);
			String[] niz = node.getPath().split("/");
			DocumentNode dn = new DocumentNode(node.getIdentifier(), node.getName(), niz[1].toUpperCase(), node
					.getPrimaryNodeType().getName(), node.getParent().getPath(), node.getPath());

			if (node.hasNodes()) {
				for (NodeIterator nodeIterator = node.getNodes(); nodeIterator.hasNext();) {
					Node subNode = nodeIterator.nextNode();
					DocumentNode dnc = new DocumentNode();
					dnc.setSelfPath(subNode.getPath());
					dn.addChild(dnc);
				}
			}
			response = jsonMapper.defaultPrettyPrintingWriter().writeValueAsString(dn);

		} catch (PathNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RepositoryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (session != null)
				closeSession();
		}
		return Response.status(Response.Status.OK).entity(response).build();
	}

	public void openSession() {
		try {
			initialContext = new InitialContext();
			repository = (Repository) initialContext.lookup("java:jcr/local");
			session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
			logger.info("SESSION OPENED");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeSession() {
		session.logout();
	}

	public DocumentCvor getDocumentCvor(String parent) {
		openSession();
		logger.info("PARENT: {}", parent);
		Node node = null;
		DocumentCvor dnl = null;
		try {
			node = session.getNode(parent);
			String[] niz = node.getPath().split("/");
			dnl = new DocumentCvor(node.getIdentifier(), node.getName(), niz[1].toUpperCase(), node
					.getPrimaryNodeType().getName(), node.getPath(), node.getParent().getPath());

			if (node.hasNodes()) {
				if (hasFolder(node)) {
					String children_href = node.getPath() + "/children";
					dnl.setChildren_href(children_href);
				} else {
					dnl.setChildren_href("");
				}

				if (hasFiles(node)) {
					String content_href = node.getPath() + "/content";
					dnl.setContent_href(content_href);
				} else {
					dnl.setContent_href("");
				}
			} else {
				dnl.setChildren_href("");
				dnl.setContent_href("");
			}

		} catch (PathNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RepositoryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if (session != null)
				closeSession();
		}
		return dnl;

	}

	@Override
	public Response getLinksJSON(@PathParam("path") String path) {
		String response = null;
		try {
			String pom = "/" + path;
			if (pom.equals("/"))
				pom = "/help";
			response = jsonMapper.defaultPrettyPrintingWriter().writeValueAsString(getDocumentCvor(pom));

		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (session != null)
				closeSession();
		}
		return Response.status(Response.Status.OK).entity(response).build();
	}

	@Override
	public Response getChildrenLinksJSON(@PathParam("parent") String parent) {

		logger.info("POZVANA METODA GETCHILDRENLINKSJSON");
		openSession();
		String response = null;
		Node node = null;
		ArrayList<DocumentCvor> children_list = new ArrayList<DocumentCvor>();
		try {
			node = session.getNode("/" + parent);
			if (node.hasNodes()) {
				for (NodeIterator nodeIterator = node.getNodes(); nodeIterator.hasNext();) {
					Node subNode = nodeIterator.nextNode();
					children_list.add(getDocumentCvor(subNode.getPath()));

				}
			}
			DocumentCvorWrapper dcw = new DocumentCvorWrapper();
			dcw.data = children_list;
			response = jsonMapper.defaultPrettyPrintingWriter().writeValueAsString(dcw);

		} catch (PathNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RepositoryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (session != null)
				closeSession();
		}
		return Response.status(Response.Status.OK).entity(response).build();
	}

	public boolean hasFolder(Node node) throws RepositoryException {
		openSession();
		for (NodeIterator nodeIterator = node.getNodes(); nodeIterator.hasNext();) {
			Node subNode = nodeIterator.nextNode();
			if (subNode.getPrimaryNodeType().getName().equals("nt:folder")) {
				return true;
			}
		}
		return false;
	}

	public boolean hasFiles(Node node) throws RepositoryException {
		openSession();
		for (NodeIterator nodeIterator = node.getNodes(); nodeIterator.hasNext();) {
			Node subNode = nodeIterator.nextNode();
			if (subNode.getPrimaryNodeType().getName().equals("nt:file")) {
				return true;
			}
		}
		return false;
	}
}
