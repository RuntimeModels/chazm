/*
 * PerformanceFunctionVisualizationPanel.java
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
import org.models.organization.entity.Pmf;

/**
 * The <code>PerformanceFunctionVisualizationPanel</code> class is a Swing component to visualize the {@link Pmf} currently in the {@link Organization}.
 *
 * @author Christopher Zhong
 * @since 6.0
 */
public class PerformanceFunctionVisualizationPanel extends AbstractListVisualizationPanel<Pmf> {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new instance of <code>PerformanceFunctionVisualizationPanel</code>.
	 *
	 * @param organization
	 *            the <code>Organization</code> that is used to visualize the <code>PerformanceFunction</code>.
	 */
	public PerformanceFunctionVisualizationPanel(final Organization organization) {
		super(organization, "Performance Functions", ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED,
				JSplitPane.VERTICAL_SPLIT, true, ListSelectionModel.SINGLE_SELECTION, JList.VERTICAL, 10);
	}

	@Override
	public void valueChanged(final ListSelectionEvent e) {
		final Pmf pmf = getList().getSelectedValue();
		if (pmf != null) {
			getDetailedInformationPanel().showDetailedInformation(pmf);
		}
	}

	@Override
	List<Pmf> getCollection() {
		final List<Pmf> pmfs = new ArrayList<>(getOrganization().getPmfs());
		final Comparator<Pmf> comparator = (performanceFunction1, performanceFunction2) -> performanceFunction1.toString().compareTo(
				performanceFunction2.toString());
		Collections.sort(pmfs, comparator);
		return pmfs;
	}

}
