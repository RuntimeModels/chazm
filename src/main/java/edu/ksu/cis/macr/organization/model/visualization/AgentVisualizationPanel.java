/*
 * TreeModel.java
 *
 * Created on Sep 25, 2007
 *
 * See License.txt file the license agreement.
 */
package edu.ksu.cis.macr.organization.model.visualization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;

import edu.ksu.cis.macr.organization.model.Agent;
import edu.ksu.cis.macr.organization.model.Organization;

/**
 * The <code>AgentVisualizationPanel</code> class is a Swing component to
 * visualize the <code>Agent</code> currently in the <code>Organization</code>.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.3.4.4 $, $Date: 2011/09/19 14:26:34 $
 * @since 3.4
 */
public class AgentVisualizationPanel extends
		AbstractListVisualizationPanel<Agent<?>> {

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new instance of <code>AgentVisualizationPanel</code>.
	 * 
	 * @param organization
	 *            the <code>Organization</code> that is used to visualize the
	 *            <code>Agent</code>.
	 */
	public AgentVisualizationPanel(final Organization organization) {
		super(organization, "Agents",
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED,
				JSplitPane.VERTICAL_SPLIT, true,
				ListSelectionModel.SINGLE_SELECTION, JList.VERTICAL, 5);
	}

	@Override
	public void valueChanged(final ListSelectionEvent e) {
		final Agent<?> agent = getList().getSelectedValue();
		if (agent != null) {
			getDetailedInformationPanel().showDetailedInformation(agent);
		}
	}

	@Override
	List<Agent<?>> getCollection() {
		final List<Agent<?>> agents = new ArrayList<>(getOrganization()
				.getAgents());
		final Comparator<Agent<?>> comparator = new Comparator<Agent<?>>() {

			@Override
			public int compare(final Agent<?> agent1, final Agent<?> agent2) {
				return agent1.toString().compareTo(agent2.toString());
			}

		};
		Collections.sort(agents, comparator);
		return agents;
	}

}
