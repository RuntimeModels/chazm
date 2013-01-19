/*
 * PerformanceFunctionVisualizationPanel.java
 *
 * Created on May 26, 2010
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

import edu.ksu.cis.macr.organization.model.Organization;
import edu.ksu.cis.macr.organization.model.PerformanceFunction;

/**
 * The <code>PerformanceFunctionVisualizationPanel</code> class is a Swing
 * component to visualize the {@link PerformanceFunction} currently in the
 * {@link Organization}.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1.2.4 $, $Date: 2011/09/19 14:26:34 $
 * @since 6.0
 */
public class PerformanceFunctionVisualizationPanel extends
		AbstractListVisualizationPanel<PerformanceFunction> {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new instance of
	 * <code>PerformanceFunctionVisualizationPanel</code>.
	 * 
	 * @param organization
	 *            the <code>Organization</code> that is used to visualize the
	 *            <code>PerformanceFunction</code>.
	 */
	public PerformanceFunctionVisualizationPanel(final Organization organization) {
		super(organization, "Performance Functions",
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED,
				JSplitPane.VERTICAL_SPLIT, true,
				ListSelectionModel.SINGLE_SELECTION, JList.VERTICAL, 10);
	}

	@Override
	public void valueChanged(final ListSelectionEvent e) {
		final PerformanceFunction performanceFunction = getList()
				.getSelectedValue();
		if (performanceFunction != null) {
			getDetailedInformationPanel().showDetailedInformation(
					performanceFunction);
		}
	}

	@Override
	List<PerformanceFunction> getCollection() {
		final List<PerformanceFunction> performanceFunctions = new ArrayList<>(
				getOrganization().getPerformanceFunctions());
		final Comparator<PerformanceFunction> comparator = new Comparator<PerformanceFunction>() {

			@Override
			public int compare(final PerformanceFunction performanceFunction1,
					final PerformanceFunction performanceFunction2) {
				return performanceFunction1.toString().compareTo(
						performanceFunction2.toString());
			}

		};
		Collections.sort(performanceFunctions, comparator);
		return performanceFunctions;
	}

}
