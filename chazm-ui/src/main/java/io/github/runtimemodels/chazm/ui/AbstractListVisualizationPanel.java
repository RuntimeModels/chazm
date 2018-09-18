/*
 * AbstractListVisualizationPanel.java
 *
 * Created on Oct 15, 2007
 *
 * See License.txt file the license agreement.
 */
package io.github.runtimemodels.chazm.ui;

import runtimemodels.chazm.api.Organization;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The <code>AbstractListVisualizationPanel</code> provides list type <code>JPanel</code> for display information about the <code>Organization</code>.
 *
 * @param <T> the type for the list.
 * @author Christopher Zhong
 * @since 3.4
 */
public abstract class AbstractListVisualizationPanel<T> extends AbstractVisualizationPanel implements ListSelectionListener {

    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The <code>DefaultListModel</code> that will be used by the <code>JList</code> to display the <code>Organization</code> information.
     */
    private final DefaultListModel<T> listModel = new DefaultListModel<>();

    /**
     * The <code>JList</code> used to display the <code>Organization</code> information.
     */
    private final JList<T> list = new JList<>(listModel);

    /**
     * Constructs a new instance of <code>AbstractListVisualizationPanel</code>.
     *
     * @param organization              the <code>Organization</code> that is used to visualize information.
     * @param description               the <code>String</code> describing the title.
     * @param verticalScrollBarPolicy   the vertical scroll bar policy which must be one of <code>ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS</code>,
     *                                  <code>ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED</code>, or <code>ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER</code>.
     * @param horizontalScrollBarPolicy the horizontal scroll bar policy which be one of <code>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS</code>,
     *                                  <code>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED</code> , or <code>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER</code>.
     * @param orientation               the orientation of the <code>JSplitPane</code> which must be either <code>JSplitPane.HORIZONTAL_SPLIT</code> or
     *                                  <code>JSplitPane.VERTICAL_SPLIT</code>.
     * @param oneTouchExpandable        <code>true</code> to specify that the <code>JSplitPane</code> should provide a collapse / expand widget, <code>false</code> otherwise.
     * @param selectionMode             the selection mode of the <code>JList</code> which must be one of <code>ListSelectionModel.SINGLE_SELECTION</code>,
     *                                  <code>ListSelectionModel.SINGLE_INTERVAL_SELECTION</code> or <code>ListSelectionModel.MULTIPLE_INTERVAL_SELECTION</code>.
     * @param layoutOrientation         the orientation of the <code>JList</code> which must be one of <code>JList.VERTICAL</code>, <code>JList.VERTICAL_WRAP</code>, or
     *                                  <code>JList.HORIZONTAL_WRAP</code>.
     * @param visibleRowCount           sets the visible row count property of the <code>JList</code>.
     */
    protected AbstractListVisualizationPanel(final Organization organization, final String description, final int verticalScrollBarPolicy,
                                             final int horizontalScrollBarPolicy, final int orientation, final boolean oneTouchExpandable, final int selectionMode, final int layoutOrientation,
                                             final int visibleRowCount) {
        super(organization);

        list.setSelectionMode(selectionMode);
        list.setLayoutOrientation(layoutOrientation);
        list.setVisibleRowCount(visibleRowCount);
        list.getSelectionModel().addListSelectionListener(this);

        initialize(list, description, verticalScrollBarPolicy, horizontalScrollBarPolicy, orientation, oneTouchExpandable);

        startPolling();
    }

    @Override
    void updateVisualizationPanel() {
        final List<T> updatedList = getCollection();
        final List<T> toRemove = new ArrayList<>();
        for (int i = 0; i < listModel.size(); i++) {
            final T o = listModel.get(i);
            if (!updatedList.contains(o)) {
                toRemove.add(o);
            }
        }
        SwingUtilities.invokeLater(() -> {
            /*
             * first, remove all elements from the list model that are not present in the updated list
             */
            toRemove.forEach(listModel::removeElement);
            /*
             * next, add the new elements from the updated list to the list model
             */
            for (final T o2 : updatedList) {
                final int index = listModel.indexOf(o2);
                if (index == -1) {
                    listModel.addElement(o2);
                } else {
                    listModel.set(index, o2);
                }
            }
        });
    }

    /**
     * Returns the <code>JList</code> that it is using to display information about the <code>Organization</code>.
     *
     * @return the <code>JList</code> that it is using to display information about the <code>Organization</code>.
     */
    JList<T> getList() {
        return list;
    }

    /**
     * Returns a <code>Collection</code> of <code>Object</code>.
     *
     * @return a <code>Collection</code> of <code>Object</code>.
     */
    abstract List<T> getCollection();

}
