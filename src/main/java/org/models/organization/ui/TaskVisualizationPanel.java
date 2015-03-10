/*
 * TaskVisualizationPanel.java
 *
 * Created on May 26, 2010
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;

import org.models.organization.Organization;
import org.models.organization.relation.Task;
import org.models.organization.relation.TaskRelation;

/**
 * The <code>TaskVisualizationPanel</code> class is a Swing component to visualize the {@link TaskRelation} currently in the {@link Organization}.
 *
 * @author Christopher Zhong
 * @since 6.0
 */
public class TaskVisualizationPanel extends AbstractListVisualizationPanel<Task> {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new instance of <code>TaskVisualizationPanel</code>.
	 *
	 * @param organization
	 *            the <code>Organization</code> that is used to visualize the <code>Task</code>.
	 */
	public TaskVisualizationPanel(final Organization organization) {
		super(organization, "Tasks", ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED,
				JSplitPane.VERTICAL_SPLIT, true, ListSelectionModel.SINGLE_SELECTION, JList.VERTICAL, 10);
	}

	@Override
	public void valueChanged(final ListSelectionEvent e) {
		final Task taskRelation = getList().getSelectedValue();
		if (taskRelation != null) {
			getDetailedInformationPanel().showDetailedInformation(taskRelation);
		}
	}

	@Override
	List<Task> getCollection() {
		final List<Task> taskRelations = new ArrayList<>(getOrganization().getTasks());
		final Comparator<Task> comparator = (task1, task2) -> task1.toString().compareTo(task2.toString());
		Collections.sort(taskRelations, comparator);
		return taskRelations;
	}

}
