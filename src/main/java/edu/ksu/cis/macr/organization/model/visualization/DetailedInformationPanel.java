/*
 * DetailedInformationPanel.java
 * 
 * Created on Sep 26, 2007
 * 
 * See License.txt file the license agreement.
 */
package edu.ksu.cis.macr.organization.model.visualization;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import edu.ksu.cis.macr.organization.model.Agent;
import edu.ksu.cis.macr.organization.model.Assignment;
import edu.ksu.cis.macr.organization.model.Attribute;
import edu.ksu.cis.macr.organization.model.Capability;
import edu.ksu.cis.macr.organization.model.Characteristic;
import edu.ksu.cis.macr.organization.model.InstanceGoal;
import edu.ksu.cis.macr.organization.model.PerformanceFunction;
import edu.ksu.cis.macr.organization.model.Role;
import edu.ksu.cis.macr.organization.model.SpecificationGoal;
import edu.ksu.cis.macr.organization.model.Task;

/**
 * The <code>DetailedInformationPanel</code> class is a Swing component that is
 * used by the other visualization <code>JPanel</code> to present additional
 * information about the entities of the organization model.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.6.2.6 $, $Date: 2010/07/30 19:19:37 $
 * @since 3.4
 */
public class DetailedInformationPanel extends JPanel {

	/**
	 * Styles for displaying additional information.
	 * 
	 * @author Christopher Zhong
	 * @version $Revision: 1.6.2.6 $, $Date: 2010/07/30 19:19:37 $
	 * @since 4.0
	 */
	static enum Styles {

		/**
		 * The standard style that every other styles in <code>Styles</code> is
		 * based on.
		 */
		REGULAR {
			@Override
			void define(final Style style) {
			}
		},
		/**
		 * Style for main entity.
		 */
		ENTITY {
			@Override
			void define(final Style style) {
				StyleConstants.setFontSize(style, 14);
				StyleConstants.setBold(style, true);
				StyleConstants.setUnderline(style, true);
			}
		},
		/**
		 * Style for relations.
		 */
		RELATION {
			@Override
			void define(final Style style) {
				StyleConstants.setFontSize(style, 11);
				StyleConstants.setBold(style, true);
			}
		},
		/**
		 * Style for entities in relations.
		 */
		RELATION_ENTITY {
			@Override
			void define(final Style style) {
				StyleConstants.setItalic(style, true);
			}
		},
		/**
		 * Style for entities in relations.
		 */
		RELATION_ENTITY_BAD {
			@Override
			void define(final Style style) {
				StyleConstants.setItalic(style, true);
				StyleConstants.setStrikeThrough(style, true);
			}
		},
		/**
		 * Style for good scores in relations.
		 */
		RELATION_SCORE {
			@Override
			void define(final Style style) {
				StyleConstants.setBold(style, true);
				StyleConstants.setForeground(style, Color.BLUE);
			}
		},
		/**
		 * Style for bad scores in relations.
		 */
		RELATION_SCORE_BAD {
			@Override
			void define(final Style style) {
				StyleConstants.setBold(style, true);
				StyleConstants.setForeground(style, Color.RED);
			}
		};

		/**
		 * Sets the appropriate values for the given <code>Style</code>.
		 * 
		 * @param style
		 *            the <code>Style</code> to be set.
		 */
		abstract void define(Style style);
	}

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Line separator used by the current system.
	 */
	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	/**
	 * The <code>JTextPane</code> that will be used to display additional
	 * information about the entities of the organization model.
	 */
	private final JTextPane textPane = new JTextPane();

	/**
	 * The <code>StyledDocument</code> for displaying additional information
	 * about the entities of the organization model.
	 */
	private final StyledDocument doc;

