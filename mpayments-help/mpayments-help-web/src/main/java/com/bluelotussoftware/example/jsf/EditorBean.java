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
	String language;
	String reseller;
	String content;
	String new_folder_name;
	String folder = null;

	public String getNew_folder_name() {
		return new_folder_name;
	}

	public void setNew_folder_name(String new_folder_name) {
		this.new_folder_name = new_folder_name;
	}

	public void setNewFolderName() {
		this.new_folder_name = getNode_name();
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setNodeContent() {
		this.content = getNodeContent();
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getReseller() {
		return reseller;
	}

	public void setReseller(String reseller) {
		this.reseller = reseller;
	}

	public String getNode_name() {
		if (node == null) {
			return " ";
		} else {
			System.out.println(node.getTitle());
			return node.getTitle();
		}
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

	public void saveFolder() {
		saveNodeChanges("false", "true", getF_name(), getPage());
	}

	public void saveFile() {
		saveNodeChanges("true", "true", getF_name(), getPage());
	}

	public void editContent(String is_file) {
		saveNodeChanges(is_file, "false", getNew_folder_name(), getContent());
	}

	public String saveNodeChanges(String is_file, String to_save, String name,
			String html_pg) {

		HttpClient httpClient = new DefaultHttpClient();
		Map<String, String> map = new HashMap<String, String>();
		String responseString = null;
		HttpResponse response = null;

		map.put("node_path", node.getSelf_href());
		map.put("html_page", html_pg);
		map.put("f_name", name);
		map.put("is_file", is_file);
		map.put("to_save", to_save);
		map.put("language", language);
		map.put("reseller", reseller);

		System.out.println("lang " + language + " res " + reseller);

		HttpGet httpPost = new HttpGet(prepareGetRequest(
				"http://localhost:8080/helprepo/getSaveStatus", map));
		try {

			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseString = EntityUtils.toString(entity, "UTF-8");
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Info", "Successfully saved!");
				FacesContext.getCurrentInstance().addMessage("", msg);
				System.out.println("Uspesno je sacuvao!" + responseString);
			} else {
				System.out.println("Nije sklisko");
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Info", "Error!");
				FacesContext.getCurrentInstance().addMessage("", msg);
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseString;
	}

	public String getNodeContent() {
		HttpClient httpClient = new DefaultHttpClient();
		Map<String, String> map = new HashMap<String, String>();
		String responseString = null;
		HttpResponse response = null;

		map.put("node_path", node.getSelf_href());
		map.put("language", "English");
		map.put("reseller", "Centili");
		System.out.println("ovdje " + node.getContent_href());
		HttpGet httpPost = new HttpGet(node.getContent_href());
		try {

			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseString = EntityUtils.toString(entity, "UTF-8");
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Info", "Successfully edited!");
				FacesContext.getCurrentInstance().addMessage("", msg);
				System.out.println("Uspesno je !" + responseString);
			} else {
				System.out.println("Nije sklisko");
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Info", "Error!");
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
				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}

			String queryString = URLEncodedUtils
					.format(nameValuePairs, "UTF-8");

			if (url.indexOf("?") == -1) {
				fullUrl = url + "?" + queryString;
			} else {
				fullUrl = url + "&" + queryString;
			}
		}

		return fullUrl;

	}

}
