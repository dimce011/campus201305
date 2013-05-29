package com.bluelotussoftware.example.jsf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

@ManagedBean
@ViewScoped
public class EditorBean {

	private String page;
	String f_name;
	String node_name;

	public String getNode_name() {
		if (node == null) {
			return "Jelena ";
		} else {
			return node.getTitle();
		}
	}

	public void setNode_name(String node_name) {
		this.node_name = node_name;
	}

	public String getF_name() {
		return f_name;
	}

	public void setF_name(String f_name) {
		this.f_name = f_name;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	static DocumentCvor node;

	public static void setSelectedDocumentNode(DocumentCvor dn) {
		node = dn;
	}

	// public void getNodeName() {
	// node_name = node.getTitle();
	// return node_name;
	// }

	public void saveFolder() {
		saveNodeChanges("false", "true");
	}

	public void saveFile() {
		saveNodeChanges("true", "true");
	}

	public void editContent(String is_file) {
		saveNodeChanges(is_file, "false");
	}

	public String saveNodeChanges(String is_file, String to_save) {

		HttpClient httpClient = new DefaultHttpClient();
		Map<String, String> map = new HashMap<String, String>();
		String responseString = null;
		HttpResponse response = null;

		map.put("node_path", node.getSelf_href());
		map.put("html_page", getPage());
		map.put("f_name", getF_name());
		map.put("is_file", is_file);
		map.put("to_save", to_save);

		HttpGet httpPost = new HttpGet(prepareGetRequest("http://localhost:8080/helprepo/getSaveStatus", map));
		try {

			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseString = EntityUtils.toString(entity, "UTF-8");
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully saved!");
				FacesContext.getCurrentInstance().addMessage("", msg);
				System.out.println("Uspesno je sacuvao!" + responseString);
			} else {
				System.out.println("Nije sklisko");
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Error!");
				FacesContext.getCurrentInstance().addMessage("", msg);
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

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

}
