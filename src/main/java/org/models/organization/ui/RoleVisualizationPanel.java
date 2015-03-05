/*
 * TreeModel.java
 *
 * Created on Sep 25, 2007
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

import org.models.organization.entity.Organization;
import org.models.organization.entity.Role;

/**
 * The <code>RoleVisualizationPanel</code> class is a Swing component to
 * visualize the <code>Role</code> currently in the <code>Organization</code>.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.2.4.4 $, $Date: 2011/09/19 14:26:34 $
 * @since 3.4
 */
public class RoleVisualizationPanel extends
		AbstractListVisualizationPanel<Role> {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new instance of <code>RoleVisualizationPanel</code>.
	 * 
	 * @param organization
	 *            the <code>Organization</code> that is used to visualize the
	 *            <code>Role</code>.
	 */
	public RoleVisualizationPanel(final Organization organization) {
		super(organization, "Roles",
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED,
				JSplitPane.VERTICAL_SPLIT, true,
				ListSelectionModel.SINGLE_SELECTION, JList.VERTICAL, 10);
	}

	@Override
	public void valueChanged(final ListSelectionEvent e) {
		final Role role = getList().getSelectedValue();
		if (role != null) {
			getDetailedInformationPanel().showDetailedInformation(role);
		}
	}

	@Override
	List<Role> getCollection() {
		final List<Role> roles = new ArrayList<>(getOrganization().getRoles());
		final Comparator<Role> comparator = new Comparator<Role>() {

			@Override
			public int compare(final Role role1, final Role role2) {
				return role1.toString().compareTo(role2.toString());
			}

		};
		Collections.sort(roles, comparator);
		return roles;
	}

}
