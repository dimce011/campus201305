package com.bluelotussoftware.example.jsf;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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

@ManagedBean
@ViewScoped
public class TreeBeanCvor implements Serializable {

	private String page = "MPM";

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	private static final long serialVersionUID = 2417620239014385855L;
	private TreeNode root;
	private TreeNode selectedNode;
	private DocumentCvor object;

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

		makeTreeFromJSON();
		root = new TreeNodeImpl("Root", null);
		TreeNode treeNode = new TreeNodeImpl(object, root);
		// createNodes(object, treeNode);
		System.out.println("Izasao");
	}

	private void makeTreeFromJSON() throws JsonParseException, JsonMappingException, IOException, JSONException {
		ObjectMapper mapper = new ObjectMapper();
		setObject(mapper.readValue(getString(), DocumentCvor.class));
		System.out.println("Ispis objekta " + getObject());
	}

	// public void createNodes(DocumentCvor object, TreeNode root) {
	//
	// TreeNode node1 = new TreeNodeImpl(list, root);
	// System.out.println("Ispis " + list.getSelfPath());
	// createNodes(list, node1);
	//
	// }

	public String getString() throws JSONException {
		HttpClient httpClient = new DefaultHttpClient();
		Map<String, String> map = new HashMap<String, String>();
		String responseString = null;
		HttpResponse response = null;
		HttpGet httpGet = new HttpGet(prepareGetRequest("http://localhost:8080/helprepo", map));
		try {

			response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseString = EntityUtils.toString(entity, "UTF-8");
				System.out.println("Ispis responsaaaaa!!!!! " + responseString);

			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Prosao");
		/*
		 * if(webServiceRestString.equals("Restful")) return "strana3";
		 */
		return responseString;
	}

	private String prepareGetRequest(String url, Map<String, String> params) {

		String fullUrl = url;

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
	 */

	public void onNodeSelect(NodeSelectEvent event) {
		// DocumentCvor dcNode = (DocumentCvor) event.getTreeNode().getData();
		// String uri = dcNode.getChildren_href();
		// getPage(uri);
		// try {
		// makeTreeFromJSON();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Manamana", ((DocumentCvor) event.getTreeNode()
				.getData()).getTitle());
		FacesContext.getCurrentInstance().addMessage(event.getComponent().getId(), msg);
		// } catch (JsonParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (JsonMappingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Adds a {@link javax.faces.application.FacesMessage} with event data to
	 * the {@link javax.faces.context.FacesContext}.
	 */
	public void onNodeExpand(NodeExpandEvent event) {

		System.out.println("NodeExpandEvent Fired");
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Expanded", ((DocumentCvor) event.getTreeNode()
				.getData()).getTitle());
		FacesContext.getCurrentInstance().addMessage(event.getComponent().getId(), msg);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * Adds a {@link javax.faces.application.FacesMessage} with event data to
	 * the {@link javax.faces.context.FacesContext}.
	 */
	public void onNodeCollapse(NodeCollapseEvent event) {
		System.out.println("NodeCollapseEvent Fired");

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Collapsed", ((DocumentCvor) event
				.getTreeNode().getData()).getTitle());
		FacesContext.getCurrentInstance().addMessage(event.getComponent().getId(), msg);
	}

	private DocumentCvor getObject() {
		return object;
	}

	private void setObject(DocumentCvor object) {
		this.object = object;
	}

	String webServiceRestString;

	public String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	private void getPage(String uri) {
		HttpClient httpClient = new DefaultHttpClient();

		HttpResponse response = null;
		System.out.println("http://localhost:8080/helprepo" + uri);
		HttpGet httpGet = new HttpGet(prepareGetRequest("http://localhost:8080/helprepo/getPage" + uri, null));
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
