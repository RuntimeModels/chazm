/*
 * CharacteristicVisualizationPanel.java
 *
 * Created on May 26, 2010
 *
 * See License.txt file the license agreement.
 */
package runtimemodels.chazm.model.ui;

import runtimemodels.chazm.api.entity.Characteristic;
import runtimemodels.chazm.api.organization.Organization;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
     * @param organization the <code>Organization</code> that is used to visualize the <code>Characteristic</code>.
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
//        final List<Characteristic> characteristics = new ArrayList<>(getOrganization().getCharacteristics());
        final List<Characteristic> characteristics = new ArrayList<>();
        final Comparator<Characteristic> comparator = Comparator.comparing(Object::toString);
        characteristics.sort(comparator);
        return characteristics;
    }

}
