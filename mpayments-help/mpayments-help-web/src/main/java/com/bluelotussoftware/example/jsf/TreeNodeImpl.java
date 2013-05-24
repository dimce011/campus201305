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
 * $Id: TreeNodeImpl.java,v 1b50918de8f7 2012/07/06 16:45:46 jyeary $
 */
package com.bluelotussoftware.example.jsf;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * Extension of the {@link org.primefaces.model.DefaultTreeNode} class that
 * overrides the node type, and includes a data component.
 *
 * @see org.primefaces.model.DefaultTreeNode
 * @author John Yeary <jyeary@bluelotussoftware.com>
 * @version 1.0
 */
public class TreeNodeImpl extends DefaultTreeNode {

    private static final long serialVersionUID = 5333810777428638968L;

    /**
     * Constructor which sets the {@link com.bluelotussoftware.example.jsf.TreeNodeType}, {@code Object}
     * data, and parent node.
     *
     * @param type The type of node this represents.
     * @param data {@code Object} value stored in the node.
     * @param parent the {@link org.primefaces.model.TreeNode} which is the
     * parent to this object, or {@code null} if this is the &quot;root&quot;
     * node.
     */
    public TreeNodeImpl(TreeNodeType type, Object data, TreeNode parent) {
        super(type.getType(), data, parent);
    }

    /**
     * Constructor which sets {@code Object} data, and parent node.
     *
     * @param data {@code Object} value stored in the node.
     * @param parent parent the {@link org.primefaces.model.TreeNode} which is
     * the parent to this object, or {@code null} if this is the
     * &quot;root&quot; node.
     */
    public TreeNodeImpl(Object data, TreeNode parent) {
        super(data, parent);
    }

    /**
     * This method returns {@link com.bluelotussoftware.example.jsf.TreeNodeType#getType()}
     * depending on whether the node is a &quot;leaf&quot; node which contains
     * no children, or a &quot;node&quot; if it contains children.
     *
     * @return {@link com.bluelotussoftware.example.jsf.TreeNodeType#getType()}
     * based on whether this node has child objects.
     */
//    @Override
//    public String getType() {
//        if (isLeaf()) {
//            return TreeNodeType.LEAF.getType();
//        } else {
//            return TreeNodeType.NODE.getType();
//        }
//    }
}
