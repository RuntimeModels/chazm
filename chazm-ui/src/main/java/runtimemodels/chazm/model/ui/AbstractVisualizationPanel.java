/*
 * AbstractVisualizationPanel.java
 *
 * Created on May 26, 2010
 *
 * See License.txt file the license agreement.
 */
package runtimemodels.chazm.model.ui;

import runtimemodels.chazm.api.organization.Organization;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The <code>AbstractVisualizationPanel</code> provides the base framework <code>JPanel</code> for display information about the <code>Organization</code>.
 *
 * @author Christopher Zhong
 * @since 6.0
 */
public abstract class AbstractVisualizationPanel extends JPanel {

    /**
     * Default serial version ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The default time (in milliseconds) for polling the <code>Organization</code> for changes.
     */
    private static final long POLL_TIME = 1000;

    /**
     * The default dividing percentage for the <code>JSplitPane</code>.
     */
    private static final double DEFAULT_DIVIDER_LOCATION = 0.7;

    /**
     * The <code>Organization</code> that is used to visualize the <code>SpecificationGoal</code> and <code>InstanceGoal</code>.
     */
    private final Organization organization;

    /**
     * The <code>DetailedInformationPanel</code> to display additional information about the <code>Organization</code> information.
     */
    private final DetailedInformationPanel detailedInformationPanel = new DetailedInformationPanel();

    /**
     * An <code>AtomicBoolean</code> to indicate if the <code>AbstractListVisualizationPanel</code> is currently pulling information from the
     * <code>Organization</code>.
     */
    private final AtomicBoolean isPolling = new AtomicBoolean(false);

    /**
     * An <code>AtomicBoolean</code> to indicate if the <code>Thread</code> that is currently pulling information from the <code>Organization</code> still
     * running.
     */
    private final AtomicBoolean isThreadRunning = new AtomicBoolean(false);

    /**
     * The <code>JSplitPane</code>.
     */
    private JSplitPane splitPane;

    /**
     * A <code>String</code> describing the title.
     */
    private String description;

    /**
     * A <code>boolean</code> representing the first time the panel is being drawn.
     */
    private boolean first = true;

    /**
     * Constructs a new instance of <code>AbstractVisualizationPanel</code>.
     *
     * @param organization the <code>Organization</code> that is used to visualize information.
     */
    protected AbstractVisualizationPanel(final Organization organization) {
        this.organization = organization;
    }

    /**
     * Initialize the GUI.
     *
     * @param component                 the <code>Component</code> to add to the <code>JSplitPane</code>.
     * @param description               the <code>String</code> describing the title.
     * @param verticalScrollBarPolicy   the vertical scroll bar policy which must be one of <code>ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS</code>,
     *                                  <code>ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED</code>, or <code>ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER</code>.
     * @param horizontalScrollBarPolicy the horizontal scroll bar policy which be one of <code>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS</code>,
     *                                  <code>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED</code> , or <code>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER</code>.
     * @param orientation               the orientation of the <code>JSplitPane</code> which must be either <code>JSplitPane.HORIZONTAL_SPLIT</code> or
     *                                  <code>JSplitPane.VERTICAL_SPLIT</code>.
     * @param oneTouchExpandable        <code>true</code> to specify that the <code>JSplitPane</code> should provide a collapse / expand widget, <code>false</code> otherwise.
     */
    void initialize(final Component component, final String description, final int verticalScrollBarPolicy, final int horizontalScrollBarPolicy,
                    final int orientation, final boolean oneTouchExpandable) {
        this.description = description;

        final JScrollPane scrollPane = new JScrollPane(component, verticalScrollBarPolicy, horizontalScrollBarPolicy);

        splitPane = new JSplitPane(orientation, scrollPane, getDetailedInformationPanel());
        splitPane.setOneTouchExpandable(oneTouchExpandable);
        splitPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createRaisedBevelBorder()), description));

        setLayout(new BorderLayout());

        add(splitPane, BorderLayout.CENTER);
    }

    /**
     * Starts pulling information from the <code>Organization</code>. Subsequent calls to this method have no effect unless the {@linkplain #stopPolling()}
     * method is called first.
     */
    void startPolling() {
        if (isThreadRunning.compareAndSet(false, true)) {
            new Thread(() -> {
                isPolling.set(true);
                while (isPolling.get()) {
                    updateVisualizationPanel();
                    try {
                        Thread.sleep(POLL_TIME);
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isThreadRunning.set(false);
            }, String.format("Polling Thread - %s", description)).start();
        }
    }

    /**
     * Stops pulling information from the <code>Organization</code>. This method only sets the value of the {@linkplain #isPolling} to <code>false</code>.
     */
    void stopPolling() {
        isPolling.set(false);
    }

    /**
     * Returns the <code>Organization</code> that is providing the updated information.
     *
     * @return the <code>Organization</code>.
     */
    Organization getOrganization() {
        return organization;
    }

    /**
     * Returns the <code>DetailedInformationPanel</code> that is used to display additional information about the <code>Organization</code>.
     *
     * @return the <code>DetailedInformationPanel</code> that is used to display additional information about the <code>Organization</code>.
     */
    DetailedInformationPanel getDetailedInformationPanel() {
        return detailedInformationPanel;
    }

    /**
     * Updates the <code>GoalVisualizationPanel</code> with the latest information from the <code>Organization</code>.
     */
    abstract void updateVisualizationPanel();

    @Override
    public void paint(final Graphics g) {
        /*
         * if this is the first time, the panel is being drawn, set the divider location of the split pane
		 */
        if (first) {
            splitPane.setDividerLocation(DEFAULT_DIVIDER_LOCATION);
            first = false;
        }
        super.paint(g);
    }

}