	/**
	 * Constructs a new instance of <code>DetailedInformationPanel</code>.
	 */
	public DetailedInformationPanel() {
		this(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}

	/**
	 * Constructs a new instance of <code>DetailedInformationPanel</code>.
	 * 
	 * @param verticalScrollbarPolicy
	 *            the vertical scrollbar policy which must be one of
	 *            <code>ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED</code>,
	 *            <code>ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER</code>,
	 *            <code>ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS</code>.
	 * @param horizontalScrollbarPolicy
	 *            the horizontal scrollbar policy which must be one of
	 *            <code>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED</code>
	 *            , <code>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER</code>,
	 *            <code>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS</code>.
	 */
	private DetailedInformationPanel(final int verticalScrollbarPolicy,
			final int horizontalScrollbarPolicy) {
		textPane.setEditable(false);
		textPane.setText("Displays Additional Information About The Entities Of The Organization Model.");

		final JScrollPane scrollPane = new JScrollPane(textPane,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);

		doc = textPane.getStyledDocument();

		/* initialize some styles for the document */
		final Style defaultStyle = StyleContext.getDefaultStyleContext()
				.getStyle(StyleContext.DEFAULT_STYLE);

		final Style regular = doc.addStyle(Styles.REGULAR.toString(),
				defaultStyle);
		Styles.REGULAR.define(defaultStyle);

		Style s = doc.addStyle(Styles.ENTITY.toString(), regular);
		Styles.ENTITY.define(s);

		s = doc.addStyle(Styles.RELATION.toString(), regular);
		Styles.RELATION.define(s);

		s = doc.addStyle(Styles.RELATION_ENTITY.toString(), regular);
		Styles.RELATION_ENTITY.define(s);

		s = doc.addStyle(Styles.RELATION_ENTITY_BAD.toString(), regular);
		Styles.RELATION_ENTITY_BAD.define(s);

		s = doc.addStyle(Styles.RELATION_SCORE.toString(), regular);
		Styles.RELATION_SCORE.define(s);

		s = doc.addStyle(Styles.RELATION_SCORE_BAD.toString(), regular);
		Styles.RELATION_SCORE_BAD.define(s);
	}

	/**
	 * Display additional information about the <code>SpecificationGoal</code>.
	 * 
	 * @param specificationGoal
	 *            the <code>SpecificationGoal</code> to process.
	 */
	public void showDetailedInformation(
			final SpecificationGoal specificationGoal) {
		try {
			/* clears the contents of the document */
			doc.remove(0, doc.getLength());
			writeEntity(specificationGoal.getIdentifier().toString());
			writeRelation("Achieved By Role(s):");
			for (final Role role : specificationGoal.getAchievedBySet()) {
				writeRelationEntity(role.getIdentifier().toString());
			}
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display additional information about the <code>InstanceGoal</code>.
	 * 
	 * @param instanceGoal
	 *            the <code>InstanceGoal</code> to process.
	 */
	public void showDetailedInformation(final InstanceGoal<?> instanceGoal) {
		try {
			/* clears the contents of the document */
			doc.remove(0, doc.getLength());
			writeEntity(instanceGoal.getIdentifier().toString());
			writeEntityRelation("Specification Goal:");
			writeEntityEntity(instanceGoal.getSpecificationIdentifier()
					.toString());
			writeEntityRelation("Instance Identifier:");
			writeEntityEntity(instanceGoal.getInstanceIdentifier().toString());
			writeRelation("Parameter(s):");
			if (instanceGoal.getParameter() != null) {
				writeRelationEntity(instanceGoal.getParameter().toString());
			}
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display additional information about the <code>Role</code>.
	 * 
	 * @param role
	 *            the <code>Role</code> to process.
	 */
	public void showDetailedInformation(final Role role) {
		try {
			/* clears the contents of the document */
			doc.remove(0, doc.getLength());
			writeEntity(role.getIdentifier().toString());
			writeRelation("Achieves Goal(s):");
			for (final SpecificationGoal specificationGoal : role
					.getAchievesSet()) {
				writeRelationEntity(specificationGoal.getIdentifier()
						.toString());
			}
			writeRelation("Requires Capability(ies):");
			for (final Capability capability : role.getRequiresSet()) {
				writeRelationEntity(capability.getIdentifier().toString());
			}
			writeRelation("Influences Attribute(s):");
			for (final Attribute attribute : role.getNeedsSet()) {
				writeRelationEntity(attribute.getIdentifier().toString());
			}
			writeRelation("Uses Performance Function(s):");
			for (final PerformanceFunction performanceFunction : role
					.getUsesSet()) {
				writeRelationEntity(performanceFunction.getIdentifier()
						.toString());
			}
			writeRelation("Contains Characteristic(s):");
			for (final Characteristic characteristic : role.getContainsSet()) {
				writeRelationEntityValue(characteristic.getIdentifier()
						.toString(), role.getContainsValue(characteristic
						.getIdentifier()));
			}
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display additional information about the <code>Capability</code>.
	 * 
	 * @param capability
	 *            the <code>Capability</code> to process.
	 */
	public void showDetailedInformation(final Capability capability) {
		try {
			/* clears the contents of the document */
			doc.remove(0, doc.getLength());
			writeEntity(capability.getIdentifier().toString());
			writeRelation("Required By Role(s):");
			for (final Role role : capability.getRequiredBySet()) {
				writeRelationEntity(role.getIdentifier().toString());
			}
			writeRelation("Possessed By Agent(s):");
			for (final Agent<?> agent : capability.getPossessedBySet()) {
				writeRelationEntityScore(agent.getIdentifier().toString(),
						capability.getPossessedByScore(agent.getIdentifier()));
			}
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display additional information about the <code>Assignment</code>.
	 * 
	 * @param assignment
	 *            the <code>Assignment</code> to process.
	 */
	public void showDetailedInformation(final Assignment assignment) {
		try {
			/* clears the contents of the document */
			doc.remove(0, doc.getLength());
			writeEntity(assignment.getIdentifier().toString());
			writeEntityRelation("Agent:");
			writeEntityEntity(assignment.getAgent().getIdentifier().toString());
			writeEntityRelation("Playing Role:");
			writeEntityEntity(assignment.getRole().getIdentifier().toString());
			writeEntityRelation("Achieving Goal:");
			writeEntityEntity(assignment.getInstanceGoal().getIdentifier()
					.toString());
			writeEntityRelation("Parameters:");
			if (assignment.getInstanceGoal().getParameter() != null) {
				writeEntityEntity(assignment.getInstanceGoal().getParameter()
						.toString());
			}
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display additional information about the <code>Agent</code>.
	 * 
	 * @param agent
	 *            the <code>Agent</code> to process.
	 */
	public void showDetailedInformation(final Agent<?> agent) {
		try {
			/* clears the contents of the document */
			doc.remove(0, doc.getLength());
			writeEntity(agent.getIdentifier().toString());
			writeRelation("Possesses Capability(ies):");
			for (final Capability capability : agent.getPossessesSet()) {
				writeRelationEntityScore(capability.getIdentifier().toString(),
						agent.getPossessesScore(capability.getIdentifier()));
			}
			writeRelation("Has Attribute(s):");
			for (final Attribute attribute : agent.getHasSet()) {
				writeRelationEntityValue(attribute.getIdentifier().toString(),
						agent.getHasValue(attribute.getIdentifier()));
			}
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display additional information about the <code>Attribute</code>.
	 * 
	 * @param attribute
	 *            the <code>Attribute</code> to process.
	 */
	public void showDetailedInformation(final Attribute attribute) {
		try {
			/* clears the contents of the document */
			doc.remove(0, doc.getLength());
			writeEntity(attribute.getIdentifier().toString());
			writeRelation("Influenced By Role(s):");
			for (final Role role : attribute.getInfluencedBySet()) {
				writeRelationEntity(role.getIdentifier().toString());
			}
			writeRelation("Has By Agent(s):");
			for (final Agent<?> agent : attribute.getHadBySet()) {
				writeRelationEntityValue(agent.getIdentifier().toString(),
						attribute.getHadByValue(agent.getIdentifier()));
			}
			writeRelation("Moderated By Performance Function(s):");
			for (final PerformanceFunction performanceFunction : attribute
					.getModeratedBySet()) {
				writeRelationEntity(performanceFunction.getIdentifier()
						.toString());
			}
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display additional information about the <code>Task</code>
	 * 
	 * @param task
	 *            the <code>Task</code> to process.
	 */
	public void showDetailedInformation(final Task task) {
		try {
			/* clears the contents of the document */
			doc.remove(0, doc.getLength());
			writeEntity(task.getIdentifier().toString());
			writeRelation("Role:");
			writeRelationEntity(task.getRole().getIdentifier().toString());
			writeRelation("Goal:");
			writeRelationEntity(task.getSpecificationGoal().getIdentifier()
					.toString());
			writeRelation("Uses:");
			for (final PerformanceFunction performanceFunction : task
					.getLinkedSet()) {
				writeRelationEntity(performanceFunction.getIdentifier()
						.toString());
			}
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display additional information about the <code>PerformanceFunction</code>
	 * .
	 * 
	 * @param performanceFunction
	 *            the <code>PerformanceFunction</code> to process.
	 */
	public void showDetailedInformation(
			final PerformanceFunction performanceFunction) {
		try {
			/* clears the contents of the document */
			doc.remove(0, doc.getLength());
			writeEntity(performanceFunction.getIdentifier().toString());
			writeRelation("Moderates Attribute:");
			final Attribute attribute = performanceFunction.getModerates();
			writeRelationEntity(attribute.getIdentifier().toString());
			writeRelation("Linked By Role(s):");
			for (final Role role : performanceFunction.getUsedBySet()) {
				writeRelationEntity(role.getIdentifier().toString());
			}
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display additional information about the <code>Characteristic</code>.
	 * 
	 * @param characteristic
	 *            the <code>Characteristic</code> to process.
	 */
	public void showDetailedInformation(final Characteristic characteristic) {
		try {
			/* clears the contents of the document */
			doc.remove(0, doc.getLength());
			writeEntity(characteristic.getIdentifier().toString());
			writeRelation("Contained By Role(s):");
			for (final Role role : characteristic.getContainedBySet()) {
				writeRelationEntityValue(role.getIdentifier().toString(),
						role.getContainsValue(characteristic.getIdentifier()));
			}
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the given <code>String</code> with a predetermined
	 * <code>Style</code>.
	 * 
	 * @param entity
	 *            the <code>String</code> representing the entity to display.
	 */
	private void writeEntity(final String entity) {
		try {
			doc.insertString(doc.getLength(), entity,
					doc.getStyle(Styles.ENTITY.toString()));
			doc.insertString(doc.getLength(), LINE_SEPARATOR,
					doc.getStyle(Styles.REGULAR.toString()));
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the given <code>String</code> with a predetermined
	 * <code>Style</code>.
	 * 
	 * @param relation
	 *            the <code>String</code> representing the relation to display.
	 */
	private void writeRelation(final String relation) {
		try {
			doc.insertString(doc.getLength(), " + ",
					doc.getStyle(Styles.REGULAR.toString()));
			doc.insertString(doc.getLength(), relation,
					doc.getStyle(Styles.RELATION.toString()));
			doc.insertString(doc.getLength(), LINE_SEPARATOR,
					doc.getStyle(Styles.REGULAR.toString()));
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the given <code>String</code> and <code>double</code> value with
	 * a predetermined <code>Style</code>.
	 * 
	 * @param entity
	 *            the <code>String</code> representing the entity to display.
	 * @param score
	 *            the <code>double</code> value to display.
	 */
	private void writeRelationEntityScore(final String entity,
			final double score) {
		try {
			doc.insertString(doc.getLength(), "   + ",
					doc.getStyle(Styles.REGULAR.toString()));
			doc.insertString(doc.getLength(), entity, doc
					.getStyle(score > 0.0 ? Styles.RELATION_ENTITY.toString()
							: Styles.RELATION_ENTITY_BAD.toString()));
			doc.insertString(doc.getLength(), " (",
					doc.getStyle(Styles.REGULAR.toString()));
			doc.insertString(doc.getLength(), String.valueOf(score), doc
					.getStyle(score > 0.0 ? Styles.RELATION_SCORE.toString()
							: Styles.RELATION_SCORE_BAD.toString()));
			doc.insertString(doc.getLength(), ")",
					doc.getStyle(Styles.REGULAR.toString()));
			doc.insertString(doc.getLength(), LINE_SEPARATOR,
					doc.getStyle(Styles.REGULAR.toString()));
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the given <code>String</code> and <code>double</code> value with
	 * a predetermined <code>Style</code>.
	 * 
	 * @param entity
	 *            the <code>String</code> representing the entity to display.
	 * @param value
	 *            the <code>double</code> value to display.
	 */
	private void writeRelationEntityValue(final String entity,
			final double value) {
		try {
			doc.insertString(doc.getLength(), "   + ",
					doc.getStyle(Styles.REGULAR.toString()));
			doc.insertString(doc.getLength(), entity,
					doc.getStyle(Styles.RELATION_ENTITY.toString()));
			doc.insertString(doc.getLength(), " (",
					doc.getStyle(Styles.REGULAR.toString()));
			doc.insertString(doc.getLength(), String.valueOf(value),
					doc.getStyle(Styles.RELATION_SCORE.toString()));
			doc.insertString(doc.getLength(), ")",
					doc.getStyle(Styles.REGULAR.toString()));
			doc.insertString(doc.getLength(), LINE_SEPARATOR,
					doc.getStyle(Styles.REGULAR.toString()));
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the given <code>String</code> with a predetermined
	 * <code>Style</code>.
	 * 
	 * @param entity
	 *            the <code>String</code> representing the entity to display.
	 */
	private void writeRelationEntity(final String entity) {
		try {
			doc.insertString(doc.getLength(), "   + ",
					doc.getStyle(Styles.REGULAR.toString()));
			doc.insertString(doc.getLength(), entity,
					doc.getStyle(Styles.RELATION_ENTITY.toString()));
			doc.insertString(doc.getLength(), LINE_SEPARATOR,
					doc.getStyle(Styles.REGULAR.toString()));
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the given <code>String</code> with a predetermined
	 * <code>Style</code>.
	 * 
	 * @param relation
	 *            the <code>String</code> representing the relation to display.
	 */
	private void writeEntityRelation(final String relation) {
		try {
			doc.insertString(doc.getLength(), " ",
					doc.getStyle(Styles.REGULAR.toString()));
			doc.insertString(doc.getLength(), relation,
					doc.getStyle(Styles.RELATION.toString()));
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the given <code>String</code> with a predetermined
	 * <code>Style</code>.
	 * 
	 * @param entity
	 */
	private void writeEntityEntity(final String entity) {
		try {
			doc.insertString(doc.getLength(), " ",
					doc.getStyle(Styles.REGULAR.toString()));
			doc.insertString(doc.getLength(), entity,
					doc.getStyle(Styles.RELATION.toString()));
			doc.insertString(doc.getLength(), LINE_SEPARATOR,
					doc.getStyle(Styles.REGULAR.toString()));
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

}
