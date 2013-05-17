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

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

import paket.Node;

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

    /**
     * Default constructor
     */
    public TreeBean() {
        root = new TreeNodeImpl("Root", null);
        TreeNode node0 = new TreeNodeImpl("Segment 0", root);
        TreeNode node1 = new TreeNodeImpl("Segment 1", root);
        TreeNode node2 = new TreeNodeImpl("Segment 2", root);
        TreeNode node00 = new TreeNodeImpl("Segment 0.0", node0);
        TreeNode node01 = new TreeNodeImpl("Segment 0.1", node0);
        TreeNode node10 = new TreeNodeImpl("Segment 1.0", node1);
        TreeNode node11 = new TreeNodeImpl("Segment 1.1", node1);
        TreeNode node000 = new TreeNodeImpl("Segment 0.0.0", node00);
        TreeNode node001 = new TreeNodeImpl("Segment 0.0.1", node00);
        TreeNode node010 = new TreeNodeImpl("Segment 0.1.0", node01);
        TreeNode node100 = new TreeNodeImpl("Segment 1.0.0", node10);

  
        
        
    }

    /**
     * This method returns the tree model based on the root node.
     *
     * @return root node.
     */
    public TreeNode getModel() {
        return root;
    }
    
	public void RecursionTree(Node node) {
		
		TreeNode treeNode = null;

		// root = new TreeNodeImpl(node.getName(), null);

		if (node.getParent() == null) {
			// System.out.println(node.name);
			root = new TreeNodeImpl(node.getName(), null);

		}
		if (node.getChildNodes() != null) {
			for (Node child : node.getChildNodes()) {
				
				System.out.println(child.getName());
				
				//treeNode = new TreeNodeImpl(child.getName(), child.getParent());
				RecursionTree(child);
			}
		}
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
     * @param selectedNode node to be set as selected.
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
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", event.getTreeNode().getData().toString());
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
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Expanded", event.getTreeNode().getData().toString());
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
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Collapsed", event.getTreeNode().getData().toString());
        FacesContext.getCurrentInstance().addMessage(event.getComponent().getId(), msg);
    }
}
