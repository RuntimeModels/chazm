/*
 * TreeModel.java
 *
 * Created on Sep 25, 2007
 *
 * See License.txt file the license agreement.
 */
package runtimemodels.chazm.model.ui;

import runtimemodels.chazm.api.entity.Capability;
import runtimemodels.chazm.api.organization.Organization;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The <code>CapabilityVisualizationPanel</code> class is a Swing component to visualize the <code>Capability</code> currently in the <code>Organization</code>.
 *
 * @author Christopher Zhong
 * @since 3.4
 */
public class CapabilityVisualizationPanel extends AbstractListVisualizationPanel<Capability> {

    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new instance of <code>CapabilityVisualizationPanel</code>.
     *
     * @param organization the <code>Organization</code> that is used to visualize the <code>Capability</code>.
     */
    public CapabilityVisualizationPanel(final Organization organization) {
        super(organization, "Capabilities", ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED,
                JSplitPane.VERTICAL_SPLIT, true, ListSelectionModel.SINGLE_SELECTION, JList.VERTICAL, 15);
    }

    @Override
    public void valueChanged(final ListSelectionEvent e) {
        final Capability capability = getList().getSelectedValue();
        if (capability != null) {
            getDetailedInformationPanel().showDetailedInformation(capability);
        }
    }

    @Override
    List<Capability> getCollection() {
        final List<Capability> capabilities = new ArrayList<>(getOrganization().getCapabilities());
        final Comparator<Capability> comparator = (capability1, capability2) -> capability1.toString().compareTo(capability2.toString());
        Collections.sort(capabilities, comparator);
        return capabilities;
    }

}
