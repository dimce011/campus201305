package org.infobip.mpayments.help.repo.rest;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
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
import javax.jcr.Binary;
import javax.jcr.ItemExistsException;
import javax.jcr.LoginException;
import javax.jcr.NamespaceRegistry;
import javax.jcr.Node;
import javax.jcr.NodeIterator;

import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyType;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Value;
import javax.jcr.Workspace;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.NodeTypeManager;
import javax.jcr.nodetype.NodeTypeTemplate;
import javax.jcr.nodetype.PropertyDefinitionTemplate;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.version.OnParentVersionAction;
import javax.jcr.version.VersionException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.infobip.mpayments.help.dto.DocumentCvor;
import org.infobip.mpayments.help.dto.DocumentCvorWrapper;
import org.infobip.mpayments.help.dto.DocumentNode;
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
			} else if ("createproperty".equals(action)) {
				createProperty();
			} else if ("createnamespace".equals(action)) {
				createNamespace();
			} else if ("createall".equals(action)) {
				createNamespace();
				createProperty();
				createHelpTree();
			}

			ispisiSvuDecu(session.getNode("/"));

			// session.save();

			// pravljenje namespace-a

			// NamespaceRegistry registry = session.getWorkspace()
			// .getNamespaceRegistry();

			// NamespaceRegistry registry =
			// session.getWorkspace().getNamespaceRegistry();


			// List<String> list = Arrays.asList(registry.getPrefixes());
			// if (!list.contains("my")) {
			// registry.registerNamespace("my", "com.infobip.jcr.my");
			// }


			//NodeTypeManager manager = session.getWorkspace()
			//		.getNodeTypeManager();


			// pravljenje novog tipa cvora sa novim propertijima

			// NamespaceRegistry nsReg = (NamespaceRegistry)
			// session.getWorkspace().getNamespaceRegistry();
			// nsReg.registerNamespace("my", "com.infobip.jcr.my");

			// NodeTypeTemplate nodeType = manager.createNodeTypeTemplate();
			// nodeType.setMixin(true);
			// nodeType.setName("my:metaPageData");
			// nodeType.setQueryable(true);
			// nodeType.setDeclaredSuperTypeNames(new String[]{"mix:title"});
			// System.out.println("ovde1");
			//
			// NodeTypeTemplate nodeType2 = manager.createNodeTypeTemplate();
			// nodeType2.setMixin(true);
			// nodeType2.setName("my:metaFolderData");
			// nodeType2.setQueryable(true);
			// nodeType2.setDeclaredSuperTypeNames(new String[]{"mix:title"});
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
			// PropertyDefinitionTemplate propertyDef3 =
			// manager.createPropertyDefinitionTemplate();
			// propertyDef3.setName("my:title");
			// propertyDef3.setMultiple(true);
			// propertyDef3.setRequiredType(PropertyType.STRING);
			// propertyDef3.setOnParentVersion(OnParentVersionAction.COPY);
			// propertyDef3.setProtected(false);
			// propertyDef3.setMandatory(false);
			// System.out.println("ovde MPM");
			//
			// nodeType.getPropertyDefinitionTemplates().add(propertyDef);
			// nodeType.getPropertyDefinitionTemplates().add(propertyDef2);
			// nodeType2.getPropertyDefinitionTemplates().add(propertyDef3);
			//
			// manager.registerNodeType(nodeType2, true);
			// session.save();
			// ispisiSvuDecu(session.getNode("/"));
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

			// ispisiSvuDecu(session.getNode("/"));

			// session.save();

			// Dodavanje propertija (title) u cvorove
			//
			// Node help = session.getNode("/help");
			// help.addMixin("my:metaFolderData");
			// String[] nizHelp = { "en#centili#Partner panel",
			// "de#centili#Hilfe Centili", "en#frogy#Help Frogy" };
			// help.setProperty("my:title", nizHelp);
			//

			// Node pp = session.getNode("/help/pp");
			// pp.addMixin("my:metaFolderData");
			// String[] niz = { "en#centili#Partner panel",
			// "de#centili#Partner Panel", "en#frogy#Partner Panel Frogy" };
			// pp.setProperty("my:title", niz);
			//
			// Node cs = session.getNode("/help/cs");
			// cs.addMixin("my:metaFolderData");
			// String[] nizCs = { "en#centili#Customer Support",
			// "de#centili#Customer Support de",
			// "en#frogy#Customer Support Frogy" };
			// cs.setProperty("my:title", nizCs);

			// Node wd = session.getNode("/help/wd");
			// wd.addMixin("my:metaFolderData");
			// String[] nizWd = { "en#centili#Payment Widget",
			// "de#centili#Zahlung Widget", "en#frogy#Frogy payment widget" };
			// wd.setProperty("my:title", nizWd);
			//
			// Node fi = session.getNode("/help/fi");
			// fi.addMixin("my:metaFolderData");
			// String[] nizFi = { "en#centili#Finance",
			// "de#centili#Finanzieren",
			// "en#frogy#Finance frogy" };
			// fi.setProperty("my:title", nizFi);
			//
			// Node ami = session.getNode("/help/ami");
			// ami.remove();
			//
			// Node service = session.getNode("/help/pp/service");
			// service.addMixin("my:metaFolderData");
			// String[] nizService = { "en#centili#Service",
			// "de#centili#Dienst", "en#frogy#Service Frogy" };
			// service.setProperty("my:title", nizService);

			// Node pp = session.getNode("/help/pp");
			//
			// Node tran = session.getNode("/help/pp/tran");
			// tran.remove();
			// Node signUp = pp.addNode("singUp", NodeType.NT_FOLDER);
			// signUp.addMixin("my:metaFolderData");
			// String[] nizTran = { "en#centili#Sign Up", "de#centili#Anmelden",
			// "en#frogy#Sign up Frogy" };
			// signUp.setProperty("my:title", nizTran);
			//
			// Node sals = session.getNode("/help/pp/sals");
			// sals.remove();
			// Node login = pp.addNode("login", NodeType.NT_FOLDER);
			// login.addMixin("my:metaFolderData");
			// String[] nizSals = { "en#centili#Login", "de#centili#Anmelden",
			// "en#frogy#Login Frogy" };
			// login.setProperty("my:title", nizSals);
			//
			// Node resel = session.getNode("/help/pp/resel");
			// resel.remove();
			// Node profile = pp.addNode("profile", NodeType.NT_FOLDER);
			// profile.addMixin("my:metaFolderData");
			// String[] nizResel = { "en#centili#Profile", "de#centili#Profil",
			// "en#frogy#Profile Frogy" };
			// profile.setProperty("my:title", nizResel);
			//
			// ispisiSvuDecu(session.getNode("/"));

			// session.save();

			// rename foldera
			// Node n = session.getNode("/help/pp");
			// n.getSession().move(n.getPath(), n.getParent().getPath() +
			// "/peradetla");
			// ispisiSvuDecu(session.getNode("/help"));
			// session.save();
			// rename contenta

			// ispis propertija
			// Property p = help.getProperty("my:title");
			// System.out.println(p.getName());
			// Value[] v = p.getValues();
			// ArrayList<String> stringList = new ArrayList<String>();
			// for (Value value : v) {
			// stringList.add(value.getString());
			// }
			// System.out.println("LISTA STRINGOVA IZ PROPERTIA");
			// for (String string : stringList) {
			// System.out.println(string);
			// }

			// ubacivanje fajla u pp folder
			// Node one = session.getNode("/help/pp").addNode("one",
			// NodeType.NT_FILE);
			// Node content = one.addNode("jcr:content", "nt:resource");
			// one.addMixin("my:metaPageData");
			//
			// one.setProperty("my:lang", "en");
			// one.setProperty("my:reseller", "centili");
			//
			// /**
			// * add template file
			// */
			// File f = new File("templatePP.ftl");
			// InputStream stream = new BufferedInputStream(new
			// FileInputStream(f));
			// Binary binary = session.getValueFactory().createBinary(stream);
			// content.setProperty("jcr:data", binary);

			ispisiSvuDecu(session.getNode("/"));
			session.save();

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

	private void createNamespace() throws RepositoryException {
		// TODO Auto-generated method stub
		NamespaceRegistry registry = session.getWorkspace().getNamespaceRegistry();

		List<String> list = Arrays.asList(registry.getPrefixes());
		if (!list.contains("my")) {
			registry.registerNamespace("my", "com.infobip.jcr.my");
		}

		// pravljenje novog tipa cvora sa novim propertijima

		NamespaceRegistry nsReg = (NamespaceRegistry) session.getWorkspace().getNamespaceRegistry();
		nsReg.registerNamespace("my", "com.infobip.jcr.my");
	}

	private void createProperty() throws RepositoryException {
		// TODO Auto-generated method stub
		NodeTypeManager manager = session.getWorkspace().getNodeTypeManager();
		NodeTypeTemplate nodeType = manager.createNodeTypeTemplate();
		nodeType.setMixin(true);
		nodeType.setName("my:metaPageData");
		nodeType.setQueryable(true);
		nodeType.setDeclaredSuperTypeNames(new String[] { "mix:title" });
		System.out.println("ovde1");

		NodeTypeTemplate nodeType2 = manager.createNodeTypeTemplate();
		nodeType2.setMixin(true);
		nodeType2.setName("my:metaFolderData");
		nodeType2.setQueryable(true);
		nodeType2.setDeclaredSuperTypeNames(new String[] { "mix:title" });

		PropertyDefinitionTemplate propertyDef = manager.createPropertyDefinitionTemplate();
		propertyDef.setName("my:lang");
		propertyDef.setMultiple(false);
		propertyDef.setRequiredType(PropertyType.STRING);
		propertyDef.setOnParentVersion(OnParentVersionAction.COPY);
		propertyDef.setProtected(false);
		System.out.println("ovde2");

		PropertyDefinitionTemplate propertyDef2 = manager.createPropertyDefinitionTemplate();
		propertyDef2.setName("my:reseller");
		propertyDef2.setMultiple(false);
		propertyDef2.setRequiredType(PropertyType.STRING);
		propertyDef2.setOnParentVersion(OnParentVersionAction.COPY);
		propertyDef2.setProtected(false);
		System.out.println("ovde2");

		PropertyDefinitionTemplate propertyDef3 = manager.createPropertyDefinitionTemplate();
		propertyDef3.setName("my:title");
		propertyDef3.setMultiple(true);
		propertyDef3.setRequiredType(PropertyType.STRING);
		propertyDef3.setOnParentVersion(OnParentVersionAction.COPY);
		propertyDef3.setProtected(false);
		propertyDef3.setMandatory(false);
		System.out.println("ovde MPM");

		nodeType.getPropertyDefinitionTemplates().add(propertyDef);
		nodeType.getPropertyDefinitionTemplates().add(propertyDef2);
		nodeType2.getPropertyDefinitionTemplates().add(propertyDef3);

		manager.registerNodeType(nodeType, true);
		manager.registerNodeType(nodeType2, true);
		session.save();
		ispisiSvuDecu(session.getNode("/"));

	}

	private void createHelpTree() {
		try {
			Node root = session.getRootNode();
			Node help = root.addNode("help", NodeType.NT_FOLDER);

			Node pp = help.addNode("pp", NodeType.NT_FOLDER);
			Node cs = help.addNode("cs", NodeType.NT_FOLDER);
			Node wd = help.addNode("wd", NodeType.NT_FOLDER);
			Node fi = help.addNode("fi", NodeType.NT_FOLDER);

			Node service = pp.addNode("service", NodeType.NT_FOLDER);
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

			// dodavanje title propertija
			help.addMixin("my:metaFolderData");
			String[] nizHelp = { "en#centili#Partner panel", "de#centili#Hilfe Centili", "en#frogy#Help Frogy" };
			help.setProperty("my:title", nizHelp);

			pp.addMixin("my:metaFolderData");
			String[] niz = { "en#centili#Partner panel", "de#centili#Partner Panel", "en#frogy#Partner Panel Frogy" };
			pp.setProperty("my:title", niz);

			cs.addMixin("my:metaFolderData");
			String[] nizCs = { "en#centili#Customer Support", "de#centili#Customer Support de",
					"en#frogy#Customer Support Frogy" };
			cs.setProperty("my:title", nizCs);

			wd.addMixin("my:metaFolderData");
			String[] nizWd = { "en#centili#Payment Widget", "de#centili#Zahlung Widget",
					"en#frogy#Frogy payment widget" };
			wd.setProperty("my:title", nizWd);

			fi.addMixin("my:metaFolderData");
			String[] nizFi = { "en#centili#Finance", "de#centili#Finanzieren", "en#frogy#Finance frogy" };
			fi.setProperty("my:title", nizFi);

			service.addMixin("my:metaFolderData");
			String[] nizService = { "en#centili#Service", "de#centili#Dienst", "en#frogy#Service Frogy" };
			service.setProperty("my:title", nizService);

			// dodavanje dece sa propertijima u pp
			Node signUp = pp.addNode("signup", NodeType.NT_FOLDER);
			signUp.addMixin("my:metaFolderData");
			String[] nizTran = { "en#centili#Sign Up", "de#centili#Anmelden", "en#frogy#Sign up Frogy" };
			signUp.setProperty("my:title", nizTran);

			Node login = pp.addNode("login", NodeType.NT_FOLDER);
			login.addMixin("my:metaFolderData");
			String[] nizSals = { "en#centili#Login", "de#centili#Anmelden", "en#frogy#Login Frogy" };
			login.setProperty("my:title", nizSals);

			Node profile = pp.addNode("profile", NodeType.NT_FOLDER);
			profile.addMixin("my:metaFolderData");
			String[] nizResel = { "en#centili#Profile", "de#centili#Profil", "en#frogy#Profile Frogy" };
			profile.setProperty("my:title", nizResel);

			// ubacivanje fajla u pp folder
			Node onepp = pp.addNode("one", NodeType.NT_FILE);
			Node contentpp = onepp.addNode("jcr:content", "nt:resource");
			onepp.addMixin("my:metaPageData");

			onepp.setProperty("my:lang", "en");
			onepp.setProperty("my:reseller", "");

			/**
			 * add template file
			 */
			File fpp = new File("templatePP.ftl");
			InputStream streampp = new BufferedInputStream(new FileInputStream(fpp));
			Binary binarypp = session.getValueFactory().createBinary(streampp);
			contentpp.setProperty("jcr:data", binarypp);

			// ubacivanje fajla u signUp folder
			Node onesu = signUp.addNode("one", NodeType.NT_FILE);
			Node contentsu = onesu.addNode("jcr:content", "nt:resource");
			onesu.addMixin("my:metaPageData");

			onesu.setProperty("my:lang", "en");
			onesu.setProperty("my:reseller", "centili");

			/**
			 * add template file
			 */
			File fsu = new File("signup.ftl");
			InputStream streamsu = new BufferedInputStream(new FileInputStream(fsu));
			Binary binarysu = session.getValueFactory().createBinary(streamsu);
			contentsu.setProperty("jcr:data", binarysu);

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

	public DocumentCvor getDocumentCvor(String parent, String language, String reseller, @Context UriInfo ui) {

		openSession();
		Node node = null;
		DocumentCvor dnl = null;
		try {
			node = session.getNode(parent);
			String title = null;

			if (reseller == null) {
				reseller = "centili";
			}
			if (language == null) {
				language = "en";
			}

			if (node.hasProperty("my:title")) {
				Property p = node.getProperty("my:title");
				Value[] v = p.getValues();
				for (Value value : v) {
					String[] mtitle = value.getString().split("#");

					if (mtitle[1].equals(reseller)) {
						if (mtitle[0].equals(language)) {
							title = mtitle[2];
						} else if (mtitle[0].equals("en")) {
							title = mtitle[2];
						}
					} else if (mtitle[1].equals("centili")) {
						if (mtitle[0].equals(language)) {
							title = mtitle[2];
						} else if (mtitle[0].equals("en")) {
							title = mtitle[2];
						}

					}
				}
			}

			if (title == null) {
				System.out.println("Node " + node.getName() + " nema odgovarajuci properti");
				title = node.getName();
			}

			String[] niz = node.getPath().split("/");


			dnl = new DocumentCvor(title, niz[1].toUpperCase(), ui.getBaseUri().toString() + "documents"
					+ node.getPath(), ui.getBaseUri().toString() + "documents" + node.getParent().getPath());


			if (node.hasNodes()) {
				if (hasFolder(node)) {
					String children_href = node.getPath() + "/children";
					dnl.setChildren_href(ui.getBaseUri().toString() + "documents" + children_href);
				} else {
					dnl.setChildren_href("");
				}

				if (hasFiles(node)) {
					String content_href = node.getPath() + "/content";
					dnl.setContent_href(ui.getBaseUri().toString() + "documents" + content_href);
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
	public Response getLinksJSON(@PathParam("path") String path, @QueryParam("language") String language,
			@QueryParam("reseller") String reseller, @Context UriInfo ui) {

		String response = null;
		try {
			String pom = "/" + path;
			if (pom.equals("/"))
				pom = "/help";

			response = jsonMapper.defaultPrettyPrintingWriter().writeValueAsString(
					getDocumentCvor(pom, language, reseller, ui));

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
	public Response getChildrenLinksJSON(@PathParam("parent") String parent, @QueryParam("language") String language,
			@QueryParam("reseller") String reseller, @Context UriInfo ui) {

		openSession();
		String response = null;
		Node node = null;
		ArrayList<DocumentCvor> children_list = new ArrayList<DocumentCvor>();
		try {
			node = session.getNode("/" + parent);
			if (node.hasNodes()) {
				for (NodeIterator nodeIterator = node.getNodes(); nodeIterator.hasNext();) {
					Node subNode = nodeIterator.nextNode();

					if (!subNode.isNodeType("nt:file")) {
						children_list.add(getDocumentCvor(subNode.getPath(), language, reseller, ui));
					}
					// children_list.add(getDocumentCvor(subNode.getPath(),
					// language, reseller, ui));


				}
			}
			DocumentCvorWrapper dcw = new DocumentCvorWrapper();
			dcw.documents = children_list;
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

	public static ObjectMapper getJsonMapper() {
		// TODO Auto-generated method stub
		return jsonMapper;
	}

	@Override
	public Response getSaveStatus(@QueryParam("node_path") String node_path, @QueryParam("html_page") String html_page,
			@QueryParam("f_name") String f_name, @QueryParam("is_file") String is_file,
			@QueryParam("to_save") String to_save, @QueryParam("language") String language,
			@QueryParam("reseller") String reseller) {
		Node node = null;
		Node file = null;
		Node content = null;
		openSession();
		try {

			if (language == null) {
				language = "en";
			}

			if (reseller == null) {
				reseller = "";
			}

			if (!node_path.startsWith("/")) {
				node_path = "/" + node_path;
			}

			System.out.println("getSaveStatus path " + node_path);

			Workspace ws = session.getWorkspace();
			QueryManager qm = ws.getQueryManager();
			Query query = qm.createQuery("SELECT * FROM [mix:title]  WHERE [my:lang] = '" + language
					+ "' and [my:reseller] = '" + reseller + "' and ISCHILDNODE([" + node_path + "])", Query.JCR_SQL2);
			QueryResult res = query.execute();
			NodeIterator it = res.getNodes();

			if (it.hasNext()) {
				node = it.nextNode();
			}

			if (node == null) {
				node = session.getNode(node_path);
			}

			if (to_save.equalsIgnoreCase("true")) {
				if (node.isNodeType(NodeType.NT_FOLDER)) {

					if (is_file.equalsIgnoreCase("true")) {
						file = node.addNode(f_name.toString() + "", "nt:file");
						file.addMixin("my:metaPageData");

						file.setProperty("my:lang", language);
						file.setProperty("my:reseller", reseller);
						content = file.addNode("jcr:content", "nt:resource");
						InputStream stream = new ByteArrayInputStream(html_page.getBytes());
						Binary binary = session.getValueFactory().createBinary(stream);

						content.setProperty("jcr:data", binary);
						session.save();
						return Response.status(Response.Status.OK).entity(content.getName()).build();
					} else {
						file = node.addNode(f_name.toString(), "nt:folder");
						session.save();
						System.out.println("folder je sacuvan");
						return Response.status(Response.Status.OK).entity(file.getName()).build();
					}
				}
				if (node.isNodeType(NodeType.NT_FILE)) {
					node.setProperty(f_name, html_page);
					session.save();
					return Response.status(Response.Status.OK).entity(node.getPath()).build();
				}

			} else if (to_save.equalsIgnoreCase("false")) {
				if (node.isNodeType(NodeType.NT_FILE)) {
					System.out.println("Nt file Jelena");

					InputStream stream = new ByteArrayInputStream(html_page.getBytes());
					Binary binary = session.getValueFactory().createBinary(stream);
					node.setProperty("jcr:data", binary);
					session.save();
					return Response.status(Response.Status.OK).entity(node.getPath()).build();
				} else if (node.isNodeType(NodeType.NT_FOLDER)) {
					System.out.println("PATH >>> " + node.getPath() + " FOLDER_NAME >>> " + f_name
							+ " PARENT PATH >>> " + node.getParent().getPath());
					node.getSession().move(node.getPath(), node.getParent().getPath() + "/" + f_name);
					// session.save();
					// node.getSession().move(node.getPath(),
					// node.getParent().getPath() + "/" + file);
					session.save();
					return Response.status(Response.Status.OK).entity(node.getParent().getPath()).build();
				}
			} else {
				System.out.println("Probao je da sacuva.....");
				return Response.status(Response.Status.NOT_MODIFIED).entity(content).build();
			}
		} catch (ItemExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PathNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchNodeTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VersionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return null;
	}

}
