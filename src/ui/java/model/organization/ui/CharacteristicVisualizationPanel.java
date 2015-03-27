/*
 * CharacteristicVisualizationPanel.java
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

import model.organization.Organization;
import model.organization.entity.Characteristic;

/**
 * The <code>CharacteristicVisualizationPanel</code> class is a Swing component to visualize the {@linkplain Characteristic} currently in the
 * {@linkplain Organization} .
 *
 * @author Christopher Zhong
 * @since 6.0
 */
public class CharacteristicVisualizationPanel extends AbstractListVisualizationPanel<Characteristic> {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new instance of <code>CharacteristicVisualizationPanel</code>.
	 *
	 * @param organization
	 *            the <code>Organization</code> that is used to visualize the <code>Characteristic</code>.
	 */
	public CharacteristicVisualizationPanel(final Organization organization) {
		super(organization, "Characteristics", ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED,
				JSplitPane.VERTICAL_SPLIT, true, ListSelectionModel.SINGLE_SELECTION, JList.VERTICAL, 10);
	}

	@Override
	public void valueChanged(final ListSelectionEvent e) {
		final Characteristic characteristic = getList().getSelectedValue();
		if (characteristic != null) {
			getDetailedInformationPanel().showDetailedInformation(characteristic);
		}
	}

	@Override
	List<Characteristic> getCollection() {
		final List<Characteristic> characteristics = new ArrayList<>(getOrganization().getCharacteristics());
		final Comparator<Characteristic> comparator = (characteristic1, characteristic2) -> characteristic1.toString().compareTo(characteristic2.toString());
		Collections.sort(characteristics, comparator);
		return characteristics;
	}

}
