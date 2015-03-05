/*
 * TreeModel.java
 *
 * Created on Sep 25, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.models.organization.entity.InstanceGoal;
import org.models.organization.entity.Organization;
import org.models.organization.entity.SpecificationGoal;

/**
 * The <code>GoalVisualizationPanel</code> class is a Swing component to visualize the <code>SpecificationGoal</code> and <code>InstanceGoal</code> currently in
 * the <code>Organization</code>.
 *
 * @author Christopher Zhong
 * @version $Revision: 1.5.2.2 $, $Date: 2011/09/19 14:26:33 $
 * @since 3.4
 */
public class GoalVisualizationPanel extends AbstractTreeVisualizationPanel {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A <code>Map</code> of the <code>SpecificationGoal</code> to a <code>DefaultMutableTreeNode</code>.
	 */
	private final Map<SpecificationGoal, MutableTreeNode> specificationGoals = new ConcurrentHashMap<>();

	/**
	 * A <code>Map</code> of the <code>InstanceGoal</code> to a <code>DefaultMutableTreeNode</code>.
	 */
	private final Map<InstanceGoal<?>, MutableTreeNode> instanceGoals = new ConcurrentHashMap<>();

	/**
	 * Constructs a new instance of <code>GoalVisualizationPanel</code>.
	 *
	 * @param organization
	 *            the <code>Organization</code> that is used to visualize the <code>SpecificationGoal</code> and <code>InstanceGoal</code>.
	 */
	public GoalVisualizationPanel(final Organization organization) {
		super(organization, "Goals", ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED,
				JSplitPane.VERTICAL_SPLIT, true, false, TreeSelectionModel.SINGLE_TREE_SELECTION, false, 10);
	}

	@Override
	void updateVisualizationPanel() {
		final Collection<MutableTreeNode> instanceGoalsToRemove = new ArrayList<>();
		for (final InstanceGoal<?> instanceGoal : instanceGoals.keySet()) {
			if (!getOrganization().getInstanceGoals().contains(instanceGoal)) {
				instanceGoalsToRemove.add(instanceGoals.remove(instanceGoal));
			}
		}
		final Collection<MutableTreeNode> specificationGoalsToRemove = new ArrayList<>();
		for (final SpecificationGoal specificationGoal : specificationGoals.keySet()) {
			if (!getOrganization().getSpecificationGoals().contains(specificationGoal)) {
				specificationGoalsToRemove.add(specificationGoals.remove(specificationGoal));
			}
		}
		SwingUtilities.invokeLater(() -> {
			/*
			 * first, remove all elements from the tree model that are not present in the updated list; removes all instance goals first then the specification
			 * goals
			 */
			for (final MutableTreeNode instanceGoal : instanceGoalsToRemove) {
				getTreeModel().removeNodeFromParent(instanceGoal);
			}
			for (final MutableTreeNode specificationGoal : specificationGoalsToRemove) {
				getTreeModel().removeNodeFromParent(specificationGoal);
				getTreeModel().reload();
			}
			/*
			 * next, add the new elements from the updated list to the tree model; adds the specification goals first then the instance goals
			 */
			for (final SpecificationGoal goal1 : getOrganization().getSpecificationGoals()) {
				if (!specificationGoals.containsKey(goal1)) {
					final DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(goal1);
					specificationGoals.put(goal1, node1);
					getTreeModel().insertNodeInto(node1, getRoot(), getRoot().getChildCount());
					getTreeModel().reload();
				}
			}
			for (final InstanceGoal<?> goal2 : getOrganization().getInstanceGoals()) {
				if (!instanceGoals.containsKey(goal2) && specificationGoals.containsKey(goal2.getSpecificationGoal())) {
					final DefaultMutableTreeNode node2 = new DefaultMutableTreeNode(goal2);
					instanceGoals.put(goal2, node2);
					getTreeModel().insertNodeInto(node2, specificationGoals.get(goal2.getSpecificationGoal()),
									specificationGoals.get(goal2.getSpecificationGoal()).getChildCount());
				}
			}
		});
	}

	@Override
	public void valueChanged(final TreeSelectionEvent e) {
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) getTree().getLastSelectedPathComponent();
		if (node != null) {
			final Object nodeInfo = node.getUserObject();
			if (nodeInfo instanceof SpecificationGoal) {
				final SpecificationGoal specificationGoal = (SpecificationGoal) nodeInfo;
				getDetailedInformationPanel().showDetailedInformation(specificationGoal);
			} else if (nodeInfo instanceof InstanceGoal<?>) {
				final InstanceGoal<?> instanceGoal = (InstanceGoal<?>) nodeInfo;
				getDetailedInformationPanel().showDetailedInformation(instanceGoal);
			}
		}
	}

}
