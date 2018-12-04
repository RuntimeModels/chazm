/*
 * TreeModel.java
 *
 * Created on Sep 25, 2007
 *
 * See License.txt file the license agreement.
 */
package runtimemodels.chazm.model.ui;

import runtimemodels.chazm.api.entity.Agent;
import runtimemodels.chazm.api.organization.Organization;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The <code>AgentVisualizationPanel</code> class is a Swing component to visualize the <code>Agent</code> currently in the <code>Organization</code>.
 *
 * @author Christopher Zhong
 * @since 3.4
 */
public class AgentVisualizationPanel extends AbstractListVisualizationPanel<Agent> {

    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new instance of <code>AgentVisualizationPanel</code>.
     *
     * @param organization the <code>Organization</code> that is used to visualize the <code>Agent</code>.
     */
    public AgentVisualizationPanel(final Organization organization) {
        super(organization, "Agents", ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED,
                JSplitPane.VERTICAL_SPLIT, true, ListSelectionModel.SINGLE_SELECTION, JList.VERTICAL, 5);
    }

    @Override
    public void valueChanged(final ListSelectionEvent e) {
        final Agent agent = getList().getSelectedValue();
        if (agent != null) {
            getDetailedInformationPanel().showDetailedInformation(agent);
        }
    }

    @Override
    List<Agent> getCollection() {
//        final List<Agent> agents = new ArrayList<>(getOrganization().getAgents());
        final List<Agent> agents = new ArrayList<>();
        final Comparator<Agent> comparator = Comparator.comparing(Agent::toString);
        Collections.sort(agents, comparator);
        return agents;
    }

}
