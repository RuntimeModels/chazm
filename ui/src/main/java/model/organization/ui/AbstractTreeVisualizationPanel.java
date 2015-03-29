/*
 * AbstractTreeVisualizationPanel.java
 *
 * Created on May 27, 2010
 *
 * See License.txt file the license agreement.
 */
package model.organization.ui;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import model.organization.Organization;

/**
 * The <code>AbstractListVisualizationPanel</code> provides tree type <code>JPanel</code> for display information about the <code>Organization</code>.
 *
 * @author Christopher Zhong
 * @since 6.0
 */
public abstract class AbstractTreeVisualizationPanel extends AbstractVisualizationPanel implements TreeSelectionListener {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The <code>DefaultMutableTreeNode</code> for the <code>DefaultTreeModel</code>.
	 */
	private final MutableTreeNode root = new DefaultMutableTreeNode();

	/**
	 * The <code>DefaultTreeModel</code> that will be used by the <code>JTree</code> to display the <code>SpecificationGoal</code> and <code>InstanceGoal</code>
	 * .
	 */
	private final DefaultTreeModel treeModel = new DefaultTreeModel(root);

	/**
	 * The <code>JTree</code> used to display the <code>SpecificationGoal</code> and <code>InstanceGoal</code>.
	 */
	private final JTree tree = new JTree(treeModel);

	/**
	 * Constructs a new instance of <code>AbstractTreeVisualizationPanel</code>.
	 *
	 * @param organization
	 *            the <code>Organization</code> that is used to visualize the <code>SpecificationGoal</code> and <code>InstanceGoal</code>.
	 * @param description
	 *            the <code>String</code> describing the title.
	 * @param verticalScrollBarPolicy
	 *            the vertical scroll bar policy which must be one of <code>ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS</code>,
	 *            <code>ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED</code>, or <code>ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER</code>.
	 * @param horizontalScrollBarPolicy
	 *            the horizontal scroll bar policy which be one of <code>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS</code>,
	 *            <code>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED</code> , or <code>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER</code>.
	 * @param orientation
	 *            the orientation of the <code>JSplitPane</code> which must be either <code>JSplitPane.HORIZONTAL_SPLIT</code> or
	 *            <code>JSplitPane.VERTICAL_SPLIT</code>.
	 * @param oneTouchExpandable
	 *            <code>true</code> to specify that the <code>JSplitPane</code> should provide a collapse / expand widget, <code>false</code> otherwise.
	 * @param editable
	 *            <code>true</code> if the <code>JTree</code> is editable, <code>false</code> otherwise.
	 * @param selectionMode
	 *            the selection mode of the <code>JTree</code> which must be one of <code>TreeSelectionModel.SINGLE_TREE_SELECTION</code>,
	 *            <code>TreeSelectionModel.CONTIGUOUS_TREE_SELECTION</code> or <code>TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION</code>.
	 * @param rootVisible
	 *            <code>true</code> if the root node of the <code>JTree</code> is visible, <code>false</code> otherwise.
	 * @param visibleRowCount
	 *            sets the visible row count property of the <code>JList</code>.
	 */
	protected AbstractTreeVisualizationPanel(final Organization organization, final String description, final int verticalScrollBarPolicy,
			final int horizontalScrollBarPolicy, final int orientation, final boolean oneTouchExpandable, final boolean editable, final int selectionMode,
			final boolean rootVisible, final int visibleRowCount) {
		super(organization);

		tree.setEditable(editable);
		tree.getSelectionModel().setSelectionMode(selectionMode);
		tree.setRootVisible(rootVisible);
		tree.setVisibleRowCount(visibleRowCount);
		tree.addTreeSelectionListener(this);

		initialize(tree, description, verticalScrollBarPolicy, horizontalScrollBarPolicy, orientation, oneTouchExpandable);

		startPolling();
	}

	/**
	 * Returns the <code>TreeModel</code>.
	 *
	 * @return the <code>TreeModel</code>.
	 */
	DefaultTreeModel getTreeModel() {
		return treeModel;
	}

	/**
	 * Returns the <code>TreeNode</code>.
	 *
	 * @return the <code>TreeNode</code>.
	 */
	MutableTreeNode getRoot() {
		return root;
	}

	/**
	 * Returns the <code>JTree</code>.
	 *
	 * @return the <code>JTree</code>.
	 */
	JTree getTree() {
		return tree;
	}

}