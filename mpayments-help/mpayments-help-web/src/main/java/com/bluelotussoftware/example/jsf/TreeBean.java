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

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

/**
 * Page backing bean which manages page data and events.
 * 
 * @author John Yeary <jyeary@bluelotussoftware.com>
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class TreeBean implements Serializable {

	private static final long serialVersionUID = 2417620239014385855L;
	private TreeNode root;
	private TreeNode selectedNode;
	private DocumentNode object;
	private String path;

	/**
	 * Default constructor
	 * 
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * 
	 * @throws IOException
	 */
	public TreeBean() throws JsonParseException, JsonMappingException, IOException {

		makeFromJsonTree();
		root = new TreeNodeImpl("Root", null);
		TreeNode node0 = new TreeNodeImpl(object.getTitle(), root);
		createNodes(object, node0);
		System.out.println("Izasao");
	}

	private void makeFromJsonTree() throws JsonParseException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();
		setObject(mapper.readValue(new File("C:\\Users\\mratkovic\\Desktop\\jsonTwoTest.json"), DocumentNode.class));
		System.out.println(getObject());
	}

	public void createNodes(DocumentNode object, TreeNode root) {
		if (object.getChildren() != null) {
			for (DocumentNode list : object.getChildren()) {
				TreeNode node1 = new TreeNodeImpl(list.getTitle(), root);
				createNodes(list, node1);
			}
		}

	}

	public void jsonParse(File file) throws IOException {

		JsonFactory jfactory = new JsonFactory();

		/*** read from file ***/
		JsonParser jParser = jfactory.createJsonParser(file);

		// loop until token equal to "}"
		while (jParser.nextToken() != JsonToken.END_OBJECT) {

			// Get field name
			String fieldname = jParser.getCurrentName();
			System.out.println("File name: " + fieldname);

			if ("documents".equals(fieldname)) {
				// move to next, which is "name"'s value
				jParser.nextToken();
				System.out.println("This is documents: " + jParser.getText()); // display
																				// mkyong

			} else
				System.out.println("There is no documents");

			if ("key".equals(fieldname)) {

				// current token is "age",
				// move to next, which is "name"'s value
				jParser.nextToken();
				System.out.println("This is key: " + jParser.getIntValue()); // display
																				// 29

			}

			if ("type".equals(fieldname)) {

				jParser.nextToken(); // current token is "[", move next

				// messages is array, loop until token equal to "]"
				while (jParser.nextToken() != JsonToken.END_ARRAY) {

					// display msg1, msg2, msg3
					System.out.println("This is type:" + jParser.getText());

				}

			}

		}
		jParser.close();

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
		System.out.println("NodeSelectEvent Fired");
		path = event.getTreeNode().getData().toString();
		//path = path + "/" + event.getTreeNode().getData().toString();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Selected" ,path);
		FacesContext.getCurrentInstance().addMessage(event.getComponent().getId(), msg);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Adds a {@link javax.faces.application.FacesMessage} with event data to
	 * the {@link javax.faces.context.FacesContext}.
	 */
	public void onNodeExpand(NodeExpandEvent event) {

		System.out.println("NodeExpandEvent Fired");
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Expanded", event.getTreeNode().getData()
				.toString());
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
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Collapsed", event.getTreeNode().getData()
				.toString());
		FacesContext.getCurrentInstance().addMessage(event.getComponent().getId(), msg);
	}

	private DocumentNode getObject() {
		return object;
	}

	private void setObject(DocumentNode object) {
		this.object = object;
	}
}
