/*
 *  Copyright 2012 Blue Lotus Software, LLC..
 *  Copyright 2012 John Yeary <jyeary@bluelotussoftware.com>.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * $Id: TreeBean.java,v fd1f822937df 2012/07/03 13:22:29 jyeary $
 */
package com.bluelotussoftware.example.jsf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.json.JSONException;
import org.primefaces.model.TreeNode;
//github.com/dimce011/campus201305.git
//github.com/dimce011/campus201305.git

/**
 * Page backing bean which manages page data and events.
 * 
 * @author John Yeary <jyeary@bluelotussoftware.com>
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class TreeBeanCvor implements Serializable {

	private String page = "MMMMMPPPPPPMMMMMM";

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	private static final long serialVersionUID = 2417620239014385855L;
	private TreeNode root;
	private TreeNode pointer;
	private TreeNode selectedNode;
	private DocumentCvor object;
	private TreeNode fakeChild;
	private String params = "";
	private String stringA;

	public String getStringA() {
		return stringA;
	}

	public void setStringA(String stringA) {
		this.stringA = stringA;
	}

	/**
	 * Default constructor
	 * 
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * 
	 * @throws IOException
	 * @throws JSONException
	 */
	public TreeBeanCvor() throws JsonParseException, JsonMappingException, IOException, JSONException {

		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			if (!params.equals("")) {
				params += "&";
			}
			params += entry.getKey() + "=" + entry.getValue();
		}

		makeFromJsonTree();
		System.out.println("OBJECT: " + object);
		root = new TreeNodeImpl("Root", null);
		pointer = new TreeNodeImpl(TreeNodeType.NODE, object, root);
		fakeChild = new TreeNodeImpl("fake", pointer);
		// createNodes(object, node0);
	}

	private void makeFromJsonTree() throws JsonParseException, JsonMappingException, IOException, JSONException {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();
		// setObject(mapper.readValue(new File("C:\\user.json"),
		// DocumentNode.class));
		// String str = URLEncoder.encode("help[2]","UTF-8");
		// String s = "http://localhost:8080/helprepo/" + str;
		// System.out.println("Ispis stringa " + s);

		// HARDCODE
		setObject(mapper.readValue(getString("http://localhost:8080/helprepo/documents/help?" + params), DocumentCvor.class));
	}

	public void addChildren(String uri) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			DocumentCvorWrapper dcw = mapper.readValue(getString(uri), DocumentCvorWrapper.class);
			// List<DocumentCvor> documentCvors =
			// mapper.readValue(getString(uri), new
			// List<DocumentCvor>().getClass());
			for (int i = 0; i < dcw.data.size(); i++) {
				// System.out.println("TITLE - " + dcw.data.get(i).getTitle() +
				// " content_href = " + dcw.data.get(i).getContent_href());
				TreeNode node = null;

				if (dcw.data.get(i).getChildren_href().equals("")) {
					if (dcw.data.get(i).getContent_href().equals("")) {
						node = new TreeNodeImpl(TreeNodeType.EMPTY, dcw.data.get(i), selectedNode);
						// System.out.println("TITLE >>> " +
						// dcw.data.get(i).getTitle() + " TYPE empty>>> " +
						// node.getType());
					} else {
						node = new TreeNodeImpl(TreeNodeType.LEAF, dcw.data.get(i), selectedNode);
						// System.out.println("TITLE >>> " +
						// dcw.data.get(i).getTitle() + " TYPE leaf>>> " +
						// node.getType());
					}
				} else {
					if (dcw.data.get(i).getContent_href().equals("")) {
						node = new TreeNodeImpl(TreeNodeType.NODE, dcw.data.get(i), selectedNode);
						// System.out.println("TITLE >>> " +
						// dcw.data.get(i).getTitle() + " TYPE node>>> " +
						// node.getType());
						TreeNode fake = new TreeNodeImpl("fake", node);
					} else {
						node = new TreeNodeImpl(TreeNodeType.CONTENTFOLDER, dcw.data.get(i), selectedNode);
						// System.out.println("TITLE >>> " +
						// dcw.data.get(i).getTitle() +
						// " TYPE contentfolder>>> " + node.getType());
						TreeNode fake = new TreeNodeImpl("fake", node);
					}
				}

				// if (dcw.data.get(i).getType().equals("nt:folder")) {
				// if (dcw.data.get(i).getChildren_href().equals("")) {
				// node = new TreeNodeImpl(TreeNodeType.EMPTY, dcw.data.get(i),
				// selectedNode);
				// } else {
				// node = new TreeNodeImpl(TreeNodeType.NODE, dcw.data.get(i),
				// selectedNode);
				// }
				// } else {
				// node = new TreeNodeImpl(TreeNodeType.LEAF, dcw.data.get(i),
				// selectedNode);
				// }
				// if (!dcw.data.get(i).getChildren_href().equals("")) {
				// TreeNode fake = new TreeNodeImpl("fake", node);
				// }

			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createNodes(DocumentCvor object, TreeNode root) {
		// if (object.getChildren() != null) {
		// for (DocumentCvor list : object.getChildren()) {
		TreeNode node1 = new TreeNodeImpl(object, root);
		// System.out.println("Ispis " + list.getSelfPath());
		// createNodes(list, node1);
		// }
		// }

	}

	public String getString(String uri) throws JSONException {
		HttpClient httpClient = new DefaultHttpClient();
		Map<String, String> map = new HashMap<String, String>();
		String responseString = null;
		HttpResponse response = null;
		HttpGet httpGet = new HttpGet(prepareGetRequest(uri, map));
		try {

			response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseString = EntityUtils.toString(entity, "UTF-8");
				// responseString =
				// URLEncoder.encode(entity.toString(),"UTF-8");

			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("proso2");
		/*
		 * if(webServiceRestString.equals("Restful")) return "strana3";
		 */
		return responseString;
	}

	private String prepareGetRequest(String url, Map<String, String> params) {

		String fullUrl = url;
		System.out.println("Usao u prepare");
		if (params != null && !params.isEmpty()) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			for (Entry<String, String> entry : params.entrySet()) {
				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}

			String queryString = URLEncodedUtils.format(nameValuePairs, "UTF-8");

			if (url.indexOf("?") == -1) {
				fullUrl = url + "?" + queryString;
			} else {
				fullUrl = url + "&" + queryString;
			}
		}
		System.out.println("Izasao iz prepare" + fullUrl);
		return fullUrl;

	}

	/**
	 * This method returns the tree model based on the root node.
	 * 
	 * @return root node.
	 */
	public TreeNode getModel() {
		return root;
	}

	/**
	 * Gets the selected node in the tree.
	 * 
	 * @return selected node in tree.
	 */
	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	/**
	 * Sets the selected node in the tree.
	 * 
	 * @param selectedNode
	 *            node to be set as selected.
	 */
	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	/**
	 * {@inheritDoc }
	 * 
	 * Adds a {@link javax.faces.application.FacesMessage} with event data to
	 * the {@link javax.faces.context.FacesContext}.
	 * @throws JSONException 
	 */


	public void onNodeSelect(NodeSelectEvent event) throws JSONException {

		if (event.getTreeNode().getType() == TreeNodeType.NODE.getType()
				|| event.getTreeNode().getType() == TreeNodeType.CONTENTFOLDER.getType()) {
			System.out.println("NodeSelectEvent Fired");
//			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Expanded", ((DocumentCvor) event
//					.getTreeNode().getData()).getTitle());
//			FacesContext.getCurrentInstance().addMessage(event.getComponent().getId(), msg);
			
		
		} else {
			DocumentCvor dNode = (DocumentCvor) event.getTreeNode().getData();
			System.out.println("SELF >>> " + dNode.getSelf_href());
			System.out.println("PARENT >>> " + dNode.getParent_href());
			System.out.println("NodeSelectEvent Fired LEAF" + dNode.getSelf_href());

		}
		
		stringA = getString(((DocumentCvor)event.getTreeNode().getData()).getSelf_href());
		
		System.out.println("Neformatirani " +stringA);
		

		
		// DocumentCvor dc = (DocumentCvor) event.getTreeNode().getData();
		// addChildren("http://localhost:8080/helprepo"+dc.getChildren_href());
		
	EditorBean.setSelectedDocumentNode((DocumentCvor) event.getTreeNode().getData());
		System.out.println(((DocumentCvor) event.getTreeNode().getData()).title+"setSelectedDocumentNode");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Adds a {@link javax.faces.application.FacesMessage} with event data to
	 * the {@link javax.faces.context.FacesContext}.
	 */
	public void onNodeExpand(NodeExpandEvent event) {
		selectedNode = event.getTreeNode();
		System.out.println("NodeExpandEvent Fired");
		DocumentCvor dc = (DocumentCvor) event.getTreeNode().getData();
		if (event.getTreeNode().getChildCount() == 1) {
			event.getTreeNode().getChildren().remove(0);
		}
		//stringA = "http://www.etf.rs";
//		addChildren("http://localhost:8080/helprepo" + dc.getChildren_href() + "?language=" + language + "&reseller="
//				+ reseller);
		
		//addChildren("http://localhost:8080/helprepo" + dc.getChildren_href() + "?" +params);
		addChildren(dc.getChildren_href());

//		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Expanded", dc.getTitle());
//		FacesContext.getCurrentInstance().addMessage(event.getComponent().getId(), msg);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * Adds a {@link javax.faces.application.FacesMessage} with event data to
	 * the {@link javax.faces.context.FacesContext}.
	 */
	public void onNodeCollapse(NodeCollapseEvent event) {
		System.out.println("NodeCollapseEvent Fired");

//		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Collapsed", ((DocumentCvor) event
//				.getTreeNode().getData()).getTitle());
//		FacesContext.getCurrentInstance().addMessage(event.getComponent().getId(), msg);
	}

	private DocumentCvor getObject() {
		return object;
	}

	private void setObject(DocumentCvor object) {
		this.object = object;
	}

	String webServiceRestString;

	public static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	private void getPage(String uri) {
		HttpClient httpClient = new DefaultHttpClient();

		HttpResponse response = null;
		System.out.println("http://localhost:8080/helprepo/" + uri);
		HttpGet httpGet = new HttpGet(prepareGetRequest("http://localhost:8080/helprepo/getPage/" + uri, null));
		try {
			response = httpClient.execute(httpGet);
			InputStream input = response.getEntity().getContent();

			String s = convertStreamToString(input);
			page = s;
			System.out.println("response !" + s);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
