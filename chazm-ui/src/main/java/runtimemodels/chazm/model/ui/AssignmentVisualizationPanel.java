/*
 * TreeModel.java
 *
 * Created on Sep 25, 2007
 *
 * See License.txt file the license agreement.
 */
package runtimemodels.chazm.model.ui;

import runtimemodels.chazm.api.Organization;
import runtimemodels.chazm.api.relation.Assignment;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The <code>AssignmentVisualizationPanel</code> class is a Swing component to visualize the <code>Assignment</code> currently in the <code>Organization</code>.
 *
 * @author Christopher Zhong
 * @since 3.4
 */
public class AssignmentVisualizationPanel extends AbstractListVisualizationPanel<Assignment> {

    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new instance of <code>AssignmentVisualizationPanel</code>.
     *
     * @param organization the <code>Organization</code> that is used to visualize the <code>Assignment</code>.
     */
    public AssignmentVisualizationPanel(final Organization organization) {
        super(organization, "Assignments", ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED,
                JSplitPane.VERTICAL_SPLIT, true, ListSelectionModel.SINGLE_SELECTION, JList.VERTICAL, 10);
    }

    @Override
    public void valueChanged(final ListSelectionEvent e) {
        final Assignment assignment = getList().getSelectedValue();
        if (assignment != null) {
            getDetailedInformationPanel().showDetailedInformation(assignment);
        }
    }

    @Override
    List<Assignment> getCollection() {
        final List<Assignment> assignments = new ArrayList<>(getOrganization().getAssignments());
        final Comparator<Assignment> comparator = (assignment1, assignment2) -> assignment1.toString().compareTo(assignment2.toString());
        Collections.sort(assignments, comparator);
        return assignments;
    }

}
