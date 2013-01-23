/*
 * DefaultVisualizationFrame.java
 *
 * Created on Oct 15, 2007
 *
 * See License.txt file the license agreement.
 */
package edu.ksu.cis.macr.organization.model.visualization;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import model.organization.entity.Organization;

/**
 * The <code>DefaultVisualizationFrame</code> provides a primitive
 * <code>JFrame</code> for visualizing the <code>Organization</code>.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.3.4.6 $, $Date: 2012/01/10 23:51:14 $
 * @since 3.4
 */
public class DefaultVisualizationFrame extends JFrame {

	static {
		final LookAndFeelInfo[] installedLookAndFeels = UIManager
				.getInstalledLookAndFeels();
		try {
			for (final LookAndFeelInfo info : installedLookAndFeels) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			/* should not occur */
			e.printStackTrace();
		} catch (final UnsupportedLookAndFeelException e) {
			/* use default look and feel */
		}
	}

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The default preferred size of the frame.
	 */
	private static final Dimension DEFAULT_PREFERRED_SIZE = new Dimension(900,
			600);

	/**
	 * Constructs a new instance of <code>DefaultVisualizationFrame</code>.
	 * 
	 * @param organization
	 *            the <code>Organization</code> that is used for visualization.
	 */
	public DefaultVisualizationFrame(final Organization organization) {
		super("Organization View");

		setLayout(new BorderLayout());

		final Container container = getContentPane();

		final JPanel topPanel = new JPanel(new GridLayout(1, 3));
		topPanel.add(new GoalVisualizationPanel(organization));
		topPanel.add(new RoleVisualizationPanel(organization));
		topPanel.add(new CapabilityVisualizationPanel(organization));

		final JPanel centerPanel = new JPanel(new GridLayout(1, 3));
		centerPanel.add(new CharacteristicVisualizationPanel(organization));
		centerPanel.add(new AttributeVisualizationPanel(organization));
		centerPanel
				.add(new PerformanceFunctionVisualizationPanel(organization));

		final JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
		bottomPanel.add(new TaskVisualizationPanel(organization));
		bottomPanel.add(new AssignmentVisualizationPanel(organization));
		bottomPanel.add(new AgentVisualizationPanel(organization));

		final JPanel overallPanel = new JPanel(new GridLayout(3, 1));
		overallPanel.add(topPanel);
		overallPanel.add(centerPanel);
		overallPanel.add(bottomPanel);
		container.add(overallPanel, BorderLayout.CENTER);

		container.setPreferredSize(DEFAULT_PREFERRED_SIZE);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		pack();
	}

}
