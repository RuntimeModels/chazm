/*
 * TaskVisualizationPanel.java
 *
 * Created on May 26, 2010
 *
 * See License.txt file the license agreement.
 */
package model.organization.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;

import model.organization.entity.Organization;
import model.organization.relation.Task;

/**
 * The <code>TaskVisualizationPanel</code> class is a Swing component to
 * visualize the {@link Task} currently in the {@link Organization}.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1.2.4 $, $Date: 2011/09/19 14:26:34 $
 * @since 6.0
 */
public class TaskVisualizationPanel extends
		AbstractListVisualizationPanel<Task> {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new instance of <code>TaskVisualizationPanel</code>.
	 * 
	 * @param organization
	 *            the <code>Organization</code> that is used to visualize the
	 *            <code>Task</code>.
	 */
	public TaskVisualizationPanel(final Organization organization) {
		super(organization, "Tasks",
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED,
				JSplitPane.VERTICAL_SPLIT, true,
				ListSelectionModel.SINGLE_SELECTION, JList.VERTICAL, 10);
	}

	@Override
	public void valueChanged(final ListSelectionEvent e) {
		final Task task = getList().getSelectedValue();
		if (task != null) {
			getDetailedInformationPanel().showDetailedInformation(task);
		}
	}

	@Override
	List<Task> getCollection() {
		final List<Task> tasks = new ArrayList<>(getOrganization().getTasks());
		final Comparator<Task> comparator = new Comparator<Task>() {

			@Override
			public int compare(final Task task1, final Task task2) {
				return task1.toString().compareTo(task2.toString());
			}

		};
		Collections.sort(tasks, comparator);
		return tasks;
	}

}
